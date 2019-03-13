package poc.asset;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "assets", path = "assets")
public interface AssetRepository extends MongoRepository<Asset, String> {

	List<Asset> findByIdentifier(@Param("identifier") String identifier);
	List<Asset> findByParent(@Param("parent") String parent);
	List<Asset> findByValue(@Param("value") String value);
}
