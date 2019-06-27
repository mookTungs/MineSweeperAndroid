package com.mookt.minesweeper;

import android.widget.Button;

public class MyButton {
    private Button button;
    private int posX, posY;

    public MyButton(Button b, int x, int y){
        button = b;
        posX = x;
        posY = y;
    }

    public Button getButton() {
        return button;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }
}
