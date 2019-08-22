package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterSearch;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelSearch;
import myshop.sky.com.shop.R;

public class Activity_Search extends AppCompatActivity
{
    EditText edsearch;
    RecyclerView recySearch;
    AdapterSearch adapterSearch;
    List<ModelSearch> modelSearches = new ArrayList<>();
    String id, image, title, price, freeprice, label, cat;
    boolean array = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);

        findview();
    }

    private void findview()
    {
        edsearch = findViewById(R.id.edsearch);
        recySearch = findViewById(R.id.recysearch);
        adapterSearch = new AdapterSearch(getApplicationContext(), modelSearches);
        recySearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recySearch.setAdapter(adapterSearch);
        recySearch.hasFixedSize();
        editor();
    }

    // برای اینکه رویداد کایک ادیت تکست کار کنه این متد را مینویسیم
    private void editor()
    {
        edsearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {

                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {

                    array = modelSearches.isEmpty();
                    if (array)
                    {
                        getsearch(edsearch.getText().toString());
                        edsearch.setText("");
                    } else
                    {
                        modelSearches.clear();
                        adapterSearch.notifyDataSetChanged();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {

                                getsearch(edsearch.getText().toString());
                                edsearch.setText("");
                            }
                        }, 100);
                    }

                    // Toast.makeText(Activity_Search.this, edsearch.getText().toString(), Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });

    }

    private void getsearch(final String search)
    {
        String url = Link.linkGetItemSearch + search;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Search.this);
        progressDialog.show();


        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Toast.makeText(Activity_Search.this, response.toString(), Toast.LENGTH_SHORT).show();

                try
                {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("records");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        id = object.getString(put.id);
                        image = object.getString(put.image);
                        title = object.getString(put.title);
                        price = object.getString(put.price);
                        freeprice = object.getString(put.freeprice);
                        label = object.getString(put.label);
                        cat = object.getString(put.cat);


                        modelSearches.add(new ModelSearch(Integer.parseInt(id), image, title, price, cat, label, freeprice));
                        adapterSearch.notifyDataSetChanged();

                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Activity_Search.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Test", "onErrorResponse: " + error.toString());
                progressDialog.dismiss();

            }
        };
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


}
