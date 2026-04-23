package DuoSecreto.util;

import java.io.*;

public class UtilidadArchivos {
    public static void guardar(Object obj, String nombreArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(obj);
        }
    }

    public static Object leer(String nombreArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return ois.readObject();
        }
    }
}
