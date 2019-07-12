package com.aslisevil.braintrainer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GamePage extends AppCompatActivity {

    TextView num1Text, num2Text, symbolText, bottomMessageText;
    Button button1, button2, button3, button4;
    int questionNum = 0, points = 0;
    Button timerButton, pointBtn;
    boolean isGameOver = false;
    CountDownTimer countDownTimer;
    Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        num1Text = (TextView)findViewById(R.id.number1Text);
        num2Text = (TextView)findViewById(R.id.number2Text);
        symbolText = (TextView)findViewById(R.id.symbolText);
        symbolText = (TextView)findViewById(R.id.symbolText);
        bottomMessageText = (TextView)findViewById(R.id.bottomMessageText);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

        timerButton = (Button)findViewById(R.id.timerBtn);
        pointBtn = (Button)findViewById(R.id.pointBtn);
        playAgainButton = (Button)findViewById(R.id.playAgainBtn);

        generateNewQuestion();
        timer();

    }

    public void timer()
    {
        countDownTimer = new CountDownTimer(30 * 1000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerButton.setText(millisUntilFinished/1000 + "s");
            }

            @Override
            public void onFinish() {
                isGameOver = true;
                bottomMessageText.setText("Total Score:" + points);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void generateButtonNumbers(int answer)
    {
        Random rand = new Random();
        int answ = rand.nextInt(4);

        /*
        random.nextInt(30 + 10) - 10;
        max = 30; min = -10;
         */

        //BAZEN İKİ AYNI SAYI ÜRETİLEBİLİR:

        button1.setText(Integer.toString(rand.nextInt(100 + 100) - 100));
        button2.setText(Integer.toString(rand.nextInt(100 + 100) - 100));
        button3.setText(Integer.toString(rand.nextInt(100 + 100) - 100));
        button4.setText(Integer.toString(rand.nextInt(100 + 100) - 100));
        //bottomMessageText.setText(answer + " " + answ);
        if(answ == 0)
        {
            button1.setText(Integer.toString(answer));
        }
        else if(answ == 1)
        {
            button2.setText(Integer.toString(answer));
        }
        else if(answ == 2)
        {
            button3.setText(Integer.toString(answer));
        }
        else if(answ == 3)
        {
            button4.setText(Integer.toString(answer));
        }
    }

    public void generateNewQuestion()
    {
        Random rand = new Random();
        int n1 = rand.nextInt(51);
        int n2 = rand.nextInt(51);
        int symbol = rand.nextInt(2);

        if(symbol == 0)
        {
            num1Text.setText(Integer.toString(n1));
            num2Text.setText(Integer.toString(n2));
            symbolText.setText(" + ");
            generateButtonNumbers(n1 + n2);
        }
        else if(symbol == 1)
        {
            num1Text.setText(Integer.toString(n1));
            num2Text.setText(Integer.toString(n2));
            symbolText.setText(" - ");
            generateButtonNumbers(n1 - n2);
        }
    }

    public void buttonClicked(View view)
    {
        if(isGameOver == false)
        {
            questionNum++;
            String numOnButton="";
            int result;
            if(view.getId()  == R.id.button1)
            {
                numOnButton = button1.getText().toString();
            }
            else if(view.getId() == R.id.button2)
            {
                numOnButton = button2.getText().toString();
            }
            else if(view.getId() == R.id.button3)
            {
                numOnButton = button3.getText().toString();
            }
            else if(view.getId() == R.id.button4)
            {
                numOnButton = button4.getText().toString();
            }

            result = getResult();

            if(result == Integer.parseInt(numOnButton))
            {
                points++;
                bottomMessageText.setVisibility(View.VISIBLE);
                bottomMessageText.setText("CORRECT!");
                pointBtn.setText(points + "/" + questionNum);
                generateNewQuestion();
            }
            else
            {
                bottomMessageText.setVisibility(View.VISIBLE);
                bottomMessageText.setText("WRONG! ");
                pointBtn.setText(points + "/" + questionNum);
                generateNewQuestion();
            }
        }
        else
        {

        }

    }

    public void playAgainClicked(View view)
    {
        isGameOver = false;
        questionNum = 0;
        points = 0;
        bottomMessageText.setVisibility(View.INVISIBLE);
        pointBtn.setText("0/0");
        countDownTimer.cancel();
        playAgainButton.setVisibility(View.INVISIBLE);
        generateNewQuestion();
        timer();
    }

    public int getResult()
    {
        String num1text = num1Text.getText().toString();
        String num2text = num2Text.getText().toString();

        //BURADA BUG VAR:
        int num1 = Integer.parseInt(num1text);
        int num2 = Integer.parseInt(num2text);

        if(symbolText.getText().toString().equals(" + "))
        {
            return num1+num2;
        }
        else if(symbolText.getText().toString().equals(" - "))
        {
            return num1-num2;
        }
        return 0;
    }
}
