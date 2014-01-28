package co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEventos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.util.Log;
import co.gov.coldeportes.deportealamano.dominio.entidades.Escenario;
import co.gov.coldeportes.deportealamano.dominio.entidades.Evento;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Comunicador;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.ServicioRest;

public class AccesoEventos {

	private String url = "";
	private String pais;
	private static List<Evento> listaEventos;
	private static String[] listaDepartamentos;
	private static String[] listaMunicipios;
	private static String[] listaNombreEventos;
	private static ServicioRest servicio;

	public AccesoEventos(String pais1) {
		pais1 = pais1.replaceAll(" ", "%20");
		this.pais = pais1;
	}

	/**
	 * Este metodo es el encargado de hacer el servicio rest y cargar los
	 * eventos correspondientes al país seleccionado por el usuario a memoria
	 * 
	 * @return verdadero cuando los eventos del set de datos son cargados en
	 *         memoria correctamente, falso de lo contrario
	 */
	@SuppressLint("DefaultLocale")
	public void cargaPaises() {

		ServicioRest s = new ServicioRest(
				"consolidadoeventos?$filter=pais%20eq%20'" + pais
						+ "'&$format=json", 2, "pais");
		s.execute();
		Comunicador.setPaisSeleccionado(this.pais.toLowerCase());
		servicio = s;

	}

	/**
	 * Este metodo mira las ciudades y/o municipios que estan disponible en los
	 * eventos del set de datos cuando el país seleccionado por el usuario es
	 * colombia
	 * 
	 * @return verdadero de ser exitosa la carga, o falso de lo contrario
	 */
	public boolean cargaListaMunicipiosColombia(String depto) {

		int x, n = 0;

		try {
			x = servicio.getListaEvento().size();
			if (listaMunicipios != null)
				listaMunicipios = null;
			if (x > 0) {
				listaMunicipios = new String[x];
				for (Evento e : listaEventos) {
					if (depto.equals(e.getTerritorio())) {

						if (n < 1) {
							listaMunicipios[n] = e.getLugar();
							n += 1;
						} else if (!verificaMunicipios(e.getLugar())) {
							listaMunicipios[n] = e.getLugar();
							n += 1;
						}
					}
				}
				return true;
			}

			return false;

		} catch (Exception ex) {
			x = 0;
			return false;

		}

	}

