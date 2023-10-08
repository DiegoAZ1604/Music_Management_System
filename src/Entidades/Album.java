package Entidades;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author diegod
 */
//clase padre
//creacion de atributos y arraylist
public class Album {
    
    private int AlbumID;
    private String NombreAlbum;
    private String ArtistaAlbum;
    private String GeneroAlbum;
    private ArrayList<Cancion> lCancion;

    //creacion del constructor
    public Album(int pAlbumID, String pNombreAlbum, String pArtistaAlbum, String pGeneroAlbum) {
        this.AlbumID = pAlbumID;
        this.NombreAlbum = pNombreAlbum;
        this.ArtistaAlbum = pArtistaAlbum;
        this.GeneroAlbum = pGeneroAlbum;
        this.lCancion = new ArrayList<>();
    }
    
    public Album(String pNombreAlbum, String pArtistaAlbum, String pGeneroAlbum) {
        this.NombreAlbum = pNombreAlbum;
        this.ArtistaAlbum = pArtistaAlbum;
        this.GeneroAlbum = pGeneroAlbum;
        this.lCancion = new ArrayList<>();
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
    
    public void agregarCancion(Cancion cancion){
        lCancion.add(cancion);
    }

    public String getNombreAlbum() {
        return NombreAlbum;
    }

    public void setNombreAlbum(String NombreAlbum) {
        this.NombreAlbum = NombreAlbum;
    }

    public String getArtistaAlbum() {
        return ArtistaAlbum;
    }

    public void setArtistaAlbum(String ArtistaAlbum) {
        this.ArtistaAlbum = ArtistaAlbum;
    }

    public String getGeneroAlbum() {
        return GeneroAlbum;
    }

    public void setGeneroAlbum(String GeneroAlbum) {
        this.GeneroAlbum = GeneroAlbum;
    }

    public ArrayList<Cancion> getlCancion() {
        return lCancion;
    }

    public void setlCancion(ArrayList<Cancion> lCancion) {
        this.lCancion = lCancion;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int AlbumID) {
        this.AlbumID = AlbumID;
    }
}
