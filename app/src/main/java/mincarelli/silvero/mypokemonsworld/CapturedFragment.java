package mincarelli.silvero.mypokemonsworld;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mincarelli.silvero.mypokemonsworld.databinding.FragmentCapturedBinding;

/**
 * Fragmento que muestra una lista de Pokémon capturados en un RecyclerView.
 * Permite visualizar los detalles de cada Pokémon y eliminar elementos con un gesto de deslizamiento.
 */
public class CapturedFragment extends Fragment {
    private FragmentCapturedBinding binding;
    private CapturedAdapter capturedAdapter;
    private RecyclerView recyclerView;
    private List<CapturedPokemon> pokemonCapturedList = new ArrayList<>();

    /**
     * Infla la vista del fragmento y configura el RecyclerView con el adaptador.
     * También establece un Listener para los clics en los ítems del RecyclerView.
     *
     * @param inflater           El inflador que se usa para inflar la vista.
     * @param container          El contenedor en el que se va a agregar la vista.
     * @param savedInstanceState El estado guardado, si existe.
     * @return La vista inflada que representa el fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCapturedBinding.inflate(inflater, container, false);
        //Inicializa RecyclerView
        recyclerView = binding.recyclerviewCaptured;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        //Crea y asigna el adaptador
        capturedAdapter = new CapturedAdapter(getContext(), pokemonCapturedList);
        recyclerView.setAdapter(capturedAdapter);

        // Configura el listener para el clic en las tarjetas
        capturedAdapter.setOnItemClickListener((capture, view) -> {
            Bundle bundle = new Bundle();
            bundle.putString("image", capture.getImageUrl());
            bundle.putString("name", capture.getName());
            bundle.putInt("index", capture.getIndex());
            bundle.putString("type", capture.getType());
            bundle.putDouble("weight", capture.getWeight());
            bundle.putDouble("height", capture.getHeight());
            Navigation.findNavController(view).navigate(R.id.capturedDetailFragment, bundle);
        });

        // Leer preferencia de eliminación desde SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("delete_PokemonCaptured", Context.MODE_PRIVATE);
        boolean isDeletionEnabled = prefs.getBoolean("enable_delete", false);


        // Configurar el Swipe para eliminar (basado en la preferencia)
        if (isDeletionEnabled) {
            // Configurar el Swipe para eliminar
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    CapturedPokemon pokemon = pokemonCapturedList.get(position);

                    // Eliminar de la lista y notificar al adaptador
                    pokemonCapturedList.remove(position);
                    capturedAdapter.notifyItemRemoved(position);

                    deleteToFirebase(pokemon);
                }
            });
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

        readToFirebase();

        return binding.getRoot();
    }

    /**
     * Elimina un Pokémon de la base de datos de Firebase y de la lista local.
     *
     * @param pokemon El Pokémon que se eliminará.
     */
    private void deleteToFirebase(CapturedPokemon pokemon) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obtener el ID del documento
        db.collection("CapturedPokemon")
                .whereEqualTo("index", pokemon.getIndex()) // Buscar el documento por el nombre del Pokémon
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        // Obtener el ID del primer documento que coincida con el nombre
                        String docId = querySnapshot.getDocuments().get(0).getId();

                        // Eliminar el documento usando el ID obtenido
                        db.collection("CapturedPokemon")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener(aVoid -> {
                                    // Eliminar el Pokémon de la lista local y notificar al adaptador
                                    pokemonCapturedList.remove(pokemon);
                                    capturedAdapter.notifyDataSetChanged();

                                    // Mostrar mensaje de éxito al usuario
                                    Toast.makeText(requireContext(), R.string.pokemon_removed, Toast.LENGTH_SHORT).show();
                                    readToFirebase();
                                })
                                .addOnFailureListener(e -> {
                                    // Mostrar mensaje de error al usuario
                                    Toast.makeText(requireContext(), R.string.error_deleting + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // Mostrar mensaje de error al usuario
                        Toast.makeText(requireContext(), R.string.error_notFound, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Mostrar mensaje de error al usuario
                    Toast.makeText(requireContext(), R.string.error_getting + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Lee los Pokémon capturados desde Firebase y actualiza la lista local.
     */
    private void readToFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("CapturedPokemon")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Limpiar la lista antes de agregar nuevos datos
                        pokemonCapturedList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Mapea directamente a CapturedPokemon
                            CapturedPokemon capturedPokemon = document.toObject(CapturedPokemon.class);
                            pokemonCapturedList.add(capturedPokemon);
                        }

                        // Ordena la lista por índice (de menor a mayor)
                        Collections.sort(pokemonCapturedList, new Comparator<CapturedPokemon>() {
                            @Override
                            public int compare(CapturedPokemon p1, CapturedPokemon p2) {
                                return Integer.compare(p1.getIndex(), p2.getIndex());
                            }
                        });
                        // Notifica al adaptador que los datos han cambiado
                        capturedAdapter.notifyDataSetChanged();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
}