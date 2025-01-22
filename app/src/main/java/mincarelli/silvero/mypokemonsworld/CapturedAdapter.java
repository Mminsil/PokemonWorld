package mincarelli.silvero.mypokemonsworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CapturedAdapter  extends RecyclerView.Adapter<CapturedAdapter.CapturedViewHolder> {
    private Context context;
    private List<CapturedPokemon> pokemonCapturedList = new ArrayList<>();
    private CapturedOnItemClickListener listener;

    public CapturedAdapter(Context context, List<CapturedPokemon> pokemonCapturedList) {
        this.context = context;
        this.pokemonCapturedList = pokemonCapturedList;
    }

    public interface CapturedOnItemClickListener {
        void onItemClick(CapturedPokemon pokemon, View view);
    }
    public void setOnItemClickListener(CapturedOnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public CapturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_captured_item, parent, false);
        return new CapturedViewHolder(view);
    }

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



    @Override
    public int getItemCount() {
        return pokemonCapturedList.size();
    }

    public void removePokemon(int position) {
        pokemonCapturedList.remove(position);
        notifyItemRemoved(position);
    }

    public static class CapturedViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, typeTextView, weightTextView, heightTextView, indexTextView;
        ImageView photoImageView, trashIcon;

        public CapturedViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.captured_name);
            indexTextView = itemView.findViewById(R.id.captured_index);
            typeTextView = itemView.findViewById(R.id.captured_type);
            weightTextView = itemView.findViewById(R.id.captured_weight);
            heightTextView = itemView.findViewById(R.id.captured_height);
            photoImageView = itemView.findViewById(R.id.captured_image);
            trashIcon = itemView.findViewById(R.id.trash_icon);
        }

        public void bind(CapturedPokemon pokemon) {
            nameTextView.setText(pokemon.getName());
            indexTextView.setText(String.valueOf(pokemon.getIndex()));
            typeTextView.setText(pokemon.getType());
            weightTextView.setText(String.format("%.1f kg", pokemon.getWeight()));
            heightTextView.setText(String.format("%.1f m", pokemon.getHeight()));
            Picasso.get().load(pokemon.getImageUrl()).into(photoImageView);
        }
    }

}

