package com.mookt.minesweeper;

import java.util.Random;

public class Board {
    private int width;
    private int height;
    private int bombs;
    public int uncovered;
    public int totalGrid;
    public int[][] board;

    public Board(int width, int height, int bombs){
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        uncovered = 0;
        totalGrid = width*height;
        board = new int[height][width];
    }

    public void generateBoard(int x, int y){
        uncovered = 0;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                board[i][j] = 0;
            }
        }

        randomBomb(x, y);
        int numBomb;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                numBomb = 0;
                if(board[i][j] != -1){
                    if(checkBomb(i-1,j-1) == true){
                        numBomb++;
                    }
                    if(checkBomb(i-1,j)  == true){
                        numBomb++;
                    }
                    if(checkBomb(i-1,j+1)==true){
                        numBomb++;
                    }
                    if(checkBomb(i,j-1)==true){
                        numBomb++;
                    }
                    if(checkBomb(i,j+1)==true){
                        numBomb++;
                    }
                    if(checkBomb(i+1,j-1)==true){
                        numBomb++;
                    }
                    if(checkBomb(i+1,j)==true){
                        numBomb++;
                    }
                    if(checkBomb(i+1,j+1)==true){
                        numBomb++;
                    }

                    board[i][j] = numBomb;
                }
            }
        }
    }

    /*
        x = height, y = width

    |x-1, y-1| x-1, y |x-1,y+1|
    | x, y-1 |  x, y  | x,y+1 |
    |x+1, y-1| x+1, y |x+1,y+1|

    */

    public boolean checkBomb(int x, int y){
        if(x >= 0 && y >= 0 && x < height && y < width){
            if(board[x][y] == -1){
                return true;
            }
        }
        return false;
    }

    private void randomBomb(int x, int y){
        int numBombLeft = bombs;
        int i, j;
        while(numBombLeft > 0){
            i = getXCoor();
            j = getYCoor();
            if(i==x && j==y){
                continue;
            }
            if(board[i][j] != -1) {
                board[i][j] = -1;
                numBombLeft--;
            }
        }
    }

    private int getXCoor(){
        Random r = new Random();
        return r.nextInt(height);
    }

    private int getYCoor(){
        Random r = new Random();
        return r.nextInt(width);
    }

}
