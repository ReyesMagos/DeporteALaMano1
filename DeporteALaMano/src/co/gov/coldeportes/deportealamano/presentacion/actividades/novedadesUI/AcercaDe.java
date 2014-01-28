package co.gov.coldeportes.deportealamano.presentacion.actividades.novedadesUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import co.gov.coldeportes.deportealamano.presentacion.R;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Tab_Mapa;

public class AcercaDe {


	private Activity actividad;
	
	public AcercaDe(Activity actividadAux){
		this.actividad= actividadAux;
	}

	public void show(){
		AlertDialog.Builder alertadd = new AlertDialog.Builder(
				actividad);
		LayoutInflater factory = LayoutInflater.from(actividad);
		final View view = factory.inflate(R.layout.acerca_de, null);
		alertadd.setView(view);
		alertadd.setIcon(R.drawable.icono);
		alertadd.setTitle("Acerca De");
		alertadd.setNeutralButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {

					}
				});
		alertadd.show();

	}
}
