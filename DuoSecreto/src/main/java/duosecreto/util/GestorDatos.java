package duosecreto.util;

import duosecreto.dto.Dificultad;
import duosecreto.dto.Resultado;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Fachada de persistencia. Centraliza la lectura/escritura de
 * dificultades, resultados e imágenes en archivos binarios.
 */
public class GestorDatos {

    private static final String ARCH_DIFICULTADES = "dificultades.bin";
    private static final String ARCH_RESULTADOS   = "resultados.bin";
    private static final String ARCH_IMAGENES     = "imagenes.bin";
    private static final String ARCH_NICKNAMES    = "nicknames.bin";

    // ── Dificultades ─────────────────────────────────────────────────────────

    public static List<Dificultad> cargarDificultades() {
        if (!UtilidadArchivos.existe(ARCH_DIFICULTADES)) {
            return crearDificultadesPorDefecto();
        }
        try {
            return UtilidadArchivos.leer(ARCH_DIFICULTADES);
        } catch (Exception e) {
            return crearDificultadesPorDefecto();
        }
    }

    public static void guardarDificultades(List<Dificultad> lista) {
        try {
            UtilidadArchivos.guardar(lista, ARCH_DIFICULTADES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Dificultad> crearDificultadesPorDefecto() {
        List<Dificultad> lista = new ArrayList<>();
        lista.add(new Dificultad("Fácil",       4,  90, 10));
        lista.add(new Dificultad("Normal",       6,  60, 15));
        lista.add(new Dificultad("Difícil",      8,  45, 20));
        lista.add(new Dificultad("Experto",     10,  30, 30));
        guardarDificultades(lista);
        return lista;
    }

    // ── Resultados (Top 10 por dificultad) ───────────────────────────────────

    public static List<Resultado> cargarResultados() {
        if (!UtilidadArchivos.existe(ARCH_RESULTADOS)) return new ArrayList<>();
        try {
            return UtilidadArchivos.leer(ARCH_RESULTADOS);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void guardarResultado(Resultado resultado) {
        List<Resultado> lista = cargarResultados();
        lista.add(resultado);
        try {
            UtilidadArchivos.guardar(lista, ARCH_RESULTADOS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Retorna los 10 mejores tiempos para una dificultad dada, ordenados por tiempo ascendente */
    public static List<Resultado> top10(String nombreDificultad) {
        return cargarResultados().stream()
                .filter(r -> r.getNombreDificultad().equalsIgnoreCase(nombreDificultad))
                .sorted(Comparator.comparingLong(Resultado::getTiempoSegundos))
                .limit(10)
                .collect(Collectors.toList());
    }

    // ── Imágenes ─────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static List<String> cargarRutasImagenes() {
        if (!UtilidadArchivos.existe(ARCH_IMAGENES)) return new ArrayList<>();
        try {
            return UtilidadArchivos.leer(ARCH_IMAGENES);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void agregarImagen(String ruta) {
        List<String> rutas = cargarRutasImagenes();
        if (!rutas.contains(ruta)) {
            rutas.add(ruta);
            try {
                UtilidadArchivos.guardar(rutas, ARCH_IMAGENES);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void eliminarImagen(String ruta) {
        List<String> rutas = cargarRutasImagenes();
        rutas.remove(ruta);
        try {
            UtilidadArchivos.guardar(rutas, ARCH_IMAGENES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ── Nicknames (para validar unicidad) ────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static Set<String> cargarNicknames() {
        if (!UtilidadArchivos.existe(ARCH_NICKNAMES)) return new HashSet<>();
        try {
            return UtilidadArchivos.leer(ARCH_NICKNAMES);
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    public static boolean nicknameExiste(String nickname) {
        return cargarNicknames().contains(nickname.trim().toLowerCase());
    }

    public static void registrarNickname(String nickname) {
        Set<String> set = cargarNicknames();
        set.add(nickname.trim().toLowerCase());
        try {
            UtilidadArchivos.guardar(set, ARCH_NICKNAMES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
