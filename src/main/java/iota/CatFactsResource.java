package iota;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello2")
public class CatFactsResource {
    @Inject
    @RestClient
    CatFactsService catFactsService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getFacts() {
        return catFactsService.getFacts().getFact();
    }
}
