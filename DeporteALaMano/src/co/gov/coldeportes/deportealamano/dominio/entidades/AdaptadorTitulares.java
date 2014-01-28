package co.gov.coldeportes.deportealamano.dominio.entidades;

import co.gov.coldeportes.deportealamano.presentacion.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorTitulares extends ArrayAdapter {

	Activity context;
	private TitularesNovedades[] datos;

	@SuppressWarnings("unchecked")
	public AdaptadorTitulares(Activity context, TitularesNovedades[] datos1) {
		super(context, R.layout.lista_titulares, datos1);
		this.context = context;
		datos = datos1;
	}

	/**
	 * Este metodo es el encargado de gestionar el view para la lista de
	 * novedades que se muestra el usuario. mira si ya esta creada en memoria
	 * para de ser asi no volverlo a crear
	 * 
	 * @return view
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		View item = convertView;
		ViewHolder holder;

		if (item == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.lista_titulares, null);

			holder = new ViewHolder();
			holder.nombre = (TextView) item.findViewById(R.id.lblNombre);
			holder.ciudadPais = (TextView) item
					.findViewById(R.id.lblCiudadPais);
			holder.Fecha = (TextView) item.findViewById(R.id.lblFecha);

			item.setTag(holder);
		} else {
			holder = (ViewHolder) item.getTag();
		}

		holder.nombre.setText("Evento; " + datos[position].getNombre());
		holder.ciudadPais.setText("Ciudad: " + datos[position].getCiudad()
				+ "-" + datos[position].getPais());
		holder.Fecha.setText("Fecha: " + datos[position].getFecha());

		return (item);
	}

	static class ViewHolder {
		TextView nombre;
		TextView ciudadPais;
		TextView Fecha;
	}
}
