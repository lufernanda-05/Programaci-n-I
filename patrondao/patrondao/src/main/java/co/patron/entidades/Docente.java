package co.patron.entidades;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Docente {
    private String nombre;
    private Long identificacion;

    @Override
    public String toString() {
        return "Nombre: " + this.getNombre() + ",\nidentificacion: " + this.identificacion;
    }
}
