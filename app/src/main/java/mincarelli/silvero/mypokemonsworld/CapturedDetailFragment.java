package mincarelli.silvero.mypokemonsworld;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import mincarelli.silvero.mypokemonsworld.databinding.FragmentCapturedDetailBinding;

/**
 * Fragmento que muestra los detalles de un Pokémon capturado.
 * Este fragmento recibe datos a través de los argumentos y los muestra en las vistas usando View Binding.
 */
public class CapturedDetailFragment extends Fragment {
    private FragmentCapturedDetailBinding binding;

    /**
     * Infla la vista del fragmento utilizando View Binding.
     *
     * @param inflater           El inflador que se usa para inflar la vista.
     * @param container          El contenedor en el que se va a agregar la vista (si es necesario).
     * @param savedInstanceState El estado guardado, si existe.
     * @return La vista inflada que representa el fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCapturedDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Se ejecuta después de que la vista ha sido creada.
     * Recupera los argumentos pasados al fragmento y configura las vistas con los datos.
     *
     * @param view               La vista del fragmento.
     * @param savedInstanceState El estado guardado del fragmento (si existe).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String imageUrl = getArguments().getString("image");
            String name = getArguments().getString("name");
            int index = getArguments().getInt("index");
            String type = getArguments().getString("type");
            double weight = getArguments().getDouble("weight");
            double height = getArguments().getDouble("height");
            Log.d("CapturedDetailFragment", "Datos recibidos: " +
                    "Name=" + name + ", Index=" + index +
                    ", Type=" + type + ", Weight=" + weight +
                    ", Height=" + height);
            // Configura los datos en las vistas
            binding.capturedName.setText(name);
            binding.capturedIndex.setText(String.valueOf(index) + "- ");
            binding.capturedType.setText(type);
            binding.capturedWeight.setText(String.format("%.1f kg", weight));
            binding.capturedHeight.setText(String.format("%.1f m", height));
            Picasso.get().load(imageUrl).into(binding.capturedImage);
        }
    }
}