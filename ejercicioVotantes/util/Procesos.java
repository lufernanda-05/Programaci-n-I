package ejercicioVotantes.util;

import java.time.LocalDate;
//usamos la clase para convertir un string a un LocalDate, para poder usarlo en la clase Votante y calcular la edad del votante
import java.time.format.DateTimeFormatter;

public class Procesos {
    public LocalDate convertirStringDate(String fechaString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fechaString, formatter);
    }
}