package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterItemBanner;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelItembanner;
import myshop.sky.com.shop.R;

public class Activity_Banner extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterItemBanner adapterItemBanner ;
    List<ModelItembanner>modelItembannerList = new ArrayList<>();
    ImageView imageBack;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__banner);
        id = getIntent().getStringExtra(put.id);
        findView();
        getItemBanner(id);
    }
    private  void findView(){
        recyclerView = findViewById(R.id.recyitembaneer);
        adapterItemBanner = new AdapterItemBanner(this,modelItembannerList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapterItemBanner);
        imageBack = findViewById(R.id.imageBack);


        onClick();

    }
    private  void onClick(){

        imageBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
    private  void getItemBanner(final  String id){
        String url = Link.linkGetBannerItem;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Banner.this);
        progressDialog.setMessage("در حال دریافت اطلاعات");
        progressDialog.show();

        Response.Listener<String>listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Gson gson = new Gson();
                ModelItembanner[] banners = gson.fromJson(response.toString(), ModelItembanner[].class);
                for (int i = 0; i < banners.length; i++) {
                    modelItembannerList.add(banners[i]);
                    adapterItemBanner.notifyDataSetChanged();

                }
               // Toast.makeText(Activity_Banner.this, response.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Activity_Banner.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap map = new HashMap();
                map.put(put.id,id);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
