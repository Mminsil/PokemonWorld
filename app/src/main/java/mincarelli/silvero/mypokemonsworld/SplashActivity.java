package mincarelli.silvero.mypokemonsworld;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that displays a splash screen when the app is launched.
 * The behavior of the splash screen differs based on the Android version:
 * - For devices running versions below Android 12 (API 31), the splash screen is manually handled.
 * - For devices running Android 12 or above, the system automatically handles the splash screen.
 */
public class SplashActivity extends AppCompatActivity {
    // Constant to define the duration of the splash screen display in milliseconds
    private static final int SPLASH_TIME_OUT = 2000;

    /**
     * Called when the activity is first created.
     * Displays the splash screen and starts the MainActivity after a specified delay.
     * The behavior depends on the Android version:
     * - For versions below Android 12 (API 31), the splash screen is manually displayed using a handler.
     * - For Android 12 (API 31) and above, the system handles the splash automatically.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Si la versión de la API es inferior a 31, mostraremos la Splash personalizada
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            // Si la versión es inferior a Android 12 (API 31), manejamos la Splash manualmente
            setContentView(R.layout.splash);
            // Usamos un Handler para retrasar el inicio de la MainActivity
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // Terminamos la SplashActivity para que no vuelva a abrirse
            }, SPLASH_TIME_OUT);
        } else {// Si la versión es 31 o superior, dejamos que Android maneje la Splash automáticamente
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
