/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Francisco
 */
public class Productos {
    public static String actualizacionTabla;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    private String codigo;
    private String codigo_barras;
    public static String respuesta;

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getSustancia_activa() {
        return sustancia_activa;
    }

    public void setSustancia_activa(String sustancia_activa) {
        this.sustancia_activa = sustancia_activa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    private String presentacion;
    private String sustancia_activa;
    private String tipo;
    private String rubro;
    private int idProveedor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public String getCodigoT() {
        return codigoT.get();
    }

    public void setCodigoT(SimpleStringProperty codigoT) {
        this.codigoT = codigoT;
    }

    public String getNombreT() {
        return nombreT.get();
    }

    public void setNombreT(SimpleStringProperty nombreT) {
        this.nombreT = nombreT;
    }

    public String getDescripcionT() {
        return descripcionT.get();
    }

    public void setDescripcionT(SimpleStringProperty descripcionT) {
        this.descripcionT = descripcionT;
    }

    public Double getPrecioT() {
        return precioT.get();
    }

    public void setPrecioT(SimpleDoubleProperty precioT) {
        this.precioT = precioT;
    }

    public Integer getCantidadT() {
        return cantidadT.get();
    }

    public void setCantidadT(SimpleIntegerProperty cantidadT) {
        this.cantidadT = cantidadT;
    }

    public String getPresentacionT() {
        return presentacionT.get();
    }

    public void setPresentacionT(SimpleStringProperty presentacionT) {
        this.presentacionT = presentacionT;
    }

    public String getSustanciaT() {
        return sustanciaT.get();
    }

    public void setSustanciaT(SimpleStringProperty sustanciaT) {
        this.sustanciaT = sustanciaT;
    }

    public String getTipoT() {
        return tipoT.get();
    }

    public void setTipoT(SimpleStringProperty tipoT) {
        this.tipoT = tipoT;
    }

    public String getRubroT() {
        return rubroT.get();
    }

    public void setRubroT(SimpleStringProperty rubroT) {
        this.rubroT = rubroT;
    }

    public String getProveedorT() {
        return proveedorT.get();
    }

    public void setProveedorT(SimpleStringProperty proveedorT) {
        this.proveedorT = proveedorT;
    }

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty codigoT = new SimpleStringProperty();
    public SimpleStringProperty nombreT = new SimpleStringProperty();
    public SimpleStringProperty descripcionT = new SimpleStringProperty();
    public SimpleDoubleProperty precioT = new SimpleDoubleProperty();
    public SimpleIntegerProperty cantidadT = new SimpleIntegerProperty();
    public SimpleStringProperty presentacionT = new SimpleStringProperty();
    public SimpleStringProperty sustanciaT = new SimpleStringProperty();
    public SimpleStringProperty tipoT = new SimpleStringProperty();
    public SimpleStringProperty rubroT = new SimpleStringProperty();
    public SimpleStringProperty proveedorT = new SimpleStringProperty();

    public String getStatusT() {
        return statusT.get();
    }

    public void setStatusT(SimpleStringProperty statusT) {
        this.statusT = statusT;
    }
    public SimpleStringProperty statusT = new SimpleStringProperty();

    public Productos() {
    }

    //Agregar a la tabla Ventas
    public Productos(Integer id, int cantidad, String codigo, String descripcion, Double precio) {
        this.id = new SimpleIntegerProperty(id);
        this.cantidadT = new SimpleIntegerProperty(cantidad);
        this.codigoT = new SimpleStringProperty(codigo);

        this.descripcionT = new SimpleStringProperty(descripcion);
        this.precioT = new SimpleDoubleProperty(precio);
    }

    public Productos(Integer id, String codigo, String descripcion, Double precio, Integer cantidad,
            String proveedor, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.codigoT = new SimpleStringProperty(codigo);

        this.descripcionT = new SimpleStringProperty(descripcion);
        this.precioT = new SimpleDoubleProperty(precio);
        this.cantidadT = new SimpleIntegerProperty(cantidad);

        this.proveedorT = new SimpleStringProperty(proveedor);
        this.statusT = new SimpleStringProperty(status);
    }

    public void registrarProductos(String codigo, String rutaCodigo) {

        Conexion con = new Conexion();
        Connection st = con.conectate();
        try {

            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("INSERT INTO producto(descripcion,precio,cantidad,codigo_barras,proveedor_id,status,rutaCodigo) VALUES(?,?,?,?,?,?,?)");

            pst.setString(1, getDescripcion());
            pst.setDouble(2, getPrecio());
            pst.setInt(3, getCantidad());
            pst.setString(4, codigo);
            pst.setInt(5, getIdProveedor());
            pst.setString(6, "Alta");
            pst.setString(7, rutaCodigo);
            int res = pst.executeUpdate();
            if (res > 0) {
                mensajeExito(rutaCodigo);
            }

        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }

    public void mensajeExito(String direccion) throws IOException {
        Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION, "Confirmación de impresión de código", ButtonType.YES, ButtonType.NO);
        dialogoAlerta.setTitle("Registro de productos");
        dialogoAlerta.setHeaderText("Ha registrado un producto con éxito");
        dialogoAlerta.setContentText("El código de barras ha sido guardado con éxito, desea imprimir su código de barras en este momento?");

        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
        if (dialogoAlerta.getResult() == ButtonType.YES) {
            respuesta = "Si";
            Reportes reportes = new Reportes();
            reportes.abrirCodigoDeBarras(direccion);
            actualizacionTabla="Si";
        } else {
            respuesta = "No";
            actualizacionTabla="Si";
        }
    }

    public static void llenarInfoProductos(ObservableList<Productos> lista) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT * FROM producto INNER JOIN proveedor ON producto.proveedor_id=proveedor.id");

            rs = pst.executeQuery();
            while (rs.next()) {

                lista.add(
                        new Productos(
                                rs.getInt("producto.id"),
                                rs.getString("producto.codigo_barras"),
                                rs.getString("producto.descripcion"),
                                rs.getDouble("producto.precio"),
                                rs.getInt("producto.cantidad"),
                                rs.getString("proveedor.razon_social"),
                                rs.getString("producto.status")
                        )
                );

            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }
        st.close();
    }

