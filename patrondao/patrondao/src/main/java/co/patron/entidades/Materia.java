package co.patron.entidades;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Materia {
    private String nombre;
    private long codigo;
    private String semestre;
    private Estudiante estudiante;

    @Override
    public String toString() {
        return "Nombre: " + this.getNombre() + ",\ncodigo: " + this.codigo + ",\nsemestre: " + this.semestre
                + ",\nestudiante: " + (this.estudiante != null ? this.estudiante.getNombres() : "N/A");
    }
}
