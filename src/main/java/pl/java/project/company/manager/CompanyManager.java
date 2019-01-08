package pl.java.project.company.manager;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import pl.java.project.company.manager.controllers.MainController;
import pl.java.project.company.manager.tables.Database;


public class CompanyManager extends Application {
  Pair<String, String> user;

  private static final String FXML_MAIN_PATCH = "/fxml/main.fxml";

  @Override
  public void start(Stage primaryStage) throws Exception {
    createConnection();

    FXMLLoader loader = new FXMLLoader(Dialogs.class.getResource(FXML_MAIN_PATCH));
    primaryStage.setScene(new Scene(loader.load()));
    MainController mainController = loader.getController();
    mainController.setUser(user);
    primaryStage.setMinWidth(primaryStage.getWidth());
    primaryStage.setMinHeight(180);
    primaryStage.setTitle("CompanyManager");
    primaryStage.show();

  }

  @FXML
  public void initialize() {

  }

  private void createConnection() {
    boolean f;
    do {
      user = Dialogs.loginDialog();
      f = Database.setUpSession(user.getKey(), user.getValue());
    } while (!f);
  }
}
