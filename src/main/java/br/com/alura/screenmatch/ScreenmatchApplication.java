package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosDoramas;
import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=my+demon&apikey=1467ed36");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosDoramas dados = conversor.obterDados(json, DadosDoramas.class);
		System.out.println(dados);

		json = consumoApi.obterDados("https://www.omdbapi.com/?t=my+demon&season=1&episode=1&apikey=1467ed36");
		DadosEpisodios dadosEpisodios = conversor.obterDados(json, DadosEpisodios.class);
		System.out.println(dadosEpisodios);
	}
}
