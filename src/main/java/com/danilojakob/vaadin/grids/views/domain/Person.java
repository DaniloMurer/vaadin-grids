package com.danilojakob.vaadin.grids.views.domain;

public class Person {

    private String name;
    private String surName;
    private int age;
    private String eMail;

    public Person(String name, String surName, int age, String eMail) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.eMail = eMail;
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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
