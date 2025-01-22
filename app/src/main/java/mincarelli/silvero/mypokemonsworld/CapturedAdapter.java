package mincarelli.silvero.mypokemonsworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para mostrar una lista de Pokémon capturados en un RecyclerView.
 * Cada elemento de la lista representa un Pokémon capturado con información como su nombre, tipo y una imagen.
 */
public class CapturedAdapter extends RecyclerView.Adapter<CapturedAdapter.CapturedViewHolder> {
    private Context context;
    private List<CapturedPokemon> pokemonCapturedList = new ArrayList<>();
    // Interfaz para manejar clics en los elementos de la lista
    private CapturedOnItemClickListener listener;

    /**
     * Constructor para el adaptador.
     *
     * @param context             El contexto en el que se ejecuta el adaptador.
     * @param pokemonCapturedList Lista de Pokémon capturados que se van a mostrar.
     */
    public CapturedAdapter(Context context, List<CapturedPokemon> pokemonCapturedList) {
        this.context = context;
        this.pokemonCapturedList = pokemonCapturedList;
    }

    /**
     * Interfaz para escuchar eventos de clic en los elementos de la lista.
     */
    public interface CapturedOnItemClickListener {
        /**
         * Método que se ejecuta cuando se hace clic en un elemento de la lista.
         *
         * @param pokemon El objeto CapturedPokemon asociado con el elemento clicado.
         * @param view    La vista sobre la que se hizo clic.
         */
        void onItemClick(CapturedPokemon pokemon, View view);
    }

    /**
     * Establece el escuchador para los clics en los elementos de la lista.
     *
     * @param listener El escuchador de clics.
     */
    public void setOnItemClickListener(CapturedOnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Crea un nuevo ViewHolder para un ítem del RecyclerView.
     *
     * @param parent   El ViewGroup donde se agregará el ViewHolder.
     * @param viewType El tipo de vista del ítem (no se usa en este caso).
     * @return Un nuevo ViewHolder con la vista inflada.
     */
    @NonNull
    @Override
    public CapturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_captured_item, parent, false);
        return new CapturedViewHolder(view);
    }

    /**
     * Vincula los datos de un Pokémon a la vista en el ViewHolder.
     *
     * @param holder   El ViewHolder donde se colocarán los datos.
     * @param position La posición del ítem en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull CapturedViewHolder holder, int position) {
        CapturedPokemon details = pokemonCapturedList.get(position);

        holder.bind(details);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(details, view);
            }
        });
    }

    /**
     * Devuelve la cantidad de elementos en la lista de Pokémon capturados.
     *
     * @return El tamaño de la lista.
     */
    @Override
    public int getItemCount() {
        return pokemonCapturedList.size();
    }

    /**
     * ViewHolder personalizado que maneja la vista de cada ítem en la lista.
     */
    public static class CapturedViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, typeTextView, weightTextView, heightTextView, indexTextView;
        ImageView photoImageView, trashIcon;

        /**
         * Constructor del ViewHolder que inicializa las vistas.
         *
         * @param itemView La vista que representa un ítem en la lista.
         */
        public CapturedViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.captured_name);
            indexTextView = itemView.findViewById(R.id.captured_index);
            typeTextView = itemView.findViewById(R.id.captured_type);
            weightTextView = itemView.findViewById(R.id.captured_weight);
            heightTextView = itemView.findViewById(R.id.captured_height);
            photoImageView = itemView.findViewById(R.id.captured_image);
        }

        /**
         * Vincula los datos de un Pokémon a las vistas del ViewHolder.
         *
         * @param pokemon El Pokémon cuyos datos se van a mostrar.
         */
        public void bind(CapturedPokemon pokemon) {
            nameTextView.setText(pokemon.getName());
            typeTextView.setText(pokemon.getType());
            Picasso.get().load(pokemon.getImageUrl()).into(photoImageView);
        }
    }

}

