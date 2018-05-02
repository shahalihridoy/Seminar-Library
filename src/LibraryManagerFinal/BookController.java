/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Shah Ali
 */
public class BookController implements Initializable {

    @FXML
    private ToggleGroup issue;
    @FXML
    private TextField reg;
    @FXML
    private TextField bookID;
    @FXML
    private TextField dateField;
    @FXML
    private Button submit;
    @FXML
    private RadioButton issued;
    @FXML
    private RadioButton returned;
    @FXML
    private void addBook(){
        try {
            Connection con = Main.getConnection();
            Statement stmn = con.createStatement();
            if(issued.isSelected()){
                stmn.execute("insert into borrow values('"+reg.getText()+"',"+Integer.parseInt(bookID.getText())+",'"+dateField.getText()+"')");
                stmn.execute("insert into date values('"+reg.getText()+"',"+Integer.parseInt(bookID.getText())+",'"+dateField.getText()+"')");
                ShowAlert.showInformation("Book Successfully issued !!!");
            }
            else if(returned.isSelected()){
                stmn.execute("delete from borrow where reg = '"+reg.getText()+"' and id = "+Integer.parseInt(bookID.getText()));
                ShowAlert.showInformation("Book Successfully returned !!!");
            }
            
        } catch (SQLException ex) {
            ShowAlert.showAlert("Invalid user or book ID !!!");
        }
        
        catch (Exception ex) {
            ShowAlert.showAlert("Fill all the fields correctly");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Date date = new Date();
        DateFormat dateformate = new SimpleDateFormat("dd-MM-YYYY");
        dateField.setText(dateformate.format(date));
        issued.setSelected(true);
        
        //shortcut button
        Main.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    addBook();
                }
            }
        });
    }    
    
}
