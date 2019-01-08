package pl.java.project.company.manager.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;
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
  public Label userLb;
  private Pair<String, String> user;

  public void exitAction() {
    System.exit(0);
  }

  public void setModena() {
    Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
  }

  public void setCaspian() {
    Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
  }

  public void AlwaysOnTopAction(ActionEvent event) {
    Stage stage =(Stage) mainBorderPane.getScene().getWindow();
    stage.setAlwaysOnTop(((CheckMenuItem)event.getSource()).isSelected());
  }

  public void aboutAction() {
    Dialogs.dialogAboutApplication();
  }

  public void setCenterPersons() {
    setCenter(PERSONS_FXML);
  }

  public void setCenterWorkers() {
    setCenter(WORKERS_FXML);
  }

  public void setCenterLeaves() {
    setCenter(LEAVES_FXML);
  }

  public void setCenterSalary() {
    setCenter(SALARY_FXML);
  }

  public void setCenterTransfers() {
    setCenter(TRANSFERS_FXML);
  }

  public void setCenterPortfolio() {
    setCenter(PORTFOLIO_FXML);
  }

  @FXML
  public void initialize(){
    permissions();
    if(!personsBtn.isDisable()){
      setCenterPersons();
      personsBtn.setSelected(true);
    }
    else if(!leavesBtn.isDisable()) {
      setCenterLeaves();
      leavesBtn.setSelected(true);
    }else{
      setCenterTransfers();
      transfersBtn.setSelected(true);
    }
  }

  private void permissions() {
    Session session = Database.openSession();
    boolean isBoss =true;
    try {
      session.createQuery("select count(id) from Portfolio").list();
    }catch (PersistenceException e){
      portfolioBtn.setDisable(true);
      transfersBtn.setDisable(true);
      isBoss=false;
    }
    try {
      session.createQuery("select count(id) from Worker").list();
    }catch (PersistenceException e){
      personsBtn.setDisable(true);
      workersBtn.setDisable(true);
      isBoss=false;
    }
    try {
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

  public void setCenterDatabase() {
    FXMLLoader loader = new FXMLLoader(Dialogs.class.getResource("/fxml/databaseMan.fxml"));
    try {
      mainBorderPane.setCenter(loader.load());
      DatabaseController databaseController = loader.getController();
      databaseController.setUser(user);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setUser(Pair<String, String> user) {
    this.user = user;
    userLb.setText(user.getKey());
  }
}
