package com.example.primsvisualizer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    TableLayout table;
    Button b1,b2;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = (TableLayout)findViewById(R.id.main_table);
        table.removeAllViews();
        b1 =(Button)findViewById(R.id.start_button);
        b2 = (Button)findViewById(R.id.reset_button);

        //Fetching the dimensions of the phone
        int h = Resources.getSystem().getDisplayMetrics().heightPixels;
        int w = Resources.getSystem().getDisplayMetrics().widthPixels;
        int densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;

        int boxWidth = (16 * densityDpi)/160;
        int boxHeight = boxWidth;

        int maxBoxesInARow = (int)Math.ceil( h/boxWidth) - 3;
        int maxBoxesInAColumn = (int)Math.ceil(w/boxHeight);

        MST mst = new MST( maxBoxesInARow, maxBoxesInAColumn, this);

        for(int i = 0; i <= maxBoxesInARow; i++){
            TableRow row = new TableRow(MainActivity.this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for(int j = 0; j <= maxBoxesInAColumn; j++){

                Button button = new Button(MainActivity.this);
                Point temp = new Point(i,j);

                button.setBackground(getDrawable(R.drawable.rect));
                button.setLayoutParams(new TableRow.LayoutParams(boxWidth,boxHeight));
                button.setTag(temp);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        v.setBackground(getDrawable(R.drawable.black_rect));
                        Point temp = (Point)v.getTag();
                        mst.addVertex(temp.x,temp.y);
                    }
                });

                row.addView(button);
            }

            table.addView(row);

        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( status == true)
                    return;

                Thread backGroundThread = new Thread(mst);
                backGroundThread.start();
                status = true;
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mst.stopThread();
                mst.reset();
                status = false;
                for(int i = 0; i <= maxBoxesInARow; i++){
                    TableRow row = (TableRow)table.getChildAt(i);
                    for(int j = 0; j <= maxBoxesInAColumn; j++){

                        Button button = (Button)row.getChildAt(j);
                        button.setBackground(getDrawable(R.drawable.rect));
                    }
                }
            }
        });
    }
}