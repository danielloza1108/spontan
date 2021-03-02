package com.example.spontan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Category implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String uuid = UUID.randomUUID().toString();

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Category &&
                Objects.equals(uuid,((Category) o).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
