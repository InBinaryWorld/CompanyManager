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
import org.hibernate.Transaction;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.Worker;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class WorkersController {
  public TableColumn<Worker, String> peselColumn;
  public TableColumn<Worker, String> professionColumn;
  public TableColumn<Worker, Integer> salaryColumn;
  public TableColumn<Worker, Date> beginColumn;
  public TableColumn<Worker, Date> endColumn;
  public TableView<Worker> tableView;
  public Button releaseBtn;

  public void initialize() {
    tableView.setStyle("-fx-base: rgba(220, 220,220, 255)");
    releaseBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
    initTable();
    setUpTable();
  }

  private void initTable() {
    Session session = Database.openSession();
    List<Worker> workers = session.createQuery("from Worker ").list();
    session.close();
    ObservableList<Worker> observableList = FXCollections.observableList(workers);
    tableView.setItems(observableList);
  }

  private void setUpTable() {
    peselColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPerson().getPesel()));
    professionColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getProfession()));
    salaryColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getSalary()));
    beginColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getBeginDate()));
    endColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getEndDate()));
  }

  public void releaseAction(ActionEvent actionEvent) {
    String s=tableView.getSelectionModel().getSelectedItem().getPerson().getPesel();
    System.out.println(s);
    Session session= Database.openSession();
    Transaction tx = session.beginTransaction();
    StoredProcedureQuery query = session
            .createStoredProcedureQuery("releaseEmployee")
            .registerStoredProcedureParameter(
                    "pesel", String.class, ParameterMode.IN)
            .setParameter("pesel", s);

    query.execute();
    tx.commit();
    initTable();
  }
}
