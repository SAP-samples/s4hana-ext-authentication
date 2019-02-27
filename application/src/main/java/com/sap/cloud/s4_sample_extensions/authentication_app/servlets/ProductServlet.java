package com.sap.cloud.s4_sample_extensions.authentication_app.servlets;

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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.sap.cloud.s4_sample_extensions.authentication_app.config.TemplateEngineUtil;
import com.sap.cloud.s4_sample_extensions.authentication_app.services.ProductService;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.productmaster.Product;

@WebServlet("/")
public class ProductServlet extends HttpServlet {

	private static final String ERRORPAGE_HTML = "errorpage.html";

	private static final String PRODUCTS_HTML = "products.html";

	private static final String APPLICATION_PROPERTIES = "application.properties";

	private static final String PRODUCTS = "products";

	private static final String COUNT = "count";

	private static final String S4CLD_PRODUCTGROUP = "s4cld.productgroup";

	private static final long serialVersionUID = 3735198720506536939L;

	private static final Logger LOGGER = CloudLoggerFactory.getLogger(ProductServlet.class);

	@Inject
	ProductService productService;

	List<Product> prodlist;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PropertiesConfiguration config = new PropertiesConfiguration();
		
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		
		WebContext context = new WebContext(request, response, request.getServletContext());
		
		try {

			config.load(APPLICATION_PROPERTIES);

			String productGroup = config.getString(S4CLD_PRODUCTGROUP);

			prodlist = productService.findByProductGroup(productGroup);

			context.setVariable(COUNT, prodlist.size());

			context.setVariable(PRODUCTS, prodlist);

			engine.process(PRODUCTS_HTML, context, response.getWriter());

		} catch (ConfigurationException e) {
			
			LOGGER.info("Exception occured while loading the properties file.."+ e);
			
			engine.process(ERRORPAGE_HTML, context, response.getWriter());

		} catch (Exception e) {
			
			LOGGER.info("Exception occured while fetching the products and the exception is :"+ e);
			
			engine.process(ERRORPAGE_HTML, context, response.getWriter());

		}
	}

}
