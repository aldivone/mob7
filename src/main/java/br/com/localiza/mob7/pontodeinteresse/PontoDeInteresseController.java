package br.com.localiza.mob7.pontodeinteresse;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/veiculos/pois")
@Tag(name = "Pontos de interesse", description = "Api responsável pelo gerenciamento de pontos de interesse de um cliente")
public class PontoDeInteresseController {

	@Autowired
	private PontoDeInteresseRepository pontoDeInteresseRepository;

	@Operation(summary = "Buscar lista de resumos com a quantidade de tempo que os veículos passaram dentro de cada POI. ")
	@ApiResponse(responseCode = "200", description = "Com a lista de pontos de interesse e seus tempos", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PontoDeInteresseRequest.class)))
	@GetMapping
	public String getResumos() {
		return "Teste dos POIs";
	}

	@Operation(summary = "Inserir um novo ponto de interesse do cliente")
	@ApiResponse(responseCode = "201", description = "Quando um novo ponto de interesse for gravado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PontoDeInteresseRequest.class)))
	@PostMapping(consumes = { MediaType.APPLICATION_JSON })
	public ResponseEntity<PontoDeInteresseRequest> gravarNovosPontosDeInteresse(
			@NotNull @Parameter @Valid @RequestBody PontoDeInteresseRequest pontoDeInteresseRequest) {
		PontoDeInteresse pontoDeInteresse = pontoDeInteresseRepository
				.save(pontoDeInteresseRequest.toPontoDeInteresse());
		return ResponseEntity.created(URI.create("/v1/veiculos/pois" + pontoDeInteresse.getId())).build();
	}

}
