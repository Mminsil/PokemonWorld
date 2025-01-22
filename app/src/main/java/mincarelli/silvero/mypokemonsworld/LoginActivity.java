package mincarelli.silvero.mypokemonsworld;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * Implementará la lógica de la pantalla de login
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    private void startSingIn() {
        // Choose authentication providers
        //Acá le estamos diciendo que queremos una auth por email y por Google
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        //Crea el intent de la auth Al usuario se le abre la pantalla de login
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                //Le pasa los proveedores que queremos de inicio
                .setAvailableProviders(providers)
                .setLogo(R.drawable.logo)      // Set logo drawable
                .setTheme(R.style.Theme_MyPokemonsWorld)      // Set theme
                .build();
        //lanza el singInLauncher cuando el usuario rellena el formulario
        signInLauncher.launch(signInIntent);
        //Este signInLauncher tiene un ActivityResultLauncher que hace que cuando esta función asíncrona
        //se lanza, nos avisa que ha acabado en el activityResultLauncher
    }

    //Este lanzador tiene un activityResultLauncher que nos avisa cuando Firebase acaba la autenticación
    //nos llamará a la función onActivityResult
// See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    //Acá trataremos la auth
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        //si el resultado ha sido OK
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //Lo mandamos a la actividad principal
            goToMainActivity();
        } else {
            //Le ponemos un toast info
            Toast.makeText(this, "Error login", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Si hay usuario lo mandamos a la pantalla principal y sino lo mandamos al login
     */
    @Override
    protected void onStart() {
        super.onStart();
        //Comprobar si hay una sesion abierta
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            goToMainActivity();
        } else {
            startSingIn();
        }

    }

    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}