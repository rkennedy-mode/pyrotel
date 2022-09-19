package com.mode.ryankennedy.pyrotel.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/greetings/{name}")
@Produces(MediaType.TEXT_PLAIN)
public class GreetingsResource {
    @GET
    public String getGreeting(@PathParam("name") String name) {
        return String.format("Hello, %s", name);
    }
}
