package pl.java.project.company.manager.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Session;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.Leave;

import java.util.Date;
import java.util.List;

public class LeavesController {
  public TableColumn<Leave, String> peselColumn;
  public TableColumn<Leave, Date> beginColumn;
  public TableColumn<Leave, Date> endColumn;
  public TableView<Leave> tableView;


  public void initialize() {

    tableView.setStyle("-fx-base: rgba(220, 220,220, 255)");
    initTable();
    Session session = Database.openSession();
    peselColumn.setCellValueFactory(cell ->
            new SimpleObjectProperty<>(cell.getValue().getWorker().getPerson().getPesel()));
    beginColumn.setCellValueFactory(cell -> new SimpleObjectProperty<Date>(cell.getValue().getBeginDate()));
    endColumn.setCellValueFactory(cell -> new SimpleObjectProperty<Date>(cell.getValue().getEndDate()));
  }

  private void initTable() {
    Session session = Database.openSession();
    List leaves = session.createQuery("from Leave").list();
    session.close();
    ObservableList<Leave> observableList = FXCollections.observableList(leaves);
    tableView.setItems(observableList);
  }

  public void leaveAction() {
    Dialogs.leaveDialog();
    initTable();
  }
}
