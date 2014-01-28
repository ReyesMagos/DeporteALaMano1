package co.gov.coldeportes.deportealamano.dominio.controladores;

import co.gov.coldeportes.deportealamano.dominio.busquedas.BusquedaEvento;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEventos.AccesoEventos;

public class SeleccionPaisController {

	private BusquedaEvento busquedaEvento;
	private String pais;

	public SeleccionPaisController(String pais1) {
		this.pais = pais1;
		busquedaEvento = new BusquedaEvento(getPais());
	}

	public void  obtenerPais() {
		 busquedaEvento.cargarPaises();
	}

	public boolean obtenerMunicipiosColombia(String departamento) {
		return busquedaEvento.cargaMunicipios(departamento);
	
	}
	
	public boolean obtenerEventos(String municipio){
		return busquedaEvento.cargaEventos(municipio);
	}
	
	public void determinaEventoSeccionado(String evento){
		busquedaEvento.asignaEventoSeleccionado(evento);
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public AccesoEventos getAccesoEvento() {
		return busquedaEvento.getAccesoEventos();
	}

	public BusquedaEvento getBusquedaEvento() {
		return busquedaEvento;
	}

	public void setBusquedaEvento(BusquedaEvento busquedaEvento) {
		this.busquedaEvento = busquedaEvento;
	}
	
	public void borraSeleccion(){
		this.busquedaEvento.restableceSeleccionados();
	}
	
}
