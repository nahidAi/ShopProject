package myshop.sky.com.shop.Activity.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.R;

public class Activity_wait extends AppCompatActivity {
    ProgressBar progressBar;
    String id;
    String image, title, visit, price, label, date, only, sale, color, garanty, description, cat, freePrice;
    String ratingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);


        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        id = getIntent().getStringExtra(put.id);
        freePrice = getIntent().getStringExtra(put.freeprice);

        sendId(id);


    }

    private void sendId(final String myId) {
        String url = Link.linkGetdata;
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        id = object.getString("id");
                        image = object.getString("image");
                        title = object.getString("title");
                        visit = object.getString("visit");
                        price = object.getString("price");
                        label = object.getString("label");
                        date = object.getString("date");
                        only = object.getString("only");
                        sale = object.getString("sale");
                        color = object.getString("color");
                        cat = object.getString("cat");
                        garanty = object.getString("garanty");
                        description = object.getString("description");
                        ratingbar = object.getString("finalrating");
                        float f = Float.parseFloat(ratingbar);
                        // Toast.makeText(Activity_wait.this, String.valueOf(ratingbar), Toast.LENGTH_LONG).show();
                        // Toast.makeText(Activity_wait.this, id, Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

                Intent intent = new Intent(Activity_wait.this, Activity_Show.class);
                intent.putExtra(put.id, id);
                intent.putExtra(put.title, title);
                intent.putExtra(put.description, description);
                intent.putExtra(put.image, image);
                intent.putExtra(put.cat, cat);
                intent.putExtra(put.visit, visit);
                intent.putExtra(put.price, price);
                intent.putExtra(put.label, label);
                if (!freePrice.equals("")) {
                    intent.putExtra(put.freeprice, freePrice);
                } else {
                    intent.putExtra(put.freeprice, "");
                }
                intent.putExtra(put.date, date);
                intent.putExtra(put.only, only);
                intent.putExtra(put.sale, sale);
                intent.putExtra(put.color, color);
                intent.putExtra(put.garanty, garanty);
                intent.putExtra(put.rating, ratingbar);
                startActivity(intent);
                finish();
                progressBar.setVisibility(View.GONE);


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_wait.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, myId);
                return map;

            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
}
