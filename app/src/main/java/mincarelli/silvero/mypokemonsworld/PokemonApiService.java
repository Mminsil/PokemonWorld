package mincarelli.silvero.mypokemonsworld;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Es una interfaz que define los métodos de la API a través de Retrofit. Se especifican los
 * endpoints (URLs) de la API y cómo se deben hacer las solicitudes HTTP.
 */
public interface PokemonApiService {
    /**
     * Define el endpoint pokemon, que obtiene una lista de Pokémon.
     * Este método recibe parámetros offset y limit para controlar la paginación de la lista.
     * @param offset
     * @param limit
     * @return
     */
    @GET("pokemon")
    Call<PokemonRequest> getPokemonList(
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    /**
     * Define el endpoint pokemon/{id}, que obtiene los detalles de un Pokémon específico, usando su
     * ID (pasado como parámetro en la URL).
     * @param id
     * @return
     */
    @GET("pokemon/{id}")
    Call<PokemonDetails> getPokemonDetails(@Path("id") int id);
}