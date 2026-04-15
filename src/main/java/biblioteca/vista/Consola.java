package biblioteca.vista;

import biblioteca.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Clase Consola: gestiona la interacción con el usuario en la biblioteca.
 * Contiene métodos para mostrar menú, pedir datos de usuarios y libros,
 * y leer fechas de préstamos y devoluciones.
 */
public class Consola {

    private Consola() {} //Evitamos instanciación
    // Muestra el menú principal
    public static void mostrarMenu() {
        System.out.println("--- MENÚ BIBLIOTECA ---");
        System.out.println("0 - Salir");
        System.out.println("1 - Insertar usuario");
        System.out.println("2 - Borrar usuario");
        System.out.println("3 - Mostrar usuarios");
        System.out.println("4 - Insertar libro");
        System.out.println("5 - Borrar libro");
        System.out.println("6 - Mostrar libros");
        System.out.println("7 - Nuevo préstamo");
        System.out.println("8 - Devolver péstamo");
        System.out.println("9 - Mostrar todos los prestamos");
        System.out.println("10 - Mostrar préstamos de un usuario");
        System.out.print("Seleccione opción: ");
    }

    //Crea un nuevo Usuario
    public static Usuario nuevoUsuario(boolean buscar) {
        String dni;
        String nombre;
        String email;
        String via;
        String numero;
        String cp;
        String localidad;

        while (true) {
            System.out.print("ID: ");
            dni = Entrada.cadena();
            try {
                Usuario test = new Usuario(dni, "tmp");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba que el ID es correcto y lanza una excepción en caso contrario
        if (buscar) {
            return new Usuario(dni);
        }
        while (true) {
            System.out.print("Nombre: ");
            nombre = Entrada.cadena();
            try {
                Usuario test = new Usuario(dni, nombre);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba que el nombre es correcto y lanza una excepción en caso contrario
        Usuario usuario = new Usuario(dni, nombre);
        while (true) {
            System.out.print("Email: ");
            email = Entrada.cadena();
            try {
                Usuario test = new Usuario(dni, nombre);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba que el email es correcto y lanza una excepción en caso contrario
        usuario.setEmail(email);

        while (true) {
            System.out.print("Vía: ");
            via = Entrada.cadena();
            try {
                Direccion prueba2 = new Direccion(via, "1", "00000", "tmp");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba que la vía es correcta y lanza una excepción en caso contrario
        while (true) {
            System.out.print("Número: ");
            numero = Entrada.cadena();
            try {
                Direccion prueba2 = new Direccion(via, numero, "00000", "tmp");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba que el número es correcto y lanza una excepción en caso contrario
        while (true) {
            System.out.print("Código Postal: ");
            cp = Entrada.cadena();
            try {
                Direccion prueba2 = new Direccion(via, numero, cp, "tmp");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba que el código postal es correcto y lanza una excepción en caso contrario
        while (true) {
            System.out.print("Localidad: ");
            localidad = Entrada.cadena();
            try {
                Direccion prueba2 = new Direccion(via, numero, cp, localidad);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba que la localidad es correcta y lanza una excepción en caso contrario
        usuario.setDireccion(new Direccion(via, numero, cp, localidad));

        return usuario;
    }

    // Crea un nuevo Libro
    public static Libro nuevoLibro(boolean buscar) {

        String isbn;
        String titulo;
        Categoria categoria = null;
        int entrada = 0;
        int anio = -1;
        int nAutores = 0;

        while (true) {
            System.out.print("ISBN: ");
            isbn = Entrada.cadena();
            try {
                new Libro(isbn, "tmp", 1, Categoria.OTROS);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        if (buscar) {
            return new Libro(isbn);
        }

        while (true) {
            System.out.print("Título: ");
            titulo = Entrada.cadena();
            try {
                new Libro(isbn, titulo, 2025, Categoria.TECNICO);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Año: ");
                anio = Entrada.entero();
                new Libro(isbn, titulo, anio, Categoria.OTROS);
                break;
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
            }
        }

        System.out.println("Categorías disponibles:");
        int i = 1;
        for (Categoria cat : Categoria.values()) {
            System.out.println(i + ". " + cat);
            i++;
        }

        while (true) {
            try {
                System.out.print("Selecciona una categoría: ");
                entrada = Entrada.entero();
                categoria = Categoria.values()[entrada - 1];
                break;
            } catch (Exception e) {
                System.out.println("Categoría no válida.");
            }
        }

        // Elegir tipo de libro
        System.out.println("Tipo de libro:");
        System.out.println("1. Libro físico");
        System.out.println("2. Audiolibro");

        int tipo = 1;

        while (true) {
            try {
                System.out.print("Seleccione tipo: ");
                tipo = Entrada.entero();
                if (tipo == 1 || tipo == 2)
                    break;
            } catch (Exception e) {
                // ignoramos
            }
            System.out.println("Tipo no válido.");
        }

        Libro libro;

        if (tipo == 2) {

            Duration duracion = null;
            String formato;

            while (true) {
                try {
                    System.out.print("Duración (HH:mm:ss): ");
                    String tiempo = Entrada.cadena().trim();

                    // Validar formato básico con regex
                    if (!tiempo.matches("\\d{1,2}:\\d{2}:\\d{2}")) {
                        throw new IllegalArgumentException();
                    }

                    String[] partes = tiempo.split(":");

                    long horas = Long.parseLong(partes[0]);
                    long minutos = Long.parseLong(partes[1]);
                    long segundos = Long.parseLong(partes[2]);

                    if (minutos >= 60 || segundos >= 60) {
                        throw new IllegalArgumentException();
                    }

                    duracion = Duration.ofHours(horas)
                            .plusMinutes(minutos)
                            .plusSeconds(segundos);
                    break;

                } catch (Exception e) {
                    System.out.println("Formato inválido. Usa HH:mm:ss (min y seg menores de 60).");
                }
            }

            while (true) {
                System.out.print("Formato (mp3, wav...): ");
                formato = Entrada.cadena();
                try {
                    new Audiolibro(isbn, titulo, anio, categoria, duracion, formato);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            libro = new Audiolibro(isbn, titulo, anio, categoria, duracion, formato);

        } else {
            libro = new Libro(isbn, titulo, anio, categoria);
        }

        while (true) {
            try {
                System.out.print("Número de autores: ");
                nAutores = Entrada.entero();
                if (nAutores > 0 && nAutores <= Libro.MAX_AUTORES)
                    break;
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
            }
        }

        for (int j = 0; j < nAutores; j++) {
            libro.addAutor(nuevoAutor());
        }

        return libro;
    }

    // Crea un nuevo Autor
    public static Autor nuevoAutor() {
        String nombre;
        String apellidos;
        String nac;

        while (true) {
            System.out.print("Nombre autor: ");
            nombre = Entrada.cadena();
            try {
                Autor autor = new Autor(nombre, "tmp", "tmp");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba la validez del nombre y lanza excepción si no es válido.
        while (true) {
            System.out.print("Apellidos: ");
            apellidos = Entrada.cadena();
            try {
                Autor autor = new Autor("tmp", apellidos, "tmp");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba la validez de los apellidos y lanza excepción si no es válido.
        while (true) {
            System.out.print("Nacionalidad: ");
            nac = Entrada.cadena();
            try {
                Autor autor = new Autor("tmp", "tmp", nac);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } //Bucle que comprueba la validez de la nacionalidad y lanza excepción si no es válido.

        return new Autor(nombre, apellidos, nac);
    }

    // Devuelve la fecha actual
    public static LocalDate leerFecha() {
        return LocalDate.now();
    }

    // Metodo de cierre de consola
    public static void terminar() {
        System.out.println("Termina consola");
    }
}