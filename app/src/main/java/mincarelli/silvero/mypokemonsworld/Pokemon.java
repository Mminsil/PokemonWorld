package mincarelli.silvero.mypokemonsworld;

/**
 * Representa un Pokémon básico, que solo contiene su nombre y una URL que apunta a la información
 * detallada del Pokémon.
 */
public class Pokemon {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
