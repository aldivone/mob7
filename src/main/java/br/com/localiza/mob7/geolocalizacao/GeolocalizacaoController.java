package br.com.localiza.mob7.geolocalizacao;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/v1/veiculos/geolocalizacoes")
@Tag(name = "Geolocalizacao", description = "Api de geolocalização de um veículo")
public class GeolocalizacaoController {

	private GeolocalizacaoRepository geolocalizacaoRepository;

	public GeolocalizacaoController(GeolocalizacaoRepository geolocalizacaoRepository) {
		super();
		this.geolocalizacaoRepository = geolocalizacaoRepository;
	}

	@PostMapping
	public void gravarNovasLocalizacoes(GeolocalizacaoRequest geolocalizacaoRequest) {
		log.info("[Geolocalização] gravar novas localizações dos veículos");
		geolocalizacaoRepository.save(geolocalizacaoRequest.toGeolocalizacao());
	}

}
