package com.mookt.minesweeper;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private int width = 30;
    private int height = 16;
    private int bombs = 99;
    private boolean firstClick;
    MyButton[][] buttonBoard;
    TableLayout tableLayout;
    Board gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);
        buttonBoard = new MyButton[height][width];
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        createDisplay();
        gameBoard = new Board(width, height, bombs);
        firstClick = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.restart:
                newGame();
                return true;
            case R.id.easy:
                width = 9;
                height = 9;
                bombs = 10;
                tableLayout.removeAllViewsInLayout();
                buttonBoard = new MyButton[height][width];
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                createDisplay();
                gameBoard = new Board(width,height,bombs);
                firstClick = true;
                return true;
            case R.id.intermediate:
                width = 16;
                height = 16;
                bombs = 40;
                tableLayout.removeAllViewsInLayout();
                buttonBoard = new MyButton[height][width];
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                createDisplay();
                gameBoard = new Board(width,height,bombs);
                firstClick = true;
                return true;
            case R.id.advanced:
                width = 30;
                height = 16;
                bombs = 99;
                tableLayout.removeAllViewsInLayout();
                buttonBoard = new MyButton[height][width];
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                createDisplay();
                gameBoard = new Board(width,height,bombs);
                firstClick = true;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void newGame(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                buttonBoard[i][j].setBackgroundResource(R.drawable.ic_button);
                buttonBoard[i][j].setClickable(true);
                buttonBoard[i][j].visited = false;
            }
        }
        firstClick = true;
    }

    public void createDisplay(){
        TableRow tableRow;
        MyButton button;

        for(int i = 0; i < height; i++){
            tableRow = new TableRow(this);
            TableRow.LayoutParams tableRowLayParam =  new TableRow.LayoutParams();
            tableRowLayParam.width =  TableRow.LayoutParams.MATCH_PARENT;
            tableRowLayParam.height = TableRow.LayoutParams.MATCH_PARENT;
            tableRowLayParam.weight = 1;
            tableRow.setLayoutParams(tableRowLayParam);
            for(int j = 0; j < width; j++){
                button = new MyButton(this,i,j);

                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 50;
                layoutParams.height = 50;
                layoutParams.weight = 1;
                button.setBackgroundResource(R.drawable.ic_button);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MyButton b = (MyButton) v;
                        if(firstClick){
                            gameBoard.generateBoard(b.getPosX(), b.getPosY());
                            firstClick = false;
                        }

                        int x = gameBoard.board[b.getPosX()][b.getPosY()];
                        if(x == 0){
                            expand(b.getPosX(), b.getPosY());
                        }else {
                            updateButton(b.getPosX(),b.getPosY(), x);
                        }
                        checkGameState(x);
                    }
                });
                buttonBoard[i][j] = button;
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
    }

    public void checkGameState(int num){
        if(num == -1){
            uncoverBomb();
            Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
        }else if(gameBoard.uncovered == (gameBoard.totalGrid-bombs)){
            uncoverBomb();
            Toast.makeText(this, "YOU WIN! :D", Toast.LENGTH_LONG).show();
        }
    }

    public void uncoverBomb(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                buttonBoard[i][j].setClickable(false);
                if(gameBoard.board[i][j] == -1){
                    buttonBoard[i][j].setBackgroundResource(R.drawable.ic_bomb);
                }
            }
        }
    }

    public void expand(int x, int y) {
        if(x >= 0 && y >= 0 && x < height && y < width ) {
            if (buttonBoard[x][y].visited) {
                return;
            }
            int result = gameBoard.board[x][y];
            updateButton(x,y,result);
            if (result > 0) {
                return;
            }
            expand(x-1, y-1);
            expand(x-1, y);
            expand(x-1, y+1);
            expand(x, y-1);
            expand(x, y+1);
            expand(x+1, y-1);
            expand(x+1, y);
            expand(x+1, y+1);
        }

    }

    public void updateButton(int x, int y, int num){
        MyButton t = buttonBoard[x][y];
        t.visited = true;
        t.setBackgroundResource(R.drawable.table_border_clicked);
        t.setClickable(false);
        gameBoard.uncovered++;
        switch (num){
            case 1:
                t.setBackgroundResource(R.drawable.ic_one);
                break;
            case 2:
                t.setBackgroundResource(R.drawable.ic_two);
                break;
            case 3:
                t.setBackgroundResource(R.drawable.ic_three);
                break;
            case 4:
                t.setBackgroundResource(R.drawable.ic_four);
                break;
            case 5:
                t.setBackgroundResource(R.drawable.ic_five);
                break;
            case 6:
                t.setBackgroundResource(R.drawable.ic_six);
                break;
            case 7:
                t.setBackgroundResource(R.drawable.ic_seven);
                break;
            case 8:
                t.setBackgroundResource(R.drawable.ic_eight);
                break;
        }
    }



}
