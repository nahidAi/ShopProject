package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterBasket;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelBasket;
import myshop.sky.com.shop.Activity.Activity.OnloadPrice;
import myshop.sky.com.shop.R;

public class Activity_basket extends AppCompatActivity
{
    TextView textTotal, textZarinpal;
    ImageView imageback;
    RecyclerView recyclerView;
    AdapterBasket adapterBasket;
    List<ModelBasket> modelBasketList = new ArrayList<>();
    int totalallprice = 0;
    String title;
    String number;
    int nm = 0;
    String session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "ورود/عضویت");

        findView();

    }

    private void findView()
    {
        textTotal = findViewById(R.id.textTotal);
        // textTotal.setText(totalallprice);
        textZarinpal = findViewById(R.id.textZarinpal);
        recyclerView = findViewById(R.id.recyBasket);
        imageback = findViewById(R.id.imageBack);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapterBasket = new AdapterBasket(getApplicationContext(), modelBasketList);
        adapterBasket.setOnloadPrice(new OnloadPrice()
        {
            @Override
            public void onloadprice()
            {
                recreate();
                Toast.makeText(Activity_basket.this, "سبد خرید شما بروز شد", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapterBasket);
        getBasket(session);
        onClick();


    }

    private void onClick()
    {
        imageback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void getBasket(final String email)
    {
        String url = Link.linkGetBsket;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_basket.this);
        progressDialog.show();
        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                // Toast.makeText(Activity_basket.this, response.toString(), Toast.LENGTH_SHORT).show();
                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(put.id);
                        title = object.getString(put.title);
                        String image = object.getString(put.image);
                        String color = object.getString(put.color);
                        String garnaty = object.getString(put.garanty);
                        String price = object.getString(put.price);
                        String allprice = object.getString(put.allprice);
                        number = object.getString(put.number);
                        modelBasketList.add(new ModelBasket(Integer.parseInt(id), title, image, number, color, garnaty, price, allprice));
                        adapterBasket.notifyDataSetChanged();
                        totalallprice += Integer.parseInt(allprice);
                        nm += Integer.parseInt(number);
                       // Toast.makeText(Activity_basket.this, "total price is : " + totalallprice + "", Toast.LENGTH_SHORT).show();


                    }

                } catch (Exception e)
                {
                    e.printStackTrace();

                }
                progressDialog.dismiss();
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                String price1 = decimalFormat.format(Integer.valueOf(totalallprice + ""));

                Typeface typeface = Typeface.createFromAsset(getAssets(), "Vazir-Medium-FD-WOL.ttf");
                textTotal.setTypeface(typeface);
                textTotal.setText(price1 + "" + " " + "تومان");

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();

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


}
