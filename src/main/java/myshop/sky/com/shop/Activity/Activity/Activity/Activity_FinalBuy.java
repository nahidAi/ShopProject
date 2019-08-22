package myshop.sky.com.shop.Activity.Activity.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.R;

public class Activity_FinalBuy extends AppCompatActivity
{

    TextView textallprice,textrefid,textfinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__final_buy);

        textallprice = findViewById(R.id.textALlpricefinal);
        textrefid = findViewById(R.id.textrefidFinal);
        textfinish = findViewById(R.id.textfinish);
        textfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textallprice.setText(getIntent().getStringExtra(put.allprice));
        textrefid.setText(getIntent().getStringExtra(put.refid));


    }
   /* @Override
    protected void attachBaseContext(Context newBase) {
       super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/
}
