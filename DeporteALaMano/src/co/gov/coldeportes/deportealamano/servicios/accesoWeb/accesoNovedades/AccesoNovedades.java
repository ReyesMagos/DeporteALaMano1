package co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoNovedades;

import java.util.concurrent.ExecutionException;

import co.gov.coldeportes.deportealamano.servicios.accesoWeb.ServicioRest;

public class AccesoNovedades {

	private ServicioRest servicio;

	private String[] fechas;

	public AccesoNovedades(String[] fechas1) {
		this.fechas = fechas1;
	}

	public boolean cargarNovedades() {
		ServicioRest s = new ServicioRest(
				"consolidadoeventos?$orderby=fechadesde%20asc&$filter=fechadesde%20eq%20'"
						+ fechas[0] + "'%20or%20fechadesde%20eq%20'"
						+ fechas[1] + "'%20or%20fechadesde%20eq%20'"
						+ fechas[2] + "'%20or%20fechadesde%20eq%20'"
						+ fechas[3] + "'%20or%20fechadesde%20eq%20'"
						+ fechas[4] + "'%20or%20fechadesde%20eq%20'"
						+ fechas[5] + "'&$format=json", 4,"novedades");
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

	public ServicioRest getServicio() {
		return servicio;
	}

	public void setServicio(ServicioRest servicio) {
		this.servicio = servicio;
	}
	
	

}
