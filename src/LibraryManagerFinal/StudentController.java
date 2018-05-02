/*
 * To change this license header, addAll License Headers in Project Properties.
 * To change this template file, addAll Tools | Templates
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class StudentController implements Initializable {
    
    @FXML
    private Button addAll;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button viewList;
    @FXML
    private TextField name;
    @FXML
    private TextField reg;
    @FXML
    private TextField session;
    @FXML
    private TextField sessionView;
    @FXML
    private TextField regDelete;
    
    Connection con;
    Statement stmn;
    PreparedStatement pst;
    @FXML
    private void addStudent(ActionEvent e){
        if(e.getSource()==add){
            try {
//                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//                con = DriverManager.getConnection("jdbc:derby:fet;create=false");
                con = Main.getConnection();
                stmn = con.createStatement();
                String n= name.getText();
                String r = reg.getText();
                String s = session.getText();
//                stmn.execute("drop table student");
//                stmn.execute("create table student (name varchar(70) not null, reg bigint primary key,session varchar(7) not null)");
//                stmn.execute("insert into student (name,reg,session) values(?,?,?)");
                if(n.equals("")||n.equals("")||r.equals(""))
                    throw new Exception();
                else
                {
                pst = con.prepareStatement("insert into student (name,reg,session) values(?,?,?)");
                pst.setString(1, n);
                pst.setString(2, r);
                pst.setString(3, s);
                pst.execute();

                ShowAlert.showInformation("Student is successfully added to the list");
                stmn.close();
                con.close();
                pst.close();
                }
//                ResultSet rs = stmn.executeQuery("select * from student");
//                while(rs.next()){
//                    System.out.println(rs.getString("name"));
//                }
//                stmn.execute("insert into student value ("+name.getText()+","+Integer.parseInt(reg.getText())+","+session.getText()+")");
            } catch (SQLException ex) {
                ShowAlert.showAlert("Student already exists");
                
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Student already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch(Exception es){
                ShowAlert.showAlert("Fill all the fields carefully");
                
//                JOptionPane.showMessageDialog(null, "Fill the fields carefully", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        else if(e.getSource()==delete){
            if(ShowAlert.showConfirmation("Are you sure to delete all entry of "+regDelete.getText())==1)
            {
                try {
                con = Main.getConnection();
                stmn = con.createStatement();
                stmn.execute("delete from student where reg = '"+regDelete.getText()+"'");
                ShowAlert.showInformation("Deleted successfully");
                stmn.close();
                con.close();
            } catch (Exception ex) {
                ShowAlert.showAlert("Student doesn't exist");
//                JOptionPane.showMessageDialog(null, "Student doesn't exist");
            }
        }
      }
        else if(e.getSource()==viewList){
            try{
                StudentListController.setString("select * from student where session = '"+sessionView.getText()+"'");
                Main.get().setCenter(FXMLLoader.load(getClass().getResource("studentList.fxml")));
            }
            catch(Exception esf){
            }
        }
        else if(e.getSource() == addAll){
                FileChooser f = new FileChooser();
                f.setTitle("Choose CSV file");
                FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("CSV (comma delimited) (*.csv)", "*.csv");
                f.getExtensionFilters().add(extension);
                File file = f.showOpenDialog(null);
            if(file != null)
            try{

                con = Main.getConnection();
//                stmn = con.createStatement();
//                stmn.execute("alter table student modify name varchar(150) not null");
                pst = con.prepareStatement("insert into student (name,reg,session) values(?,?,?)");
                BufferedReader read = new BufferedReader(new FileReader(file));
                String s,str[];
                int i;
                while((s = read.readLine())!= null)
                {
                    str = s.split(",");
                    pst.setString(1, str[0]);
                    pst.setString(2, str[1]);
                    pst.setString(3, str[2]);
                    pst.execute();
//                    System.out.println(str[0]+"    "+str[1]+"   "+str[2]);
//                    stmn.execute("insert into student values('"+str[0]+"','"+str[1]+"','"+str[2]+"')");
                }
//                stmn.close();
                pst.close();
                con.close();
                ShowAlert.showInformation("All students have been added successfully !!!");
   
           }
            catch(Exception esc){
//                ShowAlert.alert.setContentText("Please select a *CSV file "
//                        + "\nwith consequent three column name,registration,session");
                ShowAlert.showAlert("Coudn't add data !!!");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addAll.setTooltip(new Tooltip("Make sure to add *.CSV file"));
        shortcut(add);
        name.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                shortcut(add);
            }
        });
        regDelete.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    shortcut(delete);
                }
            }
        });
        sessionView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    shortcut(viewList);
                }
            }
        });
    }    
    public void shortcut(Button add){
        Main.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    addStudent(new ActionEvent(add, name));
                }
            }
        });
    }
}
