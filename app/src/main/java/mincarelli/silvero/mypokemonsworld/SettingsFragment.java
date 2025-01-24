package mincarelli.silvero.mypokemonsworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.util.Locale;

import mincarelli.silvero.mypokemonsworld.databinding.FragmentSettingsBinding;

/**
 * Clase SettingsFragment que extiende de Fragment.
 * Representa un fragmento para gestionar la configuración de la aplicación, incluyendo:
 * - Cierre de sesión.
 * - Selección de idioma.
 * - Activación de una funcionalidad de eliminación.
 * - Información sobre la aplicación.
 */
public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;

    /**
     * Método que crea y configura la vista del fragmento.
     * Se inicializan los elementos de la interfaz y sus acciones.
     *
     * @param inflater           Inflador para crear la vista.
     * @param container          Contenedor donde se añadirá la vista.
     * @param savedInstanceState Estado guardado anteriormente (si existe).
     * @return La vista raíz del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.logoutButton.setOnClickListener(this::logoutSession);

        // Leer estado del Switch de las preferencias
        SharedPreferences prefs = requireContext().getSharedPreferences("delete_PokemonCaptured", Context.MODE_PRIVATE);
        boolean isDeletionEnabled = prefs.getBoolean("enable_delete", false);
        binding.switchEnableDelete.setChecked(isDeletionEnabled);

        // Configurar Switch para guardar la preferencia
        binding.switchEnableDelete.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("enable_delete", isChecked).apply();
        });

        //Configurar clic del textview About us
        binding.titleAbout.setOnClickListener(this::clicAbout);

        return binding.getRoot();
    }

    /**
     * Método que muestra un diálogo de "Acerca de" cuando se hace clic en el texto correspondiente.
     *
     * @param view Vista que activa el evento.
     */
    private void clicAbout(View view) {
        new About().show(getParentFragmentManager(), "AboutDialog");
    }

    /**
     * Método para cerrar la sesión del usuario actual.
     *
     * @param view Vista que activa el evento.
     */
    private void logoutSession(View view) {
        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(task -> {
                    Toast.makeText(requireContext(), R.string.logout_text, Toast.LENGTH_SHORT).show();
                    goToLogin();
                });
    }

    /**
     * Navega hacia la actividad de inicio de sesión después de cerrar sesión.
     */
    private void goToLogin() {
        Intent i = new Intent(requireContext(), LoginActivity.class);
        startActivity(i);
        requireActivity().finish();
    }

    /**
     * Método llamado después de que se ha creado la vista.
     * Configura la selección inicial de idioma y establece los eventos del RadioGroup de idiomas.
     *
     * @param view               Vista creada.
     * @param savedInstanceState Estado guardado anteriormente (si existe).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inicializamos el estado del RadioGroup según el idioma actual
        initializeLanguageSelection();
        binding.languageRadioGroup.setOnCheckedChangeListener(this::onLanguageSelected);
    }

    /**
     * Inicializa la selección de idioma del RadioGroup en función del idioma actual guardado.
     */
    private void initializeLanguageSelection() {
        // Temporarily remove the listener to avoid triggering events
        binding.languageRadioGroup.setOnCheckedChangeListener(null);

        // Obtenemos el idioma actual
        String currentLanguage = getCurrentLanguage();

        if ("en".equals(currentLanguage)) {
            binding.languageRadioGroup.check(R.id.englishRadioButton);
        } else if ("es".equals(currentLanguage)) {
            binding.languageRadioGroup.check(R.id.spanishRadioButton);
        }

        // Reattach the listener
        binding.languageRadioGroup.setOnCheckedChangeListener(this::onLanguageSelected);
    }

    /**
     * Obtiene el idioma actual de las preferencias de la aplicación.
     *
     * @return El código del idioma actual (por defecto "en").
     */
    private String getCurrentLanguage() {
        SharedPreferences prefs = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        String language = prefs.getString("app_language", "en"); // Default is "en"
        Log.d("SettingsFragment", "Current saved language: " + language);
        return language;
    }

    /**
     * Método llamado cuando se selecciona un idioma en el RadioGroup.
     * Cambia el idioma de la aplicación según la selección.
     *
     * @param radioGroup Grupo de botones de radio.
     * @param checkedId  ID del botón seleccionado.
     */
    private void onLanguageSelected(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.englishRadioButton) {
            changeLanguage("en");
        } else if (checkedId == R.id.spanishRadioButton) {
            changeLanguage("es");
        }
    }

    /**
     * Guarda el idioma actual en las preferencias de la aplicación.
     *
     * @param languageCode Código del idioma a guardar.
     */
    private void saveCurrentLanguage(String languageCode) {
        SharedPreferences prefs = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        prefs.edit().putString("app_language", languageCode).apply();
    }

    /**
     * Cambia el idioma de la aplicación.
     *
     * @param codeLanguage Código del idioma (ejemplo: "en" para inglés, "es" para español).
     */
    private void changeLanguage(String codeLanguage) {
        // Cambiar el idioma de la app usando la configuración de la región
        Locale locale = new Locale(codeLanguage);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        // Actualiza la vista con el idioma seleccionado
        updateLanguageView();

        // Guarda el idioma seleccionado
        saveCurrentLanguage(codeLanguage);

        // Notificar a MainActivity para actualizar el menú
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).applySavedLanguage();
        }
    }

    /**
     * Actualiza los textos visibles en la interfaz según el idioma seleccionado.
     */
    private void updateLanguageView() {
        binding.languageTextView.setText(R.string.language);
        binding.titleLanguageTextView.setText(R.string.titleLanguage);
        binding.englishRadioButton.setText(R.string.english);
        binding.spanishRadioButton.setText(R.string.spanish);
        binding.titleAbout.setText(R.string.about);
        binding.titleEnableDelete.setText(R.string.delete);
        binding.switchEnableDelete.setText(R.string.enable_deletion);
        binding.logoutButton.setText(R.string.logout);
    }
}