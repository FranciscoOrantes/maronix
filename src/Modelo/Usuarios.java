/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Francisco
 */
public class Usuarios {

    Conexion con = new Conexion();
    Connection st = con.conectate();

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty nombreT = new SimpleStringProperty();
    public SimpleStringProperty apellidoPaternoT = new SimpleStringProperty();
    public SimpleStringProperty apellidoMaternoT = new SimpleStringProperty();
    public SimpleStringProperty usuarioT = new SimpleStringProperty();
    public SimpleStringProperty tipo_usuarioT = new SimpleStringProperty();

    public String getStatusT() {
        return statusT.get();
    }

    public void setStatusT(SimpleStringProperty statusT) {
        this.statusT = statusT;
    }
    public SimpleStringProperty statusT = new SimpleStringProperty();

    private String usuario;
    private String password;
    private String tipo_usuario;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private int usuario_id;
    private String llave_secreta = "KirtronSoluciones";
    private String existe;
    public Usuarios() {
    }

    public Usuarios(Integer id, String nombre, String apellidoPaterno, String apellidoMaterno, String usuario, String tipo_usuario,String status) {
        this.id = new SimpleIntegerProperty(id);
        this.nombreT = new SimpleStringProperty(nombre);
        this.apellidoPaternoT = new SimpleStringProperty(apellidoPaterno);
        this.apellidoMaternoT = new SimpleStringProperty(apellidoMaterno);
        this.usuarioT = new SimpleStringProperty(usuario);
        this.tipo_usuarioT = new SimpleStringProperty(tipo_usuario);
        this.statusT = new SimpleStringProperty(status);

    }

    public Integer getId() {
        return id.get();
    }

    public String getNombreT() {
        return nombreT.get();
    }

    public void setNombreT(SimpleStringProperty nombreT) {
        this.nombreT = nombreT;
    }

    public String getApellidoPaternoT() {
        return apellidoPaternoT.get();
    }

    public void setApellidoPaternoT(SimpleStringProperty apellidoPaternoT) {
        this.apellidoPaternoT = apellidoPaternoT;
    }

    public String getApellidoMaternoT() {
        return apellidoMaternoT.get();
    }

    public void setApellidoMaternoT(SimpleStringProperty apellidoMaternoT) {
        this.apellidoMaternoT = apellidoMaternoT;
    }

    public String getUsuarioT() {
        return usuarioT.get();
    }

    public void setUsuarioT(SimpleStringProperty usuarioT) {
        this.usuarioT = usuarioT;
    }

    public String getTipo_usuarioT() {
        return tipo_usuarioT.get();
    }

    public void setTipo_usuarioT(SimpleStringProperty tipo_usuarioT) {
        this.tipo_usuarioT = tipo_usuarioT;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String encriptar() {
        String passwordEncriptada = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] llavePassword = md5.digest(llave_secreta.getBytes("utf-8"));
            byte[] bytesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey key = new SecretKeySpec(bytesKey, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainTextBytes = getPassword().getBytes("utf-8");
            byte[] buf = cifrado.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            passwordEncriptada = new String(base64Bytes);
        } catch (Exception e) {
        }
        return passwordEncriptada;
    }
    
   
    
    public void checarUsuario(String usuario){
    Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT username FROM usuario WHERE username=? AND status='Alta'");
            pst.setString(1, usuario);
            rs = pst.executeQuery();
            if (rs.next()) {
                existe="Si";
            }else{
            existe = "No";
            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }
   
    }
    
    public void registrarUsuario() throws SQLException, Exception {
        checarUsuario(getUsuario());
        if(existe.equals("Si")){
         Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
            dialogoAlerta.setTitle("Error");
            dialogoAlerta.setHeaderText("Este usuario ya existe");
            dialogoAlerta.setContentText("Por favor ponga otro nombre de usuario");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }else{
        password = encriptar();
        Statement execute = st.createStatement();
        PreparedStatement pst = st.prepareStatement(
                "INSERT INTO usuario(username,password,tipo_usuario,status) VALUES(?,?,?,?)");
        pst.setString(1, getUsuario());
        pst.setString(2, password);
        pst.setString(3, getTipo_usuario());
        pst.setString(4,"Alta");
        int res = pst.executeUpdate();
        if (res > 0) {
            obtenerUltimoId();
        }
        }
    }

