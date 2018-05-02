/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Shah Ali
 */
public class AddBookController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField number;
    @FXML
    private TextField deleteId;
    @FXML
    private Button addBookFiles;
    @FXML
    private Button addBook;
    @FXML
    private Button deleteBook;
    Connection con;
    Statement stmn;
    PreparedStatement pst;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addBookFiles.setTooltip(new Tooltip("Make sure to add *.CSV file"));
//        button shortcut
            shortcut(addBook);
        name.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                shortcut(addBook);
            }
        });
        deleteId.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                shortcut(deleteBook);
            }
        });
        deleteId.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    shortcut(deleteBook);
                }
            }
        });
    }    

    @FXML
    private void addBook(ActionEvent event) {
        if(event.getSource() == addBook){
        try{
            con = Main.getConnection();
            stmn = con.createStatement();
            if(name.getText().equals("")||id.getText().equals("")||number.equals(""))
                throw new Exception();
            else
//                stmn.execute("delete from bookList");
            stmn.execute("insert into bookList values('"+name.getText()+"',"+Integer.parseInt(id.getText())+","+Integer.parseInt(number.getText())+")");
            ShowAlert.showInformation("Successfully added to the list");
            stmn.close();
            con.close();
        }
        catch(SQLException es){
            ShowAlert.showAlert("Another book with this ID already exists");
        }
        catch(Exception e){
            ShowAlert.showAlert("Fill all the fields carefully");
        }
    }
        
        else if(event.getSource() == addBookFiles){
            FileChooser f = new FileChooser();
            f.setTitle("Choose CSV file");
            FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("CSV (comma delimited) (*.csv)", "*.csv");
            f.getExtensionFilters().add(extension);
            File file = f.showOpenDialog(null);
            if(file != null)
            try{
                con = Main.getConnection();
                pst = con.prepareStatement("insert into bookList values(?,?,?)");
                BufferedReader read = new BufferedReader(new FileReader(file));
                String s,str[];
                int i;
                while((s = read.readLine())!= null)
                {
                    str = s.split(",");
                    pst.setString(1, str[0]);
                    pst.setInt(2, Integer.parseInt(str[1]));
                    pst.setInt(3, Integer.parseInt(str[2]));
                    pst.execute();

                }
                pst.close();
                con.close();
                ShowAlert.showInformation("All books have been added successfully !!!");
   
           }
            catch(Exception esc){
                ShowAlert.showAlert("Coudn't add data !!!");
                esc.printStackTrace();
            }
        }
        else if(event.getSource()==deleteBook){
            if(!deleteId.getText().equals(""))
            try{
                con=Main.getConnection();
                stmn = con.createStatement();
                stmn.execute("delete from bookList where id = "+Integer.parseInt(deleteId.getText()));
                stmn.close();
                con.close();
                ShowAlert.showInformation("Successfully deleted selected book !!!");
            }
            catch(Exception ex){
                
            }
        }
        }
    public void shortcut(Button add){
        Main.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    addBook(new ActionEvent(add, name));
                }
            }
        });
    }
}
