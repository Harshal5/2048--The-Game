package com.example.first;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/* Sets layout content to how to play */
public class HowToPlay extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_play);
    }

    /* Return to home page on backpress*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        HowToPlay.this.finish();
    }

    /* Link to the info page */
    public void moreInfo(View v){
        String url = "https://levelskip.com/puzzle/How-to-play-2048";
        Uri UriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, UriUrl);
        startActivity(launchBrowser);

    }
}
