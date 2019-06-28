package com.mookt.minesweeper;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private int width = 10;
    private int height = 10;
    private int bombs = 10;
    MyButton[][] buttonBoard;
    TableLayout tableLayout;
    Board gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);
        buttonBoard = new MyButton[height][width];

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
                button.setText("");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyButton b = (MyButton) v;
                        int x = gameBoard.board[b.getPosX()][b.getPosY()];
                        if(x == 0){
                            expand(b);
                        }else {
                            b.setText(Integer.toString(gameBoard.board[b.getPosX()][b.getPosY()]));
                        }
                        b.setBackgroundResource(R.drawable.table_border_clicked);
                        b.setClickable(false);
                    }
                });
                buttonBoard[i][j] = button;
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
    }

    public void expand(MyButton b) {
        List<Pair<Integer, Integer>> list = new ArrayList<Pair<Integer, Integer>>();
        List<Pair<Integer, Integer>> list2 = new ArrayList<Pair<Integer, Integer>>();
        list.add(new Pair<Integer, Integer>(b.getPosX(), b.getPosY()));
        while (!list.isEmpty()) {
            Pair<Integer, Integer> p = list.get(0);
            list2.add(p);
            list.remove(p);
            int top = gameBoard.checkTop(p.first, p.second, 0);
            int bottom = gameBoard.checkBottom(p.first, p.second, 0);
            int left = gameBoard.checkLeft(p.first, p.second, 0);
            int right = gameBoard.checkRight(p.first, p.second, 0);
            if (top != -1) {
                MyButton t = buttonBoard[p.first - 1][p.second];
                t.setBackgroundResource(R.drawable.table_border_clicked);
                t.setClickable(false);
                if(top == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first - 1, p.second);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }else if(top == 0){
                    t.setText(Integer.toString(gameBoard.board[t.getPosX()][t.getPosY()]));
                }
            }
            if (bottom != -1) {
                MyButton t = buttonBoard[p.first + 1][p.second];
                t.setBackgroundResource(R.drawable.table_border_clicked);
                t.setClickable(false);
                Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first + 1, p.second);
                if(bottom == 1) {
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }else if(bottom == 0){
                    t.setText(Integer.toString(gameBoard.board[t.getPosX()][t.getPosY()]));
                }
            }
            if (left != -1) {
                MyButton t = buttonBoard[p.first][p.second - 1];
                t.setBackgroundResource(R.drawable.table_border_clicked);
                t.setClickable(false);
                Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first, p.second - 1);
                if(left == 1) {
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }else if(left == 0){
                    t.setText(Integer.toString(gameBoard.board[t.getPosX()][t.getPosY()]));
                }
            }
            if (right != -1) {
                MyButton t = buttonBoard[p.first][p.second + 1];
                t.setBackgroundResource(R.drawable.table_border_clicked);
                t.setClickable(false);
                Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first, p.second + 1);
                if(right == 1) {
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }else if(right == 0){
                    t.setText(Integer.toString(gameBoard.board[t.getPosX()][t.getPosY()]));
                }
            }
        }
    }



}
