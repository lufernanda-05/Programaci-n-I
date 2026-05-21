package co.patron;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.SwingUtilities;

import co.patron.entidades.Docente;
import co.patron.entidades.Estudiante;
import co.patron.entidades.Materia;
import co.patron.gestion.GestorConexion;
import co.patron.gestion.OperacionesDocente;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Patron DAO");

            Docente docente = Docente.builder()
                    .nombre("Luisa Bernal")
                    .identificacion(10131044215L)
                    .build();

            OperacionesDocente oper = new OperacionesDocente();
            oper.crear(docente);

            // Obtener todos los docentes
            List<Docente> datos = oper.obtenerTodos();

            System.out.println("Listado -----------");
            datos.forEach(n -> System.out.println(n));

            Estudiante estudiante = Estudiante.builder()
                    .cedula(123456789)
                    .nombres("Juan")
                    .apellidos("Pérez")
                    .build();

            Materia materia = Materia.builder()
                    .codigo(1234)
                    .nombre("Matemáticas")
                    .semestre("1")
                    .estudiante(estudiante)
                    .build();

            try {
                GestorConexion.inicializarBaseDeDatos(estudiante, materia);
            } catch (Exception e) {
                System.out.println("Nota: Los datos iniciales ya estaban en la BD.");
            }

            SwingUtilities.invokeLater(() -> {
                new Ventana().setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
