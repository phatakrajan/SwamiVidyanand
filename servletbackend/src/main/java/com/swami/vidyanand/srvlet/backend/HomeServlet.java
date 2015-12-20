/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.swami.vidyanand.srvlet.backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet{

static final String ATTRIBUTE_STATUS = "status";

        /**
         * Displays the existing messages and offer the option to send a new one.
         */
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();

            out.print("<html><body>");
            out.print("<head>");
            out.print("  <title>Cure Me Devices</title>");
/*	    out.print("  <link rel='icon' href='/store_logo.ico'/>");
	    out.print("</head>");
*/	    String status = (String) req.getAttribute(ATTRIBUTE_STATUS);
            if (status != null) {
                //out.print(status);
            }
            int total = Datastore.getTotalDevices();
            if (total == 0) {
                out.print("<h2>No devices registered!</h2>");
            } else {
	      /*out.print("<h2>" + total + " device(s) registered!</h2>");
	      out.print("<form name='form' method='POST' action='sendAll'>");
	      out.print("<fieldset>");
	      out.print("<textarea rows='10' cols='100' name='tipContent'>Enter HTML Message </textarea>");
	      out.print("</fieldset>");
	      out.print("<input type='submit' value='Send Message' />");
	      out.print("</form>");*/
                try {
                    getServletContext().getRequestDispatcher("/sendAll").forward(req, resp);

                } catch (ServletException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            out.print("</body></html>");
            resp.setStatus(HttpServletResponse.SC_OK);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
            doGet(req, resp);
        }

}
