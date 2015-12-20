package com.swami.vidyanand.srvlet.backend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RegisterServlet extends BaseServlet {

	  private static final String PARAMETER_REG_ID = "regId";

	  @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException {
	    String regId = getParameter(req, PARAMETER_REG_ID);
	    Datastore.register(regId);
	    setSuccess(resp);
	  }

}
