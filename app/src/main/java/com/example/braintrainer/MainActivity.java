package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView scoreView;
    private TextView timerView;
    private TextView questionView;
    private Button firstButton;
    private Button secondButton;
    private Button thirdButton;
    private Button forthButton;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private int answer;
    private int rightButton;
    private Random ran = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.scoreView = (TextView) findViewById(R.id.scoreView);
        this.timerView = (TextView) findViewById(R.id.timerView);
        this.questionView = (TextView) findViewById(R.id.questionView);
        this.buttons.add((Button) findViewById(R.id.firstButton));
        this.buttons.add((Button) findViewById(R.id.secondButton));
        this.buttons.add((Button) findViewById(R.id.thirdButton));
        this.buttons.add((Button) findViewById(R.id.forthButton));

        createNewQuestion();
        createButtons();

    }


    public void createButtons(){
        ArrayList<Integer> usedup = new ArrayList<Integer>();
        int selectedButton = ran.nextInt(4);
        usedup.add(selectedButton);
        for (Button button : this.buttons){
            int tag = Integer.parseInt((String)button.getTag());
            if (tag == selectedButton){
                button.setText(String.valueOf(answer));
                this.rightButton = tag;
            }else{
                while (true){
                    int randInt = ran.nextInt(98)+1;
                    if(!usedup.contains(randInt)){
                        usedup.add(randInt);
                        button.setText(String.valueOf(randInt));
                        break;
                    }
                }
            }
        }

    }


    public void createNewQuestion(){
        int num1 = ran.nextInt(49)+1;
        int num2 = ran.nextInt(49)+1;
        this.answer = num1+num2;
        this.questionView.setText(num1 + " + " + num2);

    }
}
