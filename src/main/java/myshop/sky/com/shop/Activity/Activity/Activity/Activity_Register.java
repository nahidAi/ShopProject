package myshop.sky.com.shop.Activity.Activity.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import de.hdodenhof.circleimageview.CircleImageView;
import myshop.sky.com.shop.Activity.Activity.Class.ImageGallery;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Class.RuntimePermissionsActivity;
import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Register extends RuntimePermissionsActivity
{
    private TextView textTitle, textRegister;
    EditText edemail, edpass;
    CheckBox chkpass;
    ImageView imageBack;
    CircleImageView circleImageView;
    TextView textTake;
    public static final int CHOOSE_GALLERY = 220;
    public static final int REQUEST_EXTERNAL_STORAGE = 323;
    private String imagencode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register);
        findview();
    }

    @Override
    public void onPermissionsGranted(int requestCode)
    {
        if (requestCode == REQUEST_EXTERNAL_STORAGE)
        {
            getImage();
        }

    }

    @Override
    public void onPermissionsDeny(int requestCode)
    {

    }

    private void findview()
    {
        circleImageView = findViewById(R.id.circleImageUserRegister);
        textTake = findViewById(R.id.textTakeImage);
        imageBack = findViewById(R.id.imageBack);
        textRegister = findViewById(R.id.textRegsiter);
        textTitle = findViewById(R.id.textTitleActivity);
        textTitle.setText("ثبت نام");
        edemail = findViewById(R.id.edEmailRegister);
        edpass = findViewById(R.id.edPasswordRegister);
        chkpass = findViewById(R.id.chkPass);
        onclick();
    }

    private void onclick()
    {
        textTake.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity_Register.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);


            }
        });
        imageBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        chkpass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chkpass.isChecked())
                {
                    edpass.setInputType(InputType.TYPE_CLASS_TEXT);
                } else
                {
                    edpass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        textRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                if (edpass.getText().toString().equals("") || edpass.getText().toString().equals("")) {
                    Toast.makeText(Activity_Register.this, "شما مقداری وارد نکرده اید", Toast.LENGTH_SHORT).show();
                } else {
                    register(edemail.getText().toString().trim(), edpass.getText().toString().trim(), imagencode);

                }
            }
        });
    }

    private void register(final String email, final String pass,final  String image)
    {
        String url = Link.linkRegister;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Register.this);
        progressDialog.setMessage("در حال ارسال اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                Toast.makeText(Activity_Register.this, response.toString(), Toast.LENGTH_SHORT).show();
                edemail.setText("");
                edpass.setText("");
                progressDialog.dismiss();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Activity_Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                map.put(put.password, pass);
                map.put(put.image,image);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getImage()
    {
        ImageGallery.showImage(Activity_Register.this, CHOOSE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_GALLERY && resultCode == RESULT_OK)
        {
            Bitmap bitmap = ImageGallery.getBitmap(Activity_Register.this, data.getData());
            imagencode = ImageGallery.getStringImage(bitmap, 300);
            circleImageView.setImageBitmap(bitmap);
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
