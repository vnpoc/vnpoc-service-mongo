package poc;

import org.springframework.data.annotation.Id;

public class Entity {
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
	
	//=======================================================================
	// ATRIBUTOS
	//=======================================================================
	@Id private String id;

	private String name;
	private String identifier;
	private String description;
	private String origin;
}
