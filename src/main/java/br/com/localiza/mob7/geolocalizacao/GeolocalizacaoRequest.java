package br.com.localiza.mob7.geolocalizacao;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeolocalizacaoRequest {

	private String placa;

	private Date data;

	private Integer velocidade;

	private Double longitude;

	private Double latitude;

	private boolean ignicao;

	public Geolocalizacao toGeolocalizacao() {
		return Geolocalizacao.builder().placa(placa).data(data).velocidade(velocidade).longitude(longitude)
				.latitude(latitude).ignicao(ignicao).build();
	}

}
