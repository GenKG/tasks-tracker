package ru.task.tracker.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class DictStatus {
    @Id
    @SequenceGenerator(name = "dict_status_id_seq",sequenceName = "dict_status_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "dict_status_id_seq")
    private Long id;
    private String name;

    public DictStatus(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DictStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictStatus that = (DictStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
