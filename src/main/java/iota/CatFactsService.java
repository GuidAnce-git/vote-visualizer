package iota;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@RegisterRestClient(configKey="catFacts-api")
@Singleton
public interface CatFactsService {

    @GET
    @Produces("application/json")
    CatFactsDO getFacts();
    
}
