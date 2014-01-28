package co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEscenarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.util.Log;

import co.gov.coldeportes.deportealamano.dominio.entidades.Escenario;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.ServicioRest;

public class AccesoEscenarios {

	private String url = "";
	private String depto;
	private static List<Escenario> listaEscenario;
	private static String[] listaMunicipios;
	private static String[] listaNombreEscenarios;
	private static ServicioRest servicio;

	public AccesoEscenarios() {

	}

	public AccesoEscenarios(String depto1) {
		depto1 = depto1.replaceAll(" ", "%20");
		this.depto = depto1;
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
	 * Este metodo es el encargado de hacer el servicio rest y cargar los todos
	 * escenarios disponibles en el set de datos a memoria
	 * 
	 * @return verdadero cuando los eventos del set de datos son cargados en
	 *         memoria correctamente, falso de lo contrario
	 */
	public boolean cargaTodo() {
		ServicioRest s = new ServicioRest("consolidadoescenarios?$format=json",
				1, "todo");
	    s.execute();
		servicio = s;
		try {
			s.get();
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Este metodo es el encargado de hacer el servicio rest y cargar los
	 * escenarios correspondientes al departamento seleccionado por el usuario a
	 * memoria
	 * 
	 * @return verdadero cuando los escenarios del set de datos son cargados en
	 *         memoria correctamente, falso de lo contrario
	 */
	public void cargaDeptos() {

		ServicioRest s = new ServicioRest(
				"consolidadoescenarios?$filter=departamento%20eq%20'" + depto
						+ "'&$format=json", 1, "departamento");
		s.execute();
		servicio = s;

	}

	/**
	 * Este metodo obtiene una lista de los escenarios del departamento
	 * seleccionado por el usuario
	 * 
	 * @return lista de escenarios
	 */
	public List<Escenario> obtieneEscenariosDepartamentoSeleccionado() {
		return this.servicio.getListaEscenario();
	}

	/**
	 * Este metodo obtiene una lista de los escenarios del municipio
	 * seleccionado por el usuario
	 * 
	 * @return lista de escenarios
	 */
	public List<Escenario> obtieneEscenariosDeMunicipioSeleccionado(
			String municipio) {
		List<Escenario> le = new ArrayList<Escenario>();
		for (Escenario e : this.listaEscenario) {
			if (municipio.equals(e.getMunicipio())) {
				le.add(e);
			}
		}
		return le;
	}

	/**
	 * Este metodo obtiene una lista de los escenaios que coinciden en nombre
	 * con el seleccionado por el usuario
	 * 
	 * @return lista de escenarios
	 */
	public Escenario devuelveEscenarioMunicipio(String municipio, String nombreEscenario) {
		List<Escenario> le = obtieneEscenariosDeMunicipioSeleccionado(municipio);
		for (Escenario e : le) {
			if (nombreEscenario.equals(e.getNombre())) {
				return e;
			}
		}
		return null;

	}
	
	public Escenario devuelveEscenarioSolo(String nombreEscenario){
		//List<Escenario> le = obtieneEscenarios;
		for (Escenario e : this.listaEscenario) {
			if (nombreEscenario.equals(e.getNombre())) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Este metodo obtiene/carga los escenarios que estan disponible en el set
	 * de datos para el municipio/ciudad seleccionado por el usuario
	 * 
	 * @return retorna verdadero de ser exitosa la carga o falso de lo contrario
	 */
	public boolean cargaListaNombreEscenariosParaMunicipio(String municipio) {
		int x, n = 0;

		try {
			x = servicio.getListaEscenario().size();

			if (x > 0) {
				listaNombreEscenarios = new String[x];
				for (Escenario e : listaEscenario) {
					if (municipio.equals(e.getMunicipio())) {
						listaNombreEscenarios[n] = e.getNombre();
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

	public static void cargaListaNombresEscenario() {
		int x, n = 0;
		x = servicio.getListaEscenario().size();
		if (x > 0) {
			listaNombreEscenarios = new String[x];
			for (Escenario e : listaEscenario) {
				listaNombreEscenarios[n] = e.getNombre();
				n+=1;
			}
		}

	}

	/**
	 * Este metodo carga los municipios que estan disponible en el set de datos
	 * para el departamento seleccionado por el usuario
	 * 
	 * @return retorna verdadero de ser exitosa la carga o falso de lo contrario
	 */
	public static boolean cargaListaMunicipios() {
		String muni;
		int x;
		try {
			x = servicio.getListaEscenario().size();
			if (x > 0) {
				listaEscenario = servicio.getListaEscenario();
				listaMunicipios = new String[x];

				int m = 0;
				for (Escenario e : listaEscenario) {
					muni = e.getMunicipio();
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

	public void setListaMunicipios(String[] listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	/**
	 * este metodo devuelve la lista de los municipios disponibles a mostrar al
	 * usuario
	 * 
	 * @return arreglo con los departamentos a mostrar al usuario
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
	 * este metodo devuelve la lista de los escenarios disponibles a mostrar al
	 * usuario
	 * 
	 * @return arreglo con los departamentos a mostrar al usuario
	 */
	public String[] getListaNombreEscenarios() {
		String[] arre = listaNombreEscenarios;
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

}
