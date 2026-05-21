package co.patron.inventario.entidades;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    private Long id;
    private String tipo;
    private String nombre;
    private int existencias;
    private double precioCompra;
    private double precioVenta;
    private String empresaSurtidora;
    private LocalDate fechaVencimiento;

    public void calcularPrecioVenta() {
        this.precioVenta = this.precioCompra * 1.19;
    }
}
