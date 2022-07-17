package iota;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;

@RegisterRestClient(configKey="catFacts-api")
public interface CatFactsService {

    @GET
    CatFactsDO getFacts();
    
}
