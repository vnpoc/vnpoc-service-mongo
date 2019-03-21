package poc.status;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public HttpEntity<Collection<Propiedad>> get() {
		Estado estado = new Estado();
		Collection<Entity> entities = bdEntities.findAll();
		Collection<Asset> assets = bdAssets.findAll();
		Collection<Propiedad> resul;
		
		TRAZA.info("get: GET /status");
		for (Entity entity: entities) {
			estado.pon(Propiedad.Tipo.ENTITY, entity.getOrigin());
		}
		for (Asset asset: assets) {
			estado.pon(Propiedad.Tipo.ASSET, asset.getValue());
		}
		TRAZA.info("get: GET /status: " + estado);
		TRAZA.info("get: GET /status me duermo un rato");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resul = estado.propiedades();
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
