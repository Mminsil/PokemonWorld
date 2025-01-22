package mincarelli.silvero.mypokemonsworld;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adaptador para el RecyclerView que muestra la lista de Pokémon en la Pokedex.
 * Utiliza un ViewHolder para representar cada ítem individual en la lista.
 */
public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder> {

    private Context context;
    private List<PokemonDetails> pokemonDetailsList;
    private OnPokemonClickListener onPokemonClickListener;

    /**
     * Constructor del adaptador.
     *
     * @param context            El contexto de la actividad o fragmento que utiliza este adaptador.
     * @param pokemonDetailsList La lista de objetos `PokemonDetails` que se mostrarán en la Pokedex.
     * @param listener           El listener que manejará el clic sobre un Pokémon.
     */
    public PokedexAdapter(Context context, List<PokemonDetails> pokemonDetailsList, OnPokemonClickListener listener) {
        this.context = context;
        this.pokemonDetailsList = pokemonDetailsList;
        this.onPokemonClickListener = listener;
    }

    /**
     * Interfaz para manejar los clics sobre los elementos de la lista de Pokémon.
     */
    public interface OnPokemonClickListener {
        void onPokemonClick(PokemonDetails pokemonDetails);
    }

    /**
     * Infla el layout del ítem individual para cada Pokémon.
     * Este método es llamado cuando se crea un nuevo ViewHolder.
     *
     * @param parent   El ViewGroup al cual se añadirá la vista inflada.
     * @param viewType El tipo de vista del nuevo elemento.
     * @return El nuevo ViewHolder con la vista inflada.
     */
    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_pokedex_item, parent, false);
        return new PokemonViewHolder(view);
    }

    /**
     * Actualiza la vista del ítem con los datos correspondientes, como el nombre, tipo, peso, altura y la imagen del Pokémon.
     * Este método es llamado cuando se enlazan los datos con un ViewHolder.
     *
     * @param holder   El ViewHolder que debe ser actualizado con los datos del Pokémon.
     * @param position La posición del ítem en la lista de datos.
     */
    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        PokemonDetails details = pokemonDetailsList.get(position);

        holder.nameTextView.setText(details.getForms().get(0).getName());
        holder.typeTextView.setText(details.getTypes().get(0).getType().getName());
        Picasso.get().load(details.getSprites().getOther().getHome().getFrontDefault()).into(holder.photoImageView);

        if (details.isCaptured()) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.captured_background));
            holder.itemView.setEnabled(false);
            holder.capturedTextView.setVisibility(View.VISIBLE);
            holder.capturedTextView.setText(R.string.captured_text);
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.uncaptured_background));
            holder.itemView.setEnabled(true);
        }

        // Configurar click listener
        holder.itemView.setOnClickListener(v -> {
            if (onPokemonClickListener != null) {
                onPokemonClickListener.onPokemonClick(details);
            }
        });

    }

    /**
     * Devuelve el número de elementos en la lista de datos.
     *
     * @return El tamaño de la lista de Pokémon.
     */
    @Override
    public int getItemCount() {
        return pokemonDetailsList.size();
    }

    /**
     * Clase ViewHolder para los ítems de la Pokedex. Guarda las vistas de cada ítem para optimizar el rendimiento.
     */
    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, typeTextView, capturedTextView;
        ImageView photoImageView;
        CardView cardView;

        /**
         * Constructor para el ViewHolder, inicializa las vistas que se utilizarán en cada ítem de la lista.
         *
         * @param itemView La vista del ítem individual que representa un Pokémon.
         */
        public PokemonViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pokemon_name);
            typeTextView = itemView.findViewById(R.id.pokemon_type);
            photoImageView = itemView.findViewById(R.id.pokemon_image);
            capturedTextView = itemView.findViewById(R.id.pokemon_captured);
            cardView = itemView.findViewById(R.id.card_pokedex_item);
        }
    }
}
