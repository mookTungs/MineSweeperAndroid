package com.mookt.minesweeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private int width = 9;
    private int height = 9;
    private int bombs = 10;
    private int minesCounter;
    private boolean firstClick;
    MyButton[][] buttonBoard;
    TableLayout tableLayout;
    Board gameBoard;
    TextView mineDisplay;
    Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timer);
        mineDisplay = findViewById(R.id.mine);
        tableLayout = findViewById(R.id.tableLayout);
        buttonBoard = new MyButton[height][width];
        createDisplay();
        gameBoard = new Board(width, height, bombs);
        minesCounter = bombs;
        updateMinesCounter();
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
            case R.id.zoomIn:
                zoom(10);
                return true;
            case R.id.zoomOut:
                zoom(-10);
                return true;
            case R.id.easy:
                selectLevel(9,9,10);
                return true;
            case R.id.intermediate:
                selectLevel(16,16,40);
                return true;
            case R.id.advanced:
                selectLevel(30,16,99);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectLevel(int width, int height, int bombs){
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        tableLayout.removeAllViewsInLayout();
        buttonBoard = new MyButton[height][width];
        createDisplay();
        gameBoard = new Board(width,height,bombs);
        minesCounter = bombs;
        updateMinesCounter();
        firstClick = true;
        timer.stop();
        timer.setBase(SystemClock.elapsedRealtime());
    }

    public void zoom(int x){
        ViewGroup.LayoutParams params;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                params = buttonBoard[i][j].getLayoutParams();
                if(params.height == 30 && x < 0){
                    return;
                }
                params.height += x;
                params.width +=x;
                buttonBoard[i][j].setLayoutParams(params);
            }
        }
    }

    public void newGame(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                buttonBoard[i][j].setBackgroundResource(R.drawable.ic_button);
                buttonBoard[i][j].setEnabled(true);
                buttonBoard[i][j].visited = false;
                buttonBoard[i][j].opened = false;
                buttonBoard[i][j].flag = false;
            }
        }
        firstClick = true;
        minesCounter = bombs;
        updateMinesCounter();
        timer.stop();
        timer.setBase(SystemClock.elapsedRealtime());
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
                layoutParams.width = 60;
                layoutParams.height = 60;
                layoutParams.weight = 1;
                button.setBackgroundResource(R.drawable.ic_button);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyButton b = (MyButton) v;
                        if(firstClick){
                            gameBoard.generateBoard(b.getPosX(), b.getPosY());
                            firstClick = false;
                            timer.setBase(SystemClock.elapsedRealtime());
                            timer.start();
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
                button.setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View v){
                        MyButton b = (MyButton) v;
                        if(b.flag && !b.opened){
                            b.setBackgroundResource(R.drawable.ic_button);
                            b.flag = false;
                            minesCounter++;
                        }else if(!b.opened){
                            b.setBackgroundResource(R.drawable.ic_flag);
                            b.flag = true;
                            minesCounter--;
                        }
                        updateMinesCounter();
                        return true;
                    }
                });
                buttonBoard[i][j] = button;
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
    }

    public void updateMinesCounter(){
        mineDisplay.setText(Integer.toString(minesCounter));
    }

    public void checkGameState(int num){
        if(num == -1){
            timer.stop();
            uncoverBomb();
            gameOver("Game Over :(");
            //Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
        }else if(gameBoard.uncovered == (gameBoard.totalGrid-bombs)){
            timer.stop();
            uncoverBomb();
            gameOver("YOU WIN! :D");
            //Toast.makeText(this, "YOU WIN! :D", Toast.LENGTH_LONG).show();
        }
    }

    public void gameOver(String s){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Minesweeper");
        alertDialog.setMessage(s);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void uncoverBomb(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                buttonBoard[i][j].setEnabled(false);
                if(gameBoard.board[i][j] == -1 && buttonBoard[i][j].flag){
                    buttonBoard[i][j].setBackgroundResource(R.drawable.ic_flagbomb);
                }else if(gameBoard.board[i][j] == -1){
                    buttonBoard[i][j].setBackgroundResource(R.drawable.ic_bomb);
                }else if(gameBoard.board[i][j] != -1 && buttonBoard[i][j].flag){
                    buttonBoard[i][j].setBackgroundResource(R.drawable.ic_wrongbomb);
                }
            }
        }
    }

    public void expand(int x, int y) {
        if(x >= 0 && y >= 0 && x < height && y < width ) {
            if (buttonBoard[x][y].visited || buttonBoard[x][y].flag) {
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
        t.setEnabled(false);
        t.opened = true;
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
