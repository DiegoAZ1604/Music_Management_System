package Entidades_ordenamiento;
import Entidades.Album;
import java.util.Comparator;

/**
 *
 * @author diegod
 */
public class OrdenamientoAsc implements Comparator<Album>{
    @Override
    public int compare(Album a1, Album a2) {
        return a1.getNombreAlbum().compareTo(a2.getNombreAlbum());
    }
}
