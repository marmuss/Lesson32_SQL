package com.tms.entity;

import java.util.List;

public class Teacher {
    private long id;
    private String name;
    private String username;
    private String password;
    private List<Discipline> disciplines;

    public Teacher(long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Teacher(long id, String name, String username, String password, List<Discipline> disciplines) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.disciplines = disciplines;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", disciplines=" + disciplines +
                '}';
    }
}
