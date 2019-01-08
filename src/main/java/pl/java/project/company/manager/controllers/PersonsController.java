package pl.java.project.company.manager.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Session;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.Person;

import java.util.Date;
import java.util.List;

public class PersonsController {
  public TableColumn<Person, Date> birthColumn;
  public TableColumn<Person, String> surnameColumn;
  public TableColumn<Person, String> nameColumn;
  public TableColumn<Person, String> peselColumn;
  public TableView<Person> tableView;
  public Button recruitBtn;
  public Button addBtn;

  public void initialize() {
    tableView.setStyle("-fx-base: rgba(220, 220,220, 255)");
    initTable();
    setUpTable();
    recruitBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
  }

  private void setUpTable() {
    peselColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPesel()));
    birthColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getDateOfBirth()));
    surnameColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getSurname()));
    nameColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getName()));
  }

  private void initTable() {
    Session session = Database.openSession();
    List<Person> persons = session.createQuery("from Person ").list();
    session.close();
    ObservableList<Person> observableList = FXCollections.observableList(persons);
    tableView.setItems(observableList);
  }


  public void recruitAction(ActionEvent actionEvent) {
    Dialogs.addWorkerDialog(tableView.getSelectionModel().getSelectedItem());
  }

  public void addAction(ActionEvent actionEvent) {
    Dialogs.addPersonDialog();
    initTable();
  }
}
