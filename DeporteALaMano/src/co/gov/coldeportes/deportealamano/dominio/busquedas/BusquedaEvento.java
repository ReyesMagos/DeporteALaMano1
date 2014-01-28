package co.gov.coldeportes.deportealamano.dominio.busquedas;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import co.gov.coldeportes.deportealamano.dominio.entidades.Escenario;
import co.gov.coldeportes.deportealamano.dominio.entidades.Evento;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEscenarios.AccesoEscenarios;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEventos.AccesoEventos;

public class BusquedaEvento {

	private AccesoEventos accesoEventos;
	private String pais;
	private String paisSeleccionado;
	private String departamentoSeleccionado;
	private String municipioSeleccionado;
	private String eventoSeleccionado;

	public BusquedaEvento(String pais1) {
		this.pais = pais1;
	}

	public void cargarDepartamentosColombia() {

	}

	/**
	 * Este metodo es el encargado de gestionar la carga de los eventos para el
	 * País seleccionado por el usuario
	 * 
	 * @return verdadero de tener exito, falso de lo contrario
	 */
	public void cargarPaises() {
		accesoEventos = new AccesoEventos(getPais());
		accesoEventos.cargaPaises();
		this.paisSeleccionado = getPais();
		/*
		 * / if (accesoEventos.cargaPaises()) { paisSeleccionado = getPais();
		 * return true;
		 * 
		 * } return false; /
		 */
	}

	/**
	 * Este metodo es el encargado de gestionar la carga de los eventos para el
	 * departamento seleccionado por el usuario
	 * 
	 * @param departamento
	 * @return verdadero de tener exito, falso de lo contrario
	 */
	public boolean cargaMunicipios(String departamento) {
		if (this.getPais().toLowerCase().equals("colombia")) {
			if (accesoEventos.cargaListaMunicipiosColombia(departamento)) {
				departamentoSeleccionado = departamento;
				return true;
			}
			return false;
		} else {
			if (accesoEventos.cargarListaMunicipiosNoColombia()) {
				departamentoSeleccionado = departamento;
				return true;
			}
			return false;
		}

	}

	/**
	 * Este metodo es el encargado de gestionar la carga de los eventos para el
	 * municipio o ciudad seleccionado por el usuario
	 * 
	 * @param municipio
	 * @return verdadero de tener exito, falso de lo contrario
	 */
	public boolean cargaEventos(String municipio) {
		if (accesoEventos.cargarListaNombreEventosMunicipio(municipio)) {
			municipioSeleccionado = municipio;
			return true;
		}
		return false;
	}

	public String getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	public String getMunicipioSeleccionado() {
		return municipioSeleccionado;
	}

	public void setMunicipioSeleccionado(String municipioSeleccionado) {
		this.municipioSeleccionado = municipioSeleccionado;
	}

	public String getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(String eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	/**
	 * Metodo que se encarga de devolver la lista de los eventos de acuerdo a
	 * los filtros usados por el usuario, estos son pais, departamento,
	 * municipio/ciudad, evento
	 * 
	 * @return lista de eventos o evento, o nulo cuando no se ha usado ningun
	 *         filtro
	 */
	@SuppressLint("DefaultLocale")
	public List<Evento> devuelveEventos() {
		if (paisSeleccionado != null) {
			if (paisSeleccionado.toLowerCase().equals("colombia")) {
				if (departamentoSeleccionado != null) {
					if (municipioSeleccionado != null) {
						if (eventoSeleccionado != null) {
							return accesoEventos
									.obtenerListaEventosDeEventoSeleccionado(
											eventoSeleccionado,
											municipioSeleccionado);
						} else {
							return accesoEventos
									.obtenerListaEventosDeMunicipioCiudadSeleccionado(municipioSeleccionado);
						}

					} else if (eventoSeleccionado != null) {
						return accesoEventos
								.obtenerListaEventosEventoSeleccionado(eventoSeleccionado);
					} else {
						return accesoEventos
								.obtenerListaEventosDeDepartamentoSeleccionado(departamentoSeleccionado);
					}
				} else if (eventoSeleccionado != null) {
					return accesoEventos
							.obtenerListaEventosEventoSeleccionado(eventoSeleccionado);
				} else {
					return accesoEventos
							.obtenerListaEventosDePaisSeleccionado();
				}
			} else {
				if (municipioSeleccionado != null) {
					if (eventoSeleccionado != null) {
						return accesoEventos
								.obtenerListaEventosDeEventoSeleccionado(
										eventoSeleccionado,
										municipioSeleccionado);
					} else {
						return accesoEventos
								.obtenerListaEventosDeMunicipioCiudadSeleccionado(municipioSeleccionado);
					}
				} else if (eventoSeleccionado != null) {
					return accesoEventos
							.obtenerListaEventosEventoSeleccionado(eventoSeleccionado);
				}

				else {
					return accesoEventos
							.obtenerListaEventosDePaisSeleccionado();
				}

			}
		}
		return null;

	}

	/**
	 * Este metodo deja borra los filtros seleccionados por el usuario de
	 * memoria
	 */
	public void restableceSeleccionados() {
		this.paisSeleccionado = null;
		this.departamentoSeleccionado = null;
		this.municipioSeleccionado = null;
		this.eventoSeleccionado = null;
	}

	public void asignaEventoSeleccionado(String evento) {
		eventoSeleccionado = evento;
	}

	public AccesoEventos getAccesoEventos() {
		return accesoEventos;
	}

	public void setAccesoEventos(AccesoEventos accesoEventos) {
		this.accesoEventos = accesoEventos;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPaisSeleccionado() {
		return paisSeleccionado;
	}

	public void setPaisSeleccionado(String paisSeleccionado) {
		this.paisSeleccionado = paisSeleccionado;
	}

}
