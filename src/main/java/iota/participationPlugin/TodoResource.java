package iota.participationPlugin;

import io.quarkus.hibernate.reactive.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "/todo")
public interface TodoResource extends PanacheEntityResource<TodoEntity, Long> {
}
