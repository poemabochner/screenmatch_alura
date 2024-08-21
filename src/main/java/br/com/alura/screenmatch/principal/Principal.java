package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosDoramas;
import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosTemporadas;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=1467ed36";

    public void exibeMenu() {

        System.out.println("digite o nome do dorama");

        var nomeDorama = leitura.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeDorama.replace(" ", "+") + API_KEY);

        DadosDoramas dados = conversor.obterDados(json, DadosDoramas.class);

        System.out.println(dados);

        List<DadosTemporadas> temporadas = new ArrayList<>();

		for (int i = 1; i<= dados.totalTemporadas(); i++) {
			json = consumoApi.obterDados(ENDERECO + nomeDorama.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
			temporadas.add(dadosTemporadas);
		}
		temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\ntop 5 episódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);
    }
}
