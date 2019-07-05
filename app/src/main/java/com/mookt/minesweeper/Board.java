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
                    if(checkTopLeft(i,j,-1) == true){
                        numBomb++;
                    }
                    if(checkTop(i,j,-1)  == true){
                        numBomb++;
                    }
                    if(checkTopRight(i,j,-1)  == true){
                        numBomb++;
                    }
                    if(checkLeft(i,j,-1)  == true){
                        numBomb++;
                    }
                    if(checkRight(i,j,-1)  == true){
                        numBomb++;
                    }
                    if(checkBottomLeft(i,j,-1)  == true){
                        numBomb++;
                    }
                    if(checkBottom(i,j,-1)  == true){
                        numBomb++;
                    }
                    if(checkBottomRight(i,j,-1)  == true){
                        numBomb++;
                    }

                    board[i][j] = numBomb;
                }
            }
        }
    }

    /*
        x = height, y = width
     ____ ____ ____      ________ ________ ________
    |_tl_|_t__|_tr_|    |x-1, y-1| x-1, y |x-1,y+1|
    |_l__|_x__|_r__|    | x, y-1 |  x, y  | x,y+1 |
    |_bl_|_b__|_br_|    |x+1, y-1| x+1, y |x+1,y+1|

    x = self
    tl = top left
    t = top
    tr = top right
    l = left
    r = right
    bl = bottom left
    br = bottom right
    */

    public boolean checkTopLeft(int x, int y, int check){
        if(x - 1 < 0 || y - 1 < 0){
            return false;
        }
        if(board[x-1][y-1] == check){
            return true;
        }
        return false;
    }

    public boolean checkTop(int x, int y, int check){
        if(x - 1 < 0){
            return false;
        }
        if(board[x-1][y] == check){
            return true;
        }
        return false;
    }

    public boolean checkTopRight(int x, int y, int check){
        if(x - 1 < 0 || y + 1 >= width){
            return false;
        }
        if(board[x-1][y+1] == check){
            return true;
        }
        return false;
    }

    public boolean checkLeft(int x, int y, int check){
        if(y - 1 < 0){
            return false;
        }
        if(board[x][y-1] == check){
            return true;
        }
        return false;
    }

    public boolean checkRight(int x, int y, int check){
        if(y + 1 >= width){
            return false;
        }
        if(board[x][y+1] == check){
            return true;
        }
        return false;
    }

    public boolean checkBottomLeft(int x, int y, int check){
        if(x + 1 >= height || y - 1 < 0){
            return false;
        }
        if(board[x+1][y-1] == check){
            return true;
        }
        return false;
    }

    public boolean checkBottom(int x, int y, int check){
        if(x + 1 >= height){
            return false;
        }
        if(board[x+1][y] == check){
            return true;
        }
        return false;
    }

    public boolean checkBottomRight(int x, int y, int check){
        if(x + 1 >= height || y + 1 >= width){
            return false;
        }
        if(board[x+1][y+1] == check){
            return true;
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
