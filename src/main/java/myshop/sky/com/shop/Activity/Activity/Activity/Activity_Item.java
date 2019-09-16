package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

public class Activity_Item extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterItem adapterItem;
    List<ModelItem> modelItems = new ArrayList<>();
    CardView cardFilter;
    String id;
    ImageView imageback;
    TextView texttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_item);
        findView();
    }

    private void findView() {
        imageback = findViewById(R.id.imageBack);
        texttitle = findViewById(R.id.textTitleActivity);
        texttitle.setText(getIntent().getStringExtra(put.title));
        cardFilter = findViewById(R.id.cardfilter);
        id = getIntent().getStringExtra(put.id);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recyitem);
        adapterItem = new AdapterItem(getApplicationContext(), modelItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterItem);
        recyclerView.hasFixedSize();
        onClick();
        getItem(id);

    }

    private void onClick() {
        cardFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFilter();
            }
        });
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getItem(final String id) {

        String url = Link.linkGetItem;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Item.this);
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

                Toast.makeText(Activity_Item.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, id);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void dialogFilter() {
        final Dialog dialog = new Dialog(Activity_Item.this);
        dialog.setContentView(R.layout.dialog);
        RadioButton rd1, rd2, rd3, rd4;

        rd1 = dialog.findViewById(R.id.rd1);
        rd2 = dialog.findViewById(R.id.rd2);
        rd3 = dialog.findViewById(R.id.rd3);
        rd4 = dialog.findViewById(R.id.rd4);

        rd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                modelItems.clear();
                adapterItem.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // getFilter(id,"visit");
                        dialog.cancel();
                    }
                }, 100);

            }
        });

        rd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                modelItems.clear();
                adapterItem.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFilter(id, "sale");
                        dialog.cancel();
                    }
                }, 100);
            }
        });
        rd3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                modelItems.clear();
                adapterItem.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFilter(id, "priceUp");
                        dialog.cancel();
                    }
                }, 100);
            }
        });
        rd4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                modelItems.clear();
                adapterItem.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFilter(id, "priceDown");
                        dialog.cancel();
                    }
                }, 100);
            }
        });
        dialog.show();
// بخاطر اینکه طول و عرض بدیم به دیالوگ تا ظاهر بهتری بگیره
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        width = (int) ((width) * ((double) 4 / 5));


        dialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void getFilter(final String id, final String param) {
        String url = Link.linkfilter;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ModelItem[] items = gson.fromJson(response.toString(), ModelItem[].class);
                for (int i = 0; i < items.length; i++) {
                    modelItems.add(items[i]);
                    adapterItem.notifyDataSetChanged();

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Item.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("idcat", id);
                map.put("param", param);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
