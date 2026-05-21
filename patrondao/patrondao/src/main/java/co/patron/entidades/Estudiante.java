package co.patron.entidades;

import lombok.*;
import java.util.List;

import co.patron.entidades.Materia.MateriaBuilder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Estudiante {
    private String nombre;
    private String apellidos;
    private Long identificacion;
    private List<Materia> materias;

    @Override
    public String toString() {
        return "Nombre: " + this.getNombre() + ", Apellido: " + this.getApellidos() + ",\nIdentificacion: "
                + this.identificacion;
    }

    public long getCedula() {
        return this.identificacion;
    }

    public String getNombres() {
        return this.nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setNombres(String nombres) {
        this.nombre = nombres;
    }

    public void setCedula(long cedula) {
        this.identificacion = cedula;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public static class EstudianteBuilder {
        private Estudiante estudiante;

        public EstudianteBuilder() {
            this.estudiante = new Estudiante();
        }

        public EstudianteBuilder nombre(String nombre) {
            this.estudiante.nombre = nombre;
            return this;
        }

        public EstudianteBuilder identificacion(Long identificacion) {
            this.estudiante.identificacion = identificacion;
            return this;
        }

        public EstudianteBuilder materias(List<Materia> materias) {
            this.estudiante.materias = materias;
            return this;
        }

        public Estudiante build() {
            return this.estudiante;
        }

        public EstudianteBuilder cedula(long cedula) {
            this.estudiante.identificacion = cedula;
            return this;
        }

        public EstudianteBuilder nombres(String nombres) {
            this.estudiante.nombre = nombres;
            return this;
        }

        public EstudianteBuilder apellidos(String apellidos) {
            this.estudiante.apellidos = apellidos;
            return this;
        }
    }
}