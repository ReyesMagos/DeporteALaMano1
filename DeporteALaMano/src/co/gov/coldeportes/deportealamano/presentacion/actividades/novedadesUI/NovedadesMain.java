package co.gov.coldeportes.deportealamano.presentacion.actividades.novedadesUI;

import java.util.List;

import co.gov.coldeportes.deportealamano.dominio.controladores.NovedadesController;
import co.gov.coldeportes.deportealamano.dominio.controladores.RedSocialController;
import co.gov.coldeportes.deportealamano.dominio.entidades.AdaptadorTitulares;
import co.gov.coldeportes.deportealamano.dominio.entidades.Evento;
import co.gov.coldeportes.deportealamano.dominio.entidades.TitularesNovedades;
import co.gov.coldeportes.deportealamano.presentacion.AcercaDeUi;
import co.gov.coldeportes.deportealamano.presentacion.R;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Comunicador;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Tab_Principal;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class NovedadesMain extends Activity {

	private Intent cambioActividad;
	public float initX;

	private ListView lsNovedades;
	public TitularesNovedades[] titularesnovedades;
	private static boolean bConectado;
	private NovedadesController controladorNovedades;
	public TitularesNovedades titularNovedadesEvento;
	private Evento eventoSeleccionadoDeLista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.novedades);
		inicializaControles();

		if (verificaConexion(getApplicationContext())) {
			controladorNovedades = new NovedadesController();
			Comunicador.setActividad(NovedadesMain.this);
			if (controladorNovedades.obtieneNovedades()) {
				organizaNovedades();
				ListView novedades = (ListView) findViewById(R.id.lsNovedades2);
				registerForContextMenu(novedades);
			} else {
				mensajeDeAlerta(
						"No hay novedades que listar para los proximos 6 días",
						"Información", 1);
			}

		} else {
			mensajeDeAlerta("Por favor verifique su conexión a internet",
					"Error de Conexión!!", 0);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.novedades_main, menu);
		return true;
	}

	/**
	 * Metodo encargado verificar si existe conexion a internet en el
	 * dispositivo
	 * 
	 * @param ctx
	 * @return verdadero de existir conexion a internet, falso de lo contrario
	 */
	public static boolean verificaConexion(Context ctx) {
		bConectado = false;
		ConnectivityManager connec = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] redes = connec.getAllNetworkInfo();
		for (int i = 0; i < 2; i++) {

			if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
				bConectado = true;
			}
		}
		return bConectado;
	}

	/**
	 * Este metodo organiza las novedades que se mostraran al usuario y la forma
	 * en que seran mostradas ademas de ordenar la carga de la lista
	 * correspondiente a estas
	 */
	private void organizaNovedades() {
		titularesnovedades = new TitularesNovedades[controladorNovedades
				.getAccesoNovedades().getServicio().getListaEvento().size()];
		int m = 0;
		List<Evento> le = controladorNovedades.getAccesoNovedades()
				.getServicio().getListaEvento();
		for (Evento e : le) {
			titularesnovedades[m] = new TitularesNovedades(e.getNombre(),
					e.getLugar(), e.getPais(), e.getFechaD());
			m += 1;
		}

		cargarLista();
	}

	/**
	 * Este metodo se encarga de cargar la lista de novedades que se muestra al
	 * usuario
	 */
	private void cargarLista() {
		AdaptadorTitulares adaptador = new AdaptadorTitulares(this,
				titularesnovedades);
		lsNovedades = (ListView) findViewById(R.id.lsNovedades2);
		lsNovedades.setAdapter(adaptador);
		lsNovedades.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				/**
				 * String opcionSeleccionada = Long.toString(arg3);
				 */
				String opcionSeleccionada = ((TitularesNovedades) arg0
						.getAdapter().getItem(arg2)).getNombre()
						+ ", "
						+ ((TitularesNovedades) arg0.getAdapter().getItem(arg2))
								.getCiudad();

				// es lo mismo que el spinner solo que me devuelve el argumento
				// en int y en long pero es igual

				Toast.makeText(
						getApplicationContext(),
						"opcion: " + opcionSeleccionada + ", "
								+ Integer.toString(arg2), Toast.LENGTH_LONG)
						.show();
			}
		});
		this.lsNovedades
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {

						titularNovedadesEvento = (TitularesNovedades) arg0
								.getAdapter().getItem(arg2);
						List<Evento> le = controladorNovedades
								.getAccesoNovedades().getServicio()
								.getListaEvento();
						eventoSeleccionadoDeLista = le.get(arg2);

						return false;
					}
				});
	}

	/**
	 * Se crea el menu contextual que se le mostrara al usuario al hacer un
	 * click prolongado en la lista novedades
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.compartir_menu_contextual, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.compartir:

			RedSocialController accesoRedSocial = new RedSocialController();
			accesoRedSocial.compartir(this, "Voy a ir a: "
					+ titularNovedadesEvento.getNombre() + ", el día: "
					+ titularNovedadesEvento.getFecha() + ", en: "
					+ titularNovedadesEvento.getCiudad());
			return true;
		case R.id.ver:
			mostrarEvento(eventoSeleccionadoDeLista);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	/**
	 * Este metodo inicializa los controles del layout (Asocia)
	 */
	private void inicializaControles() {
		// viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
		// viewFlipper.setOnTouchListener(new ListenerTouchViewFlipper());
		cambioActividad = new Intent(this, Tab_Principal.class);
	}

	public void btnIrABusqueda1(View view) {
		startActivity(cambioActividad);
	}

	public void cargaActividad() {
		controladorNovedades = new NovedadesController();
		if (controladorNovedades.obtieneNovedades()) {
			organizaNovedades();
		} else {
			mensajeDeAlerta(
					"No hay novedades que listar para los proximos 6 días",
					"Información", 1);
		}

	}

	public void mensajeDeAlerta(String mensaje, String titulo, final int tipo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		builder.setIcon(R.drawable.icono);
		builder.setMessage(mensaje).setPositiveButton(R.string.btnaceptar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		AlertDialog dialog = builder.show();
		TextView messageText = (TextView) dialog
				.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);
		dialog.show();

	}

	public void muestraAcercaDe(View view) {
		Intent i = new Intent(this, AcercaDeUi.class);
		startActivity(i);
	}

	public void mostrarEvento(Evento ev) {
		if (ev != null) {
			AlertDialog.Builder builderInner = new AlertDialog.Builder(
					NovedadesMain.this);

			builderInner.setMessage(ev.getDescripEvento() + "\n" + "Evento: "
					+ ev.getNombre() + "\n" + "Entidad: "
					+ verificaFaltaTexto(ev.getEntidad()) + "\n" + "Tipo: "
					+ verificaFaltaTexto(ev.getTipo()) + "\n"
					+ "Fecha Inicio: " + verificaFaltaTexto(ev.getFechaD())
					+ "\n" + "Fecha Fin: " 
					+ verificaFaltaTexto(ev.getFechaH()) + "\n"+ "Localización: "
					+ verificaFaltaTexto(ev.getPais()) + "-"
					+ verificaFaltaTexto(ev.getLugar()) + "\n" + "Escenario: "
					+ verificaFaltaTexto(ev.getEscenario()) + "\n"
					+ "Descripción: "
					+ verificaFaltaTexto(ev.getDescripEvento()) + "\n"
					+ "Página Web: " + verificaFaltaTexto(ev.getPaginaWeb())
					+ "\n" + "Email: " + verificaFaltaTexto(ev.getEmail()));

			builderInner.setTitle("Descripción del Evento: " + ev.getNombre());

			builderInner.setIcon(R.drawable.icono);
			builderInner.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			AlertDialog dialogo = builderInner.show();
			TextView messageText = (TextView) dialogo
					.findViewById(android.R.id.message);
			messageText.setGravity(Gravity.CENTER);
			dialogo.show();
		}

	}

	public String verificaFaltaTexto(String s) {
		if (s.length() < 2) {
			return "Sin Información";
		}
		return s;
	}
}
