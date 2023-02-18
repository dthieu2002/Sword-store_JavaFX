package core;

public class Sword {
	// properties
	private String id;
	private String name;
	private float height;
	private float weight;
	private String madeIn;
	private String material;
	private float price;
	private String image="images/emptyImage.png"; // set dafault is empty image
	
	// method 
	public Sword() {
		
	}
	
	public Sword(String id, String name, float height, float weight, String madeIn, String material, float price, String image) {
		this.id = id;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.madeIn = madeIn;
		this.material = material;
		this.price = price;
		if(image != "") this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getMadeIn() {
		return madeIn;
	}

	public void setMadeIn(String madeIn) {
		this.madeIn = madeIn;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
