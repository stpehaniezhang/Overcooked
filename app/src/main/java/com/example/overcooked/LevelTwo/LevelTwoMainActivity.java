package com.example.overcooked.LevelTwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.overcooked.Game;
import com.example.overcooked.GeneralFailActivity;
import com.example.overcooked.Statistics.Statistics;

import java.util.Timer;
import java.util.TimerTask;

public class LevelTwoMainActivity extends Game implements LevelTwoGame {

    private LevelTwoView leveltwoView;
    private final Handler handler = new Handler();
    private final static long TIMER_INTERVAL = 30;
    private Timer timer;
    private String username;
    private String difficulty;
    private String background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle information = intent.getExtras();
        if (information != null) {
            try {
                username = information.getString("username");
                difficulty = information.getString("difficulty");
                background = information.getString("background");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Statistics statistics = new Statistics(username, this);
        LevelTwoManager levelTwoManager = new LevelTwoManager(this, statistics);
        leveltwoView = new LevelTwoView(levelTwoManager);
        setContentView(leveltwoView);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        leveltwoView.invalidate();
                    }
                });

            }
        }, 0, TIMER_INTERVAL);
    }

    /**
     * Go to GeneralFailActivity when this is called
     * Also stores user information
     */
    public void navigateFail() {
        timer.cancel();
        super.startIntent(this, GeneralFailActivity.class, username);
    }

    /**
     * Go to LevelTwoSuccessActivity when this is called
     * Also stores user information
     */
    public void navigateSuccess() {
        timer.cancel();
        startIntent(this, LevelTwoSuccessActivity.class, username);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public String getBackground() {
        return background;
    }
}
