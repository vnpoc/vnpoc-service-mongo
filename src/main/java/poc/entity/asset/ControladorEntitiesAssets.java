package poc.entity.asset;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
//CLASE ControladorEntitiesAssets
//===========================================================================
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/entities", produces = "application/json") 
public class ControladorEntitiesAssets {

	// ----------------------------------------------------------------------
	// get (GET /entities/{id}/assets)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/assets")
	public HttpEntity<Collection<Asset>> getAssets(@PathVariable final String id) {
		Collection<Asset> assets = bdAssets.findByParent(id);
		
		TRAZA.info("getAssets: " + assets.size());
		return new ResponseEntity<>(assets, HttpStatus.OK);
    }
	 
    //===========================================================================
    // ATRIBUTOS
    //===========================================================================
	private static Logger TRAZA = LoggerFactory.getLogger(ControladorEntitiesAssets.class);

	@Autowired
	private AssetRepository bdAssets;
}
