package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresVivosEnAnio(Integer anio) {
        return autorRepository.autoresVivosEnAnio(anio);
    }

    public List<Autor> buscarAutoresQueEscribieronEnIdioma(String idiomas) {
        return autorRepository.autoresPorIdioma(idiomas);
    }

    public List<Autor> buscarAutoresConMasDeNLibros(Integer cantidadMinima) {
            return autorRepository.autoresConMasDeNLibros(cantidadMinima);
    }

    public Autor guardarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

}
