package Entidades;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se utiliza para establecer una conexión a una base de datos Oracle,
 * realizar consultas y ejecutar inserciones en la base de datos.
 */
public class Conexion {
    
    private Connection conn; // Objeto de conexión a la base de datos
    private Statement stm; // Objeto utilizado para ejecutar consultas SQL
    

    public Conexion(String pIP, String pListener, String pUsuario, String pClave){
        try {
            // Carga el controlador de la base de datos
            Class.forName("oracle.jdbc.OracleDriver");
            // Establece la conexión utilizando los parámetros dados
            this.conn = DriverManager.getConnection("jdbc:oracle:thin:@" + pIP + ":1521:" + pListener, pUsuario, pClave);
            this.stm = this.conn.createStatement();
        } catch (SQLException ee) {
            System.out.println("Error en conexion: " + ee.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    // Método para obtener la conexión a la base de datos.
    public Connection getConnection(){
        return this.conn;
        
    }
    
    // Método para realizar consultas SQL y devolver los resultados en una lista.
    public ArrayList<Object[]> consultar(String query){
        ArrayList<Object[]> aResultados = new ArrayList<>();
        
        try{
            // Ejecuta la consulta SQL y obtiene un conjunto de resultados.
            ResultSet rs = this.stm.executeQuery(query);
            int columnCount = rs.getMetaData().getColumnCount();
            
            // Itera a través de los resultados y los almacena en un arreglo.
            while(rs.next()){
                Object[] fila = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    fila[i - 1] = rs.getObject(i);
                }
                aResultados.add(fila);
            }
        } catch (SQLException ee) {
            ee.printStackTrace();
            System.out.println("Error consultando en clase conexion: " + ee.getMessage());
        }

        return aResultados; 
    }
    
    // Método para realizar inserciones en la base de datos.
    public void insertar(String query, Object[] params){
        try{
            // Prepara una sentencia SQL para la inserción.
            PreparedStatement pStmt = this.conn.prepareStatement(query);
            if (params != null) {
                // Establece los parámetros en la sentencia SQL.
                for(int i = 0; i < params.length; i++)
                    pStmt.setObject(i + 1, params[i]);
                // Ejecuta la inserción.
                pStmt.executeUpdate();
            }
        } catch(Exception ee){
            System.out.println("Error en insercion");
        }
    }
    
}
