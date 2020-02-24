package com.sap.cloud.s4ext.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Thymeleaf configuration
 */
@WebListener
public class ThymeleafConfig implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
		TemplateEngineUtil.storeTemplateEngine(sce.getServletContext(), templateEngine(sce.getServletContext()));
    }

    public void contextDestroyed(ServletContextEvent sce) {
    	// nothing to do here
    }

    private TemplateEngine templateEngine(ServletContext servletContext) {
        final TemplateEngine engine = new TemplateEngine();

        engine.setTemplateResolver(templateResolver(servletContext));
        
        return engine;
    }

    private ITemplateResolver templateResolver(ServletContext servletContext) {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
        
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        
        return resolver;
    }

}
