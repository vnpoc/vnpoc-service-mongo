package poc.entity.asset;

import static java.util.stream.Collectors.toList;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import poc.asset.Asset;
import poc.asset.AssetRepository;

//===========================================================================
//CLASE ControladorEntities
//===========================================================================
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.PUT, RequestMethod.GET})
@RequestMapping(value = "/entities", produces = "application/json") 
public class ControladorEntitiesAssets {

	// ----------------------------------------------------------------------
	// get (GET /entities/{id}/assets)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/assets")
	public HttpEntity<Resources<ResourceSupport>> getAssets(@PathVariable final String id) {
		List<Asset> assets = bdAssets.findByParent(id);
		Resources<ResourceSupport> resul;
		
		TRAZA.info("getAssets: " + assets.size());
		resul = new Resources<ResourceSupport> (assets.stream().map(this::toRecurso).collect(toList())); 
		return new ResponseEntity<>(resul, HttpStatus.OK);
    }
	 
	// ----------------------------------------------------------------------
	// toRecurso
	// ----------------------------------------------------------------------
    private ResourceSupport toRecurso(Asset asset) {
    	ResourceSupport resul = new Resource<>(asset);
    	resul = new Resource<>(asset);
    	return resul;
    }

    //===========================================================================
    // ATRIBUTOS
    //===========================================================================
	private static Logger TRAZA = LoggerFactory.getLogger(ControladorEntitiesAssets.class);

	@Autowired
	private AssetRepository bdAssets;
}
