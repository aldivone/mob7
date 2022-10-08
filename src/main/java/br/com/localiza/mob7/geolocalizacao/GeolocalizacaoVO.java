package br.com.localiza.mob7.geolocalizacao;

import java.util.Date;

public interface GeolocalizacaoVO {

	public Long getId();

	public String getPlaca();

	public Date getDataPosicao();

	public Integer getVelocidade();

	public Double getLongitude();

	public Double getLatitude();

	public boolean getIgnicao();

	public Double getDistanciaKm();

	public Double getDistanciaMetros();

}
