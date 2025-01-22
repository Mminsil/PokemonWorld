package mincarelli.silvero.mypokemonsworld;

public class CapturedPokemon {
    private String name;
    private int index; // Corregido: Consistencia en el nombre
    private String type;
    private double weight;
    private double height;
    private String imageUrl;

    // Constructor vacío (necesario para Firebase)
    public CapturedPokemon() {}

    // Constructor completo
    public CapturedPokemon(String name, int index, String type, double weight, double height, String imageUrl) {
        this.name = name;
        this.index = index;
        this.type = type;
        this.weight = weight;
        this.height = height;
        this.imageUrl = imageUrl;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() { // Corregido: Cambio de getindex a getIndex
        return index;
    }

    public void setIndex(int index) { // Corregido: Cambio de setindex a setIndex
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
