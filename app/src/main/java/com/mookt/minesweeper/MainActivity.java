package com.mookt.minesweeper;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private int width = 10;
    private int height = 8;
    private int bombs = 13;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.restart){
            newGame();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void newGame(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                buttonBoard[i][j].setText("");
                buttonBoard[i][j].setBackgroundResource(R.drawable.table_border);
                buttonBoard[i][j].setClickable(true);
            }
        }
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
                        final MyButton b = (MyButton) v;
                        int x = gameBoard.board[b.getPosX()][b.getPosY()];
                        if(x == 0){
                            new Thread(){
                                public void run(){
                                    expand(b);
                                }
                            }.start();

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
            int topLeft = gameBoard.checkTopLeft(p.first, p.second, 0);
            int topRight = gameBoard.checkTopRight(p.first, p.second, 0);
            int bottomRight = gameBoard.checkBottomRight(p.first, p.second, 0);
            int bottomLeft = gameBoard.checkBottomLeft(p.first, p.second, 0);

            if (top != -1) {
                updateButton(p.first-1, p.second, top);
                if(top == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first - 1, p.second);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }
            }
            if (bottom != -1) {
                updateButton(p.first+1, p.second, bottom);
                if(bottom == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first + 1, p.second);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }
            }
            if (left != -1) {
                updateButton(p.first, p.second-1, left);
                if(left == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first, p.second - 1);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }
            }
            if (right != -1) {
                updateButton(p.first, p.second+1, right);
                if(right == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first, p.second + 1);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }
            }

            if (topLeft != -1) {
                updateButton(p.first-1, p.second-1, topLeft);
                if(topLeft == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first-1, p.second-1);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }
            }

            if (topRight != -1) {
                updateButton(p.first-1, p.second+1, topRight);
                if(topRight == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first-1, p.second + 1);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }
            }
            if (bottomRight != -1) {
                updateButton(p.first+1, p.second+1, bottomRight);
                if(bottomRight == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first+1, p.second + 1);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }
            }
            if (bottomLeft != -1) {
                updateButton(p.first+1, p.second-1, bottomLeft);
                if(bottomLeft == 1) {
                    Pair<Integer, Integer> n = new Pair<Integer, Integer>(p.first+1, p.second-1);
                    if (!list2.contains(n)) {
                        list.add(n);
                    }
                }
            }

        }
    }

    public void updateButton(final int x, final int y, final int checkIfZero){
        final MyButton t = buttonBoard[x][y];
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                t.setBackgroundResource(R.drawable.table_border_clicked);
                t.setClickable(false);
                if (checkIfZero == 0) {
                    t.setText(Integer.toString(gameBoard.board[x][y]));
                }
            }
        });

    }



}
