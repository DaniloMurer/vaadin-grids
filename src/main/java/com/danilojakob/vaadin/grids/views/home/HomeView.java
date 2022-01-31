package com.danilojakob.vaadin.grids.views.home;

import com.danilojakob.vaadin.grids.views.MainLayout;
import com.danilojakob.vaadin.grids.views.domain.Person;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends HorizontalLayout {

    private TextField name;
    private Button addEntry;
    private Grid<Person> defaultGrid;
    private ListDataProvider<Person> dataProvider;

    public HomeView() {
        initGrid();
        addEntry = new Button("Add Entry");
        addEntry.addClickListener(event -> {
            List<Person> personList = new ArrayList<>(dataProvider.getItems());
            personList.add(new Person("Lost", "And Found", 123, "test@domain.com"));
            refreshGrid(personList);
        });
        add(addEntry, defaultGrid);
    }

    private void initGrid() {
        buildGrid();
        List<Person> people = List.of(new Person("Jakob", "Danilo", 20, "danilo.jakob@gmx.ch"),
                new Person("Mustermann", "Max", 30, "max.mustermann@gmail.com"),
                new Person("Musterfrau", "Mia", 40, "mia.musterfrau@yahoo.com"),
                new Person("Mustermann", "Maja", 50, "maja.mustermann@outlook.com"));

        dataProvider = new ListDataProvider<>(people);

        defaultGrid.setDataProvider(dataProvider);

    }

    private void refreshGrid(List<Person> people) {
        remove(defaultGrid);
        buildGrid();
        dataProvider = new ListDataProvider<>(people);
        defaultGrid.setDataProvider(dataProvider);
        add(defaultGrid);
    }

    private void buildGrid() {
        defaultGrid = new Grid<>();
        defaultGrid.addComponentColumn(person -> {
            TextField textField = new TextField();
            textField.setValue(person.getSurName());
            textField.addValueChangeListener(event -> {
                person.setSurName(event.getValue());
                // Here you could also update the database, we just update the model
            });
            return textField;
        });
        defaultGrid.addColumn(Person::getName).setHeader("Last name");
        defaultGrid.addColumn(Person::getAge).setHeader("Age");
        defaultGrid.addColumn(Person::geteMail).setHeader("Email");
        defaultGrid.addComponentColumn(person -> {
            Button remove = new Button("Remove");
            remove.addClickListener(event -> {
                List<Person> personList = new ArrayList<>(dataProvider.getItems());
                personList.remove(person);
                refreshGrid(personList);
            });
            remove.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
            return remove;
        });
    }
}