	/**
	 * Este metodo mira las ciudades que estan disponible en los eventos del set
	 * de datos cuando el país seleccionado por el usuario no es colombia
	 * 
	 * @return retorna verdadero de ser exitosa la carga o falso de lo contrario
	 */
	public static boolean cargarListaMunicipiosNoColombia() {
		String muni;
		int x;
		try {
			x = servicio.getListaEvento().size();
			if (x > 0) {
				listaEventos = servicio.getListaEvento();
				listaMunicipios = new String[x];

				int m = 0;
				for (Evento e : listaEventos) {
					muni = e.getLugar();
					Log.i("Municipio", muni);
					if (m < 1) {
						listaMunicipios[m] = muni;
						m += 1;
					} else if (!verificaMunicipios(muni)) {
						listaMunicipios[m] = muni;
						m += 1;
					}

				}
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			x = 0;
			return false;

		}

	}

	/**
	 * Este metodo verifica que un municipio no pertenezca ya a la lista de los
	 * municipios que se muestran al usuario para que escoja
	 * 
	 * @param muni
	 * @return verdadero en caso de que estar ya en la lista falso de lo
	 *         contrario
	 */
	public static boolean verificaMunicipios(String muni) {

		for (String s : listaMunicipios) {
			if (s == null) {
				break;
			}

			if (s.equals(muni)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Este metodo verifica que un departamento no pertenezca ya a la lista de
	 * los departamentos que se muestran al usuario para que escoja
	 * 
	 * @param depto
	 * @return verdadero en caso de que estar ya en la lista falso de lo
	 *         contrario
	 */
	public static boolean verificaDepartamentos(String depto) {

		for (String s : listaDepartamentos) {
			if (s == null) {
				break;
			}

			if (s.equals(depto)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Este metodo obtiene una lista de los eventos que se desarrollan en el
	 * país seleccionado por el usuario
	 * 
	 * @return lista de eventos
	 */
	public List<Evento> obtenerListaEventosDePaisSeleccionado() {
		return servicio.getListaEvento();
	}

	/**
	 * Este metodo obtiene una lista de los eventos que se desarrollan en el
	 * departamento seleccionado por el usuario
	 * 
	 * @param depto
	 * @return lista de eventos
	 * 
	 */
	public List<Evento> obtenerListaEventosDeDepartamentoSeleccionado(
			String depto) {
		List<Evento> lev = new ArrayList<Evento>();
		for (Evento e : listaEventos) {
			if (e.getTerritorio().equals(depto)) {
				lev.add(e);
			}
		}
		return lev;
	}

	/**
	 * Este metodo obtiene una lista de los eventos que se desarrollan en el
	 * municipio o ciudad seleccionado por el usuario
	 * 
	 * @param ciudad
	 * @return lista de eventos
	 */
	public List<Evento> obtenerListaEventosDeMunicipioCiudadSeleccionado(
			String ciudad) {
		List<Evento> lev = new ArrayList<Evento>();
		for (Evento e : listaEventos) {
			if (e.getLugar().equals(ciudad)) {

				lev.add(e);
			}
		}
		return lev;
	}

	public List<Evento> obtenerListaEventosEventoSeleccionado(String evento) {
		List<Evento> lev = new ArrayList<Evento>();
		for (Evento e : listaEventos) {
			if (e.getNombre().equals(evento)) {
				lev.add(e);
				return lev;
			}
		}
		return lev;
	}

	/**
	 * Este metodo de los eventos que coinciden en nombre con el evento
	 * seleccionado por el usuario
	 * 
	 * @param evento
	 * @return lista de eventos
	 */
	public List<Evento> obtenerListaEventosDeEventoSeleccionado(String evento,
			String municipio) {
		List<Evento> lev = obtenerListaEventosDeMunicipioCiudadSeleccionado(municipio);
		List<Evento> ListaEventoADevolver = new ArrayList<Evento>();
		for (Evento e : lev) {
			if (e.getNombre().equals(evento)) {
				ListaEventoADevolver.add(e);
				return ListaEventoADevolver;
			}
		}
		return null;

	}

	/**
	 * Este metodo obtiene/carga los departamento que estan disponibles en el
	 * set de datos cuando el país seleccionado por el usuario es colombia
	 * 
	 * @return retorna verdadero de ser exitosa la carga o falso de lo contrario
	 */
	public static boolean cargarListaDepartamentos() {
		String depto;
		int x;
		try {
			x = servicio.getListaEvento().size();
			if (x > 0) {
				listaEventos = servicio.getListaEvento();
				listaDepartamentos = new String[x];

				int m = 0;
				for (Evento e : listaEventos) {
					depto = e.getTerritorio();
					Log.i("Departamento", depto);
					if (m < 1) {
						listaDepartamentos[m] = depto;
						m += 1;
					} else if (!verificaDepartamentos(depto)) {
						listaDepartamentos[m] = depto;
						m += 1;
					}

				}
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			x = 0;
			return false;

		}

	}

	/**
	 * Este metodo obtiene/carga los eventos que estan disponible en el set de
	 * datos para el municipio/ciudad seleccionado por el usuario
	 * 
	 * @return retorna verdadero de ser exitosa la carga o falso de lo contrario
	 */
	public boolean cargarListaNombreEventosMunicipio(String municipio) {

		int x, n = 0;

		try {
			x = servicio.getListaEvento().size();

			if (x > 0) {
				listaNombreEventos = new String[x];
				for (Evento e : listaEventos) {
					if (municipio.equals(e.getLugar())) {

						listaNombreEventos[n] = e.getNombre();

						n += 1;
					}
				}
				return true;
			}

			return false;

		} catch (Exception ex) {
			x = 0;
			return false;

		}

	}

	public static void cargarListaNombreEventosDepartamento(String depto) {

		int x, n = 0;

		x = servicio.getListaEvento().size();

		if (x > 0) {
			listaNombreEventos = new String[x];
			for (Evento e : listaEventos) {
				if (e.getTerritorio().toLowerCase().equals(depto.toLowerCase())) {

					listaNombreEventos[n] = e.getNombre();

					n += 1;

				}

			}

		}

	}

	public static void cargarListaNombreEventosTodos() {

		int x, n = 0;

		x = servicio.getListaEvento().size();

		if (x > 0) {
			listaNombreEventos = new String[x];
			for (Evento e : listaEventos) {

				listaNombreEventos[n] = e.getNombre();
				n += 1;

			}

		}

	}

	/**
	 * este metodo devuelve la lista de los departamento disponibles a mostrar
	 * al usuario
	 * 
	 * @return arreglo con los departamentos a mostrar al usuario
	 */
	public String[] getListaDepartamentos() {
		String[] arre = listaDepartamentos;
		List<String> ls = new ArrayList<String>();
		int n = 0;
		for (String s : arre) {
			if (s == null) {
				break;
			}
			if (!s.equals("")) {
				ls.add(s);
				n += 1;
			}

		}
		String[] vec = ordenaArreglo(ls);
		String[] arre1 = new String[n + 1];
		arre1[0] = "seleccione";
		for (int i = 0; i < n; i++) {

			arre1[i + 1] = vec[i];

		}

		return arre1;
	}

	/**
	 * este metodo devuelve la lista de los municipios o ciudades disponibles a
	 * mostrar al usuario
	 * 
	 * @return arreglo con los municipios mostrar al usuario
	 */
	public String[] getListaMunicipios() {
		String[] arre = listaMunicipios;
		List<String> ls = new ArrayList<String>();
		int n = 0;
		for (String s : arre) {
			if (s == null) {
				break;
			}
			if (!s.equals("")) {
				ls.add(s);
				n += 1;
			}

		}
		String[] vec = ordenaArreglo(ls);
		String[] arre1 = new String[n + 1];
		arre1[0] = "seleccione";
		for (int i = 0; i < n; i++) {

			arre1[i + 1] = vec[i];

		}

		return arre1;
	}

	/**
	 * este metodo devuelve la lista de los eventos disponibles a mostrar al
	 * usuario
	 * 
	 * @return arreglo con los eventos a mostrar al usuario
	 */
	public String[] getListaEventos() {
		String[] arre = listaNombreEventos;
		List<String> ls = new ArrayList<String>();
		int n = 0;
		for (String s : arre) {
			if (s == null) {
				break;
			}
			if (!s.equals("")) {
				ls.add(s);
				n += 1;
			}

		}
		String[] vec = ordenaArreglo(ls);
		String[] arre1 = new String[n + 1];
		arre1[0] = "seleccione";
		for (int i = 0; i < n; i++) {

			arre1[i + 1] = vec[i];

		}

		return arre1;
	}

	/**
	 * este metodo ordena alfabeticamente los arreglos que seran mostrados al
	 * usuario para su seleccion
	 * 
	 * @param ls
	 * @return arreglo ordenado alfabeticamento
	 */
	public String[] ordenaArreglo(List<String> ls) {

		String[] vec = new String[ls.size()];
		int h = 0;
		for (String s : ls) {
			vec[h] = s;
			h += 1;
		}
		Arrays.sort(vec, String.CASE_INSENSITIVE_ORDER);
		return vec;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
