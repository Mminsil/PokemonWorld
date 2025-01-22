package mincarelli.silvero.mypokemonsworld;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Es una interfaz que define los métodos de la API a través de Retrofit.
 * Se especifican los endpoints (URLs) de la API y cómo se deben realizar las solicitudes HTTP.
 */
public interface PokemonApiService {
    /**
     * Define el endpoint "pokemon", que obtiene una lista de Pokémon de la API.
     * Este método utiliza los parámetros `offset` y `limit` para controlar la paginación,
     * permitiendo obtener un subconjunto específico de Pokémon en cada solicitud.
     *
     * @param offset El número de Pokémon a omitir desde el inicio de la lista (para paginación).
     * @param limit  La cantidad máxima de Pokémon a devolver en la respuesta.
     * @return Un objeto `Call` que, al ejecutarse, devuelve un objeto `PokemonRequest`
     * con la lista de Pokémon y otros metadatos relacionados.
     */
    @GET("pokemon")
    Call<PokemonRequest> getPokemonList(
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    /**
     * Define el endpoint "pokemon/{id}", que obtiene los detalles de un Pokémon específico
     * desde la API. El ID del Pokémon se pasa como parámetro en la URL.
     *
     * @param id El identificador único del Pokémon cuyos detalles se desean obtener.
     * @return Un objeto `Call` que, al ejecutarse, devuelve un objeto `PokemonDetails`
     * con toda la información detallada del Pokémon solicitado.
     */
    @GET("pokemon/{id}")
    Call<PokemonDetails> getPokemonDetails(@Path("id") int id);
}