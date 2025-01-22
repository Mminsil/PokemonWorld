package mincarelli.silvero.mypokemonsworld;

import android.content.Context;
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
 * Es un adaptador de RecyclerView que adapta una lista de objetos PokemonDetails para que se muestren
 * correctamente en la interfaz de usuario.
 * Este adaptador se asocia a un RecyclerView en la actividad principal para mostrar los datos de
 * los Pokémon en una lista.
 */
public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder> {

    private Context context;
    private List<PokemonDetails> pokemonDetailsList;
    private OnPokemonClickListener onPokemonClickListener;

    public PokedexAdapter(Context context, List<PokemonDetails> pokemonDetailsList,  OnPokemonClickListener listener) {
        this.context = context;
        this.pokemonDetailsList = pokemonDetailsList;
        this.onPokemonClickListener = listener;
    }

    public interface OnPokemonClickListener {
        void onPokemonClick(PokemonDetails pokemonDetails);
    }
    /**
     * Infla el layout del ítem individual para cada Pokémon.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_pokedex_item, parent, false);
        return new PokemonViewHolder(view);
    }

    /**
     * Actualiza la vista del ítem con los datos correspondientes, como el nombre, tipo, peso, altura y la imagen del Pokémon.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
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

    @Override
    public int getItemCount() {
        return pokemonDetailsList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, typeTextView;
        ImageView photoImageView;
        CardView cardView;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pokemon_name);
            typeTextView = itemView.findViewById(R.id.pokemon_type);
            photoImageView = itemView.findViewById(R.id.pokemon_image);
            cardView = itemView.findViewById(R.id.card_pokedex_item);
        }

    }
}
