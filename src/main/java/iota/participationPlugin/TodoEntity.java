package iota.participationPlugin;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TodoEntity extends PanacheEntity {
    public String text;
}
