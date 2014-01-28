package co.gov.coldeportes.deportealamano.dominio.controladores;

import android.content.Context;
import co.gov.coldeportes.deportealamano.dominio.busquedas.BusquedaNovedades;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoNovedades.AccesoNovedades;

public class NovedadesController {

	// Aqui se deberia llamar a una clase en el paquete de busqueda que se llame
	// Novedades
	// crear la instacia de esa clase y llamar el metodo
	private BusquedaNovedades novedades;

	/**
	 * Este es el metodo que se encarga de gestionar la obtencion de los eventos
	 * porximos a la fecha del dispositivo del usuario
	 * 
	 * @return verdadero de ser correcta, falso de lo contrario o de no haber
	 *         novedades para listar
	 */
	public boolean obtieneNovedades() {
		novedades = new BusquedaNovedades();
		return novedades.obtenerNovedades();
	}

	public AccesoNovedades getAccesoNovedades() {
		return novedades.getAccesoNovedades();
	}

}
