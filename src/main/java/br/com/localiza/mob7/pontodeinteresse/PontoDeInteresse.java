package br.com.localiza.mob7.pontodeinteresse;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
	@SequenceGenerator(name = "SEQ_PONTO_INTERESSE", sequenceName = "SEQ_PONTO_INTERESSE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PONTO_INTERESSE")
	private Long id;

	private String nome;

	private Integer raio;

	private Double latitude;

	private Double longitude;
}
