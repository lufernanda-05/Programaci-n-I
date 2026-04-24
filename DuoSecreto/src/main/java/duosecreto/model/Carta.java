package duosecreto.model;

import java.awt.image.BufferedImage;

public class Carta {
    private int id;
    private String rutaImagen;
    private BufferedImage imagen;
    private EstadoCarta estado;

    public Carta(int id, String rutaImagen, BufferedImage imagen) {
        this.id         = id;
        this.rutaImagen = rutaImagen;
        this.imagen     = imagen;
        this.estado     = EstadoCarta.OCULTA;
    }

    public int          getId()        { return id; }
    public String       getRutaImagen(){ return rutaImagen; }
    public BufferedImage getImagen()   { return imagen; }
    public EstadoCarta  getEstado()    { return estado; }
    public void         setEstado(EstadoCarta estado) { this.estado = estado; }
}
