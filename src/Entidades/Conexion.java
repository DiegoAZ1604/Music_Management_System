package Entidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;

/**
 * Esta clase se utiliza para establecer una conexión a una base de datos Oracle,
 * realizar consultas y ejecutar inserciones en la base de datos.
 */
public class Conexion {
    
    private static final Logger LOGGER = Logger.getLogger(Conexion.class.getName());
    private Connection conn; // Objeto de conexión a la base de datos
    private Statement stm; // Objeto utilizado para ejecutar consultas SQL
    
    

    public Conexion(String pIP, String pListener, String pUsuario, String pClave) throws SQLException{
        try {
            // Carga el controlador de la base de datos
            Class.forName("oracle.jdbc.OracleDriver");
            // Establece la conexión utilizando los parámetros dados
            this.conn = DriverManager.getConnection("jdbc:oracle:thin:@" + pIP + ":1521:" + pListener, pUsuario, pClave);
            this.stm = this.conn.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Error en la conexión: Controlador no encontrado.", ex);
        }
    }

    
    // Método para obtener la conexión a la base de datos.
    public Connection getConnection(){
        return this.conn;
        
    }
    
    // Método para realizar consultas SQL y devolver los resultados en una lista.
    public ArrayList<Object[]> consultar(String query) throws SQLException {
        ArrayList<Object[]> aResultados = new ArrayList<>();

        try (ResultSet rs = this.stm.executeQuery(query)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                Object[] fila = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    fila[i - 1] = rs.getObject(i);
                }
                aResultados.add(fila);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error en la consulta: " + ex.getMessage(), ex);
            throw new SQLException("Error en la consulta: " + ex.getMessage(), ex);
        }

        return aResultados;
    }
    
    public int obtenerIDGenerado(Conexion oCon) throws SQLException {
        String queryObtenerID = "SELECT AlbumID_Seq.currval FROM dual";

        try {
            ArrayList<Object[]> resultados = oCon.consultar(queryObtenerID);
            if (!resultados.isEmpty()) {
                Object[] fila = resultados.get(0);
                int albumIDGenerado = Integer.parseInt(fila[0].toString());
                return albumIDGenerado;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el ID generado desde la base de datos");
        }
        return -1;
    }
    
    // Método para realizar inserciones en la base de datos.
    public void insertar(String query, Object[] params) throws SQLException{
            // Prepara una sentencia SQL para la inserción.
        try(PreparedStatement pStmt = this.conn.prepareStatement(query)){
            if (params != null) {
                // Establece los parámetros en la sentencia SQL.
                for(int i = 0; i < params.length; i++)
                    pStmt.setObject(i + 1, params[i]);
            }
            // Ejecuta la inserción.
            pStmt.executeUpdate();
        } catch(SQLException ex){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Error en insercion: " + ex.getMessage(), ex);
        }
    }
    
    public int insertarAlbum(String nombreAlbum, String artistaAlbum, String generoAlbum) throws SQLException {
        String queryInsertAlbum = "INSERT INTO Albums (NombreAlbum, Artista, Genero) VALUES (?, ?, ?)";
        Object[] paramsAlbum = {nombreAlbum, artistaAlbum, generoAlbum};

        try {
            PreparedStatement pStmt = this.conn.prepareStatement(queryInsertAlbum, new String[]{"AlbumID"});
            for (int i = 0; i < paramsAlbum.length; i++) {
                pStmt.setObject(i + 1, paramsAlbum[i]);
            }

            pStmt.executeUpdate();

            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

            return -1; // Devuelve -1 si no se pudo obtener el ID
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error al insertar el álbum en la base de datos: " + ex.getMessage(), ex);
        }
    }
    
    public int obtenerIDAlbumDesdeBD(String nombreAlbum, String artistaAlbum, String generoAlbum) throws SQLException {
        //valor predeterminado en caso que no encuentre el album
        int albumID = -1;

        String query = "SELECT AlbumID FROM Albums WHERE NombreAlbum  = ? AND Artista = ? AND Genero = ?";
        PreparedStatement pstmt =  this.conn.prepareStatement(query);
        pstmt.setString(1, nombreAlbum);
        pstmt.setString(2, artistaAlbum);
        pstmt.setString(3, generoAlbum);
        
        ResultSet rs = pstmt.executeQuery();
        
        if(rs.next()){
            albumID = rs.getInt("AlbumID");
        }
        
        rs.close();
        pstmt.close();
        
        return albumID;
    }

    public void insertarCancion(String NombreCancion, double DuracionCancion, int AlbumID) throws SQLException {
        String queryInsertCancion = "INSERT INTO Canciones (NombreCancion, Duracion, AlbumID) VALUES (?, ?, ?)";
        Object[] paramsCancion = {NombreCancion, DuracionCancion, AlbumID};

        try {
            insertar(queryInsertCancion, paramsCancion);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al insertar la canción en la base de datos: " + ex.getMessage(), ex);
            throw new SQLException("Error al insertar la canción en la base de datos: " + ex.getMessage(), ex);
        }
    }
    
    public int BuscarAlbumExistente(String NombreAlbum, String ArtistaAlbum, String GeneroAlbum) {
        try{
            //Construye la consulta
            String query = "SELECT AlbumID FROM albums WHERE NombreAlbum = ? AND Artista = ? AND Genero = ?";
            
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            pstmt.setString(1, NombreAlbum);
            pstmt.setString(2, ArtistaAlbum);
            pstmt.setString(3, GeneroAlbum);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
                return rs.getInt("AlbumID");
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return -1;
    }
}
