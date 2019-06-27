package com.mookt.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.mookt.minesweeper.MyButton;

public class MainActivity extends AppCompatActivity {
    private int width = 5;
    private int height = 5;
    MyButton[][] myButton;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        createDisplay();

    }

    public void createDisplay(){
        myButton = new MyButton[width][height];
        TableRow tableRow;
        Button button;


        for(int i = 0; i < width; i++){
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            for(int j = 0; j < height; j++){
                button = new Button(this);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT );
                layoutParams.weight = 1;
                button.setBackgroundResource(R.drawable.table_border);
                button.setLayoutParams(layoutParams);
                button.setText(" ");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(0XFFFF00);
                    }
                });
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }

    }
}
