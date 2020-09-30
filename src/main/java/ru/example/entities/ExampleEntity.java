package ru.example.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<ExampleChildEntity> children;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<ExampleSecondChildEntity> children2;

    public ExampleEntity() {
    }

    public ExampleEntity(int value, List<ExampleChildEntity> children) {
        this.value = value;
        this.children = children;
        if(children!=null) {
            children.forEach(exampleChildEntity -> exampleChildEntity.setParent(this));
        }
    }

    public ExampleEntity(int value, List<ExampleChildEntity> children, List<ExampleSecondChildEntity> children2) {
        this(value,children);
        this.children2 = children2;
        if(this.children2!=null) {
            this.children2.forEach(child -> child.setParent(this));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<ExampleChildEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ExampleChildEntity> children) {
        this.children = children;
    }

    public List<ExampleSecondChildEntity> getChildren2() {
        return children2;
    }

    public void setChildren2(List<ExampleSecondChildEntity> children2) {
        this.children2 = children2;
    }
}
