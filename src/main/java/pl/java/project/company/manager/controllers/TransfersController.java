package pl.java.project.company.manager.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.java.project.company.manager.Dialogs;
import pl.java.project.company.manager.tables.Database;
import pl.java.project.company.manager.tables.TransfersMade;
import pl.java.project.company.manager.tables.Worker;

import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class TransfersController {
  public TableView<TransfersMade> tableView;
  public TableColumn<TransfersMade, Integer> idColumn;
  public TableColumn<TransfersMade, Integer> transactionIdColumn;
  public TableColumn<TransfersMade, String> peselColumn;
  public TableColumn<TransfersMade, Integer> amountColumn;
  public TextField professionTF;
  public Button makeBtn;

  public void initialize() {

    tableView.setStyle("-fx-base: rgba(220, 220,220, 255)");
    initTable();
    setUpTable();

    makeBtn.disableProperty().bind(professionTF.textProperty().isEmpty());
  }

  private void setUpTable() {
    idColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getId()));
    transactionIdColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPortfolio().getId()));
    peselColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPerson().getPesel()));
    amountColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getAmount()));
  }

  private void initTable() {
    Session session = Database.openSession();
    List<TransfersMade> transfersMades = session.createQuery("from TransfersMade ").list();
    session.close();
    ObservableList<TransfersMade> observableList = FXCollections.observableList(transfersMades);
    tableView.setItems(observableList);
  }

  public void makeAction() {
    Session session = Database.openSession();
    Transaction tx = session.beginTransaction();
    StoredProcedureQuery query = session
            .createStoredProcedureQuery("makeTransfer")
            .registerStoredProcedureParameter(
                    "professionI", String.class, ParameterMode.IN)
            .setParameter("professionI", professionTF.getText());
    try {
      query.execute();
    } catch (PersistenceException e) {
      Dialogs.warningAlert("Cannot give make Transfer");
    }
    tx.commit();
    session.close();
    initTable();
  }
}
