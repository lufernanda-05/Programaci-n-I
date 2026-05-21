package co.patron.gestion;

import java.util.List;

public interface OperacionesGeneric<T> {
    void crear(T entidad);

    List<T> obtenerTodos();
}
