package mincarelli.silvero.mypokemonsworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Intermediario entre el código de la actividad (UI) y la API.
 * Realiza las solicitudes a la API y gestiona la carga de datos.
 * Se invoca desde la actividad principal para cargar la lista de Pokémon y sus detalles,
 * pasando los resultados a través de un callback a la UI.
 */
public class PokemonRepository {
    private final PokemonApiService apiService;

    public PokemonRepository() {
        apiService = RetrofitClient.getApiService();
    }

    /**
     * Realiza una solicitud para obtener una lista de Pokémon a la API y, para cada Pokémon,
     * obtiene los detalles
     *
     * @param offset
     * @param limit
     * @param callback
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
     * Realiza una solicitud adicional para obtener los detalles de cada Pokémon
     *
     * @param url
     * @param callback
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

    public interface PokemonListCallback {
        void onPokemonListLoaded(List<PokemonDetails> pokemonDetails);
    }

    public interface PokemonDetailsCallback {
        void onPokemonDetailsLoaded(PokemonDetails details);
    }
}
