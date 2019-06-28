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
        board = new int[height][width];
    }

    public void generateBoard(){
        randomBomb();
        int numBomb;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                numBomb = 0;
                if(board[i][j] != -1){
                    numBomb += checkTopLeft(i,j);
                    numBomb += checkTop(i,j);
                    numBomb += checkTopRight(i,j);
                    numBomb += checkLeft(i,j);
                    numBomb += checkRight(i,j);
                    numBomb += checkBottomLeft(i,j);
                    numBomb += checkBottom(i,j);
                    numBomb += checkBottomRight(i,j);
                    board[i][j] = numBomb;
                }
            }
        }
    }

    /*
     ____ ____ ____      ________ ________ ________
    |_tl_|_t__|_tr_|    |x-1, y-1| x, y-1 |x+1,y-1|
    |_l__|_x__|_r__|    | x-1, y |  x, y  | x+1,y |
    |_bl_|_b__|_br_|    |x-1, y+1| x, y+1 |x+1,y+1|

    x = self
    tl = top left
    t = top
    tr = top right
    l = left
    r = right
    bl = bottom left
    br = bottom right
    */

    private int checkTopLeft(int x, int y){
        if(x - 1 < 0 || y - 1 < 0){
            return 0;
        }
        if(board[x-1][y-1] == -1){
            return 1;
        }
        return 0;
    }

    private int checkTop(int x, int y){
        if(y - 1 < 0){
            return 0;
        }
        if(board[x][y-1] == -1){
            return 1;
        }
        return 0;
    }

    private int checkTopRight(int x, int y){
        if(x + 1 >= height || y - 1 < 0){
            return 0;
        }
        if(board[x+1][y-1] == -1){
            return 1;
        }
        return 0;
    }

    private int checkLeft(int x, int y){
        if(x - 1 < 0){
            return 0;
        }
        if(board[x-1][y] == -1){
            return 1;
        }
        return 0;
    }

    private int checkRight(int x, int y){
        if(x + 1 >= height){
            return 0;
        }
        if(board[x+1][y] == -1){
            return 1;
        }
        return 0;
    }

    private int checkBottomLeft(int x, int y){
        if(x - 1 < 0 || y + 1 >= width){
            return 0;
        }
        if(board[x-1][y+1] == -1){
            return 1;
        }
        return 0;
    }

    private int checkBottom(int x, int y){
        if(y + 1 >= width){
            return 0;
        }
        if(board[x][y+1] == -1){
            return 1;
        }
        return 0;
    }

    private int checkBottomRight(int x, int y){
        if(x + 1 >= height || y + 1 >= width){
            return 0;
        }
        if(board[x+1][y+1] == -1){
            return 1;
        }
        return 0;
    }

    private void randomBomb(){
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
        return r.nextInt(height);
    }

    private int getYCoor(){
        Random r = new Random();
        return r.nextInt(width);
    }

}
