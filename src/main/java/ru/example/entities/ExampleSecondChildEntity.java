package ru.example.entities;

import javax.persistence.*;

@Entity
public class ExampleSecondChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExampleEntity parent;

    private int value;

    public ExampleSecondChildEntity(int value) {
        this.value = value;
    }

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
