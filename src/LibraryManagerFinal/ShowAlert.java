/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Shah Ali
 */
public class ShowAlert {
    
    static Alert alert;

    public static void showAlert(String s) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(s);
//        alert.setContentText(s);
        alert.showAndWait();
    }
    public static void showInformation(String s){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success !!!");
        alert.setHeaderText(s);
        alert.show();
    }
    public static int showConfirmation(String s){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setHeaderText(s);
//        alert.setContentText(s);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
              return 1;
        } 
        else {
            return 0;
        }
    }
    
}
