package co.patron.juego.logica;

import java.util.*;

public class JuegoLogica {
    public String generarNumero() {
        List<Integer> nums = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(nums);
        String numeroSecreto = "" + nums.get(0) + nums.get(1) + nums.get(2) + nums.get(3);
        System.out.println("DEBUG - El número secreto es: " + numeroSecreto);
        return numeroSecreto;
    }

    public String evaluar(String secreto, String intento) {
        if (intento.length() != 4)
            return "Error: Debe digitar 4 cifras.";

        int fijas = 0, picas = 0;
        for (int i = 0; i < 4; i++) {
            if (secreto.charAt(i) == intento.charAt(i)) {
                fijas++;
            } else if (secreto.contains(String.valueOf(intento.charAt(i)))) {
                picas++;
            }
        }
        return fijas + " Fijas " + picas + " Picas";
    }
}
