package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView[][] textView_array = new TextView[3][3];
    int[][] int_array = new int[3][3];
    String[][] string_array = new String[3][3];
    int score, highScore = 0, n = 3;
    SharedPreferences highScore_save;
    String highScore_display;
    boolean playContinue , change =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int i, j;
        setTextView();

        highScore_save = this.getSharedPreferences("HighScoreKey", MODE_PRIVATE);
        highScore = highScore_save.getInt("highScoreKey", 0);
        getHighScore();

        for (i = 0; i <= 2; i++) {
            for (j = 0; j <= 2; j++) {
                int_array[i][j] = 0;
            }
        }
        int_array[0][0] = 2;
        int_array[0][1] = 0;
        int_array[0][2] = 0;
        int_array[1][2] = 2;

        print();
    }

    public void print() {
        int i, j;
        for (i = 2; i >= 0; i--) {
            for (j = 0; j < 3; j++) {
                string_array[i][j] = Integer.toString(int_array[i][j]);
                textView_array[i][j].setText(string_array[i][j]);
            }
        }
    }

    public void up(View v) {
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
                if (int_array[i][j] == 0 && int_array[i + 1][j] == 0 && i + 2 < n && int_array[i][j] != 0) {
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
        if(change) {
            setRandom();
            change = false;
        }
        getHighScore();
        setHighScore();

    }


    public void down(View v) {
        int i, j;
        for (j = n - 1; j >= 0; j--) {
            for (i = n - 1; i > 0; i--) {
                if (int_array[i][j] == int_array[i - 1][j]) {
                    int_array[i][j] += int_array[i - 1][j];

                    getScore(i, j);


                    int_array[i - 1][j] = 0;
                } else if (int_array[i - 1][j] == 0) {
                    if (i - 2 >= 0 && int_array[i][j] == int_array[i - 2][j]) {
                        int_array[i][j] += int_array[i - 2][j];

                        getScore(i, j);


                        int_array[i - 2][j] = 0;
                    }
                }
            }
            for (i = n - 1; i > 0; i--) {
                if (int_array[i][j] == 0 && int_array[i - 1][j] == 0 && i - 2 >= 0) {
                    int_array[i][j] += int_array[i - 2][j];
                    int_array[i - 2][j] = 0;
                } else if (int_array[i][j] == 0) {
                    int_array[i][j] += int_array[i - 1][j];
                    int_array[i - 1][j] = 0;
                }
            }
        }

        print();
        playContinue = checkAdjacentVertical();
        if(change) {
            setRandom();
            change = false;
        }
            getHighScore();
            setHighScore();
    }


    public void left(View v) {
        int i, j;
        for (i = 0; i <= n - 1; i++) {
            for (j = 0; j < n - 1; j++) {
                if (int_array[i][j] == int_array[i][j + 1] && int_array[i][j] != 0) {
                    int_array[i][j] += int_array[i][j + 1];
                    getScore(i, j);
                    int_array[i][j + 1] = 0;
                } else if (int_array[i][j + 1] == 0) {
                    if (j + 2 < n && int_array[i][j] == int_array[i][j + 2]) {
                        int_array[i][j] += int_array[i][j + 2];

                        getScore(i, j);


                        int_array[i][j + 2] = 0;
                    }
                }
            }
            for (j = 0; j < n - 1; j++) {
                if (int_array[i][j] == 0 && int_array[i][j + 1] == 0 && j + 2 < n) {
                    int_array[i][j] += int_array[i][j + 2];
                    int_array[i][j + 2] = 0;
                } else if (int_array[i][j] == 0) {
                    int_array[i][j] += int_array[i][j + 1];
                    int_array[i][j + 1] = 0;
                }
            }
        }

        print();
        playContinue = checkAdjacentVertical();
        if (change) {
            setRandom();
            change = false;
        }
            getHighScore();
            setHighScore();
    }

    public void right(View v) {
        int i, j;
        for (i = n - 1; i >= 0; i--) {
            for (j = n - 1; j > 0; j--) {
                if (int_array[i][j] == int_array[i][j - 1]) {
                    int_array[i][j] += int_array[i][j - 1];

                    getScore(i, j);


                    int_array[i][j - 1] = 0;
                } else if (int_array[i][j - 1] == 0) {
                    if (j - 2 >= 0 && int_array[i][j] == int_array[i][j - 2]) {
                        int_array[i][j] += int_array[i][j - 2];
                        int_array[i][j - 2] = 0;
                    }
                }
            }
            for (j = n - 1; j > 0; j--) {
                if (int_array[i][j] == 0 && int_array[i][j - 1] == 0 && j - 2 >= 0) {
                    int_array[i][j] += int_array[i][j - 2];
                    int_array[i][j - 2] = 0;
                } else if (int_array[i][j] == 0) {
                    int_array[i][j] += int_array[i][j - 1];
                    int_array[i][j - 1] = 0;
                }
            }
        }
        print();
        if(change) {
            setRandom();
            change = false;
        }
        getHighScore();
        setHighScore();
    }

    public void setRandom() {
        if (!checkEmptyBoxes()) {
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
    }

    public void getScore(int i, int j) {
        score += int_array[i][j];
        TextView s = findViewById(R.id.score);
        String s1 = Integer.toString(score);
        s.setText(s1);
        setHighScore();
        getHighScore();

    }

    public void setHighScore() {
        if (score > highScore) {
            highScore = score;
            highScore_save.edit().putInt("highScoreKey", highScore).apply();

        }
    }

    public void getHighScore() {
        highScore_display = Integer.toString(highScore);
        TextView streakCount = findViewById(R.id.highScore);
        streakCount.setText(highScore_display);
    }

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

    public boolean checkGameOver() {
        int i, j;
        boolean gameOver = true;

        if (!checkEmptyBoxes()) {
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if (!checkAdjacentVertical()) {
                        gameOver = false;
                        Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
                        return gameOver;
                    }
                }
            }
        }

        return gameOver;
    }

    public boolean checkAdjacentVertical() {
        boolean AdjacentMoveVertical = false;
        int i, j;
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < n; j++) {
                if (int_array[i][j] == int_array[i + 1][j]) {
                    AdjacentMoveVertical = true;
                    return AdjacentMoveVertical;
                }
            }
        }
        return AdjacentMoveVertical;
    }

    public boolean checkEmptyBoxes() {
        int i, j;
        boolean boxesFilled = true;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (int_array[i][j] == 0) {
                    boxesFilled = false;
                    return boxesFilled;
                }
            }
        }
        return boxesFilled;
    }



}

