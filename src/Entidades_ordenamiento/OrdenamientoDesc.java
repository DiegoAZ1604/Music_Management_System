package Entidades_ordenamiento;
import Entidades.Album;
import java.util.Comparator;

/**
 *
 * @author diegod
 */
public class OrdenamientoDesc implements Comparator<Album>{
    @Override
    public int compare(Album a1, Album a2) {
       return a2.getNombreAlbum().compareTo(a1.getNombreAlbum());
    }
    
}