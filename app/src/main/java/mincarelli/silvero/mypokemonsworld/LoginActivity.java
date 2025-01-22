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
 * Actividad de inicio de sesión donde el usuario puede autenticarte utilizando correo electrónico o Google.
 * Esta actividad utiliza Firebase Authentication con AuthUI para permitir el inicio de sesión.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Se llama cuando se crea la actividad. Este método invoca la superclase
     * y configura el estado persistente si es necesario.
     *
     * @param savedInstanceState Estado guardado de la actividad si está presente.
     * @param persistentState Estado persistente (opcional) de la actividad.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * Inicia el proceso de inicio de sesión. Se configuran los proveedores de autenticación disponibles
     * (correo electrónico y Google) y se lanza el flujo de autenticación.
     */
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
    /**
     * Lanzador de actividad para manejar el resultado de la autenticación.
     * Después de completar el proceso de inicio de sesión, se llama al callback onActivityResult.
     */
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    /**
     * Maneja el resultado de la autenticación.
     * Si la autenticación es exitosa, se redirige al usuario a la actividad principal.
     * Si la autenticación falla, se muestra un mensaje de error.
     *
     * @param result Resultado de la autenticación.
     */
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
     * Comprueba si hay un usuario autenticado al iniciar la actividad.
     * Si hay sesión activa, redirige directamente a la actividad principal.
     * Si no hay sesión activa, inicia el proceso de inicio de sesión.
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

    /**
     * Redirige al usuario a la actividad principal (MainActivity).
     * También finaliza la actividad de inicio de sesión para evitar que el usuario vuelva atrás.
     */
    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}