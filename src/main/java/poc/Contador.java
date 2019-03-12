package poc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.hateoas.ResourceSupport;

public class Contador extends ResourceSupport {
	public Contador() {
	}
	
	public long dameCuantos(String valor) {
		long resul = 0;
		if (valores.containsKey(valor))
			resul = valores.get(valor);
		return resul;
	}

	public void pon(String valor) {
		valores.put(valor, dameCuantos(valor) + 1);
	}
	
	public String toString() {
		StringBuffer resul = new StringBuffer("[");
		
		for (Entry<String, Long> valor: valores.entrySet())
			resul.append("{valor: " + valor.getKey() + ", n: " + valor.getValue() + "},");
		resul.append("]");
		return resul.toString();
	}
	
	public Set<Entry<String, Long>> entrySet() {
		return valores.entrySet();
	}
	

	//=======================================================================
	// ATRIBUTOS
	//=======================================================================
	private Map<String, Long> valores = new HashMap<String, Long>();
}
