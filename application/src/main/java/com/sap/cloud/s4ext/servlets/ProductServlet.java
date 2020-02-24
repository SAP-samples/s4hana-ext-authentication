package com.sap.cloud.s4ext.servlets;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.sap.cloud.s4ext.config.TemplateEngineUtil;
import com.sap.cloud.s4ext.services.ODataRuntimeException;
import com.sap.cloud.s4ext.services.ProductService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.productmaster.Product;

@WebServlet("/")
public class ProductServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5902901261815623650L;

	private static final Logger log = LoggerFactory.getLogger(ProductServlet.class);

	private static final String PRODUCTS_HTML = "products.html";
	private static final String ERRORPAGE_HTML = "errorpage.html";

	private static final String APPLICATION_PROPERTIES = "application.properties";
	private static final String S4CLD_PRODUCTGROUP = "s4cld.productgroup";

	private static final String PRODUCTS = "products";
	private static final String COUNT = "count";

	@Inject
	private ProductService productService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		WebContext context = new WebContext(request, response, request.getServletContext());
		
		try {
			String productGroup = loadProductGroupFromAppConfig();

			final List<Product> products = productService.findByProductGroup(productGroup);
			
			context.setVariable(COUNT, products.size());
			context.setVariable(PRODUCTS, products);

			engine.process(PRODUCTS_HTML, context, response.getWriter());
		} catch (ConfigurationException e) {
			log.error("Exception occured while loading the properties file.", e);
			engine.process(ERRORPAGE_HTML, context, response.getWriter());
		} catch (ODataRuntimeException e) {
			log.info("Exception occured while fetching the products", e);
			engine.process(ERRORPAGE_HTML, context, response.getWriter());
		}
	}

	private String loadProductGroupFromAppConfig() throws ConfigurationException {
		PropertiesConfiguration config = new PropertiesConfiguration();
		
		config.load(APPLICATION_PROPERTIES);
		
		return config.getString(S4CLD_PRODUCTGROUP);
	}

}
