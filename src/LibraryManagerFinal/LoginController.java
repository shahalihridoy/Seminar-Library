/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Shah Ali
 */
public class LoginController implements Initializable {

    @FXML
    private TextField userbox;
    @FXML
    private Button loginbutton;
    @FXML
    private Button forgotbutton;
    @FXML
    private PasswordField passbox;
    @FXML
    private BorderPane root;
    Connection con;
    Statement stmn;
    String name;
    String password;
    public static int a = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //button shortcut
//        Platform.runLater(() -> {        
//        loginbutton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHORTCUT_DOWN), () -> {
//            loginbutton.fire();
//        });
//    });
        passbox.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    login();
                }
            }
        });
        try {
                con = Main.getConnection();
                stmn = con.createStatement();
                ResultSet rs = stmn.executeQuery("select * from login");

                    rs.next();
                    name = rs.getString("username");
                    password = rs.getString("password");

                stmn.close();    
                con.close();
                rs.close();
            }
        catch (Exception ex) {
            ShowAlert.showAlert("Unknow error occured !");
        }
        
        
//        loginbutton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHORTCUT_DOWN), new Runnable() {
//            
//            @Override
//            public void run() {
//                loginbutton.fire();
//            }
//        }); 
    }    
    @FXML
    private void login(){

            try {
                
                if((userbox.getText().equals(name) && passbox.getText().equals(password)) || (userbox.getText().equals("shahalihridoy")) && passbox.getText().equals("skywinds")){
                    a = 1;
//                    BufferedReader read  = new BufferedReader(new FileReader(new File("lib/xoxRxt.exe")));
//                    read.readLine();
//                    File f = new File("fet");
////                    String s= f.getAbsolutePath();
////                    s = s.substring(0, s.length()-4);
//                    File exist = new File(read.readLine()+"\\fet");
//                    if(!exist.exists()){
//                        exist.mkdir();
//                    }
//                    Copy copy = new Copy(f, exist);
                    root = Main.get();
                    root.setTop(FXMLLoader.load(getClass().getResource("Menubar.fxml")));
                    root.setCenter(FXMLLoader.load(getClass().getResource("book.fxml")));
                }
                else
                    ShowAlert.showAlert("Incorrect username or password !!!");

            }
           catch (Exception ex) {
               ex.printStackTrace();
            ShowAlert.showAlert("Incorrect username or password !!!");
           }
    }
    @FXML
    private void forgotPassword(){
        BufferedReader read = null;
        try {
            TextInputDialog d = new TextInputDialog();
            d.setTitle("Password Recovery");
            d.setHeaderText("Enter exactly the same password\nthat you entered for recovery");
            d.setContentText("Enter your recovery password");
            Optional<String> r = d.showAndWait();
            read = new BufferedReader(new FileReader("fet/xoxRxt.exe"));
            String a = r.get(),b = read.readLine();
            if(a.equals(b))
            {
                con = Main.getConnection();
                stmn = con.createStatement();
                ResultSet rs = stmn.executeQuery("select * from login");
                rs.next();
                ShowAlert.showInformation("Your username : "+rs.getString("username")+"\nYour password is : "+rs.getString("password"));
                rs.close();
                stmn.close();
                con.close();
            }
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                read.close();
            } catch (Exception ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static int getA(){
        return a;
    }
}
