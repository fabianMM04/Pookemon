package com.example.labsoftware01.pookemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListPokemonActivity extends AppCompatActivity {

    JSONObject jsonObjectPoke;
    JSONArray jsonArraypoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pokemon);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerPoke);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, "http://pokeapi.co/api/v2/pokemon/?limit=100", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    jsonObjectPoke = new JSONObject(response);
                    jsonArraypoke = jsonObjectPoke.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(new Adapter());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }


    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.thumbnailPok);
                textView = (TextView) itemView.findViewById(R.id.name_pokemon);
            }
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pokemon, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


            Glide.with(ListPokemonActivity.this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/"
                    + Integer.toString(position + 1) + ".png").into(holder.imageView);

            try {
                holder.textView.setText(jsonArraypoke.getJSONObject(position).getString("name").toUpperCase());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListPokemonActivity.this, MainActivity.class);
                    intent.putExtra("pokemon", Integer.toString(position+1));
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return jsonArraypoke.length();
        }
    }
}
