package mincarelli.silvero.mypokemonsworld;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import mincarelli.silvero.mypokemonsworld.databinding.FragmentPokedexBinding;

import java.util.ArrayList;
import java.util.List;

public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding;
    private PokedexAdapter pokedexAdapter;
    private RecyclerView recyclerView;
    private List<PokemonDetails> pokemonDetailsList = new ArrayList<>();
    private PokemonRepository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPokedexBinding.inflate(inflater, container, false);


        // Inicializa RecyclerView
        recyclerView = binding.recyclerviewPokedex;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Define el listener
        PokedexAdapter.OnPokemonClickListener listener = pokemonDetails -> {
            // Lógica al hacer clic en un Pokémon
            capturePokemon(pokemonDetails);
            Toast.makeText(getContext(), pokemonDetails.getForms().get(0).getName() + " capturado!", Toast.LENGTH_SHORT).show();
        };


        // Crea y asigna el adaptador con el listener
        pokedexAdapter = new PokedexAdapter(getContext(), pokemonDetailsList, listener);
        recyclerView.setAdapter(pokedexAdapter);

        // Obtener datos desde el repositorio
        repository = new PokemonRepository();
        loadPokemonData();

        return binding.getRoot();
    }




    /**
     * Llama al repositorio para obtener la lista de Pokémon y actualiza el adaptador con los datos.
     */
    private void loadPokemonData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        repository.fetchPokemonList(0, 150, new PokemonRepository.PokemonListCallback() {
            @Override
            public void onPokemonListLoaded(List<PokemonDetails> pokemonDetails) {
                pokemonDetailsList.clear();
                pokemonDetailsList.addAll(pokemonDetails);
                // Cargar los Pokémon capturados desde Firebase
                db.collection("CapturedPokemon")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Integer> capturedIds = new ArrayList<>();

                                // Obtener los IDs de los Pokémon capturados
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    CapturedPokemon capturedPokemon = document.toObject(CapturedPokemon.class);
                                    capturedIds.add(capturedPokemon.getIndex());
                                }

                                // Marcar los Pokémon capturados en la lista
                                for (PokemonDetails details : pokemonDetailsList) {
                                    if (capturedIds.contains(details.getId())) {
                                        details.setCaptured(true);
                                    } else {
                                        details.setCaptured(false); // Asegurarse de que no esté marcado si no está capturado
                                    }
                                }

                                // Aquí es donde actualizas el adaptador para que se cambien los colores
                                pokedexAdapter.notifyDataSetChanged(); // Refrescar el adaptador con los cambios
                            }
                        });
            }
        });
    }

    private void capturePokemon(PokemonDetails details) {
        // Marcar como capturado
        details.setCaptured(true);

        // Guardar en Firebase
        saveToFirebase(details);

        // Refrescar el adaptador para cambiar el color del Pokémon capturado
        pokedexAdapter.notifyDataSetChanged();
    }

    private void saveToFirebase(PokemonDetails details) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CapturedPokemon captured = new CapturedPokemon(details.getForms().get(0).getName(), details.getId(),
                details.getTypes().get(0).getType().getName(), details.getWeight() / 10.0,
                details.getHeight() / 10.0, details.getSprites().getOther().getHome().getFrontDefault());

        db.collection("CapturedPokemon")
                .add(captured)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Mostrar mensaje de éxito
                        Toast.makeText(requireContext(), R.string.save, Toast.LENGTH_SHORT).show();
                        details.setCaptured(true);
                        pokedexAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(runnable ->
                        Toast.makeText(requireContext(), R.string.errorNotSave, Toast.LENGTH_SHORT).show()
                );
        pokedexAdapter.notifyDataSetChanged();
    }
}
