package pl.java.project.company.manager.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.SalaryHistory;
import pl.java.project.company.manager.tables.Worker;

import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;
import java.util.List;

public class SalaryController {
  public TableView<SalaryHistory> tableView;
  public TableColumn<SalaryHistory, String> peselColumn;
  public TableColumn<SalaryHistory, Integer> newColumn;
  public TableColumn<SalaryHistory, Integer> oldColumn;
  public TableColumn<SalaryHistory, Date> dateColumn;
  public ComboBox<Worker> workerCB;
  public TextField salaryTF;
  public Button changeBtn;

  public void initialize() {

    tableView.setStyle("-fx-base: rgba(220, 220,220, 255)");
    initTable();
    setUpTable();
    setUpChange();
  }

  private void setUpChange() {
    changeBtn.disableProperty().bind(workerCB.valueProperty().isNull()
            .or(salaryTF.textProperty().isEmpty()));

    Session session = Database.openSession();
    List<Worker> workers = session.createQuery("from Worker WHERE endDate is null or now() BETWEEN beginDate and endDate").list();
    session.close();
    ObservableList<Worker> observableList = FXCollections.observableList(workers);
    workerCB.setItems(observableList);
  }

  private void setUpTable() {
    peselColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPerson().getPesel()));
    newColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getNewSalary()));
    oldColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getOldSalary()));
    dateColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getChangeDate()));
  }

  private void initTable() {
    Session session = Database.openSession();
    List<SalaryHistory> salaryHistories = session.createQuery("from SalaryHistory ").list();
    session.close();
    ObservableList<SalaryHistory> observableList = FXCollections.observableList(salaryHistories);
    tableView.setItems(observableList);
  }

  public void changeSalaryAction(ActionEvent actionEvent) {

    int newSalary = -1;
    try {
      newSalary =Integer.parseInt(salaryTF.getText());
    }catch (NumberFormatException e) {
      Dialogs.warningAlert("Salary must be a number!!");
      final Stage stage = (Stage) changeBtn.getScene().getWindow();
      stage.close();
    }

    Session session= Database.openSession();
    Transaction tx = session.beginTransaction();
    StoredProcedureQuery query = session
            .createStoredProcedureQuery("changeSalary")
            .registerStoredProcedureParameter(
                    "pesel", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                    "workerID", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                    "newSalary", Integer.class, ParameterMode.IN)
            .setParameter("pesel", workerCB.getValue().getPerson().getPesel())
            .setParameter("workerID",workerCB.getValue().getId() )
            .setParameter("newSalary",newSalary );
    try {
      query.execute();
    }catch (PersistenceException e){
      Dialogs.warningAlert("Cannot give a Leave");
    }
    tx.commit();
    session.close();
    initTable();
  }
}
