package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_ForgetPassword extends AppCompatActivity {
    private EditText edemailforget;
    private TextView textForget, textTitle;
    private ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__password);

        edemailforget = findViewById(R.id.edEmailForget);
        textForget = findViewById(R.id.textForget);
        textTitle = findViewById(R.id.textTitleActivity);
        textTitle.setText("فراموشی رمز");
        imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forget(edemailforget.getText().toString());
            }
        });

    }

    private void forget(final String email) {

        String url = Link.linkForgetPass;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_ForgetPassword.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("1")) {
                    Toast.makeText(Activity_ForgetPassword.this, "رمز عبور به ایمیل شما ارسال شد", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {

                    Toast.makeText(Activity_ForgetPassword.this, "ارسال رمز عبور به مشکل خورده است", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
