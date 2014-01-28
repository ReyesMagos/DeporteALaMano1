package co.gov.coldeportes.deportealamano.servicios.accesoWeb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.gov.coldeportes.deportealamano.dominio.entidades.Escenario;
import co.gov.coldeportes.deportealamano.dominio.entidades.Evento;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Comunicador;
import co.gov.coldeportes.deportealamano.presentacion.actividades.tabs.Tab_Busqueda;
import co.gov.coldeportes.deportealamano.servicios.accesoImagenes.ServicioImagenes;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEscenarios.AccesoEscenarios;
import co.gov.coldeportes.deportealamano.servicios.accesoWeb.accesoEventos.AccesoEventos;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ServicioRest extends AsyncTask<String, Integer, Boolean> {

	private String respStr;
	private String añadidoUrl;
	private String[] datosEscenarios, datosEventos;
	private int caso;
	private JSONArray nombresConsulta, arregloJson;
	private boolean funciono;
	private Bitmap im;
	private List<Escenario> listaEscenario;
	private List<Evento> listaEvento;
	private Escenario escenario;
	private Evento evento;
	private String funcion;
	private ProgressDialog progressDialog;

	public ServicioRest(String añadidourl1, int caso1, String funcionAux) {
		this.añadidoUrl = añadidourl1;
		this.caso = caso1;
		this.funcion = funcionAux;

		progressDialog = new ProgressDialog(Comunicador.getActividad());
		progressDialog.setMessage("Buscando; Por Favor Espere");
		progressDialog.setCancelable(false);

	}

	public List<Escenario> getListaEscenario() {
		return listaEscenario;
	}

	public void setListaEscenario(List<Escenario> listaEscenario) {
		this.listaEscenario = listaEscenario;
	}

	public List<Evento> getListaEvento() {
		return listaEvento;
	}

	public void setListaEvento(List<Evento> listaEvento) {
		this.listaEvento = listaEvento;
	}

	public boolean isFunciono() {
		return funciono;
	}

	public void setFunciono(boolean funciono) {
		this.funciono = funciono;
	}

	public String getRespStr() {
		return respStr;
	}

	public void setRespStr(String respStr) {
		this.respStr = respStr;
	}

	public void llenaEventos() {
		JSONObject obj;
		listaEvento = new ArrayList<Evento>();

		datosEventos = new String[arregloJson.length()];
		try {
			for (int i = 0; i < arregloJson.length(); i++) {
				if (nombresConsulta.length() > 0) {

					obj = arregloJson.getJSONObject(i);
					evento = new Evento();
					evento.setPais(obj.getString("pais"));
					evento.setDescripEvento(obj
							.getString("descripciondelevento"));
					evento.setFechaH(obj.getString("fechahasta"));
					evento.setTerritorio(obj.getString("territorio"));
					evento.setNombre(obj.getString("nombre"));
					evento.setLugar(obj.getString("lugar"));
					evento.setFechaD(obj.getString("fechadesde"));
					evento.setEmail(obj.getString("email"));
					evento.setTipo(obj.getString("tipo"));
					evento.setEntidad(obj.getString("entidad"));
					evento.setPaginaWeb(obj.getString("paginaweb"));
					evento.setEscenario(obj.getString("escenario"));
					listaEvento.add(evento);
				}

			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void llenaEscenario() throws InterruptedException,
			ExecutionException {

		JSONObject obj;
		listaEscenario = new ArrayList<Escenario>();
		ServicioImagenes s;
		try {

			datosEscenarios = new String[arregloJson.length()];
			for (int i = 0; i < arregloJson.length(); i++) {

				obj = arregloJson.getJSONObject(i);
				if (nombresConsulta.length() > 0) {

					escenario = new Escenario();
					escenario
							.setClaseUbicacion(obj.getString("claseubicacion"));
					escenario.setRazonSocial(obj.getString("razonsocial"));
					escenario.setDireccion(obj.getString("direccion"));
					escenario.setDescripcion(obj.getString("descripcion"));
					escenario.setTelefono(obj.getString("telefono"));
					escenario.setLocalidadComuna(obj
							.getString("localidadcomuna"));
					escenario.setTipo(obj.getString("tipo"));
					escenario.setNombre(obj.getString("nombre"));
					escenario.setMunicipio(obj.getString("municipio"));
					escenario.setEmail(obj.getString("email"));
					escenario.setDepartamento(obj.getString("departamento"));
					escenario.setDeporte(obj.getString("deporte"));
					escenario.setLatitud(obj.getString("latitud"));
					escenario.setPaginaWeb(obj.getString("paginaweb"));
					escenario.setLongitud(obj.getString("longitud"));
					escenario.setUbicacion(obj.getString("ubicacion"));
					escenario.setImagen(obj.getString("imagenurl"));
					escenario.setCodigo(obj.getString("codigo"));
					listaEscenario.add(escenario);

				}

			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	protected Boolean doInBackground(String... params) {

		boolean resul = true;

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://servicedatosabiertoscolombia.cloudapp.net/v1/coldeportes/"
				+ añadidoUrl;

		HttpGet del = new HttpGet(url);
		
		Log.i("kkdas", url);

		try {
			HttpResponse resp = httpClient.execute(del);
			HttpEntity entity = resp.getEntity();
			
			respStr = EntityUtils.toString(entity);

			JSONObject respJSON = new JSONObject(respStr);
			arregloJson = respJSON.getJSONArray("d");
			nombresConsulta = arregloJson.getJSONObject(0).names();
			if (caso == 1) {
				llenaEscenario();
				resul = true;
			} else if (caso == 3) {
				InputStream is = entity.getContent();
				im = BitmapFactory.decodeStream(is);
			} else {
				llenaEventos();
			}

		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("ServicioRest", "Error!", e);
			resul = false;
		}
		return resul;
	}

	@Override
	protected void onPreExecute() {
		
			progressDialog.show();
		

	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			funciono = true;
			if (this.funcion.equals("departamento")) {
				AccesoEscenarios.cargaListaMunicipios();
				AccesoEscenarios.cargaListaNombresEscenario();
				Tab_Busqueda.iniciaControlMunicipio();

			} else if (this.funcion.equals("pais")) {
				if (Comunicador.getPaisSeleccionado().equals("colombia")) {

					AccesoEventos.cargarListaDepartamentos();
					AccesoEventos.cargarListaNombreEventosTodos();
					Tab_Busqueda.iniciaControlDepartamento2();

				} else {
					AccesoEventos.cargarListaMunicipiosNoColombia();
					AccesoEventos.cargarListaNombreEventosTodos();
					Tab_Busqueda.iniciaControlMunicipio2();
				}

			}
			progressDialog.dismiss();

		} else {
			if (funcion.equals("departamento") || funcion.equals("pais")) {
				Tab_Busqueda.mensajeDeAlerta(
						"Los criterios de búsqueda no arrojaron resultados",
						"Error!!");
				Tab_Busqueda.cambiaSpinnerANulo();
			}
			
				progressDialog.dismiss();
			
		}

	}

}
