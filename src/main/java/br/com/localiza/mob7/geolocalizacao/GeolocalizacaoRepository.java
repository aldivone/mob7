package br.com.localiza.mob7.geolocalizacao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GeolocalizacaoRepository extends JpaRepository<Geolocalizacao, Long> {

	List<Geolocalizacao> findByPlaca(String placa);

	// 6371 = RAIO_TERRESTRE
	/*
	 * Formula para encontrar localizações dentro de uma ponto de interesse.
	 * 
	 * SELECT *,(RAIO_TERRESTRE * acos( cos(radians(PARAMETRO_LATITUDE)) *
	 * cos(radians(COLUNA_LATITUDE)) * cos(radians(PARAMETRO_LONGITUDE) -
	 * radians(COLUNA_LONGITUDE)) + sin(radians(PARAMETRO_LATITUDE)) *
	 * sin(radians(COLUNA_LATITUDE)) )) AS CAMPOLATITUDE FROM TABELA HAVING
	 * CAMPOLATITUDE <= KM
	 * 
	 * @see
	 * https://cref.if.ufrgs.br/?contact-pergunta=calculo-aproximado-de-distancias-
	 * com-base-em-coordenadas-de-latitude-e-longitude
	 * 
	 */
	@Query(value = "select * from ( select m.id as id, m.data_posicao as dataPosicao, m.placa as placa, distanciaKm*1000 as distanciaMetros from ("
			+ " SELECT *, (6371 * acos(cos(radians(:latPonto)) * cos(radians(latitude)) * cos(radians(:longPonto) - radians(longitude)) "
			+ " + sin(radians(:latPonto)) * sin(radians(latitude)) )) AS distanciaKm "
			+ " FROM geolocalizacao where placa=:placa order by data_posicao) m "
			+ " ) j where j.distanciaMetros <= :raio ", nativeQuery = true)
	List<GeolocalizacaoVO> findGeolocalizacaoVObyPontoDeInteresse(String placa, Double latPonto, Double longPonto,
			Integer raio);

	@Query(value = "select * from ( select m.id as id, m.data_posicao as dataPosicao, m.placa as placa, distanciaKm*1000 as distanciaMetros from ("
			+ " SELECT *, (6371 * acos(cos(radians(:latPonto)) * cos(radians(latitude)) * cos(radians(:longPonto) - radians(longitude)) "
			+ " + sin(radians(:latPonto)) * sin(radians(latitude)) )) AS distanciaKm "
			+ " FROM geolocalizacao where date_trunc('day',data_posicao) between :dataInicio and :dataFim order by data_posicao) m "
			+ " ) j where j.distanciaMetros <= :raio ", nativeQuery = true)
	List<GeolocalizacaoVO> findGeolocalizacaoVObyPontoDeInteresse(Date dataInicio, Date dataFim, Double latPonto,
			Double longPonto, Integer raio);

	@Query(value = "select * from ( select m.id as id, m.data_posicao as dataPosicao, m.placa as placa, distanciaKm*1000 as distanciaMetros from ("
			+ " SELECT *, (6371 * acos(cos(radians(:latPonto)) * cos(radians(latitude)) * cos(radians(:longPonto) - radians(longitude)) "
			+ " + sin(radians(:latPonto)) * sin(radians(latitude)) )) AS distanciaKm "
			+ " FROM geolocalizacao where placa=:placa and date_trunc('day',data_posicao) between :dataInicio and :dataFim order by data_posicao) m "
			+ " ) j where j.distanciaMetros <= :raio ", nativeQuery = true)
	List<GeolocalizacaoVO> findGeolocalizacaoVObyPontoDeInteresse(String placa, Date dataInicio, Date dataFim,
			Double latPonto, Double longPonto, Integer raio);
}
