package Entidades;

/**
 *
 * @author diegod
 */
///clase hijo

public class Cancion {

    private String NombreCancion;
    private double DuracionCancion;
    private int AlbumID;

    //creacion de constructor
    public Cancion(String pNombreCancion, double pDuracionCancion, int pAlbumID) {
        this.NombreCancion = pNombreCancion;   
        this.DuracionCancion = pDuracionCancion;
        this.AlbumID = pAlbumID;
    }
    
    public Cancion(String pNombreCancion, double pDuracionCancion){ 
        this.NombreCancion = pNombreCancion;
        this.DuracionCancion = pDuracionCancion;
    }

    public String getNombreCancion() {
        return NombreCancion;
    }

    public void setNombreCancion(String NombreCancion) {
        this.NombreCancion = NombreCancion;
    }

    public double getDuracionCancion() {
        return DuracionCancion;
    }

    public void setDuracionCancion(double DuracionCancion) {
        this.DuracionCancion = DuracionCancion;
    }

    
    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int AlbumID) {
        this.AlbumID = AlbumID;
    }
    
}
