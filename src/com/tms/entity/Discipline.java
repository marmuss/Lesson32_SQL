package com.tms.entity;

import java.util.List;
import java.util.Objects;

public class Discipline {
    private long id;
    private String name;
    private List<Teacher> teachers;

    public Discipline(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Discipline(long id, String name, List<Teacher> teachers) {
        this.id = id;
        this.name = name;
        this.teachers = teachers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(teachers, that.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teachers);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
