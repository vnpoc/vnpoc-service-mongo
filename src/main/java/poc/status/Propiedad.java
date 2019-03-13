package poc.status;

public class Propiedad {
	public enum Tipo {ENTITY, ASSET};

	public Propiedad() {
	}
	
	public Propiedad(Tipo tipo, String nombre, long valor) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.valor = valor;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getValor() {
		return valor;
	}

	public void setValor(long valor) {
		this.valor = valor;
	}


	private Tipo tipo;
	private String nombre;
	private long valor;
}
