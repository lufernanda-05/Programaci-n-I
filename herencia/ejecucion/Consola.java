package herencia.ejecucion;

import herencia.Animal;
import herencia.Aves;
import herencia.Felino;

public class Consola {
    public static void main(String[] args) {
        System.out.println("Ejemplo de Herencia ");
        // objeto de la clase Animal
        Animal animal = new Animal();
        animal.setNombre("Firulais");
        animal.setEdad(5);
        System.out.println("Nombre: " + animal.getNombre());
        System.out.println("Edad: " + animal.getEdad());
        System.out.println();

        // objeto de la clase Felino
        Felino felino = new Felino();
        felino.setNombre("Garfield");
        felino.setEdad(7);
        felino.setEspecie("Gato");
        felino.setEsCarnivoro(true);
        System.out.println("Nombre: " + felino.getNombre());
        System.out.println("Edad: " + felino.getEdad());
        System.out.println("Especie: " + felino.getEspecie());
        System.out.println("Es carnívoro: " + felino.isEsCarnivoro());
        System.out.println();

        // objeto de la clase Aves
        Aves aves = new Aves();
        aves.setNombre("Piolín");
        aves.setEdad(2);
        aves.setPico("Pico corto");
        aves.setPuedeVolar(true);
        System.out.println("Nombre: " + aves.getNombre());
        System.out.println("Edad: " + aves.getEdad());
        System.out.println("Tipo de pico: " + aves.getPico());
        System.out.println("Puede volar: " + aves.isPuedeVolar());
    }
}