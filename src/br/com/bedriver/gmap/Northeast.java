package br.com.bedriver.gmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Northeast {
	@JsonProperty("lat")
	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	double lat;

	@JsonProperty("lng")
	public double getLng() {
		return this.lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	double lng;

	@Override
	public String toString() {
		return "Northeast [lat=" + lat + ", lng=" + lng + "]";
	}

}
