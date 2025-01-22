package mincarelli.silvero.mypokemonsworld;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Representa la estructura de la respuesta JSON cuando obtienes la lista de Pokémon.
 * Contiene un campo results que es una lista de objetos de tipo Pokemon.
 * Los datos de esta clase son procesados por otras clases para obtener detalles de cada Pokémon.
 */
public class PokemonRequest {
    @SerializedName("results")
    //Lista de objetos Pokemon, que contiene los nombres y URLs de los Pokémon.
    private List<Pokemon> results;

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}
