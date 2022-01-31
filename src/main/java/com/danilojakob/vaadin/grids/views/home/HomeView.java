package com.danilojakob.vaadin.grids.views.home;

import com.danilojakob.vaadin.grids.domain.Manager;
import com.danilojakob.vaadin.grids.service.PersonService;
import com.danilojakob.vaadin.grids.views.MainLayout;
import com.danilojakob.vaadin.grids.domain.Person;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private TextField name;
    private Button addEntry;
    private Grid<Person> defaultGrid;
    private ListDataProvider<Person> dataProvider;
    private TreeGrid<Person> personTreeGrid;
    private TreeDataProvider<Person> treeDataProvider;
    private PersonService personService = new PersonService();

    public HomeView() {
        initGrid();
        initTreeGrid();
        addEntry = new Button("Add Entry");
        addEntry.addClickListener(event -> {
            List<Person> personList = new ArrayList<>(dataProvider.getItems());
            personList.add(new Person("Lost", "And Found", 123, "test@domain.com", "Linus", false));
            refreshGrid(personList);
            Person rootPerson = treeDataProvider.getTreeData().getRootItems().get(1);
            List<Person> secondPersonList = new ArrayList<>(treeDataProvider.getTreeData().getChildren(rootPerson));
            secondPersonList.add(new Person("Lost", "And Found", 123, "test@domain.com", "Linus", false));
            refreshTreeGrid(secondPersonList, rootPerson);
        });
        add(addEntry, defaultGrid, personTreeGrid);
    }

    private void initGrid() {
        buildGrid();

        dataProvider = new ListDataProvider<>(personService.getPeople());

        defaultGrid.setDataProvider(dataProvider);

    }

    private void refreshGrid(List<Person> people) {
        remove(defaultGrid);
        buildGrid();
        dataProvider = new ListDataProvider<>(people);
        defaultGrid.setDataProvider(dataProvider);
        addComponentAtIndex(1, defaultGrid);
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

    private void initTreeGrid() {
        buildTreeGrid();
        TreeData<Person> treeData = new TreeData<>();
        treeData.addRootItems(personService.getManagers());
        personService.getManagers().forEach(person -> treeData.addItems(person, personService.getPeopleByManagerName(person)));
        treeDataProvider = new TreeDataProvider<>(treeData);
        personTreeGrid.setDataProvider(treeDataProvider);
        // Below would be the easier way to do it, but then you don't have access to the data provider later on
        //personTreeGrid.setItems(personService.getManagers(), personService::getPeopleByManagerName);
    }

    private void buildTreeGrid() {
        personTreeGrid = new TreeGrid<>();
        personTreeGrid.addHierarchyColumn(Person::getSurName).setHeader("First Name");
        personTreeGrid.addComponentColumn(person -> {
            TextField textField = new TextField();
            textField.setValue(person.getName());
            textField.addValueChangeListener(event -> {
                person.setName(event.getValue());
                // Here you could also update the database, we just update the model
            });
            return textField;
        });
        personTreeGrid.addColumn(Person::getAge).setHeader("Age");
        personTreeGrid.addColumn(Person::geteMail).setHeader("Email");
        personTreeGrid.addComponentColumn(person -> {
            Button remove = new Button("Remove");
            remove.addClickListener(event -> {
                Person rootPerson = personService.getManagers().stream().filter(manager -> manager.getName().equals(person.getManagerName())).findFirst().orElse(null);
                List<Person> personList = new ArrayList<>(treeDataProvider.getTreeData().getChildren(rootPerson));
                personList.remove(person);
                refreshTreeGrid(personList, rootPerson);
            });
            if (person.isManager()) {
                remove.setVisible(false);
            }
            remove.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
            return remove;
        });
    }

    private void refreshTreeGrid(List<Person> people, Person rootPerson) {
        remove(personTreeGrid);
        buildTreeGrid();
        TreeData<Person> treeData = new TreeData<>();
        treeData.addRootItems(personService.getManagers());
        personService.getManagers().forEach(person -> {
            if (!person.getName().equals(rootPerson.getName())) {
                treeData.addItems(person, personService.getPeopleByManagerName(person));
            }
        });
        treeData.addItems(rootPerson, people);
        treeDataProvider = new TreeDataProvider<>(treeData);
        personTreeGrid.setDataProvider(treeDataProvider);
        addComponentAtIndex(2, personTreeGrid);
    }
}
