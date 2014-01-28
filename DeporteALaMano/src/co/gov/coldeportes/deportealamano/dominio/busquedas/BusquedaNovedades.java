package co.gov.coldeportes.deportealamano.dominio.busquedas;

import java.util.Calendar;
import java.util.List;

import co.gov.coldeportes.deportealamano.dominio.entidades.Evento;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoNovedades.AccesoNovedades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class BusquedaNovedades {

	// Aqui se realizaria los metodos para traer las novedades, y todo eso se
	// retorna, hasta
	// llegar a la interfaz NovedadesMain.java y se muestran
	private Calendar c;
	private int day;
	private int month;
	private int year;
	private AccesoNovedades accesoNovedades;
	private List<Evento> listaEventosNovedades;

	/**
	 * Este es el metodo que se encarga de gestionar la carga desde el set de
	 * datos a memoria de la lista de eventos para mostrar al usuario como
	 * novedades
	 * 
	 * @return verdadero de ser correcta, falso de lo contrario o de no haber
	 *         novedades para listar
	 */
	public boolean obtenerNovedades() {
		accesoNovedades = new AccesoNovedades(generaFechas());
		if (accesoNovedades.cargarNovedades()) {
			listaEventosNovedades = accesoNovedades.getServicio()
					.getListaEvento();
			if (listaEventosNovedades == null) {
				return false;
			}

			return true;
		}
		return false;

	}

	/**
	 * Este es el metodo encargado de obtener la fecha del dispositivo
	 */
	public void obtenerFecha() {
		c = Calendar.getInstance();
		day = c.get(Calendar.DAY_OF_MONTH);
		month = c.get(Calendar.MONTH) + 1;
		year = c.get(Calendar.YEAR);

	}

	/**
	 * Este es el metodo encargado de verificar si el año del dispositivo
	 * corresponde a un año bisiesto (366 días)
	 * 
	 * @return verdadero de ser bisiesto, falso de lo contrario
	 */
	public boolean verificaAñoBisiesto() {
		if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
			return true;

		} else {
			return false;
		}

	}

	/**
	 * Este es el metodo encargado de generar las 5 fechas despues de la fecha
	 * del dispositivo
	 * 
	 * @return arreglo con las fechas
	 */
	public String[] generaFechas() {
		obtenerFecha();
		String dia = "";
		String mes = "";
		String[] fechas = new String[6];
		int sum = 0;
		boolean paso = false;
		boolean dec = false;

		if (month == 4 || month == 6 || month == 9 || month == 11) {
			dia = Integer.toString(day);
			mes = Integer.toString(month);
			if (dia.length() == 1) {
				dia = "0";
				dia += Integer.toString(day);
			}
			if (mes.length() == 1) {
				mes = "0";
				mes += Integer.toString(month);
			}
			fechas[0] = dia + "/" + Integer.toString(month) + "/"
					+ Integer.toString(year);
			for (int i = 1; i < 6; i++) {
				sum = day + i;
				if (sum > 30) {
					sum = sum - 30;
					if (paso == false) {
						month += 1;
						paso = true;
					}

				}

				dia = Integer.toString(sum);

				if (dia.length() == 1) {
					dia = "0";
					dia += Integer.toString(sum);
				}
				mes = Integer.toString(month);
				if (mes.length() == 1) {
					mes = "0";
					mes += Integer.toString(month);
				}

				fechas[i] = dia + "/" + mes + "/" + Integer.toString(year);
				sum = 0;
			}
		} else if (month == 1 || month == 3 || month == 5 || month == 7
				|| month == 8 || month == 10 || month == 12) {
			dia = Integer.toString(day);
			if (dia.length() == 1) {
				dia = "0";
				dia += Integer.toString(day);
			}
			mes = Integer.toString(month);
			if (mes.length() == 1) {
				mes = "0";
				mes += Integer.toString(month);
			}
			fechas[0] = dia + "/" + mes + "/" + Integer.toString(year);
			for (int i = 1; i < 6; i++) {
				sum = day + i;
				if (sum > 31) {
					sum = sum - 31;
					if (month < 12 && paso == false && dec == false) {
						month += 1;
						paso = true;
					} else if (month == 12 && dec == false) {
						month = 1;
						year += 1;
						dec = true;
					}
				}
				dia = Integer.toString(sum);
				if (dia.length() == 1) {
					dia = "0";
					dia += Integer.toString(sum);
				}
				mes = Integer.toString(month);
				if (mes.length() == 1) {
					mes = "0";
					mes += Integer.toString(month);
				}
				fechas[i] = dia + "/" + mes + "/" + Integer.toString(year);
				sum = 0;
			}
		} else if (month == 2) {
			if (!verificaAñoBisiesto()) {
				dia = Integer.toString(day);
				if (dia.length() == 1) {
					dia = "0";
					dia += Integer.toString(day);
				}
				mes = Integer.toString(month);
				if (mes.length() == 1) {
					mes = "0";
					mes += Integer.toString(month);
				}
				fechas[0] = dia + "/" + mes + "/" + Integer.toString(year);
				for (int i = 1; i < 6; i++) {
					sum = day + i;
					if (sum == 29) {
						sum = (sum + 2) - 30;
						month += 1;

					} else if (sum == 30) {
						sum = (sum + 1) - 30;
					} else if (sum > 30) {
						sum = (sum - 30) + 1;
					}
					dia = Integer.toString(sum);
					if (dia.length() == 1) {
						dia = "0";
						dia += Integer.toString(sum);
					}
					mes = Integer.toString(month);
					if (mes.length() == 1) {
						mes = "0";
						mes += Integer.toString(month);
					}
					fechas[i] = dia + "/" + mes + "/" + Integer.toString(year);
					sum = 0;
				}

			} else {
				dia = Integer.toString(day);
				if (dia.length() == 1) {
					dia = "0";
					dia += Integer.toString(day);
				}
				mes = Integer.toString(month);
				if (mes.length() == 1) {
					mes = "0";
					mes += Integer.toString(month);
				}
				fechas[0] = dia + "/" +mes + "/"
						+ Integer.toString(year);
				for (int i = 1; i < 6; i++) {
					sum = day + i;
					if (sum == 30) {
						sum = (sum + 1) - 30;
						month += 1;
					} else if (sum > 30) {
						sum = (sum - 30) + 1;
					}
					dia = Integer.toString(sum);
					if (dia.length() == 1) {
						dia = "0";
						dia += Integer.toString(sum);
					}
					mes = Integer.toString(month);
					if (mes.length() == 1) {
						mes = "0";
						mes += Integer.toString(month);
					}
					fechas[i] = dia + "/" + mes + "/"
							+ Integer.toString(year);
					sum = 0;
				}
			}
		}
		for(int i=0; i<fechas.length;i++){
			Log.i("s",fechas[i]);
		}
		return fechas;

	}

	public AccesoNovedades getAccesoNovedades() {
		return accesoNovedades;
	}

	public void setAccesoNovedades(AccesoNovedades accesoNovedades) {
		this.accesoNovedades = accesoNovedades;
	}

	public List<Evento> getListaEventosNovedades() {
		return listaEventosNovedades;
	}

	public void setListaEventosNovedades(List<Evento> listaEventosNovedades) {
		this.listaEventosNovedades = listaEventosNovedades;
	}

}
