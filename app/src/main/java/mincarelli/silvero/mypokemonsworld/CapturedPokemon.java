package mincarelli.silvero.mypokemonsworld;

/**
 * Clase que representa un Pokémon capturado con sus detalles.
 * Contiene información sobre el nombre, índice, tipo, peso, altura y la URL de la imagen del Pokémon.
 */
public class CapturedPokemon {
    private String name;
    private int index;
    private String type;
    private double weight;
    private double height;
    private String imageUrl;

    /**
     * Constructor vacío necesario para Firebase.
     * Firebase requiere un constructor sin parámetros para deserializar objetos.
     */
    public CapturedPokemon() {
    }

    /**
     * Constructor completo para crear un objeto CapturedPokemon con todos los detalles.
     *
     * @param name     El nombre del Pokémon.
     * @param index    El índice o número del Pokémon en la Pokédex.
     * @param type     El tipo del Pokémon.
     * @param weight   El peso del Pokémon.
     * @param height   La altura del Pokémon.
     * @param imageUrl La URL de la imagen del Pokémon.
     */
    public CapturedPokemon(String name, int index, String type, double weight, double height, String imageUrl) {
        this.name = name;
        this.index = index;
        this.type = type;
        this.weight = weight;
        this.height = height;
        this.imageUrl = imageUrl;
    }

    /**
     * Obtiene el nombre del Pokémon.
     *
     * @return El nombre del Pokémon.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del Pokémon.
     *
     * @param name El nombre del Pokémon.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el índice del Pokémon en la Pokédex.
     *
     * @return El índice del Pokémon.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Establece el índice del Pokémon en la Pokédex.
     *
     * @param index El índice del Pokémon.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Obtiene el tipo del Pokémon.
     *
     * @return El tipo del Pokémon.
     */
    public String getType() {
        return type;
    }

    /**
     * Establece el tipo del Pokémon.
     *
     * @param type El tipo del Pokémon.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtiene el peso del Pokémon.
     *
     * @return El peso del Pokémon en kilogramos.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Establece el peso del Pokémon.
     *
     * @param weight El peso del Pokémon en kilogramos.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Obtiene la altura del Pokémon.
     *
     * @return La altura del Pokémon en metros.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Establece la altura del Pokémon.
     *
     * @param height La altura del Pokémon en metros.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Obtiene la URL de la imagen del Pokémon.
     *
     * @return La URL de la imagen del Pokémon.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Establece la URL de la imagen del Pokémon.
     *
     * @param imageUrl La URL de la imagen del Pokémon.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
