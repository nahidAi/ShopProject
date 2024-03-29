package myshop.sky.com.shop.Activity.Activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Adapter.Adapter_fav;
import myshop.sky.com.shop.Activity.Activity.Class.DbSqlite;
import myshop.sky.com.shop.Activity.Activity.Model.ModelFav;
import myshop.sky.com.shop.R;

public class Activity_Favorit extends AppCompatActivity
{
    RecyclerView recyclerView;
    Adapter_fav adapterFav;
    List<ModelFav> modelFavs = new ArrayList<>();
    ImageView imageback;
    TextView texttitel;
    DbSqlite dbSqlite = new DbSqlite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__favorit);

       modelFavs = dbSqlite.showData();

        recyclerView = findViewById(R.id.recyfav);
       /* adapterFav = new Adapter_fav(getApplicationContext(), modelFavs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterFav);
        recyclerView.hasFixedSize();*/
        imageback = findViewById(R.id.imageBack);
        imageback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        modelFavs = dbSqlite.showData();
        adapterFav = new Adapter_fav(getApplicationContext(), modelFavs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterFav);
        recyclerView.hasFixedSize();
    }
}
