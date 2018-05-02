/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Shah Ali
 */
public class AdminController implements Initializable {

    @FXML
    private PasswordField cpass;
    @FXML
    private PasswordField npass;
    @FXML
    private PasswordField n1pass;
    @FXML
    private TextField nuser;
    Connection con;
    Statement stmn;
    ResultSet rs;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Main.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    change();
                }
            }
        });
    }    

    @FXML
    private void change() {
        String password = "23c@426*^%#^";
        int i = 0;
        try {
            con = Main.getConnection();
            stmn = con.createStatement();
            rs = stmn.executeQuery("select * from login");
            rs.next();
            {
                password = rs.getString("password");
            }
            if(password.equals(cpass.getText())){
                if(npass.getText().equals(n1pass.getText()) && (!npass.getText().equals(""))
                        )
                {
                    stmn.executeUpdate("update login set password = '"+npass.getText()+"' where username = '"+rs.getString("username")+"'");
                    i++;
                }
                else if(npass.getText().equals(""))
                {
                    
                }
                else if(!npass.getText().equals(n1pass.getText()))
                    ShowAlert.showAlert("Enter same password in both new password field !!!");
                
                if(!nuser.getText().equals(""))
                {
                    stmn.executeUpdate("update login set username = '"+nuser.getText()+"' where password = '"+password+"'");
                    i++;
                }
                if(i>0)
                ShowAlert.showInformation("Change has been performed successfully !!!");
            }
            else
                ShowAlert.showAlert("Incorrect current password");
            rs.close();
            stmn.close();
            con.close();
        } catch (Exception ex) {
//            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
