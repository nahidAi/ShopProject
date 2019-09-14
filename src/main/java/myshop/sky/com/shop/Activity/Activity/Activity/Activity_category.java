package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterCatrgory;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Model.ModelCategory;
import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_category extends AppCompatActivity {
    private RecyclerView recyclerView;
    AdapterCatrgory adapterCatrgory ;
    List<ModelCategory> modelCategoryList = new ArrayList<>();
    private ImageView imageBack;
    private TextView textTilte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        findView();
        setCategory();
    }
    private  void findView(){
        recyclerView = findViewById(R.id.recyCategory);
        adapterCatrgory = new AdapterCatrgory(getApplicationContext(),modelCategoryList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapterCatrgory);
        imageBack = findViewById(R.id.imageBack);
        textTilte = findViewById(R.id.textTitleActivity);
        textTilte.setText("دسته بندی محصولات");
        onClick();


    }
    private  void onClick(){
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private  void setCategory(){
        String url = Link.linkcategory;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_category.this);
        progressDialog.setMessage(" در حال دریافت اطلاعات");
        progressDialog.show();

        Response.Listener<JSONArray>listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                ModelCategory[] categories = gson.fromJson(response.toString(),ModelCategory[].class);
                for (int i = 0 ; i<categories.length;i++)
                {
                    modelCategoryList.add(categories[i]);
                    adapterCatrgory.notifyDataSetChanged();
                    progressDialog.dismiss();
                }


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_category.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,listener,errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
