package ejercicioVotantes.datosprincipales;

import java.time.Period;
import java.time.LocalDate;

//ENCAPSULAMIENTO: es para las clases que necesito protegerlos es usar el private, para que no se pueda acceder a ellos desde fuera de la clase y solo se pueda acceder a ellos a través de métodos públicos, como getters y setters. Esto ayuda a mantener la integridad de los datos y a controlar cómo se accede y modifica la información dentro de la clase.

// el CONSTRUCTOR: es un método especial que se llama automáticamente cuando se crea un objeto de la clase. Se utiliza para inicializar los atributos del objeto con valores específicos. El constructor tiene el mismo nombre que la clase y no tiene un tipo de retorno. Por ejemplo, en la clase Votante, podríamos tener un constructor que reciba el nombre, apellido, número de identificación y fecha de nacimiento para crear un nuevo votante con esos datos.

//el metodo getters y setters: son métodos públicos que se utilizan para acceder y modificar los atributos privados de una clase. Los getters (es decir, los métodos que comienzan con "obtener") se utilizan para obtener el valor de un atributo, mientras que los setters (es decir, los métodos que comienzan con "modificar") se utilizan para establecer o modificar el valor de un atributo. Por ejemplo, en la clase Votante, podríamos tener un getter para obtener el nombre del votante y un setter para modificar el nombre del votante.
public class Votante {

    private String nombre;
    private String apellido;
    private long numeroIdentificacion;
    private LocalDate fechaNacimiento;

    public Votante(String nombre, String apellido, long numeroIdentificacion, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroIdentificacion = numeroIdentificacion;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int calcularEdad() {
        LocalDate hoy = LocalDate.now();
        Period periodo = Period.between(this.fechaNacimiento, hoy);
        return periodo.getYears();
    }

    public void modificarNombre(String nuevoNombre) {
        this.nombre = nuevoNombre;
    }

    public void modificarApellido(String nuevoApellido) {
        this.apellido = nuevoApellido;
    }

    public void modificarNumeroIdentificacion(long nuevoNumeroIdentificacion) {
        this.numeroIdentificacion = nuevoNumeroIdentificacion;
    }

    public void modificarFechaNacimiento(LocalDate nuevaFechaNacimiento) {
        this.fechaNacimiento = nuevaFechaNacimiento;
    }

    public String obtenerNombre() {
        return this.nombre;
    }

    public String obtenerApellido() {
        return this.apellido;
    }

    public long obtenerNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public LocalDate obtenerFechaNacimiento() {
        return this.fechaNacimiento;
    }
}