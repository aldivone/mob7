package br.com.localiza.mob7.pontodeinteresse;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private PontoDeInteresseService pontoDeInteresseService;

	@Operation(summary = "Listar a quantidade de tempo que os veículos passaram dentro de cada POI.")
	@ApiResponse(responseCode = "200", description = "Com a lista de pontos de interesse e tempo que o veículo dentro da área", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PontoDeInteresseRequest.class)))
	@GetMapping
	public ResponseEntity<List<PontoDeInteresseVO>> getResumos(
			@Parameter(description = "Intervalo de data(início) para pesquisar resumo dos pontos de interesse") @NotNull @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataInicio,
			@Parameter(description = "Intervalo de data(fim) para pesquisar resumo dos pontos de interesse") @NotNull @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataFim,
			@Parameter(description = "Placa do veículo para pesquisa") @NotNull @RequestParam(required = false) String placa) {

		List<PontoDeInteresseVO> tempoDeLocalizacaoComPontoDeInteresse = pontoDeInteresseService
				.calcularTempoDeLocalizacaoComPontoDeInteresse(placa, dataInicio, dataFim);

		return ResponseEntity.ok(tempoDeLocalizacaoComPontoDeInteresse);
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
