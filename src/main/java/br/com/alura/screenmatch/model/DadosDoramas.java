package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosDoramas(@JsonAlias("Title") String titulo, @JsonAlias("totalSeasons") Integer totalTemporadas,@JsonAlias("imdbRating") String avaliacao) {

}