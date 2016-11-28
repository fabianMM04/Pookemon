package com.example.labsoftware01.pookemon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {
    private ImageView img1;
    private ImageView img2;
    private TextView vida_amigo;
    private TextView vida_enemigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        Glide.with(this).load("http://pokeapi.co/media/sprites/pokemon/back/1.png").into(img1);
        Glide.with(this).load("http://pokeapi.co/media/sprites/pokemon/back/4.png").into(img2);




    }
}
