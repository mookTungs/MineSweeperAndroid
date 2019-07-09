package com.mookt.minesweeper;

import android.content.Context;
import android.widget.ImageButton;

public class MyButton extends ImageButton {
    private int posX, posY;
    public boolean visited;
    public boolean flag;
    public boolean opened;

    public MyButton(Context context, int x, int y){
        super(context);
        posX = x;
        posY = y;
        visited = false;
        flag = false;
        opened = false;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }
}
