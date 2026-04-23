package DuoSecreto.util;

import DuoSecreto.dto.Dificultad;
import DuoSecreto.dto.Resultado;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Administracion {
    public static void guardarDificultad(Dificultad dificultad) {
        try {
            UtilidadArchivos.guardar(dificultad, "dificultad.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Dificultad cargarDificultad() {
        try {
            return (Dificultad) UtilidadArchivos.leer("dificultad.bin");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void guardarResultado(Resultado resultado) {
        try {
            List<Resultado> resultados = cargarResultados();
            resultados.add(resultado);
            UtilidadArchivos.guardar(resultados, "resultados.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Resultado> cargarResultados() {
        try {
            return (List<Resultado>) UtilidadArchivos.leer("resultados.bin");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
