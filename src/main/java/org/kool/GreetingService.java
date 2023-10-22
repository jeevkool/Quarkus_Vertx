package org.kool;

import io.quarkus.vertx.ConsumeEvent;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped                          
public class GreetingService {

    @ConsumeEvent("greetings")              
    public String hello(String name) {      
        return "Hello GOOD MORNING " + name;             
    }
}
