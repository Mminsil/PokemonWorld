package mincarelli.silvero.mypokemonsworld;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.Navigation;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import mincarelli.silvero.mypokemonsworld.databinding.FragmentCapturedDetailBinding;

public class CapturedDetailFragment extends Fragment {
    private FragmentCapturedDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCapturedDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

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
            binding.capturedIndex.setText(String.valueOf(index) +"- ");
            binding.capturedType.setText(type);
            binding.capturedWeight.setText(String.format("%.1f kg", weight));
            binding.capturedHeight.setText(String.format("%.1f m", height));
            Picasso.get().load(imageUrl).into(binding.capturedImage);
        }
    }
}