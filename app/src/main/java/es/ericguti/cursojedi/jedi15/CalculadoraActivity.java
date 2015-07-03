package es.ericguti.cursojedi.jedi15;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


public class CalculadoraActivity extends ActionBarActivity implements View.OnClickListener {
    double x = 0;
    double y = 0;
    int op = 0;
    boolean primer = true;
    TextView result;

    public void operation(){
        switch (op) {
            case 1: x += y; break;
            case 2: x -= y; break;
            case 3: x /= y; break;
            case 4: x *= y; break;
        }
        y = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView26: //num0
                if(primer) {x *= 10; result.setText(String.valueOf(x));}
                else {y *= 10; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView21: //num1
                if(primer) {x = (x*10)+1; result.setText(String.valueOf(x));}
                else {y = (y*10)+1; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView22: //num2
                if(primer) {x = (x*10)+2; result.setText(String.valueOf(x));}
                else {y = (y*10)+2; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView23: //num3
                if(primer) {x = (x*10)+3; result.setText(String.valueOf(x));}
                else {y = (y*10)+3; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView17: //num4
                if(primer) {x = (x*10)+4; result.setText(String.valueOf(x));}
                else {y = (y*10)+4; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView18: //num5
                if(primer) {x = (x*10)+5; result.setText(String.valueOf(x));}
                else {y = (y*10)+5; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView19: //num6
                if(primer) {x = (x*10)+6; result.setText(String.valueOf(x));}
                else {y = (y*10)+6; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView13: //num7
                if(primer) {x = (x*10)+7; result.setText(String.valueOf(x));}
                else {y = (y*10)+7; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView14: //num8
                if(primer) {x = (x*10)+8; result.setText(String.valueOf(x));}
                else {y = (y*10)+8; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView15: //num9
                if(primer) {x = (x*10)+9; result.setText(String.valueOf(x));}
                else {y = (y*10)+9; result.setText(String.valueOf(y));}
                break;
            case R.id.imageView25: //sum
                if(primer) primer = false;
                else operation();
                op = 1;
                result.setText(String.valueOf(x));
                break;
            case R.id.imageView27: //sub
                if(primer) primer = false;
                else operation();
                result.setText(String.valueOf(x));
                op = 2;
                break;
            case R.id.imageView20: //div
                if(primer) primer = false;
                else operation();
                result.setText(String.valueOf(x));
                op = 3;
                break;
            case R.id.imageView16: //mul
                if(primer) primer = false;
                else operation();
                result.setText(String.valueOf(x));
                op = 4;
                break;
            case R.id.imageView28: //eq
                operation();
                result.setText(String.valueOf(x));
                break;
            case R.id.imageView24: //del
                primer = true;
                x = 0;
                y = 0;
                result.setText(String.valueOf(x));
                break;
        }
    }

    public void initCalc(){
        ImageView num0 = (ImageView) findViewById(R.id.imageView26);
        ImageView num1 = (ImageView) findViewById(R.id.imageView21);
        ImageView num2 = (ImageView) findViewById(R.id.imageView22);
        ImageView num3 = (ImageView) findViewById(R.id.imageView23);
        ImageView num4 = (ImageView) findViewById(R.id.imageView17);
        ImageView num5 = (ImageView) findViewById(R.id.imageView18);
        ImageView num6 = (ImageView) findViewById(R.id.imageView19);
        ImageView num7 = (ImageView) findViewById(R.id.imageView13);
        ImageView num8 = (ImageView) findViewById(R.id.imageView14);
        ImageView num9 = (ImageView) findViewById(R.id.imageView15);
        ImageView sum = (ImageView) findViewById(R.id.imageView25);
        ImageView sub = (ImageView) findViewById(R.id.imageView27);
        ImageView div = (ImageView) findViewById(R.id.imageView20);
        ImageView mul = (ImageView) findViewById(R.id.imageView16);
        ImageView eq = (ImageView) findViewById(R.id.imageView28);
        ImageView del = (ImageView) findViewById(R.id.imageView24);
        result = (TextView) findViewById(R.id.textView4);
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        sum.setOnClickListener(this);
        sub.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        eq.setOnClickListener(this);
        del.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        initCalc();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculadora, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("x", x);
        outState.putDouble("y", y);
        outState.putInt("op", op);
        outState.putBoolean("primer",primer);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        x = savedInstanceState.getDouble("x",0);
        y = savedInstanceState.getDouble("y", 0);
        op = savedInstanceState.getInt("op", 0);
        primer = savedInstanceState.getBoolean("primer",true);
        if (primer || op != 0) result.setText(String.valueOf(x));
        else result.setText(String.valueOf(y));
    }
}
