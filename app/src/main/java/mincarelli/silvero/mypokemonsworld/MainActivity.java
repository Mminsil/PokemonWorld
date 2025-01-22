package mincarelli.silvero.mypokemonsworld;

import android.content.Context;
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

/**
 * Actividad principal de la aplicación. Configura la navegación entre los fragmentos mediante un
 * controlador de navegación, un menú inferior y un AppBar (barra de acciones).
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;

    /**
     * Se llama cuando se crea la actividad. Configura la vista, el idioma y la navegación.
     *
     * @param savedInstanceState Estado guardado de la actividad si está presente.
     */
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

        //Marca el item del menú de pokedex
        binding.bottomNavigationView.setOnItemSelectedListener(this::onMenuSelected);

        // Elimina la actionBar para que no aparezca en los fragment
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        });

        initilizeAppBar();

        binding.bottomNavigationView.setSelectedItemId(R.id.item_pokedex);

        setContentView(binding.getRoot());
    }


    /**
     * Aplica el idioma guardado en las preferencias de la aplicación.
     * Cambia la configuración regional de la aplicación para que coincida con el idioma seleccionado.
     */
    public void applySavedLanguage() {
        //Recuperamos el lenguaje guardado
        SharedPreferences prefs = getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        //Cargamos el lenguaje guardado
        String language = prefs.getString("app_language", "en");
        // Cambiar el idioma de la app usando la configuración de la región
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        // Recargar los textos del menú del BottomNavigationView
        updateBottomNavigationMenu();
    }

    /**
     * Actualiza el menú del BottomNavigationView inflando el menú nuevamente.
     */
    private void updateBottomNavigationMenu() {
        // Limpia y vuelve a inflar el menú
        binding.bottomNavigationView.getMenu().clear();
        binding.bottomNavigationView.inflateMenu(R.menu.bottom_menu);
    }

    /**
     * Inicializa la barra de acción (AppBar) para controlar la visibilidad y el comportamiento del botón de retroceso.
     */
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

    /**
     * Maneja la selección de los ítems del menú inferior y navega entre los fragmentos correspondientes.
     *
     * @param menuItem El ítem seleccionado en el menú inferior.
     * @return true si el ítem es reconocido y se maneja la navegación.
     */
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

    /**
     * Maneja la acción del botón de retroceso en la barra de acción.
     * Si se está en un fragmento que permite retroceder, se maneja con el NavController.
     *
     * @param item El ítem seleccionado de las opciones del menú.
     * @return true si se maneja la acción del retroceso.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // NavController maneja la acción de retroceso
            return navController.navigateUp();
        }
        return super.onOptionsItemSelected(item);
    }
}