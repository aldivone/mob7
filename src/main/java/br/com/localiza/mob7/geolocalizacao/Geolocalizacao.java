package br.com.localiza.mob7.geolocalizacao;

import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "GEOLOCALIZACAO")
public class Geolocalizacao {

	@Id
	@SequenceGenerator(name = "SEQ_GEOLOCALIZACAO", sequenceName = "SEQ_GEOLOCALIZACAO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEOLOCALIZACAO")
	private Long id;

	@Column(nullable = false, length = 7)
	private String placa;

	@Column(nullable = false)
	private Date data;

	private Integer velocidade;

	@Column(nullable = false)
	private Double longitude;

	@Column(nullable = false)
	private Double latitude;

	private boolean ignicao;

}
