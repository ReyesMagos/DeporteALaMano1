package co.gov.coldeportes.deportealamano.servicios.serviciosExternos.googleMaps;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface Mapa  {
	

	public GoogleMap ubicarEscenario(View view, LatLng latitud, LatLng longitud);
	
	public GoogleMap ubicarEvento(View view, LatLng latitud, LatLng longitud);
	
	public void iniciarMapa(View view, LatLng latitud, LatLng longitud);
	

}
