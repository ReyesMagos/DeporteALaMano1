package co.gov.coldeportes.deportealamano.presentacion.actividades.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.android.gms.internal.ev;

import co.gov.coldeportes.deportealamano.dominio.busquedas.BusquedaEscenario;
import co.gov.coldeportes.deportealamano.dominio.controladores.MostrarController;
import co.gov.coldeportes.deportealamano.dominio.controladores.SeleccionDepartamentoController;
import co.gov.coldeportes.deportealamano.dominio.controladores.SeleccionPaisController;
import co.gov.coldeportes.deportealamano.dominio.entidades.Evento;
import co.gov.coldeportes.deportealamano.presentacion.R;
import co.gov.coldeportes.deportealamano.presentacion.actividades.novedadesUI.AcercaDe;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.ServicioRest;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEscenarios.AccesoEscenarios;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEventos.AccesoEventos;
import co.gov.coldeportes.deportealamano.servicios.serviciosExternos.redesSociales.ServiciosExternosFacade;
import android.R.color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;

public class Tab_Busqueda extends Activity {

	private ViewSwitcher switcher;
	private CheckBox cbEscenario;
	private CheckBox cbEvento;
	private CheckBox cbEscenario2;
	private CheckBox cbEvento2;
	private TextView txtDepartamento;
	private TextView txtDepartamento2;
	private TextView txtMunicipio;
	private TextView txtMunicipio2;
	private TextView txtEscenario;
	private TextView txtEventos;
	private TextView txtPais;
	private String[] arregloPaises;
	private static String[] arregloMunicipios;
	private static String[] arregloEscenarios;
	private static String[] arregloDepartamentos2;
	private static String[] arregloMunicipios2;
	private static String[] arregloEventos;
	private String[] arregloDepartamentos;
	private static AutoCompleteTextView autoCompletarDepartamento;
	private static AutoCompleteTextView autoCompletarPaises;
	private static Button btnUbicar;
	private Button btnUbicar2;
	private Button btnNuevaBusqueda;
	private Button btnNuevaBusqueda2;
	private static Spinner spinnerEscenarios;
	private static Spinner spinnerDepartamentos;
	private static Spinner spinnerMunicipios2;
	private static Spinner spinnerEventos;
	private static Spinner spinnerMunicipios;
	private ArrayAdapter<String> adaptadorPaises;
	private static ArrayAdapter<String> adaptadorMunicipios;
	private static ArrayAdapter<String> adaptadorEscenarios;
	private static ArrayAdapter<String> adaptadorDepartamento2;
	private static ArrayAdapter<String> adaptadorMunicipios2;
	private static ArrayAdapter<String> adaptadorEventos;
	private ArrayAdapter<String> adaptadorDepartamentos;
	public static SeleccionDepartamentoController controladorEscenario;
	public static SeleccionPaisController controladorEvento;
	public static ProgressDialog dialog;
	public static Context contexto;
	public static View vv;
	public static boolean popUpWindowsVisible;
	public static boolean nuleando;
	public static PopupWindow popupWindow;
	public static ImageView btnMunicipio;
	public static ImageView btnMunicipio2;
	public static ImageView btnEscenario;
	public static ImageView btnDepartamento;
	public static ImageView btnEvento;
	public static ImageView btnAuto;
	public static ImageView btnAuto2;
	public static boolean presionado;
	public static boolean presionado2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tab__busqueda);
		contexto = getApplicationContext();
		vv = getWindow().getDecorView().findViewById(android.R.id.content);
		iniciaControlesLayout1();
		iniciaCbLayout1();
		iniciaTextViewLayout1();
		switcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);
		Comunicador.setActividad(this);
	}

	// ocultar controles al deseleccionar
	private void iniciaControlesLayout1() {
		btnAuto = (ImageView) findViewById(R.id.btnAuto);
		btnMunicipio = (ImageView) findViewById(R.id.btnMunicipio);
		btnEscenario = (ImageView) findViewById(R.id.btnEscenario);
		spinnerMunicipios = (Spinner) findViewById(R.id.spMunicipio);
		spinnerEscenarios = (Spinner) findViewById(R.id.spEscenario);
		btnUbicar = (Button) findViewById(R.id.btnMostrar);
		btnUbicar.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (!popUpWindowsVisible) {
					muestraPop();
					popUpWindowsVisible = true;
				}

				return false;
			}
		});
		btnNuevaBusqueda = (Button) findViewById(R.id.btnNuevaBusqueda);
		autoCompletarDepartamento = (AutoCompleteTextView) findViewById(R.id.autocomplete_departamentos);

		arregloDepartamentos = getResources().getStringArray(
				R.array.departamentos_array);

		adaptadorDepartamentos = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloDepartamentos);

		adaptadorDepartamentos
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);

		autoCompletarDepartamento.setAdapter(adaptadorDepartamentos);
		autoCompletarDepartamento.setHint("Escriba Depto");
		autoCompletarDepartamento
				.setOnItemClickListener(new OnItemClickListener() {

					@SuppressLint("ShowToast")
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// spinnerEscenarios.setSelection(0);
						// spinnerEscenarios.setAdapter(null);
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

						imm.hideSoftInputFromWindow(autoCompletarDepartamento.getWindowToken(), 0);
						Comunicador.setActividad(Tab_Busqueda.this);
						spinnerEscenarios.setSelection(0);
						controladorEscenario = new SeleccionDepartamentoController(
								arg0.getAdapter().getItem(arg2).toString()
										.trim());
						presionado = false;
						if (verificaConexion(getApplicationContext())) {

							controladorEscenario.obtenerDepartamento();
							if (btnAuto.getVisibility() == View.VISIBLE) {
								btnAuto.setImageResource(R.drawable.sele);
							}

						} else {
							mensajeDeAlerta(
									"Se necesita conexión a internet para esta acción",
									"Falla en Conexión");

						}

						/*
						 * /
						 * 
						 * if (controladorEscenario.obtenerDepartamento()) {
						 * iniciaControlMunicipio();
						 * 
						 * // autoCompletarDepartamento.setEnabled(false);
						 * 
						 * mensajeSeleccion(arg0.getAdapter().getItem(arg2)
						 * .toString()); } else {
						 * 
						 * if (verificaConexion(getApplicationContext())) {
						 * mensajeDeAlerta(
						 * "Los criterios de busqueda no arrojan resultados, verifique por favor"
						 * , "Error!!"); } else { mensajeDeAlerta(
						 * "Se necesita conexion a internet para esta accion",
						 * "Falla en Conexion"); }
						 * 
						 * }
						 * 
						 * /
						 */

					}
				});

	}

	public void btnMunicipioClick(View view) {
		if (spinnerMunicipios != null
				&& spinnerMunicipios.getSelectedItemPosition() != 0) {
			spinnerMunicipios.setSelection(0);
			btnMunicipio.setImageResource(R.drawable.nosele);
			btnEscenario.setImageResource(R.drawable.nosele);
		}

	}

	public void btnEscenarioClick(View view) {
		if (spinnerEscenarios != null
				&& spinnerEscenarios.getSelectedItemPosition() != 0) {
			spinnerEscenarios.setSelection(0);
			btnEscenario.setImageResource(R.drawable.nosele);
		}

	}

	public void btnDepartamentoClick(View view) {
		if (spinnerDepartamentos != null
				&& spinnerDepartamentos.getSelectedItemPosition() != 0) {
			spinnerDepartamentos.setSelection(0);
			btnDepartamento.setImageResource(R.drawable.nosele);
			btnMunicipio2.setImageResource(R.drawable.nosele);
			btnEvento.setImageResource(R.drawable.nosele);
		}

	}

	public void btnMunicipioClick2(View view) {
		if (spinnerMunicipios2 != null
				&& spinnerMunicipios2.getSelectedItemPosition() != 0) {
			spinnerMunicipios2.setSelection(0);
			btnMunicipio2.setImageResource(R.drawable.nosele);
			btnEvento.setImageResource(R.drawable.nosele);
		}

	}

	public void btnAutoClick(View view) {

		if (autoCompletarDepartamento != null) {
			presionado = true;
			autoCompletarDepartamento.setText("");
			btnAuto.setImageResource(R.drawable.nosele);
			btnMunicipio.setImageResource(R.drawable.nosele);
			btnEscenario.setImageResource(R.drawable.nosele);
			if (spinnerMunicipios != null) {
				spinnerMunicipios.setAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1,
						new String[] { "seleccione" }));
			}
			if (spinnerEscenarios != null) {
				spinnerEscenarios.setAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1,
						new String[] { "seleccione" }));
			}
			if (controladorEscenario != null) {
				controladorEscenario.borraSeleccion();
			}

		} else {
			btnAuto.setImageResource(R.drawable.nosele);
		}

	}

	public void btnAutoClick2(View view) {

		if (autoCompletarPaises != null) {
			/*
			 * presionado2 = true; if
			 * (spinnerMunicipios2 != null) { spinnerMunicipios2.setAdapter(new
			 * ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
			 * new String[] { "seleccione" })); } if (spinnerDepartamentos !=
			 * null) { spinnerDepartamentos.setAdapter(new
			 * ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
			 * new String[] { "seleccione" })); } if (spinnerEventos != null) {
			 * spinnerEventos.setAdapter(new ArrayAdapter<String>(this,
			 * android.R.layout.simple_list_item_1, new String[] { "seleccione"
			 * })); } autoCompletarPaises.setText("");
			 * btnDepartamento.setImageResource(R.drawable.nosele);
			 * btnAuto2.setImageResource(R.drawable.nosele);
			 * btnMunicipio2.setImageResource(R.drawable.nosele);
			 * btnEvento.setImageResource(R.drawable.nosele); if
			 * (controladorEvento != null) { controladorEvento.borraSeleccion();
			 * }
			 */
			if (controladorEvento != null) {
				controladorEvento.borraSeleccion();
			}
			restableceControlesLayout2();
			if (controladorEscenario != null) {
				controladorEscenario.borraSeleccion();
			}

		} else {
			btnAuto2.setImageResource(R.drawable.nosele);
		}

	}

	public void btnEventoClick(View view) {
		if (spinnerEventos != null
				&& spinnerEventos.getSelectedItemPosition() != 0) {
			spinnerEventos.setSelection(0);
		}
		btnEvento.setImageResource(R.drawable.nosele);
	}

	public void iniciaControlesLayout2() {

		btnDepartamento = (ImageView) findViewById(R.id.btnDepartamento);
		btnMunicipio2 = (ImageView) findViewById(R.id.btnMunicipio2);
		btnEvento = (ImageView) findViewById(R.id.btnEvento);
		btnAuto2 = (ImageView) findViewById(R.id.btnAuto2);
		spinnerDepartamentos = (Spinner) findViewById(R.id.spDepartamentos);
		spinnerMunicipios2 = (Spinner) findViewById(R.id.spMunicipios2);
		autoCompletarPaises = (AutoCompleteTextView) findViewById(R.id.autocomplete_paises);
		spinnerEventos = (Spinner) findViewById(R.id.spEventos);
		arregloPaises = getResources().getStringArray(R.array.paises_array);
		btnUbicar2 = (Button) findViewById(R.id.button2);

		btnUbicar2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (!popUpWindowsVisible) {
					muestraPop();
					popUpWindowsVisible = true;
				}

				return false;
			}
		});
		btnNuevaBusqueda2 = (Button) findViewById(R.id.btnNuevaBusqueda2);
		adaptadorPaises = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arregloPaises);

		adaptadorPaises
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		autoCompletarPaises.setAdapter(adaptadorPaises);
		autoCompletarPaises.setHint("Escriba País");
		autoCompletarPaises.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("DefaultLocale")
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

				imm.hideSoftInputFromWindow(autoCompletarPaises.getWindowToken(), 0);
				spinnerDepartamentos.setVisibility(View.INVISIBLE);
				btnDepartamento.setVisibility(View.INVISIBLE);
				// spinnerEventos.setAdapter(null);
				Comunicador.setActividad(Tab_Busqueda.this);
				controladorEvento = new SeleccionPaisController(arg0
						.getAdapter().getItem(arg2).toString().trim());
				presionado2 = false;
				if (verificaConexion(getApplicationContext())) {

					controladorEvento.obtenerPais();

				} else {
					mensajeDeAlerta(
							"Se necesita conexión a internet para esta acción",
							"Falla en Conexión");
					
				}

				/*
				 * / spinnerEventos.setAdapter(new ArrayAdapter<String>(
				 * Tab_Busqueda.this, android.R.layout.simple_list_item_1, new
				 * String[] { "Seleccione" })); /
				 */

				/*
				 * / if (controladorEvento.obtenerPais()) { if
				 * (arg0.getAdapter().getItem(arg2).toString().trim()
				 * .toLowerCase().equals("colombia")) {
				 * iniciaControlDepartamento2(); //
				 * autoCompletarPaises.setEnabled(false);
				 * spinnerMunicipios2.setSelection(0);
				 * 
				 * } else { iniciaControlMunicipio2(); //
				 * autoCompletarPaises.setEnabled(false); }
				 * 
				 * } else {
				 * 
				 * if (verificaConexion(getApplicationContext())) {
				 * mensajeDeAlerta(
				 * "Los criterios de busqueda no arrojan resultados, verifique por favor"
				 * , "Error!!!"); } else { mensajeDeAlerta(
				 * "Se necesita conexion a internet para esta accion",
				 * "Falla en Conexion"); }
				 * 
				 * } /
				 */

			}
		});

	}

	private void iniciaCbLayout1() {
		cbEscenario = (CheckBox) findViewById(R.id.cbEscenario);
		cbEvento = (CheckBox) findViewById(R.id.cbEvento);

		cbEscenario
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							hacerVisiblesControlesEscenario();
							cbEscenario.setEnabled(false);

						} else {

						}
					}
				});

		cbEvento.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					switcher.showNext();
					iniciaControlesLayout2();
					iniciaCbLayout2();
					iniciaTextViewLayout2();
					hacerVisiblesControlesEventos();
					cbEvento.setChecked(false);
					cbEvento.setEnabled(false);
					cbEvento2.setChecked(true);
					cbEvento2.setEnabled(false);
					restableceControlesLayout2();
					btnUbicar2.setVisibility(View.VISIBLE);

				} else {

				}
			}
		});
	}

	private void iniciaCbLayout2() {
		cbEscenario2 = (CheckBox) findViewById(R.id.cbEscenario2);
		cbEvento2 = (CheckBox) findViewById(R.id.cbEvento2);
		cbEscenario2
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							switcher.showPrevious();
							hacerVisiblesControlesEscenario();
							cbEscenario2.setChecked(false);
							cbEscenario.setChecked(true);
							cbEvento.setEnabled(true);
							cbEvento2.setEnabled(true);
							restableceControlesLayout1();

						} else {

						}
					}
				});

		cbEvento2
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {

						} else {

						}
					}
				});
	}

	private void iniciaTextViewLayout1() {
		txtDepartamento = (TextView) findViewById(R.id.txtDepartamento);
		txtMunicipio = (TextView) findViewById(R.id.txtMunicipio);
		txtEscenario = (TextView) findViewById(R.id.txtEscenario);
	}

	private void iniciaTextViewLayout2() {
		txtPais = (TextView) findViewById(R.id.txtPais);
		txtDepartamento2 = (TextView) findViewById(R.id.txtDepartamento2);
		txtMunicipio2 = (TextView) findViewById(R.id.txtMunicipio2);
		txtEventos = (TextView) findViewById(R.id.txtEvento);
	}

	public static void cambiaOpcionesSpinnerEventos() {
		if (presionado2 != true) {

			arregloEventos = controladorEvento.getAccesoEvento()
					.getListaEventos();
			adaptadorEventos = new ArrayAdapter<String>(
					Comunicador.getActividad(),
					android.R.layout.simple_list_item_1, arregloEventos);
			adaptadorEventos
					.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
			spinnerEventos.setAdapter(adaptadorEventos);
		}
	}

	public static void cambiaOpcionesSpinnerEscenarios() {
		arregloEscenarios = controladorEscenario.getAccesoEscenarios()
				.getListaNombreEscenarios();
		adaptadorEscenarios = new ArrayAdapter<String>(
				Comunicador.getActividad(),
				android.R.layout.simple_list_item_1, arregloEscenarios);

		adaptadorEscenarios
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spinnerEscenarios.setAdapter(adaptadorEscenarios);
	}

	public static void iniciaControlDepartamento2() {
		btnAuto2.setVisibility(View.VISIBLE);
		btnAuto2.setImageResource(R.drawable.sele);
		iniciaControlEventos();
		arregloDepartamentos2 = controladorEvento.getAccesoEvento()
				.getListaDepartamentos();
		adaptadorDepartamento2 = new ArrayAdapter<String>(
				Comunicador.getActividad(),
				android.R.layout.simple_list_item_1, arregloDepartamentos2);
		adaptadorDepartamento2
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spinnerDepartamentos.setAdapter(adaptadorDepartamento2);
		spinnerDepartamentos.setVisibility(View.VISIBLE);

		spinnerDepartamentos
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {
						spinnerEventos.setSelection(0);
						if (id != 0) {
							if (btnDepartamento.getVisibility() != View.VISIBLE) {
								btnDepartamento.setVisibility(View.VISIBLE);
							}
							btnDepartamento.setImageResource(R.drawable.sele);
							if (controladorEvento
									.obtenerMunicipiosColombia(spinnerDepartamentos
											.getAdapter().getItem(position)
											.toString())) {
								// spinnerEscenarios.setSelection(0);
								// spinnerEventos.setAdapter(null);

								AccesoEventos
										.cargarListaNombreEventosDepartamento(spinnerDepartamentos
												.getAdapter().getItem(position)
												.toString());

								iniciaControlMunicipio2();
								// spinnerDepartamentos.setEnabled(false);

							}
						} else if (nuleando != true && presionado2 != true) {
							AccesoEventos.cargarListaNombreEventosTodos();
							cambiaOpcionesSpinnerEventos();
							spinnerMunicipios2.setAdapter(new ArrayAdapter<String>(
									Comunicador.getActividad(),
									android.R.layout.simple_list_item_1,
									new String[] { "seleccione" }));
							controladorEvento.getBusquedaEvento()
									.setDepartamentoSeleccionado(null);

						}
						mensajeSeleccion(spinnerDepartamentos.getAdapter()
								.getItem(position).toString());

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	public static void iniciaControlMunicipio() {
		btnAuto.setVisibility(View.VISIBLE);
		btnAuto.setImageResource(R.drawable.sele);

		iniciaControlEscenario();
		arregloMunicipios = controladorEscenario.getAccesoEscenarios()
				.getListaMunicipios();
		adaptadorMunicipios = new ArrayAdapter<String>(
				Comunicador.getActividad(),
				android.R.layout.simple_list_item_1, arregloMunicipios);

		adaptadorMunicipios
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spinnerMunicipios.setAdapter(adaptadorMunicipios);
		spinnerMunicipios.setVisibility(View.VISIBLE);
		spinnerMunicipios
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {

						// spinnerEscenarios.setAdapter(new
						// ArrayAdapter<String>(
						// Comunicador.getActividad(),
						// android.R.layout.simple_list_item_1,
						// new String[] { "Seleccione" }));
						spinnerEscenarios.setSelection(0);
						if (id != 0) {

							if (btnMunicipio.getVisibility() != View.VISIBLE) {
								btnMunicipio.setVisibility(View.VISIBLE);

							}
							btnMunicipio.setImageResource(R.drawable.sele);
							if (controladorEscenario
									.obtenerMunicipio(spinnerMunicipios
											.getAdapter().getItem(position)
											.toString())) {

								//
								// spinnerMunicipios.setEnabled(false);
								iniciaControlEscenario();
							}
							mensajeSeleccion(spinnerMunicipios.getAdapter()
									.getItem(position).toString());
						} else if (nuleando != true && presionado != true) {

							AccesoEscenarios.cargaListaNombresEscenario();
							cambiaOpcionesSpinnerEscenarios();

							controladorEscenario.getBusquedaEscenario()
									.setMunicipioSeleccionado(null);

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	public static void cambiaSpinnerANulo() {
		nuleando = true;
		try {
			autoCompletarDepartamento.setText("");
			autoCompletarPaises.setText("");
		} catch (Exception ex) {

		}
		if (controladorEscenario != null
				&& controladorEscenario.getBusquedaEscenario() != null) {
			controladorEscenario.getBusquedaEscenario()
					.setDepartamentoSeleccionado(null);
			controladorEscenario.getBusquedaEscenario()
					.setMunicipioSeleccionado(null);
			controladorEscenario.getBusquedaEscenario()
					.setEscenarioSeleccionado(null);
		}
		if (controladorEvento != null
				&& controladorEvento.getBusquedaEvento() != null) {
			controladorEvento.getBusquedaEvento().setDepartamentoSeleccionado(
					null);
			controladorEvento.getBusquedaEvento().setPaisSeleccionado(null);
			controladorEvento.getBusquedaEvento()
					.setMunicipioSeleccionado(null);
			controladorEvento.getBusquedaEvento().setEventoSeleccionado(null);
		}
		if (spinnerDepartamentos != null) {
			spinnerDepartamentos.setAdapter(new ArrayAdapter<String>(
					Comunicador.getActividad(),
					android.R.layout.simple_list_item_1,
					new String[] { "seleccione" }));
		}
		if (spinnerEscenarios != null) {
			spinnerEscenarios.setAdapter(new ArrayAdapter<String>(Comunicador
					.getActividad(), android.R.layout.simple_list_item_1,
					new String[] { "seleccione" }));
		}
		if (spinnerEventos != null) {
			spinnerEventos.setAdapter(new ArrayAdapter<String>(Comunicador
					.getActividad(), android.R.layout.simple_list_item_1,
					new String[] { "seleccione" }));
		}
		if (spinnerMunicipios != null) {
			spinnerMunicipios.setAdapter(new ArrayAdapter<String>(Comunicador
					.getActividad(), android.R.layout.simple_list_item_1,
					new String[] { "seleccione" }));
		}
		if (spinnerMunicipios2 != null) {
			spinnerMunicipios2.setAdapter(new ArrayAdapter<String>(Comunicador
					.getActividad(), android.R.layout.simple_list_item_1,
					new String[] { "seleccione" }));
		}

	}

	public static void iniciaControlMunicipio2() {
		iniciaControlEventos();
		btnAuto2.setVisibility(View.VISIBLE);
		btnAuto2.setImageResource(R.drawable.sele);
		iniciaControlEventos();
		arregloMunicipios2 = controladorEvento.getAccesoEvento()
				.getListaMunicipios();
		adaptadorMunicipios2 = new ArrayAdapter<String>(
				Comunicador.getActividad(),
				android.R.layout.simple_list_item_1, arregloMunicipios2);

		adaptadorMunicipios2
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spinnerMunicipios2.setAdapter(adaptadorMunicipios2);
		spinnerMunicipios2.setVisibility(View.VISIBLE);
		spinnerMunicipios2
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {
						spinnerEventos.setSelection(0);

						if (id != 0) {

							if (controladorEvento
									.obtenerEventos(spinnerMunicipios2
											.getAdapter().getItem(position)
											.toString())) {
								iniciaControlEventos();
								if (btnMunicipio2.getVisibility() != View.VISIBLE) {
									btnMunicipio2.setVisibility(View.VISIBLE);
								}
								btnMunicipio2.setImageResource(R.drawable.sele);
								// spinnerMunicipios2.setEnabled(false);
							}

						} else if (nuleando != true) {
							if (spinnerDepartamentos.getAdapter() != null
									&& spinnerDepartamentos
											.getSelectedItemPosition() != 0) {
								AccesoEventos
										.cargarListaNombreEventosDepartamento(spinnerDepartamentos
												.getAdapter()
												.getItem(
														spinnerDepartamentos
																.getSelectedItemPosition())
												.toString());
								controladorEvento.getBusquedaEvento()
										.setMunicipioSeleccionado(null);
							} else {
								AccesoEventos.cargarListaNombreEventosTodos();
								controladorEvento.getBusquedaEvento()
										.setMunicipioSeleccionado(null);
							}
							if (presionado != true) {
								cambiaOpcionesSpinnerEventos();
							}
						}
						nuleando = false;

						mensajeSeleccion(spinnerMunicipios2.getAdapter()
								.getItem(position).toString());

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	private static void iniciaControlEscenario() {
		arregloEscenarios = controladorEscenario.getAccesoEscenarios()
				.getListaNombreEscenarios();
		adaptadorEscenarios = new ArrayAdapter<String>(
				Comunicador.getActividad(),
				android.R.layout.simple_list_item_1, arregloEscenarios);

		adaptadorEscenarios
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spinnerEscenarios.setAdapter(adaptadorEscenarios);
		spinnerEscenarios.setVisibility(View.VISIBLE);
		spinnerEscenarios
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {

						if (id != 0) {

							if (btnEscenario.getVisibility() != View.VISIBLE) {
								btnEscenario.setVisibility(View.VISIBLE);
							}
							btnEscenario.setImageResource(R.drawable.sele);
							controladorEscenario
									.determinaEscenario(spinnerEscenarios
											.getAdapter().getItem(position)
											.toString());
							// spinnerEscenarios.setEnabled(false);
							mensajeSeleccion(spinnerEscenarios.getAdapter()
									.getItem(position).toString());
						} else {
							controladorEscenario.getBusquedaEscenario()
									.setEscenarioSeleccionado(null);
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	private static void iniciaControlEventos() {
		arregloEventos = controladorEvento.getAccesoEvento().getListaEventos();
		adaptadorEventos = new ArrayAdapter<String>(Comunicador.getActividad(),
				android.R.layout.simple_list_item_1, arregloEventos);
		adaptadorEventos
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		spinnerEventos.setAdapter(adaptadorEventos);
		spinnerEventos.setVisibility(View.VISIBLE);
		spinnerEventos
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							android.view.View v, int position, long id) {

						if (id != 0) {
							if (btnEvento.getVisibility() != View.VISIBLE) {
								btnEvento.setVisibility(View.VISIBLE);
							}
							btnEvento.setImageResource(R.drawable.sele);
							controladorEvento
									.determinaEventoSeccionado(spinnerEventos
											.getAdapter().getItem(position)
											.toString());
							// spinnerEventos.setEnabled(false);
							mensajeSeleccion(spinnerEventos.getAdapter()
									.getItem(position).toString());
						} else {
							controladorEvento.getBusquedaEvento()
									.setEventoSeleccionado(null);
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

	}

	public static void mensajeSeleccion(String s) {
		Toast.makeText(Comunicador.getActividad(), "Seleccion: " + s,
				Toast.LENGTH_LONG).show();
	}

	public void hacerVisiblesControlesEscenario() {
		txtDepartamento.setVisibility(View.VISIBLE);
		txtMunicipio.setVisibility(View.VISIBLE);
		txtEscenario.setVisibility(View.VISIBLE);
		autoCompletarDepartamento.setVisibility(View.VISIBLE);
		btnUbicar.setVisibility(View.VISIBLE);
		btnNuevaBusqueda.setVisibility(View.VISIBLE);

	}

	public void hacerVisiblesControlesEventos() {
		txtPais.setVisibility(View.VISIBLE);
		txtDepartamento2.setVisibility(View.VISIBLE);
		txtMunicipio2.setVisibility(View.VISIBLE);
		txtEventos.setVisibility(View.VISIBLE);
		btnNuevaBusqueda2.setVisibility(View.VISIBLE);
		autoCompletarPaises.setVisibility(View.VISIBLE);

	}

	public static void muestraPop() {

		LayoutInflater layoutInflater = (LayoutInflater) contexto
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.popup, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		/*
		 * / Button btnDismiss = (Button) popupView.findViewById(R.id.btndis);
		 * btnDismiss.setOnClickListener(new Button.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub while(popupWindow.isShowing()){ popupWindow.dismiss();
		 * yanomas=false; }; } }); /
		 */
		popupWindow.showAsDropDown(vv, 50, -200);

	}

	public void ocultaPop() {
		if (popUpWindowsVisible == true) {
			popupWindow.dismiss();
			popUpWindowsVisible = false;
		}
	}

	public void muestraAcercaDe(View view) {
		AcercaDe acercaDe = new AcercaDe(this);
		acercaDe.show();
	}

	public void btnMostrarClick(View view) {
		Comunicador.setActividad(this);

		if (!verificaDepartamentoEscrito(autoCompletarDepartamento.getText()
				.toString())) {
			if (popUpWindowsVisible == true) {
				ocultaPop();

			}
			mensajeDeAlerta(
					"Debe Seleccionar al menos un Departamento valido, para mostrar información",
					"Departamento no Seleccionado");
			return;
		}
		if (verificaConexion(getApplicationContext())) {
			MostrarController controladorMostrar = new MostrarController();
			Comunicador.setBusco(true);
			if (controladorEscenario == null) {
				controladorEscenario = new SeleccionDepartamentoController();
			}
			controladorMostrar.ubicarEscenario();
			Tab_Principal.tabHost.setCurrentTab(0);
			ocultaPop();

		} else {
			if (popUpWindowsVisible == true) {
				ocultaPop();
			}
			mensajeDeAlerta("Se necesita conexión a internet para esta acción",
					"Falla en Conexión");
		}

	}

	@SuppressLint("DefaultLocale")
	public boolean vericaPaisEscrito(String ju) {

		for (String s : arregloPaises) {
			if (s.toLowerCase().equals(ju.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public boolean verificaDepartamentoEscrito(String ju) {
		if (ju.length() == 0) {
			return true;
		}
		for (String s : arregloDepartamentos) {
			if (s.toLowerCase().equals(ju.toLowerCase())) {
				return true;
			}

		}
		return false;
	}

	@SuppressLint("DefaultLocale")
	public void btnMotrar2Click(View view) {
		Comunicador.setActividad(Tab_Busqueda.this);
		if (!vericaPaisEscrito(autoCompletarPaises.getText().toString())) {

			if (popUpWindowsVisible == true) {
				ocultaPop();
			}
			mensajeDeAlerta(
					"Debe Seleccionar al menos un país valido, para mostrar información",
					"País no Seleccionado");
			cambiaSpinnerANulo();
			return;
		}
		if (verificaConexion(getApplicationContext())) {
			MostrarController controladorMostrar = new MostrarController();
			Comunicador.setBusco(true);
			if (controladorEvento != null
					&& controladorEvento.getBusquedaEvento() != null
					&& controladorEvento.getBusquedaEvento()
							.getPaisSeleccionado() != null) {
				if (controladorEvento != null) {
					List<Evento> listaEventoAux = controladorMostrar
							.ListaEventoInternacional();
					if (listaEventoAux.size() == 1) {
						mostrarEventoUnico(listaEventoAux);

					} else {

						mostrarEvento(listaEventoAux);
					}
					if (popUpWindowsVisible == true) {
						ocultaPop();
					}
				} else {

					if (popUpWindowsVisible == true) {
						ocultaPop();
					}

				}
			} else {
				mensajeDeAlerta(
						"Debe Seleccionar al menos un país valido, para mostrar información",
						"País no Seleccionado");
				if (popUpWindowsVisible == true) {
					ocultaPop();
				}
			}
		} else {
			if (popUpWindowsVisible == true) {
				ocultaPop();
			}
			mensajeDeAlerta("Se necesita conexion a internet para esta acción",
					"Falla en Conexion");
		}

		/*
		 * / if (controladorEvento != null) { if
		 * (controladorEvento.getBusquedaEvento().getPaisSeleccionado() != null)
		 * { if (controladorEvento.getBusquedaEvento()
		 * .getPaisSeleccionado().toLowerCase() .equals("colombia")) {
		 * 
		 * Tab_Principal.tabHost.setCurrentTab(0); if (popUpWindowsVisible ==
		 * true) { ocultaPop(); }
		 * 
		 * } else {
		 * 
		 * mostrarEvento(controladorMostrar .ListaEventoInternacional()); if
		 * (popUpWindowsVisible == true) { ocultaPop(); }
		 * 
		 * } } else {
		 * 
		 * if (popUpWindowsVisible == true) { ocultaPop(); } mensajeDeAlerta(
		 * "Debe Seleccionar al menos un país valido, para mostrar informacion",
		 * "País no Seleccionado"); } } else { if (popUpWindowsVisible == true)
		 * { ocultaPop(); } mensajeDeAlerta(
		 * "Debe Seleccionar al menos un país para mostrar informacion",
		 * "País no Seleccionado"); } /
		 */
	}

	public void btnNuevaBusquedaClick(View view) {

		restableceControlesLayout1();
		if (controladorEscenario != null) {
			controladorEscenario.borraSeleccion();
		}

	}

	public void btnNuevaBusqueda2Click(View view) {

		restableceControlesLayout2();
		if (controladorEvento != null) {
			controladorEvento.borraSeleccion();
		}

	}

	private void restableceControlesLayout1() {

		autoCompletarDepartamento.clearComposingText();
		autoCompletarDepartamento.setText("");
		autoCompletarDepartamento.setEnabled(true);
		spinnerMunicipios.setVisibility(View.INVISIBLE);
		btnMunicipio.setVisibility(View.INVISIBLE);
		btnEscenario.setVisibility(View.INVISIBLE);
		if (!spinnerMunicipios.isEnabled()) {
			spinnerMunicipios.setEnabled(true);
		}
		spinnerEscenarios.setVisibility(View.INVISIBLE);
		if (!spinnerEscenarios.isEnabled()) {
			spinnerEscenarios.setEnabled(true);
		}
		adaptadorMunicipios = null;
		adaptadorEscenarios = null;
		if (controladorEscenario != null) {
			controladorEscenario.borraSeleccion();
		}

	}

	private void restableceControlesLayout2() {
		autoCompletarPaises.setText("");
		autoCompletarPaises.setEnabled(true);
		spinnerDepartamentos.setVisibility(View.INVISIBLE);
		btnDepartamento.setVisibility(View.INVISIBLE);
		btnEvento.setVisibility(View.INVISIBLE);
		btnMunicipio2.setVisibility(View.INVISIBLE);
		if (!spinnerDepartamentos.isEnabled()) {
			spinnerDepartamentos.setEnabled(true);
		}
		spinnerMunicipios2.setVisibility(View.INVISIBLE);
		if (!spinnerMunicipios2.isEnabled()) {
			spinnerMunicipios2.setEnabled(true);
		}
		spinnerEventos.setVisibility(View.INVISIBLE);
		if (!spinnerEventos.isEnabled()) {
			spinnerEventos.setEnabled(true);
		}
		if (controladorEscenario != null) {
			controladorEscenario.borraSeleccion();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.tab__busqueda, menu);
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

	public void iniciaActividad() {
		iniciaControlesLayout1();
		iniciaCbLayout1();
		iniciaTextViewLayout1();
		switcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);
	}

	public static void mensajeDeAlerta(String mensaje, String titulo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Comunicador.getActividad());
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

	public void mostrarEventoUnico(final List<Evento> lev) {

		if (!lev.get(0).getEscenario().equals("")) {
			final MostrarController controladorMostrar = new MostrarController();
			controladorMostrar.ubicaEscenarioEvento(lev);
			Tab_Principal.cambiaTabAMApa();
			return;
		}
		final Evento e = lev.get(0);
		AlertDialog.Builder alertadd = new AlertDialog.Builder(
				Tab_Busqueda.this);
		LayoutInflater factory = LayoutInflater.from(Tab_Busqueda.this);
		final View view = factory.inflate(R.layout.sample, null);

		ImageView imd = (ImageView) view.findViewById(R.id.imsample);
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
		txtNombreAlerta.setText("Evento: " + e.getNombre());
		txtTipoAlerta.setText("Entidad: " + e.getEntidad() + "\n" + "Tipo: "
				+ e.getTipo());
		txtTipoAlerta.setGravity(Gravity.CENTER_HORIZONTAL);
		txtDeporteFechasAlerta.setText("Fecha Inicio/Fin: " + e.getFechaD()
				+ "/" + e.getFechaH());
		txtDeptoMuniPais.setText("Localización: " + e.getPais() + "/"
				+ e.getLugar());
		txtDireccionAlerta.setText("Escenario: Sin Información");
		txtDescripcionAlerta.setText("Descripción: " + e.getDescripEvento());
		txtTelefonoWebAlerta.setText("Pagina Web: " + e.getPaginaWeb());
		txtEmailAlerta.setVisibility(View.VISIBLE);
		txtEmailAlerta.setText("Email: " + e.getEmail());
		alertadd.setIcon(R.drawable.icono);
		alertadd.setTitle("Información");
		alertadd.setNeutralButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {

					}
				});

		alertadd.setPositiveButton("Compartir",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						ServiciosExternosFacade redSocial = new ServiciosExternosFacade();

						redSocial.compartirRedSocial(
								Tab_Busqueda.this,
								"Voy a ir a:" + e.getNombre()
										+ ", organizado por: " + e.getEntidad()
										+ ", el día: " + e.getFechaD()
										+ ", en: " + e.getLugar() + "-"
										+ e.getPais());

					}
				});

		alertadd.show();
	}

	public void mostrarEvento(final List<Evento> lev) {
		final MostrarController controladorMostrar = new MostrarController();
		if (lev != null) {
			AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
			builderSingle.setIcon(R.drawable.icono);
			builderSingle.setTitle("Seleccione un Evento");
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					this, android.R.layout.select_dialog_singlechoice);
			arrayAdapter.clear();
			for (Evento e : lev) {
				if (!e.getEscenario().equals("")) {
					arrayAdapter.add(e.getNombre() + "* * " + e.getEntidad()
							+ "* * Ver Mapa");
				} else {
					arrayAdapter.add(e.getNombre() + "* * " + e.getEntidad());
				}
			}
			builderSingle.setPositiveButton(R.string.btnaceptar,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});

			builderSingle.setAdapter(arrayAdapter,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Evento ev = null;
							String strName = arrayAdapter.getItem(which);
							strName = cambiaStrn(strName);
							for (Evento e : lev) {
								if (strName.equals(e.getNombre())) {
									ev = e;
								}
							}
							AlertDialog.Builder builderInner = new AlertDialog.Builder(
									Tab_Busqueda.this);
							builderInner.setIcon(R.drawable.icono);
							builderInner.setTitle("Información");

							if (ev != null && ev.getEscenario().equals("")) {
								LayoutInflater factory = LayoutInflater
										.from(Tab_Busqueda.this);
								final View view = factory.inflate(
										R.layout.sample, null);

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
								builderInner.setView(view);
								txtNombreAlerta.setText("Evento: "
										+ ev.getNombre());
								txtTipoAlerta.setText("Entidad: "
										+ ev.getEntidad() + "\n" + "Tipo: "
										+ ev.getTipo());
								txtTipoAlerta
										.setGravity(Gravity.CENTER_HORIZONTAL);
								txtDeporteFechasAlerta
										.setText("Fecha Inicio/Fin: "
												+ ev.getFechaD() + "/"
												+ ev.getFechaH());
								txtDeptoMuniPais.setText("Localización: "
										+ ev.getPais() + "/" + ev.getLugar());
								txtDireccionAlerta
										.setText("Escenario: Sin Información");
								txtDescripcionAlerta.setText("Descripción: "
										+ ev.getDescripEvento());
								txtTelefonoWebAlerta.setText("Pagina Web: "
										+ ev.getPaginaWeb());
								txtEmailAlerta.setVisibility(View.VISIBLE);
								txtEmailAlerta.setText("Email: "
										+ ev.getEmail());

								/*
								 * /
								 * builderInner.setMessage(ev.getDescripEvento()
								 * + "\n" + "Pais: " + ev.getPais() + "\n" +
								 * "ciudad: " + ev.getLugar() + "\n" +
								 * "Entidad: " + ev.getEntidad() + "\n" +
								 * "Fecha Inicio: " + ev.getFechaD() + "\n" +
								 * "Fecha Fin: " + ev.getFechaH());
								 * builderInner.setTitle("Descripcion Evento: "
								 * + ev.getNombre()); /
								 */
							} else if (!ev.getEscenario().equals("")) {
								List<Evento> lEventosAux = new ArrayList<Evento>();
								lEventosAux.add(ev);
								controladorMostrar
										.ubicaEscenarioEvento(lEventosAux);
								Tab_Principal.cambiaTabAMApa();
								return;
							} else {
								builderInner
										.setMessage("Lo sentimos hubo un error");
								builderInner.setTitle("Error!!");
							}
							final Evento ev1 = ev;
							builderInner.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									});

							builderInner.setNeutralButton("Compartir",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dlg, int sumthin) {
											ServiciosExternosFacade redSocial = new ServiciosExternosFacade();

											redSocial
													.compartirRedSocial(
															Tab_Busqueda.this,
															"Voy a ir a:"
																	+ ev1.getNombre()

																	+ ", organizado por: "
																	+ ev1.getEntidad()
																	+ ", el día: "
																	+ ev1.getFechaD()
																	+ ", en: "
																	+ ev1.getLugar()
																	+ "-"
																	+ ev1.getPais());

										}
									});
							AlertDialog dialogo = builderInner.show();
							// TextView messageText = (TextView) dialogo
							// .findViewById(android.R.id.message);
							// messageText.setGravity(Gravity.CENTER);
							dialogo.show();

						}
					});
			builderSingle.show();
		}
	}

	public String cambiaStrn(String s) {
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			if (Character.toString(s.charAt(i)).equals("*")) {
				s1 = s.substring(0, i);

				return s1;
			}
		}
		return s1;
	}

}
