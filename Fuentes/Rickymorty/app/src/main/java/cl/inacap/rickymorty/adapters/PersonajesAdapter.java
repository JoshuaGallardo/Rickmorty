package cl.inacap.rickymorty.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.rickymorty.R;
import cl.inacap.rickymorty.dto.Personaje;

public class PersonajesAdapter extends ArrayAdapter<Personaje> {
    private List<Personaje> personajes;
    private Activity activity;
    public PersonajesAdapter(@NonNull Activity context, int resource, @NonNull List<Personaje> objects) {
        super(context, resource, objects);
        this.personajes = objects;
        this.activity = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_personajes, null, true);
        TextView nombreTxt = rowView.findViewById(R.id.nombre_txt);
        ImageView imagePer = rowView.findViewById(R.id.image_per);
        nombreTxt.setText(personajes.get(position).getName());
        Picasso.get().load(this.personajes.get(position).getImage())
            .resize(300,300)
            .centerCrop()
            .into(imagePer);
        return rowView;
    }
}
