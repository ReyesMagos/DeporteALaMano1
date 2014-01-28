package co.gov.coldeportes.deportealamano.dominio.entidades;

import android.graphics.Bitmap;

public class Escenario {
	
	private String nombre;
	private String deporte;
	private String direccion;
	private String telefono;
	private String municipio;
	private String departamento;
	private String paginaWeb;
	private String email;
	private String codigo;
	private String ubicacion;
	private String razonSocial;
	private String claseUbicacion;
	private String localidadComuna;
	private String descripcion;
	private String latitud;
	private String longitud;
	private String imagenUrl;
	private String tipo;
	
	public String getImagen() {
		return imagenUrl;
	}
	
	
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public void setImagen(String imagen) {
		this.imagenUrl = imagen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDeporte() {
		return deporte;
	}
	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getPaginaWeb() {
		return paginaWeb;
	}
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getClaseUbicacion() {
		return claseUbicacion;
	}
	public void setClaseUbicacion(String claseUbicacion) {
		this.claseUbicacion = claseUbicacion;
	}
	public String getLocalidadComuna() {
		return localidadComuna;
	}
	public void setLocalidadComuna(String localidadColumna) {
		this.localidadComuna = localidadColumna;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}


}
