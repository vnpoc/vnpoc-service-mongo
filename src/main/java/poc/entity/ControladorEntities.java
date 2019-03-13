package poc.entity;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//===========================================================================
//CLASE ControladorEntities
//===========================================================================
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.PUT, RequestMethod.GET})
@RequestMapping(value = "/entities", produces = "application/json") 
public class ControladorEntities {

	// ----------------------------------------------------------------------
	// get (GET /entities)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "")
	public HttpEntity<Collection<ResourceSupport>> getEntities() {
		Collection<Entity> entidades;
		Collection<ResourceSupport> resul;
		
		entidades = bdEntities.findAll();
		TRAZA.info("getEntities: " + entidades.size());
		resul = entidades.stream().map(this::toRecurso).collect(toList()); 
		// resul.add(linkTo(methodOn(ControladorEntities.class).getEntities()).withSelfRel());
		// resul.add(linkTo(methodOn(ControladorEntities.class).getOrigins()).withRel("origins"));
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// get (GET /entities/{id})
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public HttpEntity<ResourceSupport> get(@PathVariable final String id) {
		List<Entity> entidades = bdEntities.findByIdentifier(id);
		ResourceSupport resul;
		HttpEntity<ResourceSupport> resp;
		
		if (entidades.size() > 0) {
			resul = toRecurso(entidades.get(0));
			resp = new ResponseEntity<>(resul, HttpStatus.OK);
		} else {
			resul = toRecurso(new Entity("NO ENCONTRADO", "", "", ""));
			resp = new ResponseEntity<>(resul, HttpStatus.NOT_FOUND);
		}
		return resp;
    }
	
	// ----------------------------------------------------------------------
	// pon (POST /entities)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.POST, value="")
	public HttpEntity<ResourceSupport> pon(@RequestBody Entity entidad) {
		ResourceSupport resul = null;
		
		TRAZA.info("pon - entidad nueva: " + entidad);
		bdEntities.save(entidad);
		resul = toRecurso(entidad);
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}
	
	// ----------------------------------------------------------------------
	// modifica (PUT /entities/{id})
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	public HttpEntity<ResourceSupport> modifica(@PathVariable String id,
			@RequestBody Entity entidad) {
		List<Entity> entidades = bdEntities.findByIdentifier(id);
		Entity ent;
		ResourceSupport resul = null;
		
		TRAZA.info("modifica - entidad nueva: " + entidad);
		if (entidades.size() > 0) {
			ent = entidades.get(0);
			ent.modifica(entidad);
			TRAZA.info("modifica - entidad modificada: " + ent);
			resul = toRecurso(ent);
			bdEntities.save(ent);
		}
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}
	
	// ----------------------------------------------------------------------
	// toRecurso
	// ----------------------------------------------------------------------
    private ResourceSupport toRecurso(Entity entity) {
    	ResourceSupport resul = new Resource<>(entity);
    	resul = new Resource<>(entity);
    	/*
		resul.add(linkTo(methodOn(ControladorEntities.class).get(entity.getId())).withSelfRel());
		resul.add(linkTo(methodOn(ControladorEntities.class).getEntities(entity.getIdentifier())).withRel("identifier"));
		resul.add(linkTo(methodOn(ControladorEntities.class).pon(entity)).withRel("pon"));
		resul.add(linkTo(methodOn(ControladorEntities.class).
				modifica(entity.getId(), entity)).withRel("modifica"));
		resul.add(linkTo(methodOn(ControladorAssets.class).getAssets(entity.getId())).withRel("assets"));
		*/
    	return resul;
    }
 
    //===========================================================================
    // ATRIBUTOS
    //===========================================================================
	private static Logger TRAZA = LoggerFactory.getLogger(ControladorEntities.class);

	@Autowired
	private EntityRepository bdEntities;
}
