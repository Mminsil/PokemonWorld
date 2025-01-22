package mincarelli.silvero.mypokemonsworld;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Locale;

import mincarelli.silvero.mypokemonsworld.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    int ie = R.id.capturedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //Aplicar el lenguaje guardado a la app
        applySavedLanguage();


        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            // Configurar el menú inferior con el controlador de navegación
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
            // Configurar la barra de acciones con el controlador de navegación
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        binding.bottomNavigationView.setSelectedItemId(R.id.item_pokedex);
        binding.bottomNavigationView.setOnItemSelectedListener(this::onMenuSelected);

        // Elimina la actionBar
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        });


        initilizeAppBar();


        setContentView(binding.getRoot());
    }


    private void applySavedLanguage() {
        //Recuperamos el lenguaje guardado
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Cargamos el lenguaje guardado
        String language = prefs.getString("app_language", "en");
        // Cambiar el idioma de la app usando la configuración de la región
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    //Método para que no salga el botón back en la actionBar
    private void initilizeAppBar() {
        //Configurar AppBarConfiguration directamente con los ids de los destinos raíz
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.capturedFragment,
                R.id.pokedexFragment,
                R.id.settingsFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Agregar un listener para escuchar los cambios de destino
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.capturedDetailFragment) {
                // Si el destino es CapturedDetailFragment, mostrar el botón de retroceso
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Detalles del Pokemon");
                    getSupportActionBar().show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setHomeButtonEnabled(true);
                }
            } else {
                // Si no es CapturedDetailFragment, ocultar el botón de retroceso
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                }
            }
        });
    }

    private boolean onMenuSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.item_captured) {
            navController.navigate(R.id.capturedFragment);
            return true;
        } else if (id == R.id.item_pokedex) {
            navController.navigate(R.id.pokedexFragment);
            return true;
        } else if (id == R.id.item_settings) {
            navController.navigate(R.id.settingsFragment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Verifica si el botón "Up" (retroceso) fue presionado
        if (item.getItemId() == android.R.id.home) {
            // Utiliza el NavController para manejar la acción de retroceso
            return navController.navigateUp();  // Este método manejará la navegación hacia atrás
        }
        return super.onOptionsItemSelected(item);
    }

}