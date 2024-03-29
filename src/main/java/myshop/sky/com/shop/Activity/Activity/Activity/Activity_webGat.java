package myshop.sky.com.shop.Activity.Activity.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.JsonReader;
import android.util.JsonToken;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.R;

public class Activity_webGat extends AppCompatActivity
{
    private WebView webView;
    String url = Link.linkzarinurl;
    String totalprice, session, description,number;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_gat);

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "ورود/عضویت");
        totalprice = String.valueOf(getIntent().getIntExtra("total",0));
        description = getIntent().getStringExtra("desc");
        number =getIntent().getStringExtra(put.number);

        webView = findViewById(R.id.zarinpalWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new Js(this),"HTMLViewr");
        webView.setWebViewClient(new WebViewClient() {
                                     //   final ProgressDialog pd = ProgressDialog.show(Activity_WebGat.this, "", "Please wait, your transaction is being processed...", true);

                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                         //   pd.show();
                                     }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         if (Build.VERSION.SDK_INT >= 19) {
                                             webView.evaluateJavascript("document.getElementsByTagName('html')[0].innerHTML", new ValueCallback<String>() {
                                                 @Override
                                                 public void onReceiveValue(String value) {
                                                     JsonReader reader = new JsonReader(new StringReader(value));
                                                     reader.setLenient(true);
                                                     try {
                                                         if (reader.peek() != JsonToken.NULL) {
                                                             if (reader.peek() == JsonToken.STRING) {
                                                                 String msg = reader.nextString();
                                                                 Js js = new Js(getApplicationContext());
                                                                 js.getHtml(msg);

                                                             }
                                                         }

                                                     } catch (Exception e) {
                                                         e.printStackTrace();
                                                     } finally {
                                                         try {
                                                             reader.close();
                                                         } catch (Exception e) {
                                                             e.printStackTrace();
                                                         }
                                                     }
                                                 }
                                             });
                                         } else {
                                             webView.loadUrl("javascript:window.HTMLViewr.get(document.getElementsByTagName('html')[0].innerHTML);");
                                         }
                                         //    pd.dismiss();
                                     }

                                     @Override
                                     public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                         super.onReceivedSslError(view, handler, error);
                                         handler.proceed();
                                     }
                                 }
        );

        webView.loadUrl(url + "?Amount=" + totalprice + "&Email=" + session + "&Description=" + description);
        Toast.makeText(this, "لطفا کمی صبر کنید...", Toast.LENGTH_LONG).show();
    }

    class Js {
        Context context;

        public Js(Context context) {
            this.context = context;
        }

        public void getHtml(String html) {
            final String s = Html.fromHtml(html).toString();
            final String[] result = s.split(",");
            if (result[0].equals("OK")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "پرداخت انجام شد" + " " + "شماره تراکنش: " + result[1], Toast.LENGTH_SHORT).show();
                        sendBuy(session,result[1],totalprice,number);
                        deleteBasket(session);
                        Intent intent = new Intent(Activity_webGat.this,Activity_FinalBuy.class);
                        intent.putExtra(put.allprice,totalprice);
                        intent.putExtra(put.refid,result[1]);
                        startActivity(intent);
                        finish();

                    }
                });

            } else if (result[0].equals("ERROR")) {
                Toast.makeText(context, "پرداخت با شکست مواجه شد" + " " + "شناسه خطا: " + result[1], Toast.LENGTH_SHORT).show();
            } else if (result[0].equals("CANSEL")) {
                Toast.makeText(context, "شما  تراکنش را لغو کرده اید", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void sendBuy(final String email,final String refid,final String allprice,final String number)
    {
        String url =Link.linksendBuy;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_webGat.this);
        progressDialog.setMessage("در حال ثبت اطلاعات");
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                if (response.toString().equals("1"))
                {
                    Toast.makeText(Activity_webGat.this, "اطلاعات خرید شما ثبت شد", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Activity_webGat.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> map = new HashMap<>();
                map.put(put.email,email);
                map.put(put.refid,refid);
                map.put(put.number,number);
                map.put(put.allprice,allprice);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
    private void deleteBasket(final String email)
    {
        String url =Link.linkDelteAllBasket;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_webGat.this);
        progressDialog.setMessage("در حال ثبت اطلاعات");
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
//            if (response.toString().equals("1"))
//            {
//               // Toast.makeText(Activity_WebGat.this, "اطلاعات خرید شما ثبت شد", Toast.LENGTH_SHORT).show();
//            }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Activity_webGat.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(put.email,email);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
}
