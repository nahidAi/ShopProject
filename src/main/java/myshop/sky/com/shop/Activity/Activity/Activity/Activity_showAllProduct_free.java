package myshop.sky.com.shop.Activity.Activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Adapter.AdaperVisite;
import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterFree;
import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterOnly;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Model.ModelFree;
import myshop.sky.com.shop.Activity.Activity.Model.ModelOnly;
import myshop.sky.com.shop.Activity.Activity.Model.Modelvisit;
import myshop.sky.com.shop.R;

public class Activity_showAllProduct_free extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterFree adapterFree;
    List<ModelFree> modelFreeList = new ArrayList<>();
    AdapterOnly adapterOnly;
    List<ModelOnly> modelOnlies =new ArrayList<>();
    AdaperVisite adaperVisite;
    List<Modelvisit>modelvisitArrayList = new ArrayList<>();
    String idText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_product_free);

        recyclerView = findViewById(R.id.recyAll);
        idText = getIntent().getStringExtra("idText");

        if (idText.equals("1")){
            adapterFree = new AdapterFree(getApplicationContext(), modelFreeList);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            recyclerView.setAdapter(adapterFree);
            getFreeData();
        }else if (idText.equals("2")){
            adapterOnly = new AdapterOnly(getApplicationContext(),modelOnlies);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            recyclerView.setAdapter(adapterOnly);
            getOnlyData();

        }else if (idText.equals("3")){
            adaperVisite = new AdaperVisite(getApplicationContext(),modelvisitArrayList);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            recyclerView.setAdapter(adaperVisite);
            getVsitData();

        }



    }

    private void getFreeData() {

        String url = Link.linkFree;
//        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
//        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelFree[] frees = gson.fromJson(response.toString(), ModelFree[].class);
                for (int i = 0; i < frees.length; i++) {
                    modelFreeList.add(frees[i]);
                    adapterFree.notifyDataSetChanged();

                }
                //  progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_showAllProduct_free.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    private void getOnlyData() {

        String url = Link.linkOnly;
//        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
//        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelOnly[] onlies = gson.fromJson(response.toString(), ModelOnly[].class);
                for (int i = 0; i < onlies.length; i++) {
                    modelOnlies.add(onlies[i]);
                    adapterOnly.notifyDataSetChanged();

                }
                //progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_showAllProduct_free.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    private void getVsitData() {

        String url = Link.linkVisit;
//        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
//        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Modelvisit[] modelvisits = gson.fromJson(response.toString(), Modelvisit[].class);
                for (int i = 0; i < modelvisits.length; i++) {
                    modelvisitArrayList.add(modelvisits[i]);
                    adaperVisite.notifyDataSetChanged();

                }
                //progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_showAllProduct_free.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
