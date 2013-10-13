/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Carmelita
 */
public class conexion2 {

    public String puerto = "3306";
    public String nomservidor = "localhost";
    public String db = "db_prefectura";
    public String user = "root";
    public String pass = "root_app";
    Connection conn = null;

    /**
     * @function: conectar
     * @author: PERSONALCONTROL
     * @description: esta funcion se encarga de conectar la base de datos con el
     * servidor
     * @access: public
     * @return
     */
    public Connection conectar() {
        try {
            String ruta = "jdbc:mysql://";
            System.out.println("jdbc:mysql://");
            String servidor = nomservidor + ":" + puerto + "/";
            System.out.println(nomservidor + ":" + puerto + "/");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(ruta + servidor + db, user, pass);
            if (conn != null) {
                System.out.println("Conección a base de datos listo…");
            } else if (conn == null) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Se produjo el siguiente error: " + e.getMessage());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Se produjo el siguiente error:" + e.getMessage());
        } finally {
            return conn;
        }
    }

    /**
     * @function: desconectar
     * @author: PERSONALCONTROL
     * @description: esta funcion se encarga de realizar la desconexion de la
     * base de datos con el servidor
     * @access: public
     * @return
     */
    public void desconectar() {
        conn = null;
        System.out.println("Desconexion a base de datos listo…");
    }
}
