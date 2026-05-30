package co.patron.juego.entidades;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resultado {
    private String nickname;
    private String numeroGenerado;
    private int intentos;
    private long tiempo;
}
