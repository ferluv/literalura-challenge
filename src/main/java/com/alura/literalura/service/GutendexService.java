package com.alura.literalura.service;

import com.alura.literalura.model.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GutendexService {

    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos conversor;

    public GutendexService() {
        this.consumoAPI = new ConsumoAPI();
        this.conversor = new ConvierteDatos();
    }

    public GutendexResponse obtenerLibros() {
        String url = "https://gutendex.com/books/";
        String json = consumoAPI.obtenerDatos(url);

        return conversor.obtenerDatos(json, GutendexResponse.class);
    }
}
