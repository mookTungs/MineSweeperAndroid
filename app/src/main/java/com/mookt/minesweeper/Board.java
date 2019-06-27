package com.mookt.minesweeper;

import java.util.Random;

public class Board {
    private int width;
    private int height;
    private int bombs;
    public int[][] board;

    public Board(int width, int height, int bombs){
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        board = new int[width][height];
    }

    public void generateBoard(){
        
    }

    public void randomBomb(){
        int numBombLeft = bombs;
        int x, y;
        while(numBombLeft > 0){
            x = getXCoor();
            y = getYCoor();
            if(board[x][y] != -1) {
                board[x][y] = -1;
                numBombLeft--;
            }
        }
    }

    private int getXCoor(){
        Random r = new Random();
        return r.nextInt(width);
    }

    private int getYCoor(){
        Random r = new Random();
        return r.nextInt(height);
    }

}
