package com.tymchak;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;

import java.io.IOException;
import java.util.Date;

/**
 * Simple AtmosphereHandler that implement the logic to build a Server Side
 * Events Chat application.
 * 
 * @author Jeanfrancois Arcand
 */
@AtmosphereHandlerService(path = "/chat", interceptors = { AtmosphereResourceLifecycleInterceptor.class })
public class SSEAtmosphereHandler implements AtmosphereHandler {

	@Override
	public void onRequest(AtmosphereResource r) throws IOException {

		AtmosphereRequest req = r.getRequest();
		if (req.getMethod().equalsIgnoreCase("POST")) {
			r.getBroadcaster().broadcast(req.getReader().readLine().trim());
		}
	}

	@Override
	public void onStateChange(AtmosphereResourceEvent event) throws IOException {
		AtmosphereResource r = event.getResource();
		AtmosphereResponse res = r.getResponse();

		if (event.isSuspended()) {
			String body = event.getMessage().toString();

			// Simple JSON -- Use Jackson for more complex structure
			// Message looks like { "author" : "foo", "message" : "bar" }
			String author = body.substring(body.indexOf(":") + 2,
					body.indexOf(",") - 1);
			String message = body.substring(body.lastIndexOf(":") + 2,
					body.length() - 2);

			res.getWriter().write(new Data(author, message).toString());
		} else if (!event.isResuming()) {
			event.broadcaster().broadcast(
					new Data("Someone", "say bye bye!").toString());
		}
	}

	@Override
	public void destroy() {
	}

	private final static class Data {

		private final String text;
		private final String author;

		public Data(String author, String text) {
			this.author = author;
			this.text = text;
		}

		public String toString() {
			return "{ \"text\" : \"" + text + "\", \"author\" : \"" + author
					+ "\" , \"time\" : " + new Date().getTime() + "}";
		}
	}
}
