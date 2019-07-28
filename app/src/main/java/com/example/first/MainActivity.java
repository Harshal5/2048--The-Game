package com.example.first;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Declarations */
    TextView[][] textView_array = new TextView[3][3];
    int[][] int_array = new int[3][3];
    String[][] string_array = new String[3][3];
    int score, highScore = 0, n = 3;
    SharedPreferences highScore_save;
    String highScore_display;
    boolean change =false;
    float x1,x2,y1,y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int i, j;
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
    }

    /* Actions to be performed on clicking up button*/
    public void up() {
        int i, j;
        for (j = 0; j <= n - 1; j++) {
            for (i = 0; i < n - 1; i++) {
                if (int_array[i][j] == int_array[i + 1][j] && int_array[i][j] != 0) {
                    int_array[i][j] += int_array[i + 1][j];
                    getScore(i, j);
                    int_array[i + 1][j] = 0;
                    change = true;
                } else if (int_array[i + 1][j] == 0) {
                    if (i + 2 < n && int_array[i][j] == int_array[i + 2][j] && int_array[i][j] != 0) {
                        int_array[i][j] += int_array[i + 2][j];
                        getScore(i, j);
                        int_array[i + 2][j] = 0;
                        change = true;
                    }
                }
            }
            for (i = 0; i < n - 1; i++) {
                if (int_array[i][j] == 0 && int_array[i + 1][j] == 0 && i + 2 < n && int_array[i + 2][j] != 0) {
                    int_array[i][j] += int_array[i + 2][j];
                    int_array[i + 2][j] = 0;
                    change = true;
                } else if (int_array[i][j] == 0 && int_array[i+1][j] != 0) {
                    int_array[i][j] += int_array[i + 1][j];
                    int_array[i + 1][j] = 0;
                    change = true;
                }
            }
        }

        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();

    }

    /* Actions to be performed on clicking down button*/
    public void down() {
        int i, j;
        for (j = n - 1; j >= 0; j--) {
            for (i = n - 1; i > 0; i--) {
                if (int_array[i][j] == int_array[i - 1][j] && int_array[i-1][j] != 0) {
                    int_array[i][j] += int_array[i - 1][j];
                    getScore(i, j);
                    int_array[i - 1][j] = 0;
                    change = true;
                } else if (int_array[i - 1][j] == 0) {
                    if (i - 2 >= 0 && int_array[i][j] == int_array[i - 2][j] && int_array[i-2][j] != 0) {
                        int_array[i][j] += int_array[i - 2][j];
                        getScore(i, j);
                        int_array[i - 2][j] = 0;
                        change = true;
                    }
                }
            }
            for (i = n - 1; i > 0; i--) {
                if (int_array[i][j] == 0 && int_array[i - 1][j] == 0 && i - 2 >= 0 && int_array[i-2][j] != 0) {
                    int_array[i][j] += int_array[i - 2][j];
                    int_array[i - 2][j] = 0;
                    change = true;
                } else if (int_array[i][j] == 0 && int_array[i-1][j] != 0) {
                    int_array[i][j] += int_array[i - 1][j];
                    int_array[i - 1][j] = 0;
                    change = true;
                }
            }
        }

        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();
    }

    /* Actions to be performed on clicking left button*/
    public void left() {
        int i, j;
        for (i = 0; i <= n - 1; i++) {
            for (j = 0; j < n - 1; j++) {
                if (int_array[i][j] == int_array[i][j + 1] && int_array[i][j] != 0 && int_array[i][j+1] != 0) {
                    int_array[i][j] += int_array[i][j + 1];
                    getScore(i, j);
                    int_array[i][j + 1] = 0;
                    change = true;
                } else if (int_array[i][j + 1] == 0) {
                    if (j + 2 < n && int_array[i][j] == int_array[i][j + 2] && int_array[i][j+2] !=0) {
                        int_array[i][j] += int_array[i][j + 2];
                        getScore(i, j);
                        int_array[i][j + 2] = 0;
                        change = true;
                    }
                }
            }
            for (j = 0; j < n - 1; j++) {
                if (int_array[i][j] == 0 && int_array[i][j + 1] == 0 && j + 2 < n && int_array[i][j+2] != 0) {
                    int_array[i][j] += int_array[i][j + 2];
                    int_array[i][j + 2] = 0;
                    change = true;
                } else if (int_array[i][j] == 0 && int_array[i][j+1] != 0) {
                    int_array[i][j] += int_array[i][j + 1];
                    int_array[i][j + 1] = 0;
                    change = true;
                }
            }
        }

        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();
    }

    /* Actions to be performed on clicking right button*/
    public void right() {
        int i, j;
        for (i = n - 1; i >= 0; i--) {
            for (j = n - 1; j > 0; j--) {
                if (int_array[i][j] == int_array[i][j - 1] && int_array[i][j-1] != 0) {
                    int_array[i][j] += int_array[i][j - 1];
                    getScore(i, j);
                    int_array[i][j - 1] = 0;
                    change = true;
                } else if (int_array[i][j - 1] == 0) {
                    if (j - 2 >= 0 && int_array[i][j] == int_array[i][j - 2] && int_array[i][j-2] != 0) {
                        int_array[i][j] += int_array[i][j - 2];
                        int_array[i][j - 2] = 0;
                        change = true;
                    }
                }
            }
            for (j = n - 1; j > 0; j--) {
                if (int_array[i][j] == 0 && int_array[i][j - 1] == 0 && j - 2 >= 0 && int_array[i][j-2] != 0) {
                    int_array[i][j] += int_array[i][j - 2];
                    int_array[i][j - 2] = 0;
                    change = true;
                } else if (int_array[i][j] == 0 && int_array[i][j-1] != 0) {
                    int_array[i][j] += int_array[i][j - 1];
                    int_array[i][j - 1] = 0;
                    change = true;
                }
            }
        }
        print();
        enterRandomCheckEmpty();
        getHighScore();
        setHighScore();
        checkGameOver();
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
    public void setTextView() {
        textView_array[0][0] = findViewById(R.id.textView);
        textView_array[0][1] = findViewById(R.id.textView2);
        textView_array[0][2] = findViewById(R.id.textView3);
        textView_array[1][0] = findViewById(R.id.textView4);
        textView_array[1][1] = findViewById(R.id.textView5);
        textView_array[1][2] = findViewById(R.id.textView6);
        textView_array[2][0] = findViewById(R.id.textView7);
        textView_array[2][1] = findViewById(R.id.textView8);
        textView_array[2][2] = findViewById(R.id.textView9);
    }

    /*Checks if there are any possible moves */
    public void checkGameOver() {
        if (!checkAdjacentVertical() && !checkAdjacentHorizontal() &&
                !checkEmptyBoxes()) {
            Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();

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
        for (i = 2; i >= 0; i--) {
            for (j = 0; j < 3; j++) {
                string_array[i][j] = Integer.toString(int_array[i][j]);
                textView_array[i][j].setText(string_array[i][j]);
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

    /* Guestures detection allowed */
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

    public void showDialogBox(){
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setPositiveButton("Restart ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }

                })
                .show();
    }


}

