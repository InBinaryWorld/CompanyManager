package pl.java.project.company.manager;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import pl.java.project.company.manager.controllers.AddWorkerController;
import pl.java.project.company.manager.tables.Person;

import java.io.IOException;
import java.util.Optional;

public class Dialogs {
  static Pair<String, String> loginDialog() {
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Login Dialog");
    dialog.setHeaderText("Login to CompanyManager 1.0");

    dialog.setGraphic(new ImageView("/icons/login.png"));

    ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField username = new TextField();
    username.setPromptText("Username");
    PasswordField password = new PasswordField();
    password.setPromptText("Password");

    grid.add(new Label("Username:"), 0, 0);
    grid.add(username, 1, 0);
    grid.add(new Label("Password:"), 0, 1);
    grid.add(password, 1, 1);

    Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    loginButton.setDisable(true);

    username.textProperty().addListener((ob, ol, newValue) -> loginButton.setDisable(newValue.trim().isEmpty()));
    dialog.getDialogPane().setContent(grid);
    username.requestFocus();

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == loginButtonType) {
        return new Pair<>(username.getText(), password.getText());
      }
      System.exit(0);
      return null;
    });

    Optional<Pair<String, String>> result = dialog.showAndWait();
    return result.orElse(null);
  }

  public static void wrongLoginData(){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText("Wrong Login or Password !");
    alert.setContentText("Your login details are incorrect, please try again!");
    alert.showAndWait();
  }

  public static void dialogAboutApplication() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("About Application");
    alert.setHeaderText("Company Manager 1.0");
    alert.setContentText("Application CompanyManager 1.0.");
    alert.showAndWait();
  }

  public static void addWorkerDialog(Person person){
    FXMLLoader loader = new FXMLLoader(Dialogs.class.getResource("/fxml/addWorker.fxml"));
    Pane pane = null;
    try {
      pane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    AddWorkerController addCtrl =loader.getController();
    addCtrl.setPerson(person);
    assert pane != null;
    Scene scene = new Scene(pane);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Recruit");
    stage.showAndWait();
  }

  public static void addPersonDialog(){
    FXMLLoader loader = new FXMLLoader(Dialogs.class.getResource("/fxml/addPerson.fxml"));
    Pane pane = null;
    try {
      pane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert pane != null;
    Scene scene = new Scene(pane);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Add Person");
    stage.showAndWait();
  }

  public static void warningAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setHeaderText(message);
    alert.showAndWait();
  }

  public static void leaveDialog() {

    FXMLLoader loader = new FXMLLoader(Dialogs.class.getResource("/fxml/addLeave.fxml"));
    Pane pane = null;
    try {
      pane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert pane != null;
    Scene scene = new Scene(pane);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Leave");
    stage.showAndWait();
  }

}
