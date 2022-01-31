package com.danilojakob.vaadin.grids.domain;

public class Person {

    private String name;
    private String surName;
    private int age;
    private String email;
    private String managerName;
    private boolean isManager;

    public Person(String name, String surName, int age, String email, String managerName, boolean isManager) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.email = email;
        this.managerName = managerName;
        this.isManager = isManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean manager) {
        isManager = manager;
    }
}
