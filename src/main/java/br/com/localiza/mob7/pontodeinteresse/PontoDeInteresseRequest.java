package br.com.localiza.mob7.pontodeinteresse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade para inserção de novos pontos de interesses dos clientes")
public class PontoDeInteresseRequest {

	@Schema(description = "Nome dado ao ponto de interesse")
	@NotBlank(message = "Um nome deve ser informado")
	private String nome;

	@Schema(description = "Raio que o ponto de interesse deve monitorar")
	@NotNull(message = "Um raio deve ser informado")
	private Integer raio;

	@Schema(description = "Posição geográfia de latitude do ponto de interesse", required = true)
	@NotNull(message = "A posição de latitude deve ser informada")
	private Double latitude;

	@Schema(description = "Posição geográfia de longitude do ponto de interesse", required = true)
	@NotNull(message = "A posição de longitude deve ser informada")
	private Double longitude;

	public PontoDeInteresse toPontoDeInteresse() {
		return PontoDeInteresse.builder().nome(nome).raio(raio).latitude(latitude).longitude(longitude).build();
	}
}
