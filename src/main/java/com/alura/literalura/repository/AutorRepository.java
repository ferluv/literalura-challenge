package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(Integer nacimiento, Integer fallecimiento);

    @Query("""
        SELECT a FROM Autor a 
        WHERE a.fechaNacimiento <= :anio AND a.fechaFallecimiento >= :anio
    """)
    List<Autor> autoresVivosEnAnio(Integer anio);

    @Query("""
        SELECT DISTINCT a FROM Autor a 
        JOIN a.libros l 
        WHERE :idioma MEMBER OF l.idiomas
    """)
    List<Autor> autoresPorIdioma(String idioma);

    @Query("""
        SELECT a FROM Autor a 
        WHERE SIZE(a.libros) >= :minLibros
    """)
    List<Autor> autoresConMasDeNLibros(Integer minLibros);
}
