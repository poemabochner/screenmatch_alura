package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosDoramas;
import br.com.alura.screenmatch.model.DadosTemporadas;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    }
}
