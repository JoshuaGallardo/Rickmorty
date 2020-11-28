package cl.inacap.rickymorty.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.inacap.rickymorty.R;
import cl.inacap.rickymorty.adapters.PersonajesAdapter;
import cl.inacap.rickymorty.dto.Personaje;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonajesFragment#} factory method to
 * create an instance of this fragment.
 */
public class PersonajesFragment extends Fragment {

    private List<Personaje> personajes = new ArrayList<>();
    private ListView personajesList;
    private PersonajesAdapter personajesAdapter;
    private RequestQueue queue;


    public PersonajesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        queue = Volley.newRequestQueue(this.getActivity());
        this.personajesList = getView().findViewById(R.id.personajes_list_view);
        this.personajesAdapter = new PersonajesAdapter(this.getActivity()
            ,R.layout.list_personajes,this.personajes);
        this.personajesList.setAdapter(this.personajesAdapter);
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, "https://rickandmortyapi.com/api/characters"
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Esto se ejecuta cuando el servidor responda
                try {
                    personajes.clear();
                    Personaje[] arreglo = new Gson()
                            .fromJson(response.getString("results")
                            , Personaje[].class);
                    personajes.addAll(Arrays.asList(arreglo));
                }catch (Exception ex) {
                    personajes.clear();
                    Log.e("PERSONAJES_FRAGMENT", "Error de peticion");
                }finally {
                    personajesAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Esto se ejecuta cuando haya un error en la peticion
                //no pueda acceder al servidor, o el servidor no devuelve Json
                personajes.clear();
                Log.e("PERSONAJES_FRAGMENT", "Error de respuesta");
                personajesAdapter.notifyDataSetChanged();
            }
        });
        queue.add(jsonReq);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personajes, container, false);
    }
}