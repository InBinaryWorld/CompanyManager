package pl.java.project.company.manager.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.java.project.company.manager.Dialogs;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class DatabaseController {
  public Button backupBtn;
  public Button restoreBtn;

  public void backupAction(ActionEvent actionEvent) {
    String dbUser = "root";
    String dbPass = "Krzysiu9";
    String s= new File("src\\main\\resources\\backup.bat").getAbsolutePath();
    String param = "\""+s+"\"";
    FileChooser fileChooser = new FileChooser();
    String url;
    try {
      url  = fileChooser.showSaveDialog(null).getAbsolutePath();
    }catch (NullPointerException e){
      return;
    }
    String st="\"" +url+"\"";
    String[]commands = new String[]{param,dbUser,dbPass,st};
    try {
       Runtime.getRuntime().exec(commands );
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void restoreAction(ActionEvent actionEvent) {
    String dbUser = "Boss";
    String dbPass = "Boss";
    String s= new File("src\\main\\resources\\restore.bat").getAbsolutePath();
    String param = "\""+s+"\"";
    FileChooser fileChooser = new FileChooser();
    String url;
    try {
      url = fileChooser.showOpenDialog(null).getAbsolutePath();
    }catch (NullPointerException e){
      return;
    }
    String st="\"" +url+"\"";
    String[]commands = new String[]{param,dbUser,dbPass,st};
    try {
      Runtime.getRuntime().exec(commands );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
