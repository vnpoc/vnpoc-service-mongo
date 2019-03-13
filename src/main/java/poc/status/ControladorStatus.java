package poc.status;

import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import poc.Contador;
import poc.asset.Asset;
import poc.asset.AssetRepository;
import poc.entity.Entity;
import poc.entity.EntityRepository;

//===========================================================================
//CLASE ControladorStatus
//===========================================================================
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/status", produces = "application/json") 
public class ControladorStatus {

	// ----------------------------------------------------------------------
	// get (GET /status)
	// ----------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value = "")
	public HttpEntity<Resources<Entry<String,Long>>> get() {
		Contador contador = new Contador();
		List<Entity> entities = bdEntities.findAll();
		List<Asset> assets = bdAssets.findAll();
		Resources<Entry<String,Long>> resul;
		
		TRAZA.info("get: GET /status");
		for (Entity entity: entities) {
			contador.pon(entity.getOrigin());
		}
		for (Asset asset: assets) {
			contador.pon(asset.getValue());
		}
		TRAZA.info("get: GET /status: " + contador);
		resul = new Resources<Entry<String, Long>>(contador.entrySet());
		return new ResponseEntity<>(resul, HttpStatus.OK);
	}

    //===========================================================================
    // ATRIBUTOS
    //===========================================================================
	private static Logger TRAZA = LoggerFactory.getLogger(ControladorStatus.class);

	@Autowired
	private EntityRepository bdEntities;
	@Autowired
	private AssetRepository bdAssets;
}
