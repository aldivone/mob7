package br.com.localiza.mob7.pontodeinteresse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PontoDeInteresseVO {

	private String nome;

	private Integer raio;

	private Double latitude;

	private Double longitude;

	private String placa;

	private Long tempoEmMinutosDoVeiculoNoPonto;
}
