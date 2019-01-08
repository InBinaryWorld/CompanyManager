package pl.java.project.company.manager.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.Portfolio;

import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;
import java.util.List;

public class PortfolioController {
  public TableColumn<Portfolio, Integer> idColumn;
  public TableColumn<Portfolio, Integer> changeColumn;
  public TableColumn<Portfolio, Integer> currColumn;
  public TableColumn<Portfolio, Date> dateColumn;
  public TableColumn<Portfolio, String> titleColumn;
  public TableView<Portfolio> tableView;
  public Label currStateLB;
  public TextField changeTF;
  public TextField titleTF;
  public Button doBtn;


  public void initialize() {

    tableView.setStyle("-fx-base: rgba(220, 220,220, 255)");
    initTable();
    setUpTable();
    doBtn.disableProperty().bind(changeTF.textProperty().isEmpty()
            .or(titleTF.textProperty().isEmpty()));
  }

  private void setUpTable() {
    idColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getId()));
    changeColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getChange()));
    currColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getCurrentState()));
    dateColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getChangeDate()));
    titleColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getTitle()));
  }

  private void initTable() {
    Session session = Database.openSession();
    List<Portfolio> portfolio = session.createQuery("from Portfolio ").list();
    List<Integer> list = session.createQuery("select currentState from Portfolio ORDER BY id DESC").list();
    currStateLB.setText(list.get(0).toString());
    session.close();
    ObservableList<Portfolio> observableList = FXCollections.observableList(portfolio);
    tableView.setItems(observableList);
  }

  public void doAction() {
    int change;
    try {
      change = Integer.parseInt(changeTF.getText());
    } catch (NumberFormatException e) {
      Dialogs.warningAlert("Change must be a number!!");
      return;
    }

    Session session = Database.openSession();
    Transaction tx = session.beginTransaction();
    StoredProcedureQuery query = session
            .createStoredProcedureQuery("portfolioOperation")
            .registerStoredProcedureParameter(
                    "changeI", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                    "title", String.class, ParameterMode.IN)
            .setParameter("changeI", change)
            .setParameter("title", titleTF.getText());
    try {
      query.execute();
    } catch (PersistenceException e) {
      Dialogs.warningAlert("Cannot give a Leave");
    }
    tx.commit();
    session.close();
    initTable();
  }
}
