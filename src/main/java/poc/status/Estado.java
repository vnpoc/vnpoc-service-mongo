package poc.status;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

public class Estado extends ResourceSupport {
	public Estado() {
	}
	
	public long dameCuantos(String valor) {
		long resul = 0;
		if (valores.containsKey(valor))
			resul = valores.get(valor).getValor();
		return resul;
	}

	public void pon(Propiedad.Tipo tipo, String valor) {
		Propiedad p;
		if (valores.containsKey(valor)) {
			p = valores.get(valor);
			p.setValor(p.getValor() + 1);
		}
		else {
			p = new Propiedad(tipo, valor, 1);
			valores.put(valor, p);
		}
	}
	
	public String toString() {
		StringBuffer resul = new StringBuffer("[");
		
		for (Propiedad p: valores.values())
			resul.append("{tipo: " + p.getTipo() + ", nombre: " + p.getNombre() + ", valor: " + p.getValor() + "},");
		resul.append("]");
		return resul.toString();
	}
	
	public Collection<Propiedad> propiedades() {
		return valores.values();
	}
	

	//=======================================================================
	// ATRIBUTOS
	//=======================================================================
	private Map<String, Propiedad> valores = new HashMap<String, Propiedad>();
}
