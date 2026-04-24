package duosecreto.util;

import java.io.*;
import java.nio.file.*;

public class UtilidadArchivos {

    private static final String DIR_DATOS = "datos";

    static {
        // Crear carpeta de datos si no existe
        new File(DIR_DATOS).mkdirs();
    }

    public static void guardar(Object obj, String nombreArchivo) throws IOException {
        String ruta = DIR_DATOS + File.separator + nombreArchivo;
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ruta))) {
            oos.writeObject(obj);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T leer(String nombreArchivo) throws IOException, ClassNotFoundException {
        String ruta = DIR_DATOS + File.separator + nombreArchivo;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ruta))) {
            return (T) ois.readObject();
        }
    }

    public static boolean existe(String nombreArchivo) {
        return new File(DIR_DATOS + File.separator + nombreArchivo).exists();
    }

    /** Copia una imagen al directorio de imágenes del juego y retorna la ruta destino */
    public static String copiarImagen(File origen) throws IOException {
        File dirImg = new File(DIR_DATOS + File.separator + "imagenes");
        dirImg.mkdirs();
        File destino = new File(dirImg, origen.getName());
        Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return destino.getAbsolutePath();
    }
}
