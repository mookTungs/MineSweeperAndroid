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
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private int width = 20;
    private int height = 10;
    private int bombs = 25;
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
        firstClick = true;
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
                        if(firstClick == true){
                            gameBoard.generateBoard(b.getPosX(), b.getPosY());
                            firstClick = false;
                        }

                        int x = gameBoard.board[b.getPosX()][b.getPosY()];
                        if(x == 0){
                            expand(b.getPosX(), b.getPosY());
                        }else {
                            updateButton(b.getPosX(),b.getPosY(),x);
                            if(x == -1){
                                gameOver();
                            }
                        }
                        win();
                    }
                });
                buttonBoard[i][j] = button;
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
    }

    public void win(){
        System.out.println(gameBoard.uncovered);
        if(gameBoard.uncovered == (gameBoard.totalGrid-bombs)){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    buttonBoard[i][j].setClickable(false);
                    if(gameBoard.board[i][j] == -1){
                        buttonBoard[i][j].setText("-1");
                        buttonBoard[i][j].setBackgroundResource(R.drawable.table_border_clicked);
                    }
                }
            }
            Toast.makeText(this, "YOU WIN! :D", Toast.LENGTH_LONG).show();
        }
    }

    public void gameOver(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                buttonBoard[i][j].setClickable(false);
                if(gameBoard.board[i][j] == -1){
                    buttonBoard[i][j].setText("-1");
                    buttonBoard[i][j].setBackgroundResource(R.drawable.table_border_clicked);
                }
            }
        }
        Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
    }

    public void expand(int x, int y) {
        if(x >= 0 && y >= 0 && x < height && y < width ) {
            if (buttonBoard[x][y].visited) {
                return;
            }
            int result = gameBoard.board[x][y];
            updateButton(x,y,result);
            System.out.println("hello");
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
        buttonBoard[x][y].visited = true;
        t.setBackgroundResource(R.drawable.table_border_clicked);
        t.setClickable(false);
        if (num > 0) {
            t.setText(Integer.toString(num));
        }
        gameBoard.uncovered++;
    }



}
