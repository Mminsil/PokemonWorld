package mincarelli.silvero.mypokemonsworld;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase que representa la estructura de la respuesta JSON de la API cuando se obtiene
 * una lista de Pokémon.
 * <p>
 * Esta clase mapea automáticamente el campo "results" del JSON recibido al atributo `results`,
 * que contiene una lista de objetos de tipo {@link Pokemon}.
 * <p>
 * Esta estructura es utilizada para acceder a los nombres y URLs de los Pokémon que se obtienen
 * al realizar una solicitud a la API.
 */
public class PokemonRequest {
    @SerializedName("results")
    //Lista de objetos Pokemon, que contiene los nombres y URLs de los Pokémon.
    private List<Pokemon> results;

    /**
     * Obtiene la lista de Pokémon de la respuesta.
     *
     * @return Una lista de objetos {@link Pokemon} que contiene los nombres y URLs de los Pokémon.
     */
    public List<Pokemon> getResults() {
        return results;
    }

    /**
     * Establece la lista de Pokémon de la respuesta.
     *
     * @param results Una lista de objetos {@link Pokemon} que contiene los nombres y URLs de los Pokémon.
     */
    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}
