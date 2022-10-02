package br.com.localiza.mob7.pontodeinteresse;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PONTO_INTERESSE")
public class PontoDeInteresse {

	@Id
	private Long id;

	private String nome;

	private Integer raio;

	private Double latitude;

	private Double longitude;
}
