package com.tenface.StickyView.view.GameView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tenface.StickyView.R;
import com.tenface.StickyView.view.GameView.GameView;

/**
 * Created by TenFace on 2016/12/1.
 */

public class GameActivity extends AppCompatActivity {
    private TextView scoreTextView,bestScoreTextView;
    private Button buttonNewGame;

    private GameView gameView = null;

    private Score score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buttonNewGame = (Button) findViewById(R.id.btnNewGame);
        scoreTextView = (TextView) findViewById(R.id.score);
        bestScoreTextView = (TextView) findViewById(R.id.bestScore);

        gameView = (GameView) findViewById(R.id.gameView);

        score = new Score();
        gameView.setScore(score);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startNewGame();
            }
        });

    }


    private void startNewGame()
    {
        score.clearScore();
        showScore();
        showBestScore();
        gameView.startGame();
    }

    private void showBestScore(){
        bestScoreTextView.setText(String.valueOf(score.getBestScore()));
    }

    private void showScore(){
        scoreTextView.setText(String.valueOf(score.getScore()));
    }


    class Score
    {
        public int score=0;
        private static final String SP_KEY_BEST_SCORE = "bestScore";

        public void clearScore(){
            score=0;
        }

        public int getScore()
        {
            return score;
        }

        public void addScore(int s)
        {
            score+=s;
            showScore();

            saveBestScore(Math.max(score, getBestScore()));
            showBestScore();
        }

        public void saveBestScore(int s){
            SharedPreferences.Editor e = getPreferences(MODE_PRIVATE).edit();
            e.putInt(SP_KEY_BEST_SCORE, s);
            e.commit();
        }

        public int getBestScore(){
            return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
        }


    }
}
