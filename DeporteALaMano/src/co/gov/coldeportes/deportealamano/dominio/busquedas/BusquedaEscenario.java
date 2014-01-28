package co.gov.coldeportes.deportealamano.dominio.busquedas;

import java.util.ArrayList;
import java.util.List;

import co.gov.coldeportes.deportealamano.dominio.entidades.Escenario;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEscenarios.AccesoEscenarios;

public class BusquedaEscenario {

	private AccesoEscenarios accesoEscenarios;
	private String depto;
	private String departamentoSeleccionado;
	private String municipioSeleccionado;
	private String escenarioSeleccionado;

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

	public String getEscenarioSeleccionado() {
		return escenarioSeleccionado;
	}

	public void setEscenarioSeleccionado(String escenarioSeleccionado) {
		this.escenarioSeleccionado = escenarioSeleccionado;
	}

	public BusquedaEscenario() {

	}

	public BusquedaEscenario(String depto1) {
		this.depto = depto1;
	}

	/**
	 * Este metodo es el encargado de gestionar la carga de los escenarios para
	 * el departamento seleccionado por el usuario
	 * 
	 * @return verdadero de tener exito, falso de lo contrario
	 */
	public void buscarDepartamento() {
		accesoEscenarios = new AccesoEscenarios(getDepto());
		accesoEscenarios.cargaDeptos();
		this.departamentoSeleccionado = depto;
		/*
		 * /
		 * 
		 * if (accesoEscenarios.cargaDeptos()) { this.departamentoSeleccionado =
		 * depto; return true; } return false; /
		 */

	}

	/**
	 * Este metodo es el encargado de gestionar la carga de los escenarios para
	 * el municipio seleccionado por el usuario
	 * 
	 * @param municipio
	 * @return verdadero de tener exito, falso de lo contrario
	 */
	public boolean cargarMunicipios(String municipio) {
		if (municipio != null) {
			if (accesoEscenarios
					.cargaListaNombreEscenariosParaMunicipio(municipio))
				this.municipioSeleccionado = municipio;
			return true;

		}
		return false;

	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public void asignarEscenarioSeleccionado(String escenario) {
		this.escenarioSeleccionado = escenario;
	}

	public AccesoEscenarios getAccesoEscenarios() {
		return accesoEscenarios;
	}

	public void setAccesoEscenarios(AccesoEscenarios accesoEscenarios) {
		this.accesoEscenarios = accesoEscenarios;
	}

	/**
	 * Metodo que se encarga de devolver la lista de los escenarios de acuerdo a
	 * los filtros usados por el usuario, estos son departamento,
	 * municipio/ciudad, escenario
	 * 
	 * @return lista de escenarios o escenario
	 */
	public List<Escenario> devuelveEscenarios() {
		if (departamentoSeleccionado != null) {
			if (municipioSeleccionado != null) {

				if (escenarioSeleccionado != null) {
					List<Escenario> le = new ArrayList<Escenario>();
					le.add(accesoEscenarios.devuelveEscenarioMunicipio(
							municipioSeleccionado, escenarioSeleccionado));
					return le;
				}

				return this.accesoEscenarios
						.obtieneEscenariosDeMunicipioSeleccionado(municipioSeleccionado);
			} else if (escenarioSeleccionado != null) {
				List<Escenario> le = new ArrayList<Escenario>();
				le.add(accesoEscenarios.devuelveEscenarioSolo(
						escenarioSeleccionado));
				return le;
			}

			return this.accesoEscenarios
					.obtieneEscenariosDepartamentoSeleccionado();
		} else {
			AccesoEscenarios ac = new AccesoEscenarios();
			ac.cargaTodo();
			return ac.obtieneEscenariosDepartamentoSeleccionado();
		}

	}

	/**
	 * Este metodo deja borra los filtros seleccionados por el usuario de
	 * memoria
	 */
	public void restableceSeleccionados() {
		this.departamentoSeleccionado = null;
		this.municipioSeleccionado = null;
		this.escenarioSeleccionado = null;
	}

}
