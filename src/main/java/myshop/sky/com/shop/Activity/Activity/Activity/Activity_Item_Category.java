package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterItemCategory;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelItemCategory;
import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Item_Category extends AppCompatActivity
{
    RecyclerView recyclerView;
    AdapterItemCategory adapterItemCategory;
    List<ModelItemCategory>modelItemCategoryList = new ArrayList<>();
    ImageView imageback;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__item__category);
        id = getIntent().getStringExtra(put.id);


        recyclerView = findViewById(R.id.recyitemcategory);
        adapterItemCategory = new AdapterItemCategory(Activity_Item_Category.this,modelItemCategoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterItemCategory);
        imageback = findViewById(R.id.imageBack);
        imageback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        getItemCategory(id);


    }

    private void getItemCategory(final String id){
        String url = Link.linkGetItemCategory;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Item_Category.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ModelItemCategory[] categories = gson.fromJson(response.toString(), ModelItemCategory[].class);
                for (int i = 0; i < categories.length; i++) {
                    modelItemCategoryList.add(categories[i]);
                    adapterItemCategory.notifyDataSetChanged();

                }
                progressDialog.dismiss();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Item_Category.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener)
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> map = new HashMap<>();
                map.put(put.id,id);
                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
