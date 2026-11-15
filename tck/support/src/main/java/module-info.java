/// Module for providing integration with the CDI TCK suite.
module eu.jbeernink.hypospray.tck.support {
	requires eu.jbeernink.hypospray.lite;
	requires eu.jbeernink.hypospray.core;
	requires eu.jbeernink.hypospray.scope.request;
	requires cdi.tck.api;
	requires jakarta.el;

	exports eu.jbeernink.hypospray.tck.support;
}