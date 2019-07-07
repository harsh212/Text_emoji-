package com.example.text_emoji;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.*;
import android.widget.Toast;

import java.lang.Object;
//import static java.awt.image.BufferedImage.*;

public class MainActivity extends AppCompatActivity {

    EditText etemoji,ettext;
    Button btnsubmit,btnshare;
    TextView tvout;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etemoji=findViewById(R.id.etemoji);
        ettext=findViewById(R.id.ettext);
        btnsubmit=findViewById(R.id.btnsubmit);
        btnshare=findViewById(R.id.btnshare);
        tvout=findViewById(R.id.tvout);
        final Toast toast=Toast.makeText(getApplicationContext(),"Null Value is entered!!",Toast.LENGTH_LONG);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text=ettext.getText().toString().trim();
                if(etemoji.getText().toString().trim().isEmpty()||text.isEmpty()){
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
                   toast.show();}
                   else {
                    char emoji=etemoji.getText().toString().charAt(0);
                     String result="";
                    for (int z = 0; z < text.length(); z++) {
                        Bitmap image = textAsBitmap(text.charAt(z) + "");
                        for (int y = 0; y < image.getHeight(); y++) {
                            for (int x = 0; x < image.getWidth(); x++) {
                                result += 0 == image.getPixel(x, y) ? "\t\t" : emoji + "  ";
                            }
                            result += "\n";
                        }
                        result += "\n";
                    }
                    tvout.setText(result);
                    closeKeyboard();

                }
            }


        });

        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND, Uri.parse("smsto:"));
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

    }
    public Bitmap textAsBitmap(String text) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(24);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
    private void showKeyboard(EditText editText) {
        editText.requestFocus();
        editText.setFocusableInTouchMode(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        editText.setSelection(editText.getText().length());
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
