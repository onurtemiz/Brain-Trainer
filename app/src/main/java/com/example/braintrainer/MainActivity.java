package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView scoreView;
    private TextView timerView;
    private TextView questionView;
    private TextView result;
    private CountDownTimer countDownTimer = null;
    private ConstraintLayout scoreLayout;
    private LinearLayout buttonLayout;
    private Button tryAgain;
    private Button goButton;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private boolean gameOver = false;
    private int answer;
    private int rightButton;
    private int rights;
    private int total = 0;
    private Random ran = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.scoreView = (TextView) findViewById(R.id.scoreView);
        this.timerView = (TextView) findViewById(R.id.timerView);
        this.questionView = (TextView) findViewById(R.id.questionView);
        this.result = (TextView) findViewById(R.id.result);
        this.buttons.add((Button) findViewById(R.id.firstButton));
        this.buttons.add((Button) findViewById(R.id.secondButton));
        this.buttons.add((Button) findViewById(R.id.thirdButton));
        this.buttons.add((Button) findViewById(R.id.forthButton));
        this.tryAgain = (Button) findViewById(R.id.tryAgain);
        this.goButton = (Button) findViewById(R.id.goButton);
        this.scoreLayout = (ConstraintLayout) findViewById(R.id.scoreLayout);
        this.buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        this.scoreLayout.setVisibility(View.INVISIBLE);
        this.buttonLayout.setVisibility(View.INVISIBLE);
        this.tryAgain.setVisibility(View.INVISIBLE);
        this.result.setVisibility(View.INVISIBLE);
        this.goButton.setVisibility(View.VISIBLE);



    }

    public void tryAgain(View view){
        this.tryAgain.setVisibility(View.INVISIBLE);
        this.result.setVisibility(View.INVISIBLE);
        this.gameOver = false;
        startClock();
        createNewQuestion();
        createButtons();
        this.rights = 0;
        this.total = 0;
        updateScore();
    }

    public void start(View view){
        view.setVisibility(View.INVISIBLE);
        this.scoreLayout.setVisibility(View.VISIBLE);
        this.buttonLayout.setVisibility(View.VISIBLE);
        createNewQuestion();
        createButtons();
        startClock();
    }

    public void startClock() {
        countDownTimer = new CountDownTimer(15_000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText((millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                finishGame();
            }


        };
        countDownTimer.start();
    }

    public void stopClock() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void finishGame() {
        this.gameOver = true;
        this.result.setText("TIME IS UP!");
        this.result.setTextColor(Color.parseColor("#E64A19"));
        this.tryAgain.setVisibility(View.VISIBLE);
    }

    public void userClicked(View view) {
        if (!gameOver) {
            Button clickedButton = (Button) view;
            int clickedTag = Integer.parseInt((String) clickedButton.getTag());
            if (clickedTag == this.rightButton) {
                this.rights++;
                this.total++;
                updateScore();
                right();
                createNewQuestion();
                createButtons();
            } else {
                this.total++;
                wrong();
                updateScore();
                createNewQuestion();
                createButtons();
            }
        }
    }

    public void right() {
        if (this.result.getVisibility() == View.INVISIBLE) this.result.setVisibility(View.VISIBLE);
        this.result.setText("RIGHT!");
        this.result.setTextColor(Color.parseColor("#689F38"));
    }

    public void wrong() {
        if (this.result.getVisibility() == View.INVISIBLE) this.result.setVisibility(View.VISIBLE);
        this.result.setText("WRONG!");
        this.result.setTextColor(Color.parseColor("#D32F2F"));
    }


    public void updateScore() {
        this.scoreView.setText(this.rights + "/" + this.total);
    }

    public void createButtons() {
        if (!gameOver) {
            ArrayList<Integer> usedup = new ArrayList<Integer>();
            int selectedButton = ran.nextInt(4);
            usedup.add(selectedButton);
            for (Button button : this.buttons) {
                int tag = Integer.parseInt((String) button.getTag());
                if (tag == selectedButton) {
                    button.setText(String.valueOf(answer));
                    this.rightButton = tag;
                } else {
                    while (true) {
                        int randInt = ran.nextInt(98) + 1;
                        if (!usedup.contains(randInt)) {
                            usedup.add(randInt);
                            button.setText(String.valueOf(randInt));
                            break;
                        }
                    }
                }
            }
        }
    }


    public void createNewQuestion() {
        if (!gameOver) {
            int num1 = ran.nextInt(49) + 1;
            int num2 = ran.nextInt(49) + 1;
            this.answer = num1 + num2;
            this.questionView.setText(num1 + " + " + num2);
        }
    }
}
