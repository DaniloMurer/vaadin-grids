package com.danilojakob.vaadin.grids.domain;

public class Manager {
    private String lastName;
    private String firstName;
    private int age;
    private String email;

    public Manager(String lastName, String firstName, int age, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
}
