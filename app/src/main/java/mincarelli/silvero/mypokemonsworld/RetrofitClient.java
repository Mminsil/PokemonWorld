package mincarelli.silvero.mypokemonsworld;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que configura y proporciona una instancia de Retrofit.
 * Retrofit es una librería que simplifica las solicitudes HTTP a APIs RESTful.
 * Esta clase implementa el patrón Singleton para garantizar que solo haya una instancia de Retrofit,
 * optimizando el uso de recursos.
 */
public class RetrofitClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private static Retrofit retrofit;

    //Esto es para encender el retrofit

    /**
     * Proporciona una instancia del servicio de API de Pokémon (PokemonApiService).
     * Si la instancia de Retrofit no está creada, la inicializa utilizando un Retrofit.Builder
     * con la URL base (BASE_URL) y un convertidor para manejar respuestas en formato JSON
     * (usando GsonConverterFactory).
     *
     * @return Una instancia de PokemonApiService para interactuar con la API de Pokémon.
     */
    public static PokemonApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(PokemonApiService.class);
    }
}