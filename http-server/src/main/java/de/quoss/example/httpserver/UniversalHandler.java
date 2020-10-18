package de.quoss.example.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class UniversalHandler implements HttpHandler {

	public void handle(final HttpExchange exchange) {
		System.out.format("[exchange=%s]", exchange);
	}

}
