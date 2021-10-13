package mintic.edu.tiendaVirtual.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jhon
 */
public class VentaDAO {

    // Definir los Atributos. Capa de Datos. Se comunica con la BDs
    Connection con = null; // Hacer la conexion a la BDs
    Conexion cn = new Conexion();
    Statement stm = null; // Separa el espacio para construir un comando SQL
    ResultSet res = null; // Guarda el resultado de la consulta
    PreparedStatement ps = null;

    public int calcularIdVenta() {
        int idVenta = 0;
        String sql = "SELECT max(cod_venta) from venta";
        try {
            con = cn.Conexion();
            stm = con.createStatement();
            res = stm.executeQuery(sql);
            while (res.next()) {
                idVenta = res.getInt(1);
            }
            stm.close();;
            con.close();;
            res.close();
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        return idVenta;
    }

    public boolean agregarVenta(Venta venta) {
        boolean registrar = false; // Permite identificar si ya existe el usuario
        boolean encontrado = false; // Encuentra un usuario con el correo Institucional
        String buscar = "SELECT * FROM venta where cod_venta = " // Instrucción sql
                + venta.getCod_venta(); // Para buscar un registro con el mismo id
        encontrado = buscar(buscar); // Ejecutamos el método con la consulta
        if (!encontrado) {
            // La instrucción para insertar el registro
            String sql = "INSERT INTO venta values (" + venta.getCod_venta()
                    + ", " + venta.getIdCliente()
                    + " ," + venta.getIdUsuario()
                    + " , " + venta.getIvaVenta()
                    + "," + venta.getTotalVenta() 
                    + "," + venta.getValorVenta()
                    + ")";
            try {
                con = cn.Conexion();
                stm = con.createStatement();
                stm.execute(sql);
                registrar = true;
                stm.close();
                con.close();
            } catch (SQLException e) {
                System.out.println("Error: Clase VentaDao, método agregarVenta");
                e.printStackTrace();
            }
        }
        
        return registrar;
    }

    public boolean buscar(String sql) {
        boolean encontrado = false;
        con = cn.Conexion();
        try {
            stm = con.createStatement();
            res = stm.executeQuery(sql);
            while (res.next()) {
                encontrado = true;
            }
        } catch (SQLException e) {
            System.out.println("Mensaje:" + e.getMessage());
            System.out.println("Estado:" + e.getSQLState());
            System.out.println("Codigo del error:" + e.getErrorCode());
            System.out.println("Error: Clase ClienteDao, método agregarCliente" + e.getMessage());
        }
        return encontrado;
    }
}
