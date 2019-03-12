package poc.asset;

import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Asset implements Identifiable<String> {	
    @JsonCreator
	public Asset(@JsonProperty("name") String name,
			@JsonProperty("identifier") String identifier,
			@JsonProperty("description") String description,
			@JsonProperty("value") String value) {
    	super();
    	this.name = name;
    	this.identifier = identifier;
    	this.description = description;
    	this.value = value;		
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public String getId() {
		return id;
	}

	public void modifica(Asset asset) {
    	if (asset.name != null) this.name = asset.name;
    	if (asset.identifier != null) this.identifier = asset.identifier;
    	if (asset.description != null) this.description = asset.description;
    	if (asset.value != null) this.value = asset.value;		
    	if (asset.parent != null) this.parent = asset.parent;		
	}
	
	//=======================================================================
	// ATRIBUTOS
	//=======================================================================
	@Id private String id;
	
	private String name;
	private String identifier;
	private String description;
	private String value;
	private String parent;
}
