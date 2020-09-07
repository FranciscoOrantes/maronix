/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Francisco
 */
public class Ventas {

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty productoT = new SimpleStringProperty();

    public String getCodigoT() {
        return codigoT.get();
    }

    public void setCodigoT(SimpleStringProperty codigoT) {
        this.codigoT = codigoT;
    }
    public SimpleStringProperty codigoT = new SimpleStringProperty();
    public SimpleIntegerProperty cantidadT = new SimpleIntegerProperty();
    public SimpleDoubleProperty montoT = new SimpleDoubleProperty();
    public SimpleStringProperty cajeroT = new SimpleStringProperty();

    public Integer getId() {
        return id.get();
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public String getProductoT() {
        return productoT.get();
    }

    public void setProductoT(SimpleStringProperty productoT) {
        this.productoT = productoT;
    }

    public Integer getCantidadT() {
        return cantidadT.get();
    }

    public void setCantidadT(SimpleIntegerProperty cantidadT) {
        this.cantidadT = cantidadT;
    }

    public Double getMontoT() {
        return montoT.get();
    }

    public void setMontoT(SimpleDoubleProperty montoT) {
        this.montoT = montoT;
    }

    public String getCajeroT() {
        return cajeroT.get();
    }

    public void setCajeroT(SimpleStringProperty cajeroT) {
        this.cajeroT = cajeroT;
    }

    public Ventas() {
    }

    public Ventas(Integer id, String producto, String codigo, Integer cantidad, Double monto, String cajero) {
        this.id = new SimpleIntegerProperty(id);
        this.productoT = new SimpleStringProperty(producto);
        this.codigoT = new SimpleStringProperty(codigo);
        this.cantidadT = new SimpleIntegerProperty(cantidad);
        this.montoT = new SimpleDoubleProperty(monto);
        this.cajeroT = new SimpleStringProperty(cajero);
    }

    public static int recuperarCantidad(int id) {
        int cantidad = 0;
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs = null;
        try {
            PreparedStatement pstVenta = st.prepareStatement(
                    "SELECT cantidad FROM producto WHERE id = ? ");
            pstVenta.setInt(1, id);
            rs = pstVenta.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
        } catch (Exception e) {

        }
        return cantidad;
    }

    public static void actualizarCantidades(List<Integer> cantidades, ObservableList<Productos> ventas) throws SQLException {
        Conexion con = new Conexion();
        Connection st = con.conectate();

        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("UPDATE producto SET cantidad = ? WHERE id = ?");
            for (Productos producto1 : ventas) {
                int index = ventas.indexOf(producto1);

                System.out.println("CANTIDAD COMPRADA " + cantidades.get(index));
                int cantidadOriginal = recuperarCantidad(producto1.getId());
                System.out.println("CANTIDAD ORIGINAL " + cantidadOriginal);
                int cantidad = cantidadOriginal - cantidades.get(index);
                System.out.println("CANTIDAD RESULTANTE " + cantidad);
                int idProducto = producto1.getId();
                System.out.println("ID PRODUCTO " + idProducto);
                pst.setInt(1, cantidad);

                pst.setInt(2, idProducto);
                int res = pst.executeUpdate();

                if (res > 0) {

                }
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

    public static void registrarVentas(List<Integer> cantidades, ObservableList<Productos> ventas, String fecha, int usuario_id) throws SQLException {
        Conexion con = new Conexion();
        SecureRandom random = new SecureRandom();
        String folio = new BigInteger(130, random).toString(32);
        double monto = 0;
        Connection st = con.conectate();
        try {
            Statement execute = st.createStatement();
            PreparedStatement pst = st.prepareStatement("INSERT INTO venta(producto_id,cantidad,monto,fecha,folio,usuario_id) VALUES(?,?,?,?,?,?)");
            for (Productos producto1 : ventas) {
                System.out.println("Cantidades " + cantidades.size());
                System.out.println(producto1.getPrecioT());
                int index = ventas.indexOf(producto1);
                System.out.println("Cantidades array " + cantidades.get(index));
                int cantidad = cantidades.get(index);
                monto = cantidad * (producto1.getPrecioT());

                System.out.println("Monto " + monto);
                pst.setInt(1, producto1.getId());
                pst.setInt(2, cantidad);
                pst.setDouble(3, monto);
                pst.setString(4, fecha);
                pst.setString(5, folio);
                pst.setInt(6, usuario_id);
                pst.executeUpdate();
            }
            actualizarCantidades(cantidades, ventas);
        } catch (Exception e) {
            System.err.println("ERROR " + e);
        }
    }

    public static void llenarInfoVentas(String fecha, ObservableList<Ventas> lista) {
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs;

        try {
            Statement execute = st.createStatement();

            PreparedStatement pst = st.prepareStatement(
                    "SELECT * FROM venta INNER JOIN producto ON venta.producto_id = producto.id INNER JOIN info_usuario ON venta.usuario_id=info_usuario.usuario_id WHERE fecha=?");
            pst.setString(1, fecha);
            rs = pst.executeQuery();
            while (rs.next()) {

                lista.add(
                        new Ventas(
                                rs.getInt("venta.id"),
                                rs.getString("producto.descripcion"),
                                rs.getString("producto.codigo_barras"),
                                rs.getInt("venta.cantidad"),
                                rs.getDouble("venta.monto"),
                                rs.getString("info_usuario.nombre") + " " + rs.getString("info_usuario.apellido_paterno") + " " + rs.getString("info_usuario.apellido_materno")
                        )
                );

            }

        } catch (Exception e) {
            System.err.println("excetpcion " + e);

        }
    }

    public Double sumaTotalDia(String fecha) throws SQLException {
        Double total = 0.0;
        Conexion con = new Conexion();
        Connection st = con.conectate();
        ResultSet rs = null;
        try {
            PreparedStatement pstVenta = st.prepareStatement(
                    "SELECT SUM(venta.monto)as suma FROM venta WHERE fecha = ? ");
            pstVenta.setString(1, fecha);
            rs = pstVenta.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("suma");
            }
        } catch (Exception e) {

        }
        return total;
    }
}
