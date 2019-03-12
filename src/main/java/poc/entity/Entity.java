package poc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//=======================================================================
// CLASE
//=======================================================================
public class Entity implements Identifiable<String> {
    @JsonCreator
	public Entity(@JsonProperty("name") String name,
			@JsonProperty("identifier") String identifier,
			@JsonProperty("description") String description,
			@JsonProperty("origin") String origin) {
		this.name = name;
		this.identifier = identifier;
		this.description = description;
		this.origin = origin;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public String toString() {
		return "{  name: " + name + 
				", identifier: " + identifier +
				", description: " + description +
				", origin: " + origin +
				"}";
	}
	
	public void modifica(Entity entidad) {
		if (entidad.name != null) this.name = entidad.name;
		if (entidad.identifier != null) this.identifier = entidad.identifier;
		if (entidad.description != null) this.description = entidad.description;
		if (entidad.origin != null) this.origin = entidad.origin;
	}

	//=======================================================================
	// ATRIBUTOS
	//=======================================================================
	@Id private String id;

	private String name;
	private String identifier;
	private String description;
	private String origin;
}
