package co.gov.coldeportes.deportealamano.presentacion.actividades.tabs;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.app.FragmentActivity;
import android.text.method.ScrollingMovementMethod;
import co.gov.coldeportes.deportealamano.presentacion.*;
import co.gov.coldeportes.deportealamano.servicios.accesoImagenes.ServicioImagenes;
import co.gov.coldeportes.deportealamano.servicios.serviciosExternos.googleMaps.MapaEscenario;
import co.gov.coldeportes.deportealamano.servicios.serviciosExternos.redesSociales.ServiciosExternosFacade;
import co.gov.coldeportes.deportealamano.dominio.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
//import co.gov.coldeportes.deportealamano.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_Mapa extends android.support.v4.app.FragmentActivity {

	private String nombre = "";
	private String depto = "";
	private String direccion = "";
	public static GoogleMap mapa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tab__mapa);
		ubicarMapa();
		// MapaEscenario.ubicarEscenario(mapa,new LatLng(5.2, -75.08175),
		// "ola");
	}

	private void ubicarMapa() {

		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment1)).getMap();

		mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraPosition posicion = new CameraPosition.Builder()
				.target(new LatLng(4.60971, -74.08175)).zoom(5).bearing(0)
				.build();
		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
		mapa.animateCamera(camUpd);
		mapa.setMyLocationEnabled(true);
		mapa.setInfoWindowAdapter(new InfoWindowAdapter() {

			// Use default InfoWindow frame

			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
				// TODO Auto-generated method stub

			}

			@Override
			public View getInfoContents(Marker arg0) {
				// TODO Auto-generated method stub

				View v = getLayoutInflater().inflate(
						R.layout.custom_info_window, null);

				// se coje la poscion del marcador creado o posicionado
				LatLng latLng = arg0.getPosition();

				// se cojen los textview que se definieron en el ml
				TextView lblNombre = (TextView) v.findViewById(R.id.lblnombre);
				lblNombre.setMovementMethod(new ScrollingMovementMethod());

				TextView lblTipo = (TextView) v.findViewById(R.id.lbltipo);

				TextView lblMuni = (TextView) v.findViewById(R.id.lblmuni);

				TextView lblDireccion = (TextView) v
						.findViewById(R.id.lbldireccion);

				String[] datos = MapaEscenario.arreglaTitulo(arg0.getTitle());
				if (datos != null) {
					if (datos.length == 8) {

						lblNombre.setText(datos[0]);
						lblTipo.setText(datos[1]);
						lblMuni.setText(datos[3]);
						lblDireccion.setText(datos[4]);
						if (datos[7] != "" && datos[7] != null) {

							arg0.setSnippet(datos[7]);

						}

					} else if (datos.length == 10) {
						lblNombre.setText(datos[1]);
						lblTipo.setText(datos[0]);
						lblMuni.setText(datos[3]);
						lblDireccion.setText(datos[4]);
						if (datos[9] != "" && datos[9] != null) {

							arg0.setSnippet(datos[9]);

						}

					}
				}

				return v;
			}

		});

		mapa.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {

				// TODO Auto-generated method stub

				Comunicador.setActividad(Tab_Mapa.this);
				if (verificaConexion(getApplicationContext())) {
					AlertDialog.Builder alertadd = new AlertDialog.Builder(
							Tab_Mapa.this);
					LayoutInflater factory = LayoutInflater.from(Tab_Mapa.this);
					final View view = factory.inflate(R.layout.sample, null);

					ImageView imd = (ImageView) view
							.findViewById(R.id.imsample);
					TextView txtNombreAlerta = (TextView) view
							.findViewById(R.id.lblNombreAlerta);
					TextView txtTipoAlerta = (TextView) view
							.findViewById(R.id.lblTipoAlerta);
					TextView txtDeporteFechasAlerta = (TextView) view
							.findViewById(R.id.lblDeporteFechasAlerta);
					TextView txtDeptoMuniPais = (TextView) view
							.findViewById(R.id.lblDeptoMuniPais);
					TextView txtDireccionAlerta = (TextView) view
							.findViewById(R.id.lblDireccionAlerta);
					TextView txtDescripcionAlerta = (TextView) view
							.findViewById(R.id.lblDescripcionAlerta);
					TextView txtTelefonoWebAlerta = (TextView) view
							.findViewById(R.id.lblTelefonoWebAlerta);
					TextView txtEmailAlerta = (TextView) view
							.findViewById(R.id.lblEmailAlerta);
					alertadd.setView(view);
					final String[] datos = MapaEscenario.arreglaTitulo(marker
							.getTitle());

					if (datos != null) {
						if (datos.length == 8) {
							txtNombreAlerta.setText("Nombre: " + datos[0]);
							txtNombreAlerta.setGravity(Gravity.CENTER);
							txtTipoAlerta.setText("Tipo: " + datos[1]);
							txtTipoAlerta.setGravity(Gravity.CENTER);
							txtDeporteFechasAlerta.setText("Deporte: "
									+ datos[2]);
							txtDeporteFechasAlerta.setGravity(Gravity.CENTER);
							txtDeptoMuniPais.setText("Localizacion: "
									+ datos[3]);
							txtDeptoMuniPais.setGravity(Gravity.CENTER);
							txtDireccionAlerta
									.setText("Direccion: " + datos[4]);
							txtDireccionAlerta.setGravity(Gravity.CENTER);
							txtDescripcionAlerta.setText("Descripcion: "
									+ datos[5]);
							txtDescripcionAlerta.setGravity(Gravity.CENTER);
							txtTelefonoWebAlerta.setText("Telefono: "
									+ datos[6]);
							txtTelefonoWebAlerta.setGravity(Gravity.CENTER);
							txtEmailAlerta.setVisibility(View.INVISIBLE);
							txtEmailAlerta.setGravity(Gravity.CENTER);

						} else if (datos.length == 10) {
							txtNombreAlerta.setText("Evento: " + datos[1]);
							txtNombreAlerta.setGravity(Gravity.CENTER);
							txtTipoAlerta.setText("Entidad: " + datos[2] + "\n"
									+ "Tipo: " + datos[3]);

							txtTipoAlerta.setGravity(Gravity.CENTER);
							txtDeporteFechasAlerta.setText("Fecha Inicio/Fin: "
									+ datos[4]);
							txtDeporteFechasAlerta.setGravity(Gravity.CENTER);
							txtDeptoMuniPais.setText("Localizacion: "
									+ datos[5]);
							txtDeptoMuniPais.setGravity(Gravity.CENTER);
							txtDireccionAlerta
									.setText("Escenario: " + datos[0]);
							txtDireccionAlerta.setGravity(Gravity.CENTER);
							txtDescripcionAlerta.setText("Descripcion: "
									+ datos[6]);
							txtDescripcionAlerta.setGravity(Gravity.CENTER);
							txtTelefonoWebAlerta.setText("Pagina Web: "
									+ datos[7]);
							txtTelefonoWebAlerta.setGravity(Gravity.CENTER);
							txtEmailAlerta.setText("Email: " + datos[8]);
							txtEmailAlerta.setVisibility(View.VISIBLE);
							txtEmailAlerta.setGravity(Gravity.CENTER);
						}
					}

					String s3 = marker.getSnippet();

					if (s3 != null && !s3.equals("")
							&& !s3.equals("Sin Informacion")) {
						ServicioImagenes s = new ServicioImagenes(marker
								.getSnippet());
						s.execute();
						try {

							s.get();

							imd.setImageBitmap(Bitmap.createScaledBitmap(
									s.getBmp(), 150, 150, false));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.i("Me Toco", "Tocame Tocame");

					}
					alertadd.setIcon(R.drawable.icono);
					alertadd.setTitle("Informacion");
					alertadd.setNeutralButton("Aceptar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dlg,
										int sumthin) {

								}
							});

					alertadd.setPositiveButton("Compartir",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dlg,
										int sumthin) {
									ServiciosExternosFacade redSocial = new ServiciosExternosFacade();
									if (datos != null) {
										if (datos.length == 8) {
											redSocial.compartirRedSocial(
													Tab_Mapa.this, "Nombre: "
															+ datos[0] + ",\n"
															+ "direccion: "
															+ datos[4] + ",\n"
															+ "telefono"
															+ datos[6] + ",\n"
															+ "lugar: "
															+ datos[3]);
										} else if (datos.length == 10) {
											redSocial
													.compartirRedSocial(
															Tab_Mapa.this,
															"Voy a ir a: "
																	+ datos[1]
																	+ ", organizada por: "
																	+ datos[2]
																	+ ", el día: "
																	+organizaFechas( datos[4])
																	+ ", en: "
																	+ datos[0]);
										}

									}
								}
							});

					alertadd.show();

				} else {
					Tab_Busqueda.mensajeDeAlerta(
							"Se necesita conexión a internet para esta acción",
							"Falla en Conexión");
				}
			}
		});

	}

	public String organizaFechas(String s){
		for(int i=0;i <s.length();i++){
			if(Character.toString(s.charAt(i)).equals("-")){
				return s.substring(0,i);
			}
		}
		return s;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Tab_Principal.tabHost.setCurrentTab(1);
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.tab__escenarios, menu);
		return true;
	}

	public static boolean verificaConexion(Context ctx) {
		boolean bConectado = false;
		ConnectivityManager connec = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// No sólo wifi, también GPRS
		NetworkInfo[] redes = connec.getAllNetworkInfo();
		for (int i = 0; i < 2; i++) {
			// ¿Tenemos conexión? ponemos a true
			if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
				bConectado = true;
			}
		}
		return bConectado;
	}

}