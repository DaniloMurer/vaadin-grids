package com.danilojakob.vaadin.grids.service;

import com.danilojakob.vaadin.grids.domain.Manager;
import com.danilojakob.vaadin.grids.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonService {
    private List<Person> people;
    private List<Person> managers;

    public PersonService() {
        people = List.of(new Person("Jakob", "Danilo", 20, "danilo.jakob@gmx.ch", "Torvalds", false),
                new Person("Mustermann", "Max", 30, "max.mustermann@gmail.com", "Torvalds", false),
                new Person("Musterfrau", "Mia", 40, "mia.musterfrau@yahoo.com", "Jobs", false),
                new Person("Mustermann", "Maja", 50, "maja.mustermann@outlook.com", "Gates", false));

        managers = List.of(new Person("Torvalds", "Linus", 60, "linus.torvalds@linux-foundation.com", "Linus", true),
                new Person("Gates", "Bill", 70, "bill.gates@microsoft.com", "Bill", true),
                new Person("Jobs", "Steve", 80, "steve.jobs@apple.com", "Steve", true));
    }

    public List<Person> getPeopleByManagerName(Person manager) {
        return people.stream().filter(person -> person.getManagerName().equals(manager.getName())).collect(Collectors.toList());
    }

    public List<Person> getPeople() {
        return people;
    }

    public List<Person> getManagers() {
        return managers;
    }
}
