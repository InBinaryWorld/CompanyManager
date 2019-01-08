package pl.java.project.company.manager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.convenetrs.DateConv;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.Worker;

import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;
import java.util.List;

public class AddLeaveController {

  public DatePicker endCalendar;
  public DatePicker beginCalendar;
  public ComboBox<Worker> peselCB;
  public Button addBtn;

  public void initialize() {
    addBtn.disableProperty().bind(endCalendar.valueProperty().isNull()
            .or(beginCalendar.valueProperty().isNull()
            .or(peselCB.valueProperty().isNull())));

    Session session = Database.openSession();
    List<Worker> workers = session.createQuery("from Worker WHERE endDate is NULL or now() BETWEEN beginDate and endDate").list();
    session.close();
    ObservableList<Worker> observableList = FXCollections.observableList(workers);
    peselCB.setItems(observableList);
  }

  public void addAction() {
    Session session= Database.openSession();
    Transaction tx = session.beginTransaction();
    StoredProcedureQuery query = session
            .createStoredProcedureQuery("addLeave")
            .registerStoredProcedureParameter(
                    "id", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                    "beginn", Date.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                    "endd", Date.class, ParameterMode.IN)
            .setParameter("id", peselCB.getValue().getId())
            .setParameter("beginn", DateConv.convertToDate(beginCalendar.valueProperty().get()))
            .setParameter("endd", DateConv.convertToDate(endCalendar.valueProperty().get()));
    try {
      query.execute();
    }catch (PersistenceException e){
      Dialogs.warningAlert("Cannot give a Leave");
      return;
    }
    tx.commit();
    session.close();
    final Stage stage = (Stage) addBtn.getScene().getWindow();
    stage.close();
  }
}
