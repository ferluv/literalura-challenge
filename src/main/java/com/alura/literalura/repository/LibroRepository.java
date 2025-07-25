package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import com.alura.literalura.service.LibroService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    Optional<Libro> findByTitulo(String titulo);

    List<Libro> findByIdiomasContaining(String idioma);

    @Query("""
        SELECT l FROM Libro l 
        WHERE :idioma MEMBER OF l.idiomas AND l.numeroDeDescargas >= :minDescargas
    """)
    List<Libro> buscarPorIdiomaYDescargas(@Param("idioma") String idioma, @Param("minDescargas") Integer minDescargas);

    @Query("""
    SELECT DISTINCT l FROM Libro l 
    JOIN FETCH l.autores 
    WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :fragmento, '%'))
""")
    List<Libro> buscarPorFragmentoTitulo(@Param("fragmento") String fragmento);

    @Query("""
        SELECT COUNT(l) FROM Libro l 
        WHERE :idioma MEMBER OF l.idiomas
    """)
    Long contarLibrosPorIdioma(@Param("idioma") String idioma);

//    private void listarLibrosRegistrados() {
//        var libros = LibroService.obtenerTodosLosLibros();
//
//        if (libros.isEmpty()) {
//            System.out.println("No hay libros registrados.");
//        } else {
//            for (Libro libro : libros) {
//                System.out.println(libro);
//            }
//        }
//    }

}
