package com.five.web.template.server;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;

import com.five.web.template.client.GreetingService;
import com.five.web.template.shared.FieldVerifier;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {

		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}else{			
		    PersistenceManager pm = PMF.get().getPersistenceManager();
		    WebUser user = new WebUser(input, "Smith", new Date()); 
		    Query query = null;
		    try {
		    	pm.makePersistent(user);
	
			    query = pm.newQuery(WebUser.class);
			    query.setFilter("firstName == firstNameParam");
			    query.setOrdering("enrollDate desc");
			    query.declareParameters("String firstNameParam");
	
		        List<WebUser> results = (List<WebUser>) query.execute("Mike");
		        if (!results.isEmpty()) {
		            for (WebUser u : results) {
		                System.out.println("FirstName: " + u.getFirstName() + " LastName: " + u.getLastName() + " EnrollDate: " + u.getEnrollDate());
		            }
		        } else {
		            // ... no results ...
		        }
		    } finally {
		        query.closeAll();
		        pm.close();
		    }	    
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
