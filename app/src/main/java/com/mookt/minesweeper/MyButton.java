package com.mookt.minesweeper;

import android.content.Context;
import android.widget.Button;

public class MyButton extends Button {
    private int posX, posY;
    public boolean visited;

    public MyButton(Context context, int x, int y){
        super(context);
        posX = x;
        posY = y;
        visited = false;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }
}
