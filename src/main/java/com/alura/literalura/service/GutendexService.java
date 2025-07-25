package com.alura.literalura.service;

import com.alura.literalura.model.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GutendexService {

    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private final ObjectMapper objectMapper;

    public GutendexService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GutendexResponse buscarPorTitulo(String titulo) {
        String url = URL_BASE + titulo.replace(" ", "+");

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapeador = new ObjectMapper();
            return mapeador.readValue(respuesta.body(), GutendexResponse.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consultar Gutendex", e);
        }
=======

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
>>>>>>> 67c9dadae7f692562d28c47e665f8b1304b9c475
    }
}
