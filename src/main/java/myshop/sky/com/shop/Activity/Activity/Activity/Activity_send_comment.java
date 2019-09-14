package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.HashMap;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_send_comment extends AppCompatActivity
{
    EditText edcomment, edposotive, ednegarive;
    Button btncomment;
    ScaleRatingBar scaleRatingBar;
    private ImageView imageBack;
    private TextView textTilte;
    String id, session, imageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);
        id = getIntent().getStringExtra(put.id);
        // Toast.makeText(this, id+"", Toast.LENGTH_SHORT).show();

        findView();

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "عضویت و ورود");
        imageUser = preferences.getString(put.image, "");
    }

    private void findView()
    {
        textTilte = findViewById(R.id.textTitleActivity);
        textTilte.setText("ثبت دیدگاه");
        scaleRatingBar = findViewById(R.id.ratingbarFinal);
        edcomment = findViewById(R.id.edcooment);
        ednegarive = findViewById(R.id.ednegative);
        edposotive = findViewById(R.id.edposotive);
        btncomment = findViewById(R.id.btncomment);
        imageBack = findViewById(R.id.imageBack);
        onclick();
    }

    private void onclick()
    {
        imageBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        btncomment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (session.equals("عضویت و ورود") || imageUser.equals(""))
                {
                    Toast.makeText(Activity_send_comment.this, "شما وارد حساب کاربری خود نشده اید", Toast.LENGTH_SHORT).show();
                } else if (edcomment.getText().toString().equals("") || ednegarive.getText().toString().equals("") || edposotive.getText().toString().equals(""))
                {
                    Toast.makeText(Activity_send_comment.this, "لطفا مقادیر لازم را پر کنید", Toast.LENGTH_SHORT).show();

                } else
                {
                    sendComment(id,session,imageUser,edcomment.getText().toString(),edposotive.getText().toString(),ednegarive.getText().toString(),scaleRatingBar.getRating());

                }

            }
        });

    }

    private void sendComment(final String idproduct, final String email, final String imageuser, final String comment, final String posotive, final String negative, final float rating)
    {

        String url = Link.linkSendComment;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_send_comment.this);
        progressDialog.show();
        progressDialog.setMessage("در حال ارسال دیدگاه");

        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                if (response.toString().equals("1"))
                {
                    Toast.makeText(Activity_send_comment.this, "اطلاعات ثبت شد", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText(Activity_send_comment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };


        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, idproduct);
                map.put(put.email, email);
                map.put(put.image, imageuser);
                map.put(put.negative, negative);
                map.put(put.comment, comment);
                map.put(put.posotive, posotive);
                map.put(put.rating, String.valueOf(rating));


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