    public void obtenerUltimoId() {

        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT MAX(id) as ultimoId FROM usuario");
            rs = pst.executeQuery();
            if (rs.next()) {
                usuario_id = rs.getInt("ultimoId");
                registrarDatosUsuario(usuario_id);
            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }
    }

    public void registrarDatosUsuario(int usuario_id) throws SQLException {
        Statement execute = st.createStatement();
        PreparedStatement pst = st.prepareStatement(
                "INSERT INTO info_usuario(usuario_id,nombre,apellido_paterno,apellido_materno) VALUES(?,?,?,?)");
        pst.setInt(1, usuario_id);
        pst.setString(2, getNombre());
        pst.setString(3, getApellido_paterno());
        pst.setString(4, getApellido_materno());
        int res = pst.executeUpdate();
        if (res > 0) {
            mensajeExito();
        }
    }

    public void mensajeExito() {
        Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
        dialogoAlerta.setTitle("Exito");
        dialogoAlerta.setHeaderText("Se han guardado los Datos");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
    }

    public static void llenarInfoUsuarios(ObservableList<Usuarios> lista) {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT * FROM info_usuario INNER JOIN usuario ON info_usuario.usuario_id=usuario.id");
            rs = pst.executeQuery();
            while (rs.next()) {

                lista.add(
                        new Usuarios(
                                rs.getInt("usuario.id"),
                                rs.getString("info_usuario.nombre"),
                                rs.getString("info_usuario.apellido_paterno"),
                                rs.getString("info_usuario.apellido_materno"),
                                rs.getString("usuario.username"),
                                rs.getString("usuario.tipo_usuario"),
                                rs.getString("usuario.status")
                        )
                );

            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }
    }
    public void actualizar(int id, String nombre, String apellidoPaterno, String apellidoMaterno,String usuario, String rol) throws SQLException{
    Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE info_usuario INNER JOIN usuario ON info_usuario.usuario_id=usuario.id SET info_usuario.nombre = ?, info_usuario.apellido_paterno = ?, info_usuario.apellido_materno = ?, usuario.username = ?, usuario.tipo_usuario = ?  WHERE info_usuario.usuario_id = ?");

            pst.setString(1, nombre);
            pst.setString(2, apellidoPaterno);
            pst.setString(3, apellidoMaterno);
            pst.setString(4, usuario);
            pst.setString(5, rol);
            pst.setInt(6, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se han actualizado los Datos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            }

        } catch (Exception e) {
           System.err.println(e);
            Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
            dialogoAlerta.setTitle("Error");
            dialogoAlerta.setHeaderText("Ha ocurrido un error con la Base de Datos");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }
        st.close();
    }
    
    public static void buscarUsuarios(ObservableList<Usuarios> lista,String valor) {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT * FROM info_usuario INNER JOIN usuario ON info_usuario.usuario_id=usuario.id WHERE info_usuario.nombre LIKE '%" + valor + "%'"+" OR info_usuario.apellido_paterno LIKE '%" + valor + "%'"+" OR info_usuario.apellido_materno LIKE '%" + valor + "%'");
            rs = pst.executeQuery();
            while (rs.next()) {

                lista.add(
                        new Usuarios(
                                rs.getInt("usuario.id"),
                                rs.getString("info_usuario.nombre"),
                                rs.getString("info_usuario.apellido_paterno"),
                                rs.getString("info_usuario.apellido_materno"),
                                rs.getString("usuario.username"),
                                rs.getString("usuario.tipo_usuario"),
                                rs.getString("usuario.status")
                        )
                );

            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }
    }
    public void darDeBaja(int id) throws SQLException{
     Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE usuario SET status = ? WHERE id = ?");

            pst.setString(1, "Baja");
          
            pst.setInt(2, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se ha dado de baja con éxito");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            }

        } catch (Exception e) {
           System.err.println(e);
            Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
            dialogoAlerta.setTitle("Error");
            dialogoAlerta.setHeaderText("Ha ocurrido un error con la Base de Datos");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }
        st.close();
    }
    
    public void reactivar(int id) throws SQLException{
     Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE usuario SET status = ? WHERE id = ?");

            pst.setString(1, "Alta");
          
            pst.setInt(2, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se ha reactivado con éxito");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            }

        } catch (Exception e) {
           System.err.println(e);
            Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
            dialogoAlerta.setTitle("Error");
            dialogoAlerta.setHeaderText("Ha ocurrido un error con la Base de Datos");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }
        st.close();
    }
}
