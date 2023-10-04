package Entidades;
import java.util.ArrayList;

/**
 *
 * @author diegod
 */
//clase padre
//creacion de atributos y arraylist
public class Album {

    private String NombreAlbum;
    private String ArtistaAlbum;
    private String GeneroAlbum;
    private ArrayList<Cancion> lCancion;

    //creacion del constructor
    public Album(String pNombreAlbum, String pArtistaAlbum, String pGeneroAlbum) {
        this.NombreAlbum = pNombreAlbum;
        this.ArtistaAlbum = pArtistaAlbum;
        this.GeneroAlbum = pGeneroAlbum;
        this.lCancion = new ArrayList<>();
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

}
