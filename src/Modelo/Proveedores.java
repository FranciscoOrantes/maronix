/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Francisco
 */
public class Proveedores {

    Conexion con = new Conexion();
    Connection st = con.conectate();

    private String razon_social;

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    private String razonSocial;
    private String direccion;
    private String telefono;
    private String correo;

    public Integer getId() {
        return id.get();
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public String getRazon_socialT() {
        return razon_socialT.get();
    }

    public void setRazon_socialT(SimpleStringProperty razon_socialT) {
        this.razon_socialT = razon_socialT;
    }

    public String getDireccionT() {
        return direccionT.get();
    }

    public void setDireccionT(SimpleStringProperty direccionT) {
        this.direccionT = direccionT;
    }

    public String getTelefonoT() {
        return telefonoT.get();
    }

    public void setTelefonoT(SimpleStringProperty telefonoT) {
        this.telefonoT = telefonoT;
    }

    public String getCorreoT() {
        return correoT.get();
    }

    public void setCorreoT(SimpleStringProperty correoT) {
        this.correoT = correoT;
    }

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty razon_socialT = new SimpleStringProperty();
    public SimpleStringProperty direccionT = new SimpleStringProperty();
    public SimpleStringProperty telefonoT = new SimpleStringProperty();
    public SimpleStringProperty correoT = new SimpleStringProperty();
    public SimpleStringProperty statusT = new SimpleStringProperty();

    public String getStatusT() {
        return statusT.get();
    }

    public void setStatusT(SimpleStringProperty statusT) {
        this.statusT = statusT;
    }

    public Proveedores() {
    }

    public Proveedores(Integer id, String razon_social, String direccion, String telefono, String correo,String status) {
        this.id = new SimpleIntegerProperty(id);
        this.razon_socialT = new SimpleStringProperty(razon_social);
        this.direccionT = new SimpleStringProperty(direccion);
        this.telefonoT = new SimpleStringProperty(telefono);
        this.correoT = new SimpleStringProperty(correo);
        this.statusT = new SimpleStringProperty(status);

    }

    public Proveedores(String razon_social) {
        this.razon_social = razon_social;

    }

    public static void llenarInfoProveedores(ObservableList<Proveedores> lista) {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT * FROM proveedor");
            rs = pst.executeQuery();
            while (rs.next()) {

                lista.add(
                        new Proveedores(
                                rs.getInt("proveedor.id"),
                                rs.getString("proveedor.razon_social"),
                                rs.getString("proveedor.direccion"),
                                rs.getString("proveedor.telefono"),
                                rs.getString("proveedor.correo"),
                                rs.getString("proveedor.status")
                        )
                );

            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }

    }

    public void registrarProveedor() throws SQLException, Exception {

        Statement execute = st.createStatement();
        PreparedStatement pst = st.prepareStatement(
                "INSERT INTO proveedor(razon_social,direccion,telefono,correo,status) VALUES(?,?,?,?,?)");
        pst.setString(1, getRazonSocial());
        pst.setString(2, getDireccion());
        pst.setString(3, getTelefono());
        pst.setString(4, getCorreo());
        pst.setString(5, "Alta");
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

    public static void llenarListaProveedores(ArrayList<String> lista, ArrayList<Integer> listaId) {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT * FROM proveedor");
            rs = pst.executeQuery();
            while (rs.next()) {

                lista.add(
                        rs.getString("proveedor.razon_social")
                );
                listaId.add(
                        rs.getInt("proveedor.id")
                );

            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }
    }

    public void actualizar() throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE proveedor SET razon_social = ?, direccion = ?, telefono = ?, correo = ? WHERE id = ?");

            // pst.setString(1, nombre);
            //pst.setString(2, apellidoPaterno);
            //pst.setString(3, apellidoMaterno);
            pst.setString(4, telefono);

            //pst.setInt(5, id);
            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se han actualizado los Datos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            }

        } catch (Exception e) {
            System.err.println("EXCEPCION " + e);
            Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
            dialogoAlerta.setTitle("Error");
            dialogoAlerta.setHeaderText("Ha ocurrido un error con la Base de Datos");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }
        st.close();

    }

    public void actualizar(int id, String razon, String direccion, String telefono, String correo) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE proveedor SET razon_social = ?, direccion = ?, telefono = ?, correo = ? WHERE id = ?");

            pst.setString(1, razon);
            pst.setString(2, direccion);
            pst.setString(3, telefono);
            pst.setString(4, correo);
            pst.setInt(5, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se han actualizado los Datos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            }

        } catch (Exception e) {

            Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
            dialogoAlerta.setTitle("Error");
            dialogoAlerta.setHeaderText("Ha ocurrido un error con la Base de Datos");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }
        st.close();
    }
     public static void buscarProveedores(ObservableList<Proveedores> lista, String valor) {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT * FROM proveedor WHERE info_usuario.nombre LIKE '%" + valor + "%'");
            rs = pst.executeQuery();
            while (rs.next()) {

                lista.add(
                        new Proveedores(
                                rs.getInt("proveedor.id"),
                                rs.getString("proveedor.razon_social"),
                                rs.getString("proveedor.direccion"),
                                rs.getString("proveedor.telefono"),
                                rs.getString("proveedor.correo"),
                                rs.getString("proveedor.status")
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
            PreparedStatement pst = st.prepareStatement("UPDATE proveedor SET status = ? WHERE id = ?");

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
            PreparedStatement pst = st.prepareStatement("UPDATE proveedor SET status = ? WHERE id = ?");

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
