package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.GutendexService;
import com.alura.literalura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    private final LibroService libroService;
    private final GutendexService gutendexService;
    private final AutorService autorService;
    private final Scanner scanner = new Scanner(System.in);

    public Principal(LibroService libroService, GutendexService gutendexService, AutorService autorService) {
        this.libroService = libroService;
        this.gutendexService = gutendexService;
        this.autorService = autorService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    ==============================
                    Catálogo de Libros Literalura
                    ==============================
                    Elija la opción a través de su número:
                    1- Buscar libro por título
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un año
                    5- Listar libros por idioma
                    0- Salir
                    """);

                opcion = teclado.nextInt();
                teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresPorAnio();
                    break;
                case 5:
                    listarPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo del catálogo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void buscarPorTitulo() {
        System.out.println("Ingrese el título a buscar:");
        String titulo = scanner.nextLine();

        GutendexResponse respuesta = gutendexService.buscarPorTitulo(titulo);
        int maxResultados = Math.min(respuesta.results().size(), 3);

        for (int i = 0; i < maxResultados; i++) {
            DatosLibro datosLibro = respuesta.results().get(i);

            System.out.println("Título: " + datosLibro.titulo());
            for (DatosAutor autor : datosLibro.autor()) {
                System.out.println("Autor: " + autor.nombre());
            }
            System.out.println("Idiomas: " + datosLibro.idiomas());
            System.out.println("Descargas: " + datosLibro.numeroDeDescargas());
            System.out.println("----------------------");

            try {
                libroService.guardarLibroDesdeApi(datosLibro);
            } catch (Exception e) {
                System.err.println("Error guardando libro: " + e.getMessage());
                // seguir con el siguiente sin detener todo
            }
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = libroService.obtenerTodosLosAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresPorAnio() {
        System.out.print("Ingrese el año: ");
        int anio = scanner.nextInt();
        scanner.nextLine();

        List<Autor> autores = libroService.buscarAutoresVivosEnAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en " + anio);
        } else {
            System.out.println("Autores vivos en " + anio + ":");
            autores.forEach(System.out::println);
        }
    }

    private void listarPorIdioma() {
        System.out.print("Ingrese el idioma (en, es, fr, etc.): ");
        String idioma = scanner.nextLine();

        List<Libro> libros = libroService.buscarLibrosPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el idioma: " + idioma);
        } else {
            libros.forEach(System.out::println);
        }
    }
}
