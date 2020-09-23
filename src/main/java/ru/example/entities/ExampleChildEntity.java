package ru.example.entities;

import javax.persistence.*;

@Entity
public class ExampleChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExampleEntity parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExampleEntity getParent() {
        return parent;
    }

    public void setParent(ExampleEntity parent) {
        this.parent = parent;
    }
}
