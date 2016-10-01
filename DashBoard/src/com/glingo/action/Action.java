package com.glingo.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class Action extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = Logger.getLogger(Action.class);
	
	public Action() {
		super();
		ActionBusiness.register(this);
	}

	public void dispatch(Object path){
		LOG.info(Object.class.getMethods());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
}