    public static boolean filtradoCodigo(ObservableList<Productos> lista, int cantidad, String codigo) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;
        Statement execute = st.createStatement();
        PreparedStatement pst = st.prepareStatement(
                "SELECT * FROM producto WHERE codigo_barras= ?");
        pst.setString(1, codigo);
        rs = pst.executeQuery();
        while (rs.next()) {
            if (rs.getInt("producto.cantidad") < cantidad || rs.getString("producto.status").equals("Baja")) {

                if (rs.getInt("producto.cantidad") < cantidad) {
                    Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
                    dialogoAlerta.setTitle("Advertencia");
                    dialogoAlerta.setHeaderText("Cantidad no válida");
                    dialogoAlerta.setContentText("No existe esa cantidad en el inventario");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    return false;
                }
                if (rs.getString("producto.status").equals("Baja")) {
                    Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
                    dialogoAlerta.setTitle("Advertencia");
                    dialogoAlerta.setHeaderText("Producto dado de baja");
                    dialogoAlerta.setContentText("Ese producto está inactivo, favor de contactar al administrador");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    return false;
                }
            } else {
                lista.add(
                        new Productos(
                                rs.getInt("producto.id"),
                                cantidad,
                                rs.getString("producto.codigo_barras"),
                                rs.getString("producto.descripcion"),
                                rs.getDouble("producto.precio")
                        )
                );
                return true;
            }

        }
        Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
        dialogoAlerta.setTitle("Advertencia");
        dialogoAlerta.setHeaderText("Producto no válido");
        dialogoAlerta.setContentText("El código introducido no existe");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
        return false;
    }

    public static void filtradoBuscar(ObservableList<Productos> lista, String codigo) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;
        Statement execute = st.createStatement();
        PreparedStatement pst = st.prepareStatement(
                "SELECT * FROM producto INNER JOIN proveedor ON producto.proveedor_id=proveedor.id WHERE producto.codigo_barras LIKE '%" + codigo + "%'" + " OR producto.descripcion LIKE '%" + codigo + "%'");

        rs = pst.executeQuery();
        while (rs.next()) {
            lista.add(
                    new Productos(
                            rs.getInt("producto.id"),
                            rs.getString("producto.codigo_barras"),
                            rs.getString("producto.descripcion"),
                            rs.getDouble("producto.precio"),
                            rs.getInt("producto.cantidad"),
                            rs.getString("proveedor.razon_social"),
                            rs.getString("producto.status")
                    )
            );
        }
    }

    public void actualizar(int id, String descripcion, Double precio, int cantidad, int proveedor_id,String ruta) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE producto SET descripcion = ?, precio = ?, cantidad = ?"
                    + ", proveedor_id = ?, rutaCodigo= ? WHERE id = ?");

            pst.setString(1, descripcion);
            pst.setDouble(2, precio);
            pst.setInt(3, cantidad);

            pst.setInt(4, proveedor_id);
            pst.setString(5, ruta);
            pst.setInt(6, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se han actualizado los Datos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                actualizacionTabla="Si";
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

    public void darDeBaja(int id) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE producto SET status = ? WHERE id = ?");

            pst.setString(1, "Baja");

            pst.setInt(2, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se ha dado de baja el producto");
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

    public void reactivar(int id) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE producto SET status = ? WHERE id = ?");

            pst.setString(1, "Alta");

            pst.setInt(2, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se ha reactivado el producto");
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

    public String obtenerRutaCodigo(int id) throws SQLException {
        String ruta = null;
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;
        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("SELECT rutaCodigo FROM producto WHERE id = ?");

            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                ruta = rs.getString("rutaCodigo");
                return ruta;
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
        return ruta;
    }
    
    public String obtenerCodigo(String codigo) throws SQLException {
        String codigo_barras = null;
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;
        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("SELECT codigo_barras FROM producto WHERE codigo_barras = ?");

            pst.setString(1, codigo);
            rs = pst.executeQuery();

            if (rs.next()) {
                codigo_barras = rs.getString("codigo_barras");
                return codigo_barras;
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
        return codigo_barras;
    }
    

    public void eliminar(int id) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("DELETE producto.* FROM producto WHERE producto.id = ?");

            pst.setInt(1, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se ha eliminado el producto");
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
    public void actualizarDireccion(int id, String direccion) throws SQLException{
    Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE producto SET rutaCodigo = ? WHERE id = ?");

            pst.setString(1, direccion);

            pst.setInt(2, id);

            int res = pst.executeUpdate();

            if (res > 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Exito");
                dialogoAlerta.setHeaderText("Se ha generado los códigos de barra con éxito");
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
