package br.com.localiza.mob7.pontodeinteresse;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.localiza.mob7.geolocalizacao.GeolocalizacaoRepository;
import br.com.localiza.mob7.geolocalizacao.GeolocalizacaoVO;

@Service
public class PontoDeInteresseService {

	@Autowired
	private PontoDeInteresseRepository pontoDeInteresseRepository;

	@Autowired
	private GeolocalizacaoRepository geolocalizacaoRepository;

	public List<PontoDeInteresseVO> calcularTempoDeLocalizacaoComPontoDeInteresse(String placa, Date dataInicio,
			Date dataFim) {
		List<PontoDeInteresseVO> pontosComTempoDeInteressecao = new ArrayList<>();

		List<PontoDeInteresse> pontosInteresseDB = pontoDeInteresseRepository.findAll();

		for (PontoDeInteresse pontoDeInteresse : pontosInteresseDB) {
			List<GeolocalizacaoVO> geolocalizacoesDoVeiculo = getLocalizacoesDoVeiculo(placa, dataInicio, dataFim,
					pontoDeInteresse.getLatitude(), pontoDeInteresse.getLongitude(), pontoDeInteresse.getRaio());

			Map<String, List<GeolocalizacaoVO>> placaComGeolocalizacoes = gerarPlacaComGeolocalizacoes(
					geolocalizacoesDoVeiculo);

			if (!placaComGeolocalizacoes.isEmpty()) {

				Set<Entry<String, List<GeolocalizacaoVO>>> entrySetPlaca = placaComGeolocalizacoes.entrySet();

				for (Entry<String, List<GeolocalizacaoVO>> placaComListaLocalizacoes : entrySetPlaca) {
					adicionarPontoDeInteresseDentroDoRaio(pontosComTempoDeInteressecao, pontoDeInteresse,
							placaComListaLocalizacoes);
				}

			}
		}
		return pontosComTempoDeInteressecao;
	}

	private void adicionarPontoDeInteresseDentroDoRaio(List<PontoDeInteresseVO> pontosComTempoDeInteressecao,
			PontoDeInteresse pontoDeInteresse, Entry<String, List<GeolocalizacaoVO>> placaComListaLocalizacoes) {

		Optional<Date> dataEntradaNoPontoDeInteresse = getDataEntradaNoPontoInteresse(
				placaComListaLocalizacoes.getValue());
		Optional<Date> dataSaidaNoPontoDeInteresse = getDataSaidaDoPontoDeInteresse(
				placaComListaLocalizacoes.getValue());

		long somaDeTempoDentroDoPonto = somarIntervaloDentroDoPontoDeInteresse(dataEntradaNoPontoDeInteresse,
				dataSaidaNoPontoDeInteresse);

		if (somaDeTempoDentroDoPonto > 0L) {
			pontosComTempoDeInteressecao.add(PontoDeInteresseVO.builder().nome(pontoDeInteresse.getNome())
					.raio(pontoDeInteresse.getRaio()).latitude(pontoDeInteresse.getLatitude())
					.longitude(pontoDeInteresse.getLongitude()).placa(placaComListaLocalizacoes.getKey())
					.tempoEmMinutosDoVeiculoNoPonto(somaDeTempoDentroDoPonto).build());
		}
	}

	private Map<String, List<GeolocalizacaoVO>> gerarPlacaComGeolocalizacoes(
			List<GeolocalizacaoVO> geolocalizacoesDoVeiculo) {
		Map<String, List<GeolocalizacaoVO>> result = new WeakHashMap<>();

// Mais bonito, mais muito mais lento.		
//		List<String> placas = geolocalizacoesDoVeiculo.stream().map(GeolocalizacaoVO::getPlaca)
//				.collect(Collectors.toList());
//		placas.stream().forEach(placa -> result.put(placa, geolocalizacoesDoVeiculo.parallelStream()
//				.filter(geo -> placa.equalsIgnoreCase(geo.getPlaca())).collect(Collectors.toList())));

		String placa = null;
		for (GeolocalizacaoVO geolocalizacaoVO : geolocalizacoesDoVeiculo) {
			placa = geolocalizacaoVO.getPlaca();
			boolean containsKeyPlaca = result.containsKey(placa);
			if (!containsKeyPlaca) {
				result.put(placa, new ArrayList<>());
			} else {
				result.get(placa).add(geolocalizacaoVO);
			}
		}

		return result;
	}

	private List<GeolocalizacaoVO> getLocalizacoesDoVeiculo(String placa, Date dataInicio, Date dataFim,
			Double latitude, Double longitude, Integer raio) {
		if (placa != null && dataInicio == null && dataFim == null) {
			return geolocalizacaoRepository.findGeolocalizacaoVObyPontoDeInteresse(placa, latitude, longitude, raio);
		} else if (placa == null && dataInicio != null && dataFim != null) {
			return geolocalizacaoRepository.findGeolocalizacaoVObyPontoDeInteresse(dataInicio, dataFim, latitude,
					longitude, raio);
		} else if (placa != null && dataInicio != null && dataFim != null) {
			return geolocalizacaoRepository.findGeolocalizacaoVObyPontoDeInteresse(placa, dataInicio, dataFim, latitude,
					longitude, raio);
		}
		return Collections.emptyList();
	}

	private long somarIntervaloDentroDoPontoDeInteresse(Optional<Date> dataEntradaNoPontoDeInteresse,
			Optional<Date> dataSaidaNoPontoDeInteresse) {
		long somaDeTempoDentroDoPonto = 0L;
		if (dataEntradaNoPontoDeInteresse.isPresent() && dataSaidaNoPontoDeInteresse.isPresent()) {
			Temporal dataInicioTemporal = dataEntradaNoPontoDeInteresse.get().toInstant();
			Temporal dataFimTemporal = dataSaidaNoPontoDeInteresse.get().toInstant();
			Duration between = Duration.between(dataInicioTemporal, dataFimTemporal);
			somaDeTempoDentroDoPonto = between.toHours();
		}
		return somaDeTempoDentroDoPonto;
	}

	private Optional<Date> getDataEntradaNoPontoInteresse(List<GeolocalizacaoVO> geolocalizacoesVeiculo) {
		if (!geolocalizacoesVeiculo.isEmpty()) {
			return Optional.of(geolocalizacoesVeiculo.iterator().next().getDataPosicao());
		}
		return Optional.empty();
	}

	private Optional<Date> getDataSaidaDoPontoDeInteresse(List<GeolocalizacaoVO> geolocalizacoesVeiculo) {
		if (!geolocalizacoesVeiculo.isEmpty()) {
			return Optional.of(geolocalizacoesVeiculo.get(geolocalizacoesVeiculo.size() - 1).getDataPosicao());
		}
		return Optional.empty();
	}

}
