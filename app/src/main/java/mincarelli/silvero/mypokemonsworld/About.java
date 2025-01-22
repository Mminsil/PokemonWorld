package mincarelli.silvero.mypokemonsworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Clase que representa un fragmento de diálogo mostrando información sobre la aplicación.
 * Este diálogo contiene un título, un mensaje con información de la aplicación y un botón de aceptación.
 */
public class About extends DialogFragment {
    /**
     * Crea y devuelve el diálogo con el contenido de la clase About.
     *
     * @param savedInstanceState Estado guardado del fragmento, si existe.
     * @return El diálogo configurado con el título, mensaje y botón.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.about)
                .setMessage(R.string.about_text)
                .setPositiveButton(R.string.accept, (dialog, which) -> dialog.dismiss())
                .create();
    }
}
