package myshop.sky.com.shop.Activity.Activity.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Profile extends AppCompatActivity
{
    private Button btnprofile, btnfav, btnexite;
    private ImageView imageBack;
    private TextView textTilte;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        findview();
    }

    private void findview()
    {
        imageBack = findViewById(R.id.imageBack);
        textTilte = findViewById(R.id.textTitleActivity);
        textTilte.setText("پروفایل");
        btnexite = findViewById(R.id.btnExite);
        btnfav = findViewById(R.id.btnFav);
        btnprofile = findViewById(R.id.btnProfile);
        onclick();

    }

    private void onclick()
    {
        imageBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                // startActivity(new Intent(Activity_Profile.this, Activity_EditeProfile.class));
            }
        });
        btnexite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(put.email, "عضویت و ورود");
                editor.putString(put.image, "");
                editor.apply();
                Intent intent = new Intent();
                intent.putExtra(put.email, "عضویت و ورود");
                intent.putExtra(put.image, "");
                setResult(RESULT_OK, intent);


                finish();
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Activity_Profile.this, Activity_EditProfile.class));
            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
