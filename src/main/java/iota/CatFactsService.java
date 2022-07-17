package iota;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.GET;

@RegisterRestClient(configKey="catFacts-api")
@Singleton
public interface CatFactsService {

    @GET
    CatFactsDO getFacts();
    
}
