package com.alura.literalura.model;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.print.Book;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponse (List<DatosLibro> results){
=======
import java.awt.print.Book;
import java.util.List;

public class GutendexResponse {
    private int count;
    private String next;
    private List<Book> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }
>>>>>>> 67c9dadae7f692562d28c47e665f8b1304b9c475
}
