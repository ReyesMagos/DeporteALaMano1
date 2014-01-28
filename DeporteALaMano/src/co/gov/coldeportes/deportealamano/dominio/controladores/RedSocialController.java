package co.gov.coldeportes.deportealamano.dominio.controladores;

import android.app.Activity;
import co.gov.coldeportes.deportealamano.servicios.serviciosExternos.redesSociales.ServiciosExternosFacade;

public class RedSocialController {

	private ServiciosExternosFacade facadeServicios;

	public void compartir(Activity a, String evento) {
		facadeServicios = ServiciosExternosFacade.getInstancia();
		facadeServicios.compartirRedSocial(a, evento);
	}

}
