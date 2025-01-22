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

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.util.Locale;

import mincarelli.silvero.mypokemonsworld.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container,false);

        binding.logoutButton.setOnClickListener(this::logoutSession);

        // Leer estado del Switch de las preferencias
        SharedPreferences prefs = requireContext().getSharedPreferences("delete_PokemonCaptured", Context.MODE_PRIVATE);
        boolean isDeletionEnabled = prefs.getBoolean("enable_delete", false);
        binding.switchEnableDelete.setChecked(isDeletionEnabled);

        // Configurar Switch para guardar la preferencia
        binding.switchEnableDelete.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("enable_delete", isChecked).apply();
        });

        binding.image.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.poke));

        //Configurar clic del textview About us
        binding.titleAbout.setOnClickListener(this::clicAbout);

        return binding.getRoot();
    }

    private void clicAbout(View view) {
        new About().show(getParentFragmentManager(), "AboutDialog");
    }

    private void logoutSession(View view) {
        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(task -> {
                    Toast.makeText(requireContext(), "Se ha cerrado sesión correctamente", Toast.LENGTH_SHORT).show();
                    goToLogin();
                });

    }

    private void goToLogin() {
        Intent i = new Intent(requireContext(), LoginActivity.class);
        startActivity(i);
        requireActivity().finish();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inicializamos el estado del RadioGroup según el idioma actual
        initializeLanguageSelection();
        binding.languageRadioGroup.setOnCheckedChangeListener(this::onLanguageSelected);
    }

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

    private String getCurrentLanguage() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String language = prefs.getString("app_language", "en"); // Default is "en"
        Log.d("SettingsFragment", "Current saved language: " + language);
        return language;
    }

    private void onLanguageSelected(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.englishRadioButton) {
            changeLanguage("en");
        } else if (checkedId == R.id.spanishRadioButton) {
            changeLanguage("es");
        }
    }

    private void saveCurrentLanguage(String languageCode) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        prefs.edit().putString("app_language", languageCode).apply();
    }

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


        // Invalida el menú para aplicar el cambio de idioma
        invalidateOptionsMenu();
    }

    /**
     * Updates the text displayed in the view based on the selected language.
     * This method should be called after the language has been changed.
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

    /**
     * Invalidates the options menu to ensure the changes are reflected.
     */
    private void invalidateOptionsMenu() {
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
    }
}