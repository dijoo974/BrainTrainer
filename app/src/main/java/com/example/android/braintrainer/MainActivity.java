package com.example.android.braintrainer;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView timerView;
    TextView scoreView;
    TextView number0View;
    TextView number1View;
    TextView number2View;
    TextView number3View;
    TextView falseAnswerView;
    TextView correctAnswerView;
    TextView finalScore;
    TextView operationView;
    Button playAgainButton;

    View timerBackgroundView;
    View scoreBackgroundView;

    View startPageView;
    View playingPageView;

    CountDownTimer countDownTimer;
    boolean countDownTimerIsRunning = false;

    int score = 0;
    int numberOfTries = 0;
    final int INITIAL_TIMER_VALUE = 30;

    int firstNumber = 0;
    int secondNumber = 0;
    int totalSum = 0;
    int [] numbers = {0, 0, 0, 0};

    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rand = new Random();

        /* Creating all the views */
        playingPageView = findViewById(R.id.playingPage);
        playingPageView.setVisibility(View.INVISIBLE);

        startPageView = findViewById(R.id.startPage);
        startPageView.setVisibility(View.VISIBLE);

        timerView = (TextView) findViewById(R.id.timer);
        scoreView = (TextView) findViewById(R.id.score);
        timerBackgroundView = findViewById(R.id.timerBackground);
        scoreBackgroundView = findViewById(R.id.scoreBackground);

        operationView = (TextView) findViewById(R.id.operation);

        number0View = (TextView) findViewById(R.id.number0);
        number1View = (TextView) findViewById(R.id.number1);
        number2View = (TextView) findViewById(R.id.number2);
        number3View = (TextView) findViewById(R.id.number3);

        falseAnswerView = (TextView) findViewById(R.id.falseAnswer);
        correctAnswerView = (TextView) findViewById(R.id.correctAnswer);
        finalScore = (TextView) findViewById(R.id.finalScore);

        playAgainButton = (Button) findViewById(R.id.playAgain);


        /* set the visibility of the start page and hide the playing page*/
        playingPageView.setVisibility(View.INVISIBLE);
        startPageView.setVisibility(View.VISIBLE);

        falseAnswerView.setVisibility(View.INVISIBLE);
        correctAnswerView.setVisibility(View.INVISIBLE);
        playingPageView.setVisibility(View.INVISIBLE);

        getNumbers();
        displayNumbers();


    }


    public void start(View view)
    {
        startPageView.setVisibility(View.INVISIBLE);
        playingPageView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        correctAnswerView.setVisibility(View.GONE);
        falseAnswerView.setVisibility(View.GONE);

        countDownTimer = new CountDownTimer(INITIAL_TIMER_VALUE * 1000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int sec = (int) millisUntilFinished / 1000;
                timerView.setText(sec + "s");
                countDownTimerIsRunning = true;

                // LayerDrawable bgDrawable = (LayerDrawable)timerBackgroundView.getBackground();
                // GradientDrawable oval = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.circle);



                if (sec < 10 && sec >= 5)
                {

                    timerBackgroundView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.sec10Color)));

                }

                if (sec < 5)
                {
                    timerBackgroundView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.sec5Color)));

                }
            }

            @Override
            public void onFinish() {
                timerView.setText("Fin !");
                countDownTimerIsRunning = false;
                correctAnswerView.setVisibility(View.GONE);
                falseAnswerView.setVisibility(View.GONE);
                finalScore.setVisibility(View.VISIBLE);
                finalScore.setText("Ton score : " + score + " / " + numberOfTries );

                playAgainButton.setVisibility(View.VISIBLE);
            }
        };

        countDownTimer.start();



    }


    public void displayScore()
    {

            scoreView.setText(score + " / " + numberOfTries);


    }


    public void getNumbers()
    {
        firstNumber = rand.nextInt(48) + 1;
        secondNumber = rand.nextInt(48) + 1;
        totalSum = firstNumber + secondNumber;

        while (totalSum < 16 || totalSum > 85)
        {
            firstNumber = rand.nextInt(48) + 1;
            secondNumber = rand.nextInt(48) + 1;
            totalSum = firstNumber + secondNumber;

        }

        int [] otherNumbers = {1, 2, 3};

        otherNumbers[0] = rand.nextInt(21) - 10;
        while (otherNumbers[0] == 0)
        {
            otherNumbers[0] = rand.nextInt(21) - 10;
        }

        otherNumbers[1] = rand.nextInt(21) - 10;
        while (otherNumbers[1] == 0 || otherNumbers[0] == otherNumbers[1])
        {
            otherNumbers[1] = rand.nextInt(21) - 10;
        }

        otherNumbers[2] = rand.nextInt(21) - 10;
        while (otherNumbers[2] == 0 || otherNumbers[1] == otherNumbers[2] || otherNumbers[0] == otherNumbers[2])
        {
            otherNumbers[2] = rand.nextInt(21) - 10;
        }



        int placeAnswer = rand.nextInt(4);

        numbers[placeAnswer] = totalSum;

        int k = 0;
        for (int i = 0; i < 4; i++)
        {
            if (i != placeAnswer)
            {

                numbers[i] = otherNumbers[k] + totalSum;
                k++;
            }

        }

    }


    public void displayNumbers()
    {

        number0View.setText(Integer.toString(numbers[0]));
        number1View.setText(Integer.toString(numbers[1]));
        number2View.setText(Integer.toString(numbers[2]));
        number3View.setText(Integer.toString(numbers[3]));


        if (firstNumber > 9 && secondNumber <= 9)
        {
            operationView.setText(Integer.toString(firstNumber) + " + " + " " + Integer.toString(secondNumber));
        }
        else if (firstNumber <= 9 && secondNumber <= 9)
        {
            operationView.setText(" " + Integer.toString(firstNumber) + " + " + " " + Integer.toString(secondNumber));
        }
        else if (firstNumber > 9 && secondNumber > 9)
        {
            operationView.setText(Integer.toString(firstNumber) + " + " + Integer.toString(secondNumber));
        }
        else if (firstNumber <= 9 && secondNumber > 9)
        {
            operationView.setText(" " + Integer.toString(firstNumber) + " + " + Integer.toString(secondNumber));
        }


    }


    public void checkAnswer(View view)
    {
        TextView answerView = (TextView) findViewById(view.getId());
        int answerViewTag = (int) Integer.parseInt(answerView.getTag().toString());

        if (numbers[answerViewTag] == totalSum && countDownTimerIsRunning)
        {
            score++;

            correctAnswerView.setVisibility(View.VISIBLE);
            falseAnswerView.setVisibility(View.GONE);

            numberOfTries++;
            getNumbers();
            displayNumbers();
            displayScore();
        }
        else if (numbers[answerViewTag] != totalSum && countDownTimerIsRunning)
        {

            correctAnswerView.setVisibility(View.GONE);
            falseAnswerView.setVisibility(View.VISIBLE);

            numberOfTries++;
            getNumbers();
            displayNumbers();
            displayScore();

        }

    }


    public void playAgain(View view)
    {
        score = 0;
        numberOfTries = 0;

        getNumbers();
        displayNumbers();
        displayScore();

        correctAnswerView.setVisibility(View.INVISIBLE);
        falseAnswerView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        finalScore.setVisibility(View.GONE);

        timerBackgroundView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.normalSecColor)));

        start(view);
    }

}
