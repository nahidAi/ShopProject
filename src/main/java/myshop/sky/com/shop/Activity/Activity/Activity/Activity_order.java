package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Adapter.AdpterOrder;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelOrder;
import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_order extends AppCompatActivity
{
    ImageView imageback;
    RecyclerView recyclerView;
    AdpterOrder adpterOrder;
    List<ModelOrder>modelOrderList = new ArrayList<>();
    String session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        findView();

    }
    private  void  findView(){
        imageback = findViewById(R.id.imageBack);
        recyclerView = findViewById(R.id.recyOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adpterOrder = new AdpterOrder(getApplicationContext(),modelOrderList);
        recyclerView.setAdapter(adpterOrder);

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "ورود/عضویت");

        getOrder(session);



        onClick();

    }
    private  void onClick(){
        imageback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    private void getOrder(final String email) {
        String url = Link.linkGetOrder;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_order.this);
        progressDialog.show();


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(put.id);
                        String price = object.getString(put.allprice);
                        String refid = object.getString(put.refid);
                        String number = object.getString(put.number);
                        String date = object.getString(put.date);
                        String email = object.getString(put.email);
                        modelOrderList.add(new ModelOrder(email, refid, price, number, date));
                        adpterOrder.notifyDataSetChanged();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();


            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                return map;
            }
        };
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
