package com.alura.literalura.service;

import com.alura.literalura.model.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    }
}
