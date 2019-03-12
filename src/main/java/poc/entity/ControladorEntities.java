package poc.entity;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Map.Entry;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poc.Contador;
import poc.asset.ControladorAssets;

//===========================================================================
//CLASE ControladorEntities
//===========================================================================
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.PUT, RequestMethod.GET})
@RequestMapping(value = "/entities", produces = "application/json") 
public class ControladorEntities {

	// ----------------------------------------------------------------------
	// get (GET /entities?identifier=11111111A)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "")
	public HttpEntity<Resources<ResourceSupport>> getEntities(@RequestParam(name="identifier", required = false) String identifier) {
		List<Entity> entidades;
		Resources<ResourceSupport> resul;
		
		if (identifier == null)
			entidades = bdEntities.findAll();
		else
			entidades = bdEntities.findByIdentifier(identifier);
		TRAZA.info("getEntities (" + identifier + "): " + entidades.size());
		resul = new Resources<ResourceSupport> (entidades.stream().map(this::toRecurso).collect(toList())); 
		resul.add(linkTo(methodOn(ControladorEntities.class).getEntities(identifier)).withSelfRel());
		resul.add(linkTo(methodOn(ControladorEntities.class).getOrigins()).withRel("origins"));
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// get (GET /entities/{id})
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public HttpEntity<ResourceSupport> get(@PathVariable final String id) {
		Optional<Entity> entidad = bdEntities.findById(id);
		ResourceSupport resul = toRecurso(entidad.orElse(new Entity("NO ENCONTRADO", "", "", "")));
		return new ResponseEntity<>(resul, HttpStatus.OK);
    }
	
	// ----------------------------------------------------------------------
	// get (GET /entities/origins)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/origins")
	public HttpEntity<Resources<Entry<String,Long>>> getOrigins() {
		Contador contador = new Contador();
		List<Entity> entities = bdEntities.findAll();
		Resources<Entry<String,Long>> resul;
		
		TRAZA.info("getValues: GET /entities/origins");
		for (Entity entity: entities) {
			contador.pon(entity.getOrigin());
		}
		TRAZA.info("getValues: GET /entities/origins: " + contador);
		resul = new Resources<Entry<String, Long>>(contador.entrySet());
		resul.add(linkTo(methodOn(ControladorEntities.class).getEntities("identifier")).withSelfRel());
		resul.add(linkTo(methodOn(ControladorEntities.class).getOrigins()).withRel("origins"));
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// modificaEntidad (PUT /entities/{id})
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	public HttpEntity<ResourceSupport> modificaEntidad(@PathVariable String id,
			@RequestBody Entity entidad) {
		Optional<Entity> ent = bdEntities.findById(id);
		ResourceSupport resul = null;
		
		TRAZA.info("modificaEntidad - entidad nueva: " + entidad);
		if (ent.isPresent()) {
			ent.get().modifica(entidad);
			TRAZA.info("modificaEntidad - entidad modificada: " + ent.get());
			resul = toRecurso(ent.get());
			bdEntities.save(ent.get());
		}
		return new ResponseEntity<>(resul, HttpStatus.OK);
}
	
	// ----------------------------------------------------------------------
	// toRecurso
	// ----------------------------------------------------------------------
    private ResourceSupport toRecurso(Entity entity) {
    	ResourceSupport resul = new Resource<>(entity);
    	resul = new Resource<>(entity);
		resul.add(linkTo(methodOn(ControladorEntities.class).get(entity.getId())).withSelfRel());
		resul.add(linkTo(methodOn(ControladorEntities.class).getEntities(entity.getIdentifier())).withRel("identifier"));
		resul.add(linkTo(methodOn(ControladorEntities.class).
				modificaEntidad(entity.getId(), entity)).withRel("modifica"));
		resul.add(linkTo(methodOn(ControladorAssets.class).getAssets(entity.getId())).withRel("assets"));
    	return resul;
    }
 
    //===========================================================================
    // ATRIBUTOS
    //===========================================================================
	private static Logger TRAZA = LoggerFactory.getLogger(ControladorEntities.class);

	@Autowired
	private EntityRepository bdEntities;
}
