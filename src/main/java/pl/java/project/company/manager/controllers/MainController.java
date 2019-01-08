package pl.java.project.company.manager.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.tables.Database;

import javax.persistence.PersistenceException;
import java.io.IOException;

public class MainController {
  private static final String PERSONS_FXML = "/fxml/persons.fxml";
  private static final String WORKERS_FXML = "/fxml/workers.fxml";
  private static final String LEAVES_FXML = "/fxml/leaves.fxml";
  private static final String SALARY_FXML = "/fxml/salary.fxml";
  private static final String TRANSFERS_FXML = "/fxml/transfers.fxml";
  private static final String PORTFOLIO_FXML = "/fxml/portfolio.fxml";
  public BorderPane mainBorderPane;
  public ToggleButton personsBtn;
  public ToggleButton workersBtn;
  public ToggleButton leavesBtn;
  public ToggleButton salaryBtn;
  public ToggleButton transfersBtn;
  public ToggleButton portfolioBtn;
  public ToggleButton databaseBtn;

  public void exitAction(ActionEvent actionEvent) {
    System.exit(0);
  }

  public void setModena(ActionEvent actionEvent) {
    Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
  }

  public void setCaspian(ActionEvent actionEvent) {
    Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
  }

  public void AlwaysOnTopAction(ActionEvent event) {
    Stage stage =(Stage) mainBorderPane.getScene().getWindow();
    stage.setAlwaysOnTop(((CheckMenuItem)event.getSource()).isSelected());
  }

  public void aboutAction(ActionEvent actionEvent) {
    Dialogs.dialogAboutApplication();
  }

  public void setCenterPersons(ActionEvent actionEvent) {
    setCenter(PERSONS_FXML);
  }

  public void setCenterWorkers(ActionEvent actionEvent) {
    setCenter(WORKERS_FXML);
  }

  public void setCenterLeaves(ActionEvent actionEvent) {
    setCenter(LEAVES_FXML);
  }

  public void setCenterSalary(ActionEvent actionEvent) {
    setCenter(SALARY_FXML);
  }

  public void setCenterTransfers(ActionEvent actionEvent) {
    setCenter(TRANSFERS_FXML);
  }

  public void setCenterPortfolio(ActionEvent actionEvent) {
    setCenter(PORTFOLIO_FXML);
  }

  @FXML
  public void initialize(){
    Session session = Database.openSession();
    Boolean isBoss =true;
    try {
      session.createQuery("select count(id) from Portfolio").list();
    }catch (PersistenceException e){
      portfolioBtn.setDisable(true);
      transfersBtn.setDisable(true);
      isBoss=false;
    }try {
      session.createQuery("select count(id) from Worker").list();
    }catch (PersistenceException e){
      personsBtn.setDisable(true);
      workersBtn.setDisable(true);
      isBoss=false;
    } try {
      session.createQuery("select count(id) from Leave").list();
    }catch (PersistenceException e){
      leavesBtn.setDisable(true);
      salaryBtn.setDisable(true);
      isBoss=false;
    }
    databaseBtn.disableProperty().setValue(!isBoss);
    session.close();
  }

  private void setCenter(String URL){
    FXMLLoader loader = new FXMLLoader(Dialogs.class.getResource(URL));
    try {
      mainBorderPane.setCenter(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setCenterDatabase(ActionEvent actionEvent) {
      setCenter("/fxml/databaseMan.fxml");
  }
}
