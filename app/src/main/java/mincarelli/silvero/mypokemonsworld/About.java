package mincarelli.silvero.mypokemonsworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Class representing a dialog box "Acerca de" using a {@link DialogFragment}.
 * This dialog displays information about the application and includes a button to close it.
 */
public class About extends DialogFragment {
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
