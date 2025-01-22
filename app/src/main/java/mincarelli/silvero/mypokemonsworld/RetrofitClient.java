package mincarelli.silvero.mypokemonsworld;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Configura y proporciona una instancia de Retrofit, librería que facilita las solicitudes
 * HTTP a APIs RESTfull
 * Asegura que Retrofit solo se cree una vez (instancia única) para evitar sobrecargar recursos.
 */
public class RetrofitClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private static Retrofit retrofit;

    //Esto es para encender el retrofit

    /**
     * getApiService(): Si la instancia de Retrofit no está creada, la crea utilizando un Retrofit.Builder
     * con la URL base (BASE_URL) y un convertidor de respuestas a formato JSON (usando Gson).
     * @return
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