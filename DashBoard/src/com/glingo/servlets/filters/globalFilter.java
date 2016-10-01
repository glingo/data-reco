package com.glingo.servlets.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class globalFilter implements Filter {

	private static Logger LOG = null;
	
	private void beforeProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request ;
		String host = httpRequest.getRemoteHost();
		String url = httpRequest.getRequestURL().toString();
		System.err.println("L'hôte [" + host +  "] fait une requête sur [" + url +  "]");
		// construction d'un request dispatcher sur la page JSP, qui doit exister
		// dans la web application courante
		//      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/monInclusion.jsp") ;
		// inclusion de cette ressource
		//      requestDispatcher.include(request, response) ;
		// la traitement de la requête courante continue
   }
   
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		 LOG.info("doFilter");
		 beforeProcessing(request, response) ;
		 filterChain.doFilter(request, response) ;
		 destroy();
   }

	@Override
	public void destroy() {
		LOG.info("destroy");
		LOG.info(LOG);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOG = Logger.getLogger(globalFilter.class);
		LOG.info(arg0);
		LOG.info("init");
	}
}
