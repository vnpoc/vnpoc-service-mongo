package poc.entity;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "entities", path = "entities")
public interface EntityRepository extends MongoRepository<Entity, String> {

	List<Entity> findByIdentifier(@Param("identifier") String identifier);

}
