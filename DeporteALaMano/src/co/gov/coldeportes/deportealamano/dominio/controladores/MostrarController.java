package co.gov.coldeportes.deportealamano.dominio.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import co.gov.coldeportes.deportealamano.dominio.entidades.Escenario;
import co.gov.coldeportes.deportealamano.dominio.entidades.Evento;
import co.gov.coldeportes.deportealamano.presentacion.R;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Tab_Busqueda;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Tab_Mapa;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.ServicioRest;
import co.gov.coldeportes.deportealamano.servicios.serviciosExternos.googleMaps.MapaEscenario;

public class MostrarController {

	public MapaEscenario mapaEscenario;
	public List<Escenario> ListaEscenarioEventos;

	public MostrarController() {
		ListaEscenarioEventos = new ArrayList<Escenario>();
	}

	/**
	 * Este es el metodo encargado de controlar la ubicacion en el mapa de los
	 * escenarios que cumplen con los criterios de busqueda establecidos por el
	 * usuario
	 */
	public void ubicarEscenario() {
		mapaEscenario = new MapaEscenario();
		List<Escenario> le = Tab_Busqueda.controladorEscenario.busquedaEscenario
				.devuelveEscenarios();
		if (le == null)
			return;
		int size = 0;
		if (le.size() > 10) {
			size = 6;
		} else if (le.size() > 1) {
			size = 11;

		} else {
			size = 12;
		}

		MapaEscenario.limpiaMapa(Tab_Mapa.mapa);
		for (Escenario escenario : le) {
			if (escenario == null)
				return;
			MapaEscenario.ubicarEscenario(Tab_Mapa.mapa, escenario, size);
		}
	}

	/**
	 * Este es el metodo encargado de devolver la lista de los eventos para los
	 * cuales el país no es colombia y que cumplen con los criterios de busqueda
	 * ingresados por el usuario
	 * 
	 * @return lista de eventos
	 */
	public List<Evento> ListaEventoInternacional() {
		List<Evento> lev = Tab_Busqueda.controladorEvento.getBusquedaEvento()
				.devuelveEventos();
		return lev;
	}

	@SuppressWarnings("static-access")
	public void ubicaEscenarioEvento(List<Evento> lev) {
		mapaEscenario = new MapaEscenario();
		mapaEscenario.limpiaMapa(Tab_Mapa.mapa);
		ServicioRest sr;
		for (Evento eventico : lev) {
			if (!eventico.getEscenario().equals("")
					&& eventico.getEscenario() != null) {
				Escenario escenario = verificaEscenarioDeEvento(eventico
						.getEscenario());
				if (escenario == null) {
					sr = new ServicioRest(
							"consolidadoescenarios?$filter=codigo%20eq%20'"
									+ eventico.getEscenario()
									+ "'&$format=json", 1,"mapa");
					sr.execute();
					try {
						sr.get();
						List<Escenario> le = sr.getListaEscenario();
						if (le.size() > 0) {
							Escenario escenarito = le.get(0);
							MapaEscenario.ubicarEscenarioEvento(Tab_Mapa.mapa,
									escenarito, eventico, 10);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					List<Escenario> le= new ArrayList<Escenario>();
					MapaEscenario.ubicarEscenarioEvento(Tab_Mapa.mapa,
							escenario, eventico, 10);
					
				}

			}
		}
	}

	public Escenario verificaEscenarioDeEvento(String codigoEscenario) {
		if (ListaEscenarioEventos.size() == 0)
			return null;
		for (Escenario escenario : ListaEscenarioEventos) {
			if (escenario.getCodigo().equals(codigoEscenario)) {
				return escenario;
			}
		}
		return null;

	}

}
