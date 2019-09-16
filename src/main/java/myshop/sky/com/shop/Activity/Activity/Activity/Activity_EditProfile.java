package myshop.sky.com.shop.Activity.Activity.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelComment;
import myshop.sky.com.shop.R;

public class Activity_EditProfile extends AppCompatActivity {
    private TextView textEdite;
    private EditText edusername, edaddress, edphone;
    private ImageView imageback;
    private TextView textTile;
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private String emailEdite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit_profile);

        findView();
        preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        emailEdite = preferences.getString(put.email, "ایمیل");

        sendEditForgetinfoUser(emailEdite);

    }

    private void findView() {
        edaddress = findViewById(R.id.edAddress);
        edphone = findViewById(R.id.edPhone);
        edusername = findViewById(R.id.edUsername);
        textEdite = findViewById(R.id.textEdite);
        imageback = findViewById(R.id.imageBack);
        textTile = findViewById(R.id.textTitleActivity);
        textTile.setText("ویرایش اطلاعات");
        preferences2 = getSharedPreferences(put.shared2, MODE_PRIVATE);
        edusername.setText(preferences2.getString(put.username, ""));
        edphone.setText(preferences2.getString(put.phone, ""));
        edaddress.setText(preferences2.getString(put.address, ""));
        onClick();
    }

    private void onClick() {
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        textEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edusername.getText().toString().trim();
                String address = edaddress.getText().toString().trim();
                String phone = edphone.getText().toString().trim();
                if (user.equalsIgnoreCase("") || address.equalsIgnoreCase("") || phone.equalsIgnoreCase("")) {
                    Toast.makeText(Activity_EditProfile.this, "فیلدهای مورد نظر را پر کنید", Toast.LENGTH_SHORT).show();
                } else {
                    sendEditProfile(emailEdite, edusername.getText().toString().trim(), edphone.getText().toString().trim(), edaddress.getText().toString().trim());

                }

            }
        });
    }

    private void sendEditProfile(final String email, final String username, final String phone, final String address) {
        String url = Link.linkEdite;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_EditProfile.this);
        progressDialog.setMessage(" در حال ارسال اطلاعات");
        progressDialog.show();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ینجا داده هارو داخل شیرد نگهش میداره  و در شروع اکتیویتی باید اینا ست بشن واسه ادیت تکست هامون
                preferences2 = getSharedPreferences(put.shared2, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences2.edit();
                editor.putString(put.username, username);
                editor.putString(put.phone, phone);
                editor.putString(put.address, address);
                editor.apply();
                Toast.makeText(Activity_EditProfile.this, " اطلاعات به روز رسانی شد", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_EditProfile.this, " خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                map.put(put.username, username);
                map.put(put.phone, phone);
                map.put(put.address, address);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void sendEditForgetinfoUser(final String email) {
        String url = Link.sendEditForgetinfoUser;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_EditProfile.this);
        progressDialog.setMessage(" در حال ارسال اطلاعات");
        progressDialog.show();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String username = jsonObject.getString(put.username);
                        String phone = jsonObject.getString(put.phone);
                        String address = jsonObject.getString(put.address);

                        edusername.setText(username);
                        edphone.setText(phone);
                        edaddress.setText(address);


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
                Toast.makeText(Activity_EditProfile.this, " خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
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

}
