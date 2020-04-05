package com.androidsingh.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView player_1_points,player_2_points;
    private Button reset_button;
    private Button[][] buttons=new Button[3][3];
    private int p1Points,p2Points,number_of_rounds;
    private boolean p1Turn=true;
    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player_1_points=(TextView)findViewById(R.id.player_1_points);
        player_2_points=(TextView)findViewById(R.id.player_2_points);
        reset_button=(Button)findViewById(R.id.reset_button);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resetTheGame();
            }
        });
        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                String buttonId="button_"+i+j;
                int id=getResources().getIdentifier(buttonId,"id",getPackageName());
                buttons[i][j]=findViewById(id);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals(""))
        {
            return;
        }
        if (p1Turn)
        {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        number_of_rounds++;
        if (check_who_wins())
        {
            if (p1Turn)
            {
                p1Wins();
            }
            else
            {
                p2Wins();
            }
        }
        else if (number_of_rounds==9)
        {
            draw();
        }
        else
        {
            p1Turn=!p1Turn;
        }
    }

    private boolean check_who_wins(){
        String[][] fields=new String[3][3];
        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                fields[i][j]=buttons[i][j].getText().toString();
            }
        }
        for (int i=0;i<3;i++)
        {
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2]) && !fields[i][0].equals(""))
            {
                return true;
            }
        }
        for (int i=0;i<3;i++)
        {
            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i]) && !fields[0][i].equals(""))
            {
                return true;
            }
        }
        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2]) && !fields[0][0].equals(""))
        {
            return true;
        }
        if (fields[0][2].equals(fields[1][1]) && fields[0][2].equals(fields[2][0]) && !fields[0][2].equals(""))
        {
            return true;
        }
        return false;
    }

    private void p1Wins() {
        p1Points++;
        Toast.makeText(this,"Player 1 Wins!!",Toast.LENGTH_SHORT).show();
        updatePointsInTextView();
        resetButtons();
    }

    private void p2Wins() {
        p2Points++;
        Toast.makeText(this,"Player 2 Wins!!",Toast.LENGTH_SHORT).show();
        updatePointsInTextView();
        resetButtons();
    }

    private void draw() {
        Toast.makeText(this,"Draw!!",Toast.LENGTH_SHORT).show();
        resetButtons();
    }

    private void updatePointsInTextView() {
        player_1_points.setText("Player 1 Points: "+p1Points);
        player_2_points.setText("Player 2 Points: "+p2Points);
    }

    private void resetButtons() {
        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                buttons[i][j].setText("");
            }
        }
        number_of_rounds=0;
        p1Turn=true;
    }

    private void resetTheGame() {
        p1Points=0;
        p2Points=0;
        updatePointsInTextView();
        resetButtons();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("number_of_rounds",number_of_rounds);
        outState.putInt("p1Points",p1Points);
        outState.putInt("p2Points",p2Points);
        outState.putBoolean("p1Turn",p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        number_of_rounds=savedInstanceState.getInt("number_of_rounds");
        p1Points=savedInstanceState.getInt("p1Points");
        p2Points=savedInstanceState.getInt("p2Points");
        p1Turn=savedInstanceState.getBoolean("p1Turn");
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
