package br.com.localiza.mob7.pontodeinteresse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PontoDeInteresseRequest {

	private String nome;

	private Integer raio;

	private Double latitude;

	private Double longitude;

	public PontoDeInteresse toPontoDeInteresse() {
		return PontoDeInteresse.builder().nome(nome).raio(raio).latitude(latitude).longitude(longitude).build();
	}
}
