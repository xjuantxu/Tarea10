package biblioteca;

import biblioteca.controlador.Controlador;
import biblioteca.modelo.Modelo;
import biblioteca.vista.Vista;

/**
 * Clase AppBiblioteca.
 * Punto de entrada de la aplicación.
 * Inicializa el Modelo, la Vista y el Controlador y arranca la aplicación.
 */
public class AppBiblioteca {

    //Inicializa la aplicación
    public static void main(String[] args) {

        try {
            Vista vista = new Vista();
            Modelo modelo = new Modelo();

            Controlador controlador = new Controlador(modelo, vista);
            controlador.comenzar();

        } catch (Exception e) {
            System.out.println("Error grave en la ejecución.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
