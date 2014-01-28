package co.gov.coldeportes.deportealamano.servicios.serviciosExternos.googleMaps;

import android.view.View;
import android.widget.TextView;
import co.gov.coldeportes.deportealamano.dominio.entidades.*;
import co.gov.coldeportes.deportealamano.presentacion.R;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Tab_Mapa;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.res.Resources;

public class MapaEscenario extends android.support.v4.app.FragmentActivity {

	public void iniciarMapa(GoogleMap mapa, LatLng latlong, int fragmento) {
		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(fragmento)).getMap();

		CameraPosition posicion = new CameraPosition.Builder().target(latlong)
				.zoom(5).bearing(0).build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
		mapa.animateCamera(camUpd);

	}

	public static void ubicarEscenario(GoogleMap mapa, Escenario escenario,
			int size) {

		LatLng latlong = new LatLng(Double.parseDouble(escenario.getLatitud()),
				Double.parseDouble(escenario.getLongitud()));
		// mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraPosition posicion = new CameraPosition.Builder().target(latlong)
				.zoom(size).bearing(0).build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
		mapa.animateCamera(camUpd);
		MarkerOptions markerOptions = new MarkerOptions();

		String titulo = "1" + ";";
		titulo += modificaValores(escenario.getNombre()) + ";";
		titulo += modificaValores(escenario.getTipo()) + ";";
		titulo += modificaValores(escenario.getDeporte()) + ";";
		titulo += modificaValores(escenario.getDepartamento()) + "/"
				+ modificaValores(escenario.getMunicipio()) + ";";
		titulo += modificaValores(escenario.getDireccion()) + ";";
		titulo += modificaValores(escenario.getDescripcion()) + ";";
		titulo += modificaValores(escenario.getTelefono()) + ";";
		titulo += modificaValores(escenario.getImagen()) + ";";
		markerOptions.title(titulo);

		markerOptions.position(latlong).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		Marker marker = mapa.addMarker(markerOptions);
		marker.setFlat(true);
		marker.showInfoWindow();

	}

	public static void ubicarEscenarioEvento(GoogleMap mapa,
			Escenario escenario, Evento evento, int size) {

		LatLng latlong = new LatLng(Double.parseDouble(escenario.getLatitud()),
				Double.parseDouble(escenario.getLongitud()));
		// mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraPosition posicion = new CameraPosition.Builder().target(latlong)
				.zoom(size).bearing(0).build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
		mapa.animateCamera(camUpd);
		MarkerOptions markerOptions = new MarkerOptions();
		String titulo = "2" + ";";
		titulo += modificaValores(escenario.getNombre()) + ";";
		titulo += modificaValores(evento.getNombre()) + ";";
		titulo += modificaValores(evento.getEntidad()) + ";";
		titulo += modificaValores(evento.getTipo()) + ";";
		titulo += modificaValores(evento.getFechaD()) + "-"
				+ evento.getFechaH() + ";";
		titulo += modificaValores(evento.getPais()) + "/" + evento.getLugar()
				+ ";";
		titulo += modificaValores(evento.getDescripEvento()) + ";";
		titulo += modificaValores(evento.getPaginaWeb()) + ";";
		titulo += modificaValores(evento.getEmail()) + ";";
		titulo += modificaValores(escenario.getImagen()) + ";";
		markerOptions.title(titulo);

		// Se posiciona y se le añade la imagen como iconoBit
		markerOptions.position(latlong).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		Marker marker = mapa.addMarker(markerOptions);
		marker.setFlat(true);
		// se muestra la venta de info
		marker.showInfoWindow();

	}

	public static String modificaValores(String s) {
		if (s.equals("") || s == null) {
			return s = "Sin Informacion";
		}
		return s;
	}

	public static String[] arreglaTitulo(String s) {

		if (Character.toString(s.charAt(0)).equals("1")) {
			s = s.replaceFirst("1;", "");
			String nombre = null;
			String tipo = null;
			String deporte = null;
			String deptoMunicipio = null;
			String direccion = null;
			String descripcion = null;
			String telefono = null;
			String imagenUrl = null;
			String[] datos = new String[8];
			int n = 0;
			for (int i = 0; i < s.length(); i++) {
				if (Character.toString(s.charAt(i)).equals(";")) {
					if (n == 0) {
						nombre = s.substring(0, i);
						s = s.replaceFirst(nombre + ";", "");
						n += 1;
						i = -1;
					} else if (n == 1) {
						tipo = s.substring(0, i);
						s = s.replaceFirst(tipo + ";", "");
						n += 1;
						i = -1;
					} else if (n == 2) {
						deporte = s.substring(0, i);
						s = s.replaceFirst(deporte + ";", "");
						n += 1;
						i = -1;
					} else if (n == 3) {
						deptoMunicipio = s.substring(0, i);
						s = s.replaceFirst(deptoMunicipio + ";", "");

						n += 1;
						i = -1;
					} else if (n == 4) {
						direccion = s.substring(0, i);
						s = s.replaceFirst(direccion + ";", "");
						n += 1;
						i = -1;

					} else if (n == 5) {
						descripcion = s.substring(0, i);
						s = s.replaceFirst(descripcion + ";", "");
						n += 1;
						i = -1;
					} else if (n == 6) {
						telefono = s.substring(0, i);
						s = s.replaceFirst(telefono + ";", "");
						n += 1;
						i = -1;
					} else if (n == 7) {
						imagenUrl = s.substring(0, i);
						s = s.replaceFirst(imagenUrl + ";", "");
						n += 1;
					}

				}
			}
			datos[0] = nombre;
			datos[1] = tipo;
			datos[2] = deporte;
			datos[3] = deptoMunicipio;
			datos[4] = direccion;
			datos[5] = descripcion;
			datos[6] = telefono;
			datos[7] = imagenUrl;
			return datos;

		} else {
			s = s.replaceFirst("2;", "");
			String nombreEscenario = null;
			String nombre = null;
			String entidad = null;
			String tipo = null;
			String fechas = null;
			String paisMunicipio = null;
			String descripcion = null;
			String paginaWeb = null;
			String email = null;
			String imagenUrl = null;

			String[] datos = new String[10];
			
			int n = 0;
			for (int i = 0; i < s.length(); i++) {
				if (Character.toString(s.charAt(i)).equals(";")) {
					if (n == 0) {
						nombreEscenario = s.substring(0, i);
						s = s.replaceFirst(nombreEscenario + ";", "");
						n += 1;
						i = -1;
					} else if (n == 1) {
						nombre = s.substring(0, i);
						s = s.replaceFirst(nombre + ";", "");
						n += 1;
						i = -1;
					} else if (n == 2) {
						entidad = s.substring(0, i);
						s = s.replaceFirst(entidad + ";", "");
						n += 1;
						i = -1;
					} else if (n == 3) {
						tipo = s.substring(0, i);
						s = s.replaceFirst(tipo + ";", "");
						n += 1;
						i = -1;
					} else if (n == 4) {
						fechas = s.substring(0, i);
						s = s.replaceFirst(fechas + ";", "");
						n += 1;
						i = -1;
					} else if (n == 5) {
						paisMunicipio = s.substring(0, i);
						s = s.replaceFirst(paisMunicipio + ";", "");
						n += 1;
						i = -1;
					} else if (n == 6) {
						descripcion = s.substring(0, i);
						s = s.replaceFirst(descripcion + ";", "");
						n += 1;
						i = -1;
					} else if (n == 7) {
						paginaWeb = s.substring(0, i);
						s = s.replaceFirst(paginaWeb + ";", "");
						n += 1;
						i = -1;
					} else if (n == 8) {
						email = s.substring(0, i);
						s = s.replaceFirst(email + ";", "");
						n += 1;
					} else if (n == 9) {
						imagenUrl = s.substring(0, i);
						s = s.replaceFirst(imagenUrl + ";", "");
						n += 1;
					}

				}
			}
			datos[0] = nombreEscenario;
			datos[1] = nombre;
			datos[2] = entidad;
			datos[3] = tipo;
			datos[4] = fechas;
			datos[5] = paisMunicipio;
			datos[6] = descripcion;
			datos[7] = paginaWeb;
			datos[8] = email;
			datos[9] = imagenUrl;
			return datos;
		}

	}

	public static void limpiaMapa(GoogleMap mapa) {
		if(mapa==null){
			return;
		}
		mapa.clear();
	}

	public void asignainfoWindow(GoogleMap mapa) {

	}

}
