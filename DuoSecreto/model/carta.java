package DuoSecreto.model;

public class carta {
    private int id;
    private String imagen;
    private EstadoCarta estado;

    public carta(int id, String imagen) {
        this.id = id;
        this.imagen = imagen;
        this.estado = EstadoCarta.OCULTA;
    }

    public int getId() {
        return id;
    }

    public String getImagen() {
        return imagen;
    }

    public EstadoCarta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCarta estado) {
        this.estado = estado;
    }
}