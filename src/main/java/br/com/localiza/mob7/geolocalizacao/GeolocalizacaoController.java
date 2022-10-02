package br.com.localiza.mob7.geolocalizacao;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
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
@RequestMapping("/v1/veiculos/geolocalizacoes")
@Tag(name = "Geolocalizacao", description = "Api para inserir novos posicionamentos de um veículo")
public class GeolocalizacaoController {

	private GeolocalizacaoRepository geolocalizacaoRepository;

	public GeolocalizacaoController(GeolocalizacaoRepository geolocalizacaoRepository) {
		super();
		this.geolocalizacaoRepository = geolocalizacaoRepository;
	}

	@Operation(summary = "Inserir um novo posicionamento de um veículo")
	@ApiResponse(responseCode = "201", description = "Quando um novo posicionamento for gravado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GeolocalizacaoRequest.class)))
	@PostMapping(consumes = { MediaType.APPLICATION_JSON })
	public ResponseEntity<GeolocalizacaoRequest> gravarNovasLocalizacoes(
			@NotNull @Parameter @Valid @RequestBody GeolocalizacaoRequest geolocalizacaoRequest) {
		Geolocalizacao geolocalizacao = geolocalizacaoRepository.save(geolocalizacaoRequest.toGeolocalizacao());
		return ResponseEntity.created(URI.create("/v1/veiculos/geolocalizacoes/" + geolocalizacao.getId())).build();
	}

}
