package DuoSecreto.util;

public class UtilidadTiempo {
    public static long obtenerTiempoTranscurrido(long tiempoInicio) {
        return System.currentTimeMillis() - tiempoInicio;
    }
}
