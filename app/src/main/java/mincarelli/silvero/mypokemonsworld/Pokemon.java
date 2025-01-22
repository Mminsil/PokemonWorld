package mincarelli.silvero.mypokemonsworld;

/**
 * Representa un Pokémon básico, que solo contiene su nombre y una URL que apunta a la información
 * detallada del Pokémon.
 */
public class Pokemon {
    private String name;
    private String url;

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
     * @param name El nombre a establecer para el Pokémon.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la URL que apunta a la información detallada del Pokémon.
     *
     * @return La URL del Pokémon.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL que apunta a la información detallada del Pokémon.
     *
     * @param url La URL a establecer para el Pokémon.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
