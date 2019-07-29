package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterItem;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelItem;
import myshop.sky.com.shop.R;

public class Activty_item extends AppCompatActivity
{
    RecyclerView recyclerView;
    AdapterItem adapterItem;
    List<ModelItem> modelItems = new ArrayList<>();
    CardView cardFilter;
    String id;
    ImageView imageback;
    TextView texttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_item);
        findView();
    }
    private  void findView(){
        imageback = findViewById(R.id.imageBack);
        texttitle = findViewById(R.id.textTitleActivity);
        texttitle.setText(getIntent().getStringExtra(put.title));
        cardFilter = findViewById(R.id.cardfilter);
        id =getIntent().getStringExtra(put.id);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recyitem);
        adapterItem = new AdapterItem(getApplicationContext(),modelItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterItem);
        recyclerView.hasFixedSize();
        onClick();
        getItem(id);

    }
    private  void onClick(){
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void getItem(final String id) {

        String url = Link.linkGetItem;
        final ProgressDialog progressDialog = new ProgressDialog(Activty_item.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ModelItem[] items = gson.fromJson(response.toString(), ModelItem[].class);
                for (int i = 0; i < items.length; i++) {
                    modelItems.add(items[i]);
                    adapterItem.notifyDataSetChanged();

                }
                progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activty_item.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> map = new HashMap<>();
                map.put(put.id,id);
                return map;
            }
        }
                ;
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
