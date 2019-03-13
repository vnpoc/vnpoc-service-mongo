package poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//===========================================================================
// CLASE Controlador
//===========================================================================
@CrossOrigin(origins = "*")
@RestController
public class Controlador {
	// ----------------------------------------------------------------------
	// get (GET /)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public HttpEntity<ResourceSupport> get() {
		ResourceSupport resul = new ResourceSupport();
		TRAZA.info("get: GET /");
		// resul.add(linkTo(methodOn(Controlador.class).get()).withSelfRel());
		// resul.add(linkTo(methodOn(ControladorEntities.class).getEntities("identifier")).withSelfRel());
		// resul.add(linkTo(methodOn(ControladorAssets.class).getAssets("parent")).withSelfRel());
        return new ResponseEntity<>(resul, HttpStatus.OK);
	}
	
    //===========================================================================
    // ATRIBUTOS
    //===========================================================================
	private static Logger TRAZA = LoggerFactory.getLogger(Controlador.class);
	
}
