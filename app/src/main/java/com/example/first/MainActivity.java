package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* Go to Game Page*/
    public void onClick(View page2){
        Intent view = new Intent(this,game_interface.class);
        startActivity(view);
        MainActivity.this.finish();
    }

    /* Exit The App*/
    public void exit(View exit){
        finish();
    }

    /* Opens up how to play */
    public void howToPlay(View howToPlay){
        Intent view = new Intent(this,HowToPlay.class);
        startActivity(view);
        MainActivity.this.finish();
    }
}
