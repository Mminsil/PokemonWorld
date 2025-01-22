package mincarelli.silvero.mypokemonsworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que actúa como intermediario entre la capa de interfaz de usuario (UI) y la API de Pokémon.
 * <p>
 * Gestiona la comunicación con la API, realiza las solicitudes necesarias y procesa las respuestas.
 * Proporciona métodos para obtener una lista de Pokémon y los detalles individuales de cada uno.
 * <p>
 * Es invocado desde las actividades o fragmentos para cargar datos y entregarlos a través de
 * callbacks a la UI.
 */
public class PokemonRepository {
    private final PokemonApiService apiService;

    /**
     * Constructor que inicializa el cliente Retrofit para interactuar con la API de Pokémon.
     */
    public PokemonRepository() {
        apiService = RetrofitClient.getApiService();
    }

    /**
     * Realiza una solicitud a la API para obtener una lista de Pokémon y, posteriormente,
     * obtiene los detalles de cada Pokémon en la lista.
     * <p>
     * La lista final de detalles se entrega ordenada por el ID del Pokémon.
     *
     * @param offset   Desplazamiento para la paginación (número de elementos a saltar).
     * @param limit    Número máximo de Pokémon que se desea obtener en la solicitud.
     * @param callback Interfaz para devolver la lista de detalles del Pokémon a la UI.
     */
    public void fetchPokemonList(int offset, int limit, PokemonListCallback callback) {
        apiService.getPokemonList(offset, limit).enqueue(new Callback<PokemonRequest>() {
            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pokemon> pokemonList = response.body().getResults();
                    List<PokemonDetails> detailsList = new ArrayList<>();

                    for (Pokemon pokemon : pokemonList) {
                        // Fetch details for each Pokémon
                        fetchPokemonDetails(pokemon.getUrl(), new PokemonDetailsCallback() {
                            @Override
                            public void onPokemonDetailsLoaded(PokemonDetails details) {
                                detailsList.add(details);
                                if (detailsList.size() == pokemonList.size()) {
                                    // Ordenar la lista por ID
                                    Collections.sort(detailsList, new Comparator<PokemonDetails>() {
                                        @Override
                                        public int compare(PokemonDetails p1, PokemonDetails p2) {
                                            return Integer.compare(p1.getId(), p2.getId());
                                        }
                                    });
                                    callback.onPokemonListLoaded(detailsList);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonRequest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Realiza una solicitud adicional a la API para obtener los detalles de un Pokémon específico
     * utilizando su URL.
     *
     * @param url      URL del Pokémon en la API, que contiene su ID único.
     * @param callback Interfaz para devolver los detalles del Pokémon a la UI.
     */
    private void fetchPokemonDetails(String url, PokemonDetailsCallback callback) {
        String id = url.split("/")[url.split("/").length - 1];
        apiService.getPokemonDetails(Integer.parseInt(id)).enqueue(new Callback<PokemonDetails>() {

            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onPokemonDetailsLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Interfaz para devolver la lista de detalles de Pokémon a la interfaz de usuario.
     */
    public interface PokemonListCallback {
        /**
         * Método que se ejecuta cuando la lista de detalles de Pokémon se ha cargado exitosamente.
         *
         * @param pokemonDetails Lista de objetos {@link PokemonDetails}.
         */
        void onPokemonListLoaded(List<PokemonDetails> pokemonDetails);
    }

    /**
     * Interfaz para devolver los detalles de un Pokémon específico a la interfaz de usuario.
     */
    public interface PokemonDetailsCallback {
        /**
         * Método que se ejecuta cuando los detalles de un Pokémon se han cargado exitosamente.
         *
         * @param details Objeto {@link PokemonDetails} con la información del Pokémon.
         */
        void onPokemonDetailsLoaded(PokemonDetails details);
    }
}
