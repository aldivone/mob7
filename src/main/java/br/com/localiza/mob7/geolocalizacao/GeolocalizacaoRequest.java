package br.com.localiza.mob7.geolocalizacao;

import java.util.Date;

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
@Schema(description = "Entidade para inserção de novos posicionamentos dos veículos")
public class GeolocalizacaoRequest {

	@Schema(description = "Placa do veículo", required = true)
	@NotBlank(message = "Placa deve ser informada")
	private String placa;

	@Schema(description = "data da posição do veículo", required = true)
	@NotNull(message = "Data da posição deve ser informada")
	private Date data;

	@Schema(description = "velocidade do veículo", required = false)
	private Integer velocidade;

	@Schema(description = "Posição geográfia de longitude do veículo", required = true)
	@NotNull(message = "A posição de longitude deve ser informada")
	private Double longitude;

	@Schema(description = "Posição geográfia de latitude do veículo", required = true)
	@NotNull(message = "A posição de latitude deve ser informada")
	private Double latitude;

	@Schema(description = "Informa se o veículo deu a ignição", required = true)
	@NotNull(message = "Informe se o carro foi ligado ou não")
	private boolean ignicao;

	public Geolocalizacao toGeolocalizacao() {
		return Geolocalizacao.builder().placa(placa).data(data).velocidade(velocidade).longitude(longitude)
				.latitude(latitude).ignicao(ignicao).build();
	}

}
