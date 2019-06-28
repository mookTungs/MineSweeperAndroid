package com.mookt.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class MainActivity extends AppCompatActivity {
    private int width = 10;
    private int height = 8;
    private int bombs = 10;
    TableLayout tableLayout;
    Board gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);

        createDisplay();
        gameBoard = new Board(width, height, bombs);
        gameBoard.generateBoard();
    }

    public void createDisplay(){
        TableRow tableRow;
        MyButton button;


        for(int i = 0; i < height; i++){
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            for(int j = 0; j < width; j++){
                button = new MyButton(this,i,j);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT );
                layoutParams.weight = 1;
                button.setBackgroundResource(R.drawable.table_border);
                button.setLayoutParams(layoutParams);
                button.setText(" ");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyButton b = (MyButton) v;
                        b.setText(Integer.toString(gameBoard.board[b.getPosX()][b.getPosY()]));
                    }
                });
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
    }

}
