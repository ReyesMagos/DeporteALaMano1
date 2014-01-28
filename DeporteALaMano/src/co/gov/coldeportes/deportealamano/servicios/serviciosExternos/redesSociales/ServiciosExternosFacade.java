package co.gov.coldeportes.deportealamano.servicios.serviciosExternos.redesSociales;

import android.app.Activity;

import co.gov.coldeportes.deportealamano.servicios.serviciosExternos.googleMaps.MapaEscenario;

public class ServiciosExternosFacade {

	private static ServiciosExternosFacade instancia;
	private RedSocial accesoRedSocial;
	private MapaEscenario mapaEscenario;

	public ServiciosExternosFacade() {
		accesoRedSocial = new RedSocial();
		mapaEscenario = new MapaEscenario();

	}

	public static ServiciosExternosFacade getInstancia() {
		if (instancia == null) {
			instancia = new ServiciosExternosFacade();
		}
		return instancia;
	}

	/*
	 * public void ubicarEscenario(GoogleMap map, Escenario e, int size){
	 * MapaEscenario.ubicarEscenario(map, e, size); }
	 */

	public void compartirRedSocial(Activity a, String evento) {
		accesoRedSocial = new RedSocial();
		accesoRedSocial.compartirRedSocial(a, evento);
	}

}
