package co.gov.coldeportes.deportealamano.dominio.entidades;

public class TitularesNovedades {

	private String Nombre;
	private String Ciudad;
	private String Pais;
	private String Fecha;

	public TitularesNovedades(String nombre, String ciudad, String pais,
			String fecha) {
		this.Nombre = nombre;
		this.Ciudad = ciudad;
		this.Pais = pais;
		this.Fecha = fecha;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getCiudad() {
		return Ciudad;
	}

	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}

	public String getPais() {
		return Pais;
	}

	public void setPais(String pais) {
		Pais = pais;
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

}
