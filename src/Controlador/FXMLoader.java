/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.net.URL;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Francisco
 */
public class FXMLoader {
    private AnchorPane vista;
    
    public AnchorPane getPage(String filename){
    
        try{
        URL fileUrl = MultipleFxmlHandlingJavaFX.class.getResource("/Vista/"+filename+".fxml");
            if (fileUrl==null) {
                throw new java.io.FileNotFoundException("FXML no encontrado");
            }
        vista = new FXMLLoader().load(fileUrl);
        }catch(Exception e){
        
        }
        
        
    return vista;
    }
}
