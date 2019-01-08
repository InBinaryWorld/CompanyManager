package pl.java.project.company.manager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.convenetrs.DateConv;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.Person;
import pl.java.project.company.manager.tables.Worker;

public class AddWorkerController {
  public Button addBtn;
  public Label peselLabel;
  public Label surnameLabel;
  public Label nameLabel;
  public DatePicker endCalendar;
  public DatePicker beginCalendar;
  public TextField salaryFld;
  public TextField professionFld;

  private Person person;

  @FXML
  private void addAction() {
    Worker worker = new Worker();
    try {
      worker.setSalary(Integer.parseInt(salaryFld.getText()));
    }catch (NumberFormatException e){
      Dialogs.warningAlert("Salary must be a number!!");
      return;
    }
    worker.setProfession(professionFld.getText());
    worker.setPerson(person);
    worker.setBeginDate(DateConv.convertToDate(beginCalendar.getValue()));
    if(endCalendar.valueProperty().isNotNull().get())
      worker.setEndDate(DateConv.convertToDate(endCalendar.getValue()));

    Session session = Database.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.save(worker);
      tx.commit();
      session.close();
      final Stage stage = (Stage) addBtn.getScene().getWindow();
      stage.close();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      Dialogs.warningAlert(e.getCause().getMessage());
    } finally {
      session.close();
    }
  }


  public void initialize() {
    initBindings();
  }


  private void initBindings() {
    addBtn.disableProperty().bind(professionFld.textProperty().isEmpty()
            .or(salaryFld.textProperty().isEmpty())
            .or(beginCalendar.valueProperty().isNull()));
  }

  public void setPerson(Person person) {
    this.person = person;
    nameLabel.setText(person.getName());
    surnameLabel.setText(person.getSurname());

    peselLabel.setText(person.getPesel());
  }
}
