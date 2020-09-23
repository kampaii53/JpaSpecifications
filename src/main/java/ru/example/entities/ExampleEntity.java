package ru.example.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String field1;

    private String field2;

    private int field3;

    @OneToMany(mappedBy = "parent")
    private List<ExampleChildEntity> children;

    public ExampleEntity() {
    }

    public ExampleEntity(String field1, String field2, int field3, List<ExampleChildEntity> children) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(int field3) {
        this.field3 = field3;
    }
}
