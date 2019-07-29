package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Adapter.Adapter_comment;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelComment;
import myshop.sky.com.shop.R;

public class Activity_show_comment extends AppCompatActivity
{
    ImageView imageback;
    RecyclerView recyComment;
    FloatingActionButton floatingActionButton;
    Adapter_comment adapterComment;
    List<ModelComment> modelCommentList = new ArrayList<>();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);
        id = getIntent().getStringExtra(put.id);
        findView();

        // Toast.makeText(this, id+"", Toast.LENGTH_SHORT).show();

    }

    private void findView()
    {
        imageback = findViewById(R.id.imageBack);
        floatingActionButton = findViewById(R.id.floataction);
        recyComment = findViewById(R.id.recyComment);
        adapterComment = new Adapter_comment(getApplicationContext(), modelCommentList);
        recyComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyComment.setAdapter(adapterComment);
        recyComment.hasFixedSize();

        getComments(id);
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
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Activity_show_comment.this,Activity_send_comment.class);
                intent.putExtra(put.id,id);
                startActivity(intent);
            }
        });
    }


    private void getComments(final String myId)
    {
        String url = Link.linkِGetComment;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_show_comment.this);
        progressDialog.setMessage("ر حال دریافت اطلاعات");
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                // Toast.makeText(Activity_show_comment.this, response.toString(), Toast.LENGTH_SHORT).show();
                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString(put.id);
                        String image = jsonObject.getString(put.image);
                        String user = jsonObject.getString("user");
                        String comment = jsonObject.getString("comment");
                        String posotive = jsonObject.getString("posotive");
                        String negative = jsonObject.getString("negative");
                        String rating = jsonObject.getString("rating");
                        String confirm = jsonObject.getString("confirm");
                        String idproduct = jsonObject.getString("idproduct");
                        modelCommentList.add(new ModelComment(image, user, comment, posotive, negative, Float.parseFloat(rating)));
                        adapterComment.notifyDataSetChanged();

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
                Toast.makeText(Activity_show_comment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, myId);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
