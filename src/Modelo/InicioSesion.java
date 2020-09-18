/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.VentanaInicialController;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Francisco
 */
public class InicioSesion {

    public static String nombreCompleto;
    public static String rol;
    private String usuario;
    private String contraseña;
    public static int idUsuarioActivo;
    static Stage ventanaInicio;
    static Stage ventanaLogin;
    static FXMLLoader loaderInicioAdmin;
    private String password;
    private String passwordDes;
    private String llave_secreta = "KirtronSoluciones";
     String path = "/Imagenes/icono.jpeg";

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String desencriptar(String secretKey, String cadenaEncriptada) {
        String passwordDesencriptada = "";
        try {
            byte[] message = Base64.decodeBase64(cadenaEncriptada.getBytes("utf-8"));
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md5.digest(secretKey.getBytes("utf-8"));
            byte[] keyBites = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBites, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            passwordDesencriptada = new String(plainText, "utf-8");
        } catch (Exception e) {
        }
        return passwordDesencriptada;
    }

    public boolean checarUsuario() {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT * FROM usuario INNER JOIN info_usuario ON usuario.id = info_usuario.usuario_id WHERE username=?");
            pst.setString(1, usuario);
            rs = pst.executeQuery();
            if (rs.next()) {
                password = rs.getString("password");
                passwordDes = desencriptar(llave_secreta, password);
                if (passwordDes.equals(getContraseña())) {
                    idUsuarioActivo = rs.getInt("id");
                    nombreCompleto = rs.getString("info_usuario.nombre") + " " + rs.getString("info_usuario.apellido_paterno") + " " + rs.getString("info_usuario.apellido_materno");
                    rol = rs.getString("usuario.tipo_usuario");
                    Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
                    dialogoAlerta.setTitle("Inicio de sesion");
                    dialogoAlerta.setContentText("Iniciando sesion...");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();

                    loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/VentanaInicial.fxml"));
                   
                    Parent root1 = (Parent) loaderInicioAdmin.load();
                    ventanaInicio = new Stage();
                    ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
                    ventanaInicio.setScene(new Scene(root1));

                    ventanaInicio.setTitle("Punto de venta");
                    ventanaInicio.setResizable(false);
                    ventanaInicio.show();
                    return true;
                } else {
                    System.out.println(password);
                    System.out.println(passwordDes);
                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                    dialogoAlerta.setTitle("Advertencia");
                    dialogoAlerta.setHeaderText("Datos No Validos");
                    dialogoAlerta.setContentText("Contraseña incorrecta");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    return false;
                }
            } else {
                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Advertencia");
                dialogoAlerta.setHeaderText("Datos No Validos");
                dialogoAlerta.setContentText("Usuario incorrecto");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                return false;
            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }
        return false;
    }

    public boolean iniciarSesion() throws SQLException, IOException {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        Statement execute2 = st.createStatement();
        PreparedStatement pst2 = st.prepareStatement(
                "SELECT * FROM usuario INNER JOIN info_usuario ON usuario.id = info_usuario.usuario_id WHERE username= ? And password = ?");
        pst2.setString(1, this.getUsuario());
        pst2.setString(2, this.getContraseña());
        rs = pst2.executeQuery();
        if (rs.next()) {
            idUsuarioActivo = rs.getInt("id");
            nombreCompleto = rs.getString("info_usuario.nombre") + " " + rs.getString("info_usuario.apellido_paterno") + " " + rs.getString("info_usuario.apellido_materno");
            rol = rs.getString("usuario.tipo_usuario");
            Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
            dialogoAlerta.setTitle("Inicio de sesion");
            dialogoAlerta.setContentText("Iniciando sesion...");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();

            loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/VentanaInicial.fxml"));
            InicioSesion.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
            Parent root1 = (Parent) loaderInicioAdmin.load();
            ventanaInicio = new Stage();
            ventanaInicio.setScene(new Scene(root1));

            ventanaInicio.setTitle("Punto de venta");
            ventanaInicio.setResizable(false);
            ventanaInicio.show();
            return true;
        } else {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Advertencia");
            dialogoAlerta.setHeaderText("Datos No Validos");
            dialogoAlerta.setContentText("Usuario o Contraseña incorrecta");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
            return false;

        }
    }
}
