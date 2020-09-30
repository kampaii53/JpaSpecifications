package ru.example.entities;

import javax.persistence.*;

@Entity
public class ExampleSubChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExampleChildEntity parent;

    private int value;

    public ExampleSubChildEntity(int value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExampleChildEntity getParent() {
        return parent;
    }

    public void setParent(ExampleChildEntity parent) {
        this.parent = parent;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
