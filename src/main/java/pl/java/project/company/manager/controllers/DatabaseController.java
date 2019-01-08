package pl.java.project.company.manager.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class DatabaseController {
  public Button backupBtn;
  public Button restoreBtn;
  private Pair<String, String> user;

  public void backupAction(ActionEvent actionEvent) {
    String s = new File("src\\main\\resources\\backup.bat").getAbsolutePath();
    String param = "\"" + s + "\"";
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter ef =
            new FileChooser.ExtensionFilter("SQL Extension for MYSQL","*.sql");
    fileChooser.getExtensionFilters().add(ef);
    String url;
    try {
      url = fileChooser.showSaveDialog(null).getAbsolutePath();
    } catch (NullPointerException e) {
      return;
    }
    String st = "\"" + url + "\"";
    String[] commands = new String[]{param, user.getKey(), user.getValue(), st};
    try {
      Runtime.getRuntime().exec(commands);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void restoreAction(ActionEvent actionEvent) {
    String s = new File("src\\main\\resources\\restore.bat").getAbsolutePath();
    String param = "\"" + s + "\"";
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter ef =
            new FileChooser.ExtensionFilter("SQL Extension for MYSQL","*.sql");
    fileChooser.getExtensionFilters().add(ef);
    String url;
    try {
      url = fileChooser.showOpenDialog(null).getAbsolutePath();
    } catch (NullPointerException e) {
      return;
    }
    String st = "\"" + url + "\"";
    String[] commands = new String[]{param, user.getKey(), user.getValue(), st};
    try {
      Runtime.getRuntime().exec(commands);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void setUser(Pair<String, String> user) {
    this.user = user;
  }
}
