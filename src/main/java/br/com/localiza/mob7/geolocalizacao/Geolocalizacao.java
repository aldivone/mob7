package br.com.localiza.mob7.geolocalizacao;

import java.util.Date;

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
@Table(name = "GEOLOCALIZACAO")
public class Geolocalizacao {

	@Id
	private Long id;

	private String placa;

	private Date data;

	private Integer velocidade;

	private Double longitude;

	private Double latitude;

	private boolean ignicao;

}
