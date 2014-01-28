package co.gov.coldeportes.deportealamano.presentacion.actividades.tabs;

import android.app.Activity;

public class Comunicador {
	private static boolean busco;
	private static Activity actividad;
	private static String paisSeleccionado;
	
	
	public static boolean isBusco() {
		return busco;
	}

	
	public static String getPaisSeleccionado() {
		return paisSeleccionado;
	}


	public static void setPaisSeleccionado(String paisSeleccionado) {
		Comunicador.paisSeleccionado = paisSeleccionado;
	}


	public static void setBusco(boolean busco) {
		Comunicador.busco = busco;
	}

	public static Activity getActividad() {
		return actividad;
	}

	public static void setActividad(Activity actividad) {
		Comunicador.actividad = actividad;
	}
	
	

}
