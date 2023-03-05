package com.micro.webservice.apigateway.config;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		Function<PredicateSpec, Buildable<Route>> routeFunction = p->p
				.path("/get")
				.filters(f->f.addRequestHeader("MyHeader", "MyUri").addRequestParameter("Param", "someVal"))
				.uri("http://httpbin.org:80");
		return builder.routes()
				.route(routeFunction)
				.route(p->p.path("/currency-exchange/**").uri("lb://currency-exchange/"))//this means that it will route all request to currency-exchange to eureka and load balance to that specific names running service
				.route(p->p.path("/currency-conversion/**").uri("lb://currency-conversion/"))
				.route(p->p.path("/currency-conversion-feign/**").uri("lb://currency-conversion/"))
				.route(p->p.path("/spotify-sim").uri("lb://spotify-sim/api/login")) //recheck if this is working
				.route(p->p.path("/currency-conversion-new/**")
						.filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)", "/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion/"))
				.build();
	}
}
