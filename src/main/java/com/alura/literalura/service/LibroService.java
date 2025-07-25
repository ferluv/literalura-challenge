package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional
    public void guardarLibroDesdeApi(DatosLibro datosLibro) {
        // ✅ Paso 1: Verificar si ya existe en la base
        Optional<Libro> libroExistente = libroRepository.findByTitulo(datosLibro.titulo());

        if (libroExistente.isPresent()) {
            System.out.println("✅ El libro ya existe en la base de datos: " + datosLibro.titulo());
            return; // No lo guardes de nuevo
        }

        List<Autor> autores = datosLibro.autor().stream()
                .map(a -> autorRepository.findByNombre(a.nombre())
                        .orElseGet(() -> autorRepository.save(new Autor(a.nombre(), a.fechaNacimiento(), a.fechaFallecimiento()))))
                .toList();

        Libro libro = new Libro(
                datosLibro.id(),
                datosLibro.titulo(),
                autores,
                datosLibro.idiomas(),
                datosLibro.numeroDeDescargas()
        );

        autores.forEach(autor -> autor.getLibros().add(libro));

        libroRepository.save(libro);
        System.out.println("✅ Nuevo libro guardado: " + datosLibro.titulo());
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresVivosEnAnio(Integer anio) {
        return autorRepository.findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(anio, anio);
    }

    public List<Libro> buscarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdiomasContaining(idioma);
    }

    public List<Libro> buscarLibrosPorIdiomaYDescargas(String idioma, Integer minDescargas) {
        return libroRepository.buscarPorIdiomaYDescargas(idioma, minDescargas);
    }

}
