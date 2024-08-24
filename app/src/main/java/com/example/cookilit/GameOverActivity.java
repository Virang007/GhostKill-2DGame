package com.example.cookilit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button startGameAgain;
    private  TextView Score;
    private String score;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        startGameAgain =(Button) findViewById(R.id.Play_agian_btn);
        Score = (TextView) findViewById(R.id.Score);

        score =getIntent().getExtras().get("score").toString();

        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameOverIntent = new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(gameOverIntent);
            }
        });

        Score.setText("Score:"+score);
    }
}