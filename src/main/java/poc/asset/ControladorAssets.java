package poc.asset;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

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

//===========================================================================
//CLASE ControladorEntities
//===========================================================================
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/assets", produces = "application/json") 
public class ControladorAssets {

	// ----------------------------------------------------------------------
	// get (GET /assets?parent=parent)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "")
	public HttpEntity<Resources<ResourceSupport>> getAssets(@RequestParam(name="parent", required = false) String parent) {
		List<Asset> lista;
		Resources<ResourceSupport> resul;
		
		TRAZA.info("getAssets: GET /assets: " + parent);
		if (parent == null)
			lista = bdAssets.findAll();
		else
			lista = bdAssets.findByParent(parent);
		resul = new Resources<ResourceSupport> (lista.stream().map(this::toRecurso).collect(toList()));
		resul.add(linkTo(methodOn(ControladorAssets.class).getAssets(parent)).withSelfRel());
		resul.add(linkTo(methodOn(ControladorAssets.class).getValues()).withSelfRel());
		return new ResponseEntity<Resources<ResourceSupport>>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// get (GET /assets/{id})
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public HttpEntity<ResourceSupport> get(@PathVariable final String id) {
		Optional<Asset> asset = bdAssets.findById(id);
		ResourceSupport resul = toRecurso(asset.orElse(new Asset("NO ENCONTRADO", "", "", "")));
		return new ResponseEntity<>(resul, HttpStatus.OK);
    }
	
	// ----------------------------------------------------------------------
	// get (GET /assets/values)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/values")
	public HttpEntity<Resources<Entry<String,Long>>> getValues() {
		Contador contador = new Contador();
		List<Asset> assets = bdAssets.findAll();
		Resources<Entry<String,Long>> resul;
		
		TRAZA.info("getValues: GET /assets/value");
		for (Asset asset: assets) {
			contador.pon(asset.getValue());
		}
		TRAZA.info("getValues: GET /assets/value: " + contador);
		resul = new Resources<Entry<String, Long>>(contador.entrySet());
		resul.add(linkTo(methodOn(ControladorAssets.class).getAssets("parent")).withSelfRel());
		resul.add(linkTo(methodOn(ControladorAssets.class).getValues()).withSelfRel());
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// modifica (PUT /assets/{id})
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	public HttpEntity<ResourceSupport> modifica(@PathVariable String id,
			@RequestBody Asset asset) {
		Optional<Asset> ent = bdAssets.findById(id);
		ResourceSupport resul = null;
		
		TRAZA.info("modifica - asset nuevo: " + asset);
		if (ent.isPresent()) {
			ent.get().modifica(asset);
			TRAZA.info("modifica - asset modificado: " + ent.get());
			resul = toRecurso(ent.get());
			bdAssets.save(ent.get());
		}
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// toRecurso
	// ----------------------------------------------------------------------
    private ResourceSupport toRecurso(Asset asset) {
    	ResourceSupport resul = new Resource<>(asset);
    	resul = new Resource<>(asset);
		resul.add(linkTo(methodOn(ControladorAssets.class).get(asset.getId())).withSelfRel());
		resul.add(linkTo(methodOn(ControladorAssets.class).getAssets(asset.getParent())).withRel("parent"));
		resul.add(linkTo(methodOn(ControladorAssets.class).
				modifica(asset.getId(), asset)).withRel("modifica"));
		resul.add(linkTo(methodOn(ControladorAssets.class).getValues()).withRel("values"));
		return resul;
    }
 
    //===========================================================================
    // ATRIBUTOS
    //===========================================================================
	private static Logger TRAZA = LoggerFactory.getLogger(ControladorAssets.class);

	@Autowired
	private AssetRepository bdAssets;
}
