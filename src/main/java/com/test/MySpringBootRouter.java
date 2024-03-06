package com.test;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:readMongo")
                .routeId("readMongo")
                .toD("mongodb://camelMongoClient?database=test&collection=test&operation=findOneByQuery")
                .to("log:test?showAll=true");
    }

}
