package poc.asset;

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
//CLASE ControladorAssets
//===========================================================================
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/assets", produces = "application/json") 
public class ControladorAssets {

	// ----------------------------------------------------------------------
	// get (GET /assets)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "")
	public HttpEntity<Collection<ResourceSupport>> getAssets() {
		Collection<Asset> lista;
		Collection<ResourceSupport> resul;
		
		TRAZA.info("getAssets: GET /assets");
		lista = bdAssets.findAll();
		resul = lista.stream().map(this::toRecurso).collect(toList());
		// resul.add(linkTo(methodOn(ControladorAssets.class).getAssets(parent)).withSelfRel());
		// resul.add(linkTo(methodOn(ControladorAssets.class).getValues()).withSelfRel());
		return new ResponseEntity<Collection<ResourceSupport>>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// get (GET /assets/{id})
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public HttpEntity<ResourceSupport> get(@PathVariable final String id) {
		List<Asset> assets = bdAssets.findByIdentifier(id);
		ResourceSupport resul;
		HttpEntity<ResourceSupport> resp;
		
		if (assets.size() > 0) {
			resul = toRecurso(assets.get(0));
			resp = new ResponseEntity<>(resul, HttpStatus.OK);
		}
		else {
			resul = toRecurso(new Asset("NO ENCONTRADO", "", "", ""));
			resp = new ResponseEntity<>(resul, HttpStatus.NOT_FOUND);
		}
		return resp;
    }
	
	// ----------------------------------------------------------------------
	// pon (POST /assets)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.POST, value="")
	public HttpEntity<ResourceSupport> pon(@RequestBody Asset asset) {
		ResourceSupport resul = null;
		
		TRAZA.info("pon - asset nuevo: " + asset);
		bdAssets.save(asset);
		resul = toRecurso(asset);
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// modifica (PUT /assets/{id})
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	public HttpEntity<ResourceSupport> modifica(@PathVariable String id,
			@RequestBody Asset asset) {
		List<Asset> assets = bdAssets.findByIdentifier(id);
		Asset elem;
		ResourceSupport resul = null;
		
		TRAZA.info("modifica - asset nuevo: " + asset);
		if (assets.size() > 0) {
			elem = assets.get(0);
			elem.modifica(asset);
			TRAZA.info("modifica - asset modificado: " + elem);
			resul = toRecurso(elem);
			bdAssets.save(elem);
		}
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------
	// toRecurso
	// ----------------------------------------------------------------------
    private ResourceSupport toRecurso(Asset asset) {
    	ResourceSupport resul = new Resource<>(asset);
    	resul = new Resource<>(asset);
    	/*
		resul.add(linkTo(methodOn(ControladorAssets.class).get(asset.getId())).withSelfRel());
		resul.add(linkTo(methodOn(ControladorAssets.class).getAssets(asset.getParent())).withRel("parent"));
		resul.add(linkTo(methodOn(ControladorAssets.class).pon(asset)).withRel("pon"));
		resul.add(linkTo(methodOn(ControladorAssets.class).
				modifica(asset.getId(), asset)).withRel("modifica"));
		resul.add(linkTo(methodOn(ControladorAssets.class).getValues()).withRel("values"));
		*/
		return resul;
    }
 
    //===========================================================================
    // ATRIBUTOS
    //===========================================================================
	private static Logger TRAZA = LoggerFactory.getLogger(ControladorAssets.class);

	@Autowired
	private AssetRepository bdAssets;
}
