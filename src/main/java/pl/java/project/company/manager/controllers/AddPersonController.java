package pl.java.project.company.manager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.convenetrs.DateConv;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.Person;

public class AddPersonController {

  public TextField peselFld;
  public TextField surnameFld;
  public TextField nameFld;
  public DatePicker birthCalendar;
  public Button addBtn;

  @FXML
  private void addAction() {
    Person person = new Person();
    person.setName(nameFld.getText());
    person.setSurname(surnameFld.getText());
    person.setPesel(peselFld.getText());
    person.setDateOfBirth(DateConv.convertToDate(birthCalendar.getValue()));

    Session session = Database.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.save(person);
      tx.commit();
      session.close();
      final Stage stage = (Stage) addBtn.getScene().getWindow();
      stage.close();
    }
    catch (Exception e) {
      if (tx!=null) tx.rollback();
      Dialogs.warningAlert(e.getCause().getMessage());
    }
    finally {
      session.close();
    }
  }

  public void initialize() {
    initBindings();
  }


  private void initBindings() {
    addBtn.disableProperty().bind(peselFld.textProperty().isEmpty()
            .or(surnameFld.textProperty().isEmpty())
            .or(nameFld.textProperty().isEmpty())
            .or(birthCalendar.valueProperty().isNull()));
  }

}
