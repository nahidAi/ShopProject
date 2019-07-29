package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
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

public class Activity_Login extends AppCompatActivity {
    ImageView imgback;
    TextView textTitle, register, textLogin;
    CheckBox checkBox;
    EditText edpassword, edemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findviw();
        onClick();
    }

    public void findviw() {
        imgback = findViewById(R.id.imageBack);
        textTitle = findViewById(R.id.textTitleActivity);
        register = findViewById(R.id.textRegister);
        textTitle.setText("ورود");
        checkBox = findViewById(R.id.chkPass);
        edpassword = findViewById(R.id.edPasswordLogin);
        edemail = findViewById(R.id.edEmailLogin);
        textLogin = findViewById(R.id.textLogin);

    }

    public void onClick() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(intent);
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    edpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    edpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(edemail.getText().toString(), edpassword.getText().toString());





            }
        });
    }

    private void login(final String email, final String pass) {
        String url = Link.linkLogin;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Login.this);
        progressDialog.setMessage("در حال ارسال اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")) {
                    Toast.makeText(Activity_Login.this, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {

                   // Toast.makeText(Activity_Login.this, response.toString(), Toast.LENGTH_SHORT).show();
                    /////------2
                    String image = response.toString();
                    int emailsplite = email.length();
                    int responseSplite = image.length();
                    String splite = image.substring(emailsplite,responseSplite);
                   // Toast.makeText(Activity_Login.this, splite, Toast.LENGTH_LONG).show();


                    Intent intent = new Intent();
                    intent.putExtra(put.email, email);
                    intent.putExtra(put.image, splite);
                    setResult(RESULT_OK, intent);
                    finish();
                    progressDialog.dismiss();

                }


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                map.put(put.password, pass);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
