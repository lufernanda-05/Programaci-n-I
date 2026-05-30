package co.juego.entidades;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class resultado {
    private String nickname;
    private String numeroGenerado;
    private int intentos;
    private long tiempo;
}
