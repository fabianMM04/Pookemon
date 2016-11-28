package com.example.labsoftware01.pookemon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import uk.co.barbuzz.beerprogressview.BeerProgressView;


public class MainActivity extends AppCompatActivity {
    private ImageView img1;
    private ImageView img2;
    private TextView my_life;
    private TextView machine_life;
    private Button Fight;
    private BeerProgressView beerProgressView1;
    private BeerProgressView beerProgressView2;

    Random r = new Random();
    int my_attack = r.nextInt(101);
    int machine_attack = r.nextInt(101);
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        Fight = (Button) findViewById(R.id.Fight);
        beerProgressView1 = (BeerProgressView) findViewById(R.id.beerProgressView1);
        beerProgressView2 = (BeerProgressView) findViewById(R.id.beerProgressView2);
        beerProgressView2.setBeerProgress(100);
        beerProgressView1.setBeerProgress(100);
        beerProgressView2.setMax(100);
        beerProgressView1.setMax(100);

        bundle = getIntent().getExtras();
        if (bundle != null)
            Glide.with(this).
                    load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/"
                            + bundle.getString("pokemon") + ".png").into(img1);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://pokeapi.co/api/v2/pokemon/" + Integer.toString(my_attack + 1), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                llamarPokemon(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

            Fight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Random rr = new Random();
                    int machine_attack = rr.nextInt(100) + 1;
                    my_attack = rr.nextInt(100) + 1;
                    beerProgressView1.setBeerProgress(beerProgressView1.getBeerProgress() - my_attack);
                    if (beerProgressView1.getBeerProgress() < 1) {
                        Toast.makeText(MainActivity.this, "Haz Ganaooooo..... celebralo tiburon", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    beerProgressView2.setBeerProgress(beerProgressView2.getBeerProgress() - machine_attack);
                    if (beerProgressView2.getBeerProgress() < 1) {
                        Toast.makeText(MainActivity.this, "Te Ganaron jejejeje......", Toast.LENGTH_SHORT).show();
                        return;
                    }


                }
            });
    }

    private void llamarPokemon(String response) {
        JSONObject jsonObject;
        String url = "";
        try {
            jsonObject = new JSONObject(response);
            url = jsonObject.getJSONObject("sprites").getString("front_default");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Glide.with(this).load(url).into(img2);
    }
}