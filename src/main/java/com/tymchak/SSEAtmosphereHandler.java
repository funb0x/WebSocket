package com.tymchak;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;

import java.io.IOException;
import java.util.Date;

@AtmosphereHandlerService(path = "/chat", interceptors = { AtmosphereResourceLifecycleInterceptor.class })
public class SSEAtmosphereHandler implements AtmosphereHandler {

	@Override
	public void onRequest(AtmosphereResource r) throws IOException {

		System.out.println("someone connected");

		// AtmosphereResponse res = r.getResponse();
		//
		// System.out.println("on req r = " + r.toString());
		// System.out.println("on req res = " + res.toString());
		//
		// Broadcaster b = r.getBroadcaster();
		//
		// AtmosphereRequest req = r.getRequest();
		// if (req.getMethod().equalsIgnoreCase("POST")) {
		// r.getBroadcaster().broadcast(req.getReader().readLine().trim());
		// }
	}

	@Override
	public void onStateChange(AtmosphereResourceEvent event) throws IOException {
		AtmosphereResource r = event.getResource();
		AtmosphereResponse res = r.getResponse();

		if (event.isSuspended()) {
			String body = event.getMessage().toString();
			System.out.println("body = " + body);
			System.out.println("{ \"a\" : \"aaaa\", \"b\" : \"bbb\", \"c\" : \"ccc\" }");
			// Simple JSON -- Use Jackson for more complex structure
			// Message looks like { "a" : "aaaa", "b" : "bbb", "c" : "ccc" }
			
			res.getWriter().write("{ \"a\" : \"aaaa\", \"b\" : \"bbb\", \"c\" : \"ccc\" }");
		}
	}

	@Override
	public void destroy() {
		System.out.println("sedtroyed!!!!!!!!!!!!!!!!!");
	}

}
