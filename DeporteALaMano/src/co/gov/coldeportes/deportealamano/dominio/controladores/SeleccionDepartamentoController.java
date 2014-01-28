package co.gov.coldeportes.deportealamano.dominio.controladores;

import co.gov.coldeportes.deportealamano.dominio.busquedas.BusquedaEscenario;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEscenarios.AccesoEscenarios;

public class SeleccionDepartamentoController {

	BusquedaEscenario busquedaEscenario;
	String dpto;

	public SeleccionDepartamentoController(String departamento) {
		this.dpto = departamento;
		this.busquedaEscenario = new BusquedaEscenario(this.getDpto());
	}

	public SeleccionDepartamentoController() {
		// TODO Auto-generated constructor stub
		this.busquedaEscenario = new BusquedaEscenario();
	}

	public void obtenerDepartamento() {

		 busquedaEscenario.buscarDepartamento();

	}
	
	

	public BusquedaEscenario getBusquedaEscenario() {
		return busquedaEscenario;
	}

	public void setBusquedaEscenario(BusquedaEscenario busquedaEscenario) {
		this.busquedaEscenario = busquedaEscenario;
	}

	public boolean obtenerMunicipio(String municipio) {
		return busquedaEscenario.cargarMunicipios(municipio);

	}

	public void determinaEscenario(String escenario) {
		busquedaEscenario.asignarEscenarioSeleccionado(escenario);

	}

	public AccesoEscenarios getAccesoEscenarios() {
		return busquedaEscenario.getAccesoEscenarios();
	}
	
	public void borraSeleccion(){
		busquedaEscenario.restableceSeleccionados();
	}

	public String getDpto() {
		return dpto;
	}
	
	
	
	

}
