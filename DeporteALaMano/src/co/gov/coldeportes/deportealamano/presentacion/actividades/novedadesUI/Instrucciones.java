package co.gov.coldeportes.deportealamano.presentacion.actividades.novedadesUI;

import co.gov.coldeportes.deportealamano.presentacion.R;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Tab_Mapa;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Movie;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

public class Instrucciones {

	private String EULA_PREFIX = "eula_";
	private Activity mActivity;

	public Instrucciones(Activity context) {
		mActivity = context;
	}

	private PackageInfo getPackageInfo() {
		PackageInfo pi = null;
		try {
			pi = mActivity.getPackageManager().getPackageInfo(
					mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi;
	}

	public void show() {
		PackageInfo versionInfo = getPackageInfo();

		// the eulaKey changes every time you increment the version number
		// in the AndroidManifest.xml
		final String eulaKey = EULA_PREFIX + versionInfo.versionCode;
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(mActivity);
		boolean hasBeenShown = prefs.getBoolean(eulaKey, false);
		if (hasBeenShown == false) {

			// Show the Eula
			String title = mActivity.getString(R.string.app_name) + " v"
					+ versionInfo.versionName;

			// Includes the updates as well so users know what changed.
			String message = "ola";
			LayoutInflater factory = LayoutInflater.from(mActivity);
			final View view = factory.inflate(R.layout.instrucciones_ui, null);
			TextView txtInstrucciones= (TextView)view.findViewById(R.id.txtInstruccion);
			txtInstrucciones.setText("Deslice la pantalla en la parte de abajo hacia la izquierda o la derecha, para moverse a las demas pantallas");
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
					.setView(view)
					.setTitle("Bienvenido")
					.setIcon(R.drawable.icono)
					.setNeutralButton("No mostrar de Nuevo",
							new Dialog.OnClickListener() {

								@Override
								public void onClick(
										DialogInterface dialogInterface, int i) {
									// Mark this version as read.
									SharedPreferences.Editor editor = prefs
											.edit();
									editor.putBoolean(eulaKey, true);
									editor.commit();
									dialogInterface.dismiss();
								}
							})
					.setNegativeButton("Aceptar", new Dialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Close the activity as they have
							// declined the EULA
							
						}

					});
			builder.create().show();
		}
	}

}
