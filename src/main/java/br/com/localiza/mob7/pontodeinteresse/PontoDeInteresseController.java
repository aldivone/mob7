package br.com.localiza.mob7.pontodeinteresse;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/v1/veiculos/pois")
@Tag(name = "Pontos de interesse", description = "Api para os pontos de interesse de um cliente")
public class PontoDeInteresseController {

	@Autowired
	private PontoDeInteresseRepository pontoDeInteresseRepository;

	@GetMapping
	public String getResumos() {
		return "Teste dos POIs";
	}

	@PostMapping
	public void gravarNovosPontosDeInteresse(@Valid PontoDeInteresseRequest pontoDeInteresseRequest) {
		log.info("[Ponto de interesse] gravar novos pontos de interesse");
		pontoDeInteresseRepository.save(pontoDeInteresseRequest.toPontoDeInteresse());
	}

}
