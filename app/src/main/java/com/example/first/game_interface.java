package com.example.first;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Random;

public class game_interface extends AppCompatActivity {

    /* Declarations */
    int score, highScore = 0, n = 4;//Permissible values of n are 3 and 4 for more textViews need to be changed
    TextView[][] textView_array = new TextView[n][n];
    int[][] int_array = new int[n][n];
    SharedPreferences highScore_save;
    String highScore_display;
    String[][] string_array = new String[n][n];
    boolean change =false , win = false;
    boolean[][] mergeChecker = new boolean[n][n];
    float x1,x2,y1,y2;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game2048);

        /* Adds Music to game */
        mediaPlayer = MediaPlayer.create(this, R.raw.summer);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        int i, j;

        /* Sets all textViews */
        setTextView();

        /* Initializing shared preference*/
        highScore_save = this.getSharedPreferences("HighScoreKey", MODE_PRIVATE);
        highScore = highScore_save.getInt("highScoreKey", 0);
        getHighScore();

        /*Initializing array elements to zero*/
        for (i = 0; i <= 2; i++) {
            for (j = 0; j <= 2; j++) {
                int_array[i][j] = 0;
            }
        }

        /* Randomly setting two tiles to 2 */
        setRandom();
        setRandom();
        print();
        colorChanger();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    /* Actions to be performed on clicking up button*/
    public void up() {
        int i, j , k;
        for (j = 0; j <= n - 1; j++) {
            for (i = 0; i < n - 1; i++) {
                for(k = 0 ; k < n-1 ; k++){
                   if(int_array[k][j] == 0 && int_array[k + 1][j] != 0){
                       int_array[k][j] = int_array[k + 1][j];
                       int_array[k + 1][j] = 0;
                       change = true;
                   } else if (int_array[k][j] == int_array[k + 1][j] && int_array[k+1][j] != 0 && !mergeChecker[k][j] && !mergeChecker[k+1][j]){
                       int_array[k][j] *= 2;
                       getScore(k, j);
                       int_array[k + 1][j] =0;
                       mergeChecker[k][j] = true;
                       change =true;
                    }
                }
            }
        }

        setMergeChecker();
        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();
        colorChanger();
    }

    /* Actions to be performed on clicking down button*/
    public void down() {

        int i, j , k;
        for (j = 0; j <= n - 1; j++) {
            for (i = 0; i < n - 1; i++) {
                for(k = n-1 ; k > 0 ; k--){
                    if(int_array[k][j] == 0 && int_array[k - 1][j] != 0){
                        int_array[k][j] = int_array[k - 1][j];
                        int_array[k - 1][j] = 0;
                        change = true;
                    } else if (int_array[k][j] == int_array[k - 1][j] && int_array[k-1][j] != 0 && !mergeChecker[k][j] && !mergeChecker[k-1][j]){
                        int_array[k][j] *= 2;
                        getScore(k, j);
                        int_array[k - 1][j] =0;
                        mergeChecker[k][j] = true;
                        change =true;
                    }
                }
            }
        }
        setMergeChecker();
        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();
        colorChanger();
    }

    /* Actions to be performed on clicking left button*/
    public void left() {
        int i, j ,k;
        for (i = 0; i <= n - 1; i++) {
            for (j = 0; j < n - 1; j++) {
                for(k = 0 ; k < n-1 ; k++){
                    if(int_array[i][k] == 0 && int_array[i][k +1] != 0){
                        int_array[i][k] = int_array[i][k + 1];
                        int_array[i][k + 1] = 0;
                        change = true;
                    } else if (int_array[i][k] == int_array[i][k + 1] && int_array[i][k + 1] != 0 && !mergeChecker[i][k] && !mergeChecker[i][k + 1]){
                        int_array[i][k] *= 2;
                        getScore(i, k);
                        int_array[i][k + 1] =0;
                        mergeChecker[i][k] = true;
                        change =true;
                    }
                }
            }
        }
        setMergeChecker();
        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();
        colorChanger();
    }

    /* Actions to be performed on clicking right button*/
    public void right() {
        int i, j ,k;
        for (i = n - 1; i >= 0; i--) {
            for (j = n - 1; j > 0; j--) {
                for(k = n - 1 ; k > 0 ; k--){
                    if(int_array[i][k] == 0 && int_array[i][k - 1] != 0){
                        int_array[i][k] = int_array[i][k - 1];
                        int_array[i][k - 1] = 0;
                        change = true;
                    } else if (int_array[i][k] == int_array[i][k - 1] && int_array[i][k - 1] != 0 && !mergeChecker[i][k] && !mergeChecker[i][k - 1]){
                        int_array[i][k] *= 2;
                        getScore(i, k);
                        int_array[i][k - 1] =0;
                        mergeChecker[i][k] = true;
                        change =true;
                    }
                }
            }
        }

        setMergeChecker();
        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();
        colorChanger();
    }

    /* Puts 2 in random empty box */
    public void setRandom() {
            Random x = new Random();
            Random y = new Random();
            int r, s;
            do {
                r = x.nextInt(n);
                s = y.nextInt(n);
            } while (int_array[r][s] != 0);
            int_array[r][s] = 2;
            print();
    }

    /*  Updates Score*/
    public void getScore(int i, int j) {
        score += int_array[i][j];
        TextView s = findViewById(R.id.score);
        String s1 = Integer.toString(score);
        s.setText(s1);
        setHighScore();
        getHighScore();
    }

    /* If score is greater than highScore set it as HigScore and save preference*/
    public void setHighScore() {
        if (score > highScore) {
            highScore = score;
            highScore_save.edit().putInt("highScoreKey", highScore).apply();
        }
    }

    /*Display HighScore on Screen*/
    public void getHighScore() {
        highScore_display = Integer.toString(highScore);
        TextView streakCount = findViewById(R.id.highScore);
        streakCount.setText(highScore_display);
    }

    /* Set all the textViews to respective array elements */
    /*Needs to be modified for switching from 3x3 to 4x4 dynamically */
    public void setTextView() {

        if(n >= 3) {
            textView_array[0][0] = findViewById(R.id.textView00);
            textView_array[0][1] = findViewById(R.id.textView01);
            textView_array[0][2] = findViewById(R.id.textView02);

            textView_array[1][0] = findViewById(R.id.textView10);
            textView_array[1][1] = findViewById(R.id.textView11);
            textView_array[1][2] = findViewById(R.id.textView12);

            textView_array[2][0] = findViewById(R.id.textView20);
            textView_array[2][1] = findViewById(R.id.textView21);
            textView_array[2][2] = findViewById(R.id.textView22);
        }
        if(n > 3){
            textView_array[0][3] = findViewById(R.id.textView03);
            textView_array[1][3] = findViewById(R.id.textView13);
            textView_array[2][3] = findViewById(R.id.textView23);

            textView_array[3][0] = findViewById(R.id.textView30);
            textView_array[3][1] = findViewById(R.id.textView31);
            textView_array[3][2] = findViewById(R.id.textView32);
            textView_array[3][3] = findViewById(R.id.textView33);

        }
    }

    /*Checks if there are any possible moves */
    public void checkGameOver() {
        if (!checkAdjacentVertical() && !checkAdjacentHorizontal() &&
                !checkEmptyBoxes()) {
                showDialogBox();
        }
    }

    /* Checks for possible Vertical moves */
    public boolean checkAdjacentVertical() {
        int i, j;
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < n; j++) {
                if (int_array[i][j] == int_array[i + 1][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Checks for possible Horizontal moves */
    public boolean checkAdjacentHorizontal() {
            int i, j;
            for (i = 0; i < n ; i++) {
                for (j = 0; j < n - 1; j++) {
                    if (int_array[i][j] == int_array[i][j + 1]) {
                        return true;
                    }
                }
            }
            return false;
    }

    /* return true if any block is 0 */
    public boolean checkEmptyBoxes() {
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (int_array[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Prints all values to textView */
    public void print() {
        int i, j;
        for (i = n - 1; i >= 0; i--) {
            for (j = 0; j < n; j++) {
                string_array[i][j] = Integer.toString(int_array[i][j]);
                if (!string_array[i][j].equals("0")) {
                    textView_array[i][j].setText(string_array[i][j]);
                }
                else {
                    textView_array[i][j].setText(" ");
                }
            }
        }
    }

    /* If tiles position is changed and there exists an empty block then calls random function*/
    public void enterRandomCheckEmpty() {
            if (change && checkEmptyBoxes()) {
                setRandom();
                change = false;
            }
        }

    /* Gestures detection allowed */
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if(Math.abs(x2-x1)>Math.abs(y2-y1)) {
                    //Horizontal
                    if(x2>x1) {
                        //Right
                        right();
                    } else {
                        //Left
                        left();
                    }
                } else {
                    //Vertical
                    if(y2>y1) {
                        //down
                        down();
                    } else {
                        //up
                        up();
                    }
                }
                break;
        }
        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();
        return super.onTouchEvent(event);
    }

    /* Dialog Box for game over condition*/
    public void showDialogBox(){
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setPositiveButton("Restart ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(game_interface.this, game_interface.class));
                        finish();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(game_interface.this , MainActivity.class));
                        finish();
                    }

                })
                .show();
    }

    /* Set merge Checker off */
    public void setMergeChecker(){
        int i,j;
        for(i = 0; i < n; i++){
            for(j = 0 ; j < n ;j++){
                mergeChecker[i][j] = false;
            }
        }
    }

    /* Change Tile color on different values*/
    public void colorChanger() {
        int i, j;
        for (i = 0; i < n ; i++) {
            for (j = 0; j < n ; j++) {
                if(int_array[i][j] == 0){
                    textView_array[i][j].setBackgroundResource(R.color.tile0);
                }
                else if (int_array[i][j] == 2) {
                    textView_array[i][j].setBackgroundResource(R.color.tile2);
                }
                else if(int_array[i][j] == 4){
                    textView_array[i][j].setBackgroundResource(R.color.tile4);
                }
                else if(int_array[i][j] == 8){
                    textView_array[i][j].setBackgroundResource(R.color.tile8);
                }
                else if (int_array[i][j] == 16){
                    textView_array[i][j].setBackgroundResource(R.color.tile16);
                }
                else if (int_array[i][j] == 32){
                    textView_array[i][j].setBackgroundResource(R.color.tile32);
                }
                else if (int_array[i][j] == 64){
                    textView_array[i][j].setBackgroundResource(R.color.tile64);
                }
                else if (int_array[i][j] == 128){
                    textView_array[i][j].setBackgroundResource(R.color.tile128);
                }
                else if (int_array[i][j] == 256){
                    textView_array[i][j].setBackgroundResource(R.color.tile256);
                }
                else if (int_array[i][j] == 512){
                    textView_array[i][j].setBackgroundResource(R.color.tile512);
                }
                else if (int_array[i][j] == 1024){
                    textView_array[i][j].setBackgroundResource(R.color.tile1024);
                }
                else if (int_array[i][j] == 2048){
                    textView_array[i][j].setBackgroundResource(R.color.tile2048);
                    if(!win){
                        new AlertDialog.Builder(this)
                                .setMessage("You Win")
                                .setCancelable(true)
                                .show();
                    }
                    win = true;

                }
                else {
                    textView_array[i][j].setBackgroundResource(R.color.green);
                }
            }
        }
    }

    /* Return to home page on clicking back*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this , MainActivity.class));
        game_interface.this.finish();
    }

}