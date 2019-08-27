package myshop.sky.com.shop.Activity.Activity.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Class.DbSqlite;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelFav;
import myshop.sky.com.shop.R;

public class Activity_Show extends AppCompatActivity
{
    SliderLayout sliderLayout;
    ImageView imageback, imagebacket, imagefavorite;
    AppBarLayout appBarLayout;
    ArrayList<String> strings = new ArrayList<>();
    String id, label, freePrice;
    TextView titleShow, textColr, garanty, textprice, nexttext, description, textCountbasket, textFree, textrating;
    CardView basket, comment, cardPr;
    boolean b = true;
    Context context = this;
    private boolean bf = true;
    DbSqlite sqlite = new DbSqlite(context);
    String session;
    String titleS, colorS, garantyS, priceS, imageS, cat, visit;
    ScaleRatingBar scaleRatingBar;
    int ratingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        id = getIntent().getStringExtra(put.id);
        label = getIntent().getStringExtra(put.label);
        freePrice = getIntent().getStringExtra(put.freeprice);

        //Toast.makeText(this, ratingbar+"", Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "عضویت و ورود");
        //Toast.makeText(this, session, Toast.LENGTH_SHORT).show();

        findView();

//برای اینکه قبل از هر چیز فیوریت رو چک کنه  و اگه همچی ایدیی بود رنگ ستاره رو قرمز کنه این کدها روو مینویسیم
        Cursor cursor = sqlite.cu(Integer.parseInt(id));

        if (cursor.getCount() == 1)
        {
            imagefavorite.setColorFilter(getApplicationContext().getResources().getColor(R.color.red));
            imagefavorite.setImageResource(R.drawable.ic_star_black_24dp);
            bf = false;
        } else
        {
            imagefavorite.setColorFilter(getApplicationContext().getResources().getColor(R.color.gray));
            imagefavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
            bf = true;
        }
        ////////////////////////////////////////////


    }

    private void findView()
    {
        imagefavorite = findViewById(R.id.imageFavorite);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Vazir-Medium-FD-WOL.ttf");
        textFree = findViewById(R.id.textPriceShowfree);
        textFree.setVisibility(View.GONE);
        textprice = findViewById(R.id.textPriceShow);
        textprice.setTypeface(typeface);
        if (!freePrice.equals(""))
        {
            textFree.setVisibility(View.VISIBLE);
            textFree.setText(freePrice);
            textprice.setTextColor(Color.RED);
            textprice.setPaintFlags(textprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else
        {
            textFree.setVisibility(View.GONE);
        }
        textFree.setTypeface(typeface);
        cardPr = findViewById(R.id.cardPr);
        textrating = findViewById(R.id.textRating);
        scaleRatingBar = findViewById(R.id.ratingbarFinal);
        // ratingbar = getIntent().getIntExtra(put.rating, 0);
        String rat = getIntent().getStringExtra(put.rating);

        int r = rat.length();
        if (r == 1)
        {
            rat = rat + "." + "0";
        } else
        {
            rat = rat.substring(0, 3);
        }
//        rat = rat.s);
        textrating.setText(rat + " " + "از" + " " + "5");
        textrating.setTypeface(typeface);
        ScaleRatingBar bar = findViewById(R.id.ratingbarFinal);
        bar.setRating(Float.parseFloat(rat));
        titleS = getIntent().getStringExtra(put.title);
        colorS = getIntent().getStringExtra(put.color);
        garantyS = getIntent().getStringExtra(put.garanty);
        priceS = getIntent().getStringExtra(put.price);
        imageS = getIntent().getStringExtra(put.image);
        textCountbasket = findViewById(R.id.textCountBasket);
        SharedPreferences preferences = getSharedPreferences("c", MODE_PRIVATE);
        textCountbasket.setText(preferences.getString("count", "0"));
        imagebacket = findViewById(R.id.imageBasket);
        comment = findViewById(R.id.cardViewComment);


        titleShow = findViewById(R.id.textTitleShow);
        titleShow.setTypeface(typeface);
        textColr = findViewById(R.id.textcolorShow);
        textColr.setTypeface(typeface);

        garanty = findViewById(R.id.textGarantyShow);
        garanty.setTypeface(typeface);


        nexttext = findViewById(R.id.textNextShow);
        nexttext.setTypeface(typeface);

        basket = findViewById(R.id.cardViewBasket);

        description = findViewById(R.id.textDescriptionShow);
        description.setTypeface(typeface);


        appBarLayout = findViewById(R.id.app_bar_layout);
        sliderLayout = findViewById(R.id.slider);
        imageback = findViewById(R.id.imageBack);
        imagebacket = findViewById(R.id.imageBasket);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i)
            {

                int scroll = -(i);
                Log.i("collaps", scroll + "");
                if (scroll >= 652)
                {
                    imageback.setColorFilter(Color.rgb(255, 255, 255));
                    imagebacket.setColorFilter(Color.rgb(255, 255, 255));
                    textCountbasket.setTextColor(Color.rgb(255, 255, 255));
                } else
                {
                    imageback.setColorFilter(Color.rgb(189, 189, 189));
                    imagebacket.setColorFilter(Color.rgb(189, 189, 189));
                    textCountbasket.setTextColor(Color.rgb(189, 189, 189));
                }

            }
        });
        // installSliderLayout();


        getSilder(id);
        setIntent();
        onClick();
    }

    private void onClick()
    {
        imagefavorite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (bf)
                {
                    if (!freePrice.equals(""))
                    {
                        sqlite.insertFav(new ModelFav(Integer.parseInt(id), imageS, titleS, visit, freePrice, priceS, label));
                        imagefavorite.setImageResource(R.drawable.ic_star_black_24dp);
                        imagefavorite.setColorFilter(getApplicationContext().getResources().getColor(R.color.red));
                        Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد", Toast.LENGTH_SHORT).show();
                        bf = false;
                    } else
                    {
                        sqlite.insertFav(new ModelFav(Integer.parseInt(id), imageS, titleS, visit, priceS, priceS, label));
                        imagefavorite.setImageResource(R.drawable.ic_star_black_24dp);
                        imagefavorite.setColorFilter(getApplicationContext().getResources().getColor(R.color.red));
                        Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد", Toast.LENGTH_SHORT).show();
                        bf = false;
                    }

                } else
                {
                    sqlite.deleteFav(Integer.parseInt(id));
                    imagefavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
                    imagefavorite.setColorFilter(getApplicationContext().getResources().getColor(R.color.gray));
                    Toast.makeText(context, "از لیست علاقه مندی ها حذف شد", Toast.LENGTH_SHORT).show();
                    bf = true;
                }

            }
        });
        cardPr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Activity_Show.this, Activity_properties.class);
                intent.putExtra(put.id, id);
                intent.putExtra(put.title, titleS);
                startActivity(intent);
            }
        });

        comment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Activity_Show.this, Activity_show_comment.class);
                intent.putExtra(put.id, id);
                startActivity(intent);
            }
        });
        nexttext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (b)
                {
                    description.setSingleLine(false);
                    nexttext.setText("بستن");
                    b = false;


                } else
                {
                    description.setSingleLine(true);
                    nexttext.setText("ادامه مطلب");
                    b = true;

                }
            }
        });
        basket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (session.equals("عضویت و ورود"))
                {
                    Toast.makeText(Activity_Show.this, "شما وارد حساب کاربری خود نشده اید", Toast.LENGTH_SHORT).show();
                } else
                {
                    if (!freePrice.equals(""))
                    {
                        sendBasket(id, session, titleS, colorS, imageS, garantyS, freePrice);
                    } else
                    {
                        sendBasket(id, session, titleS, colorS, imageS, garantyS, priceS);
                    }

                    // Toast.makeText(Activity_Show.this, id + " " + session+" "+titleS + " " + colorS + " " + garantyS + "  " + priceS, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Activity_Show.this, Activity_basket.class);
                    startActivity(intent);

                }


            }
        });
        imagebacket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Activity_Show.this, Activity_basket.class));
            }
        });
    }


    public void installSliderLayout()
    {
//        Map<String, String> url_image = new TreeMap<>();
//        url_image.put("image1", "http://uupload.ir/files/2zds_s4.png");
//        url_image.put("image2", "http://uupload.ir/files/a7xa_s2.png");
//        url_image.put("image3", "http://uupload.ir/files/yua_s6.png");
//        url_image.put("image4", "http://uupload.ir/files/eid_s7.png");
//
//        for (String name : url_image.keySet()) {
//            TextSliderView textSliderView = new TextSliderView(this);
//            // initialize a SliderLayout
//            textSliderView
//                    .description(name)
//                    .image(url_image.get(name))
//                    //.setOnSliderClickListener(this)
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra", name);
//            sliderLayout.setDuration(3000);
//            sliderLayout.addSlider(textSliderView);
//
//        }

    }

    private void getSilder(final String myId)
    {

        final String url = Link.linkImage;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Show.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {


                try
                {
                    //Toast.makeText(Activity_Show.this, response.toString(), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pics = jsonObject.getString("pics");
                        strings.add(pics);
                        TextSliderView textSliderView = new TextSliderView(Activity_Show.this);
                        // textSliderView.description(keyname)
                        textSliderView.image(strings.get(i))
                                .empty(R.drawable.plcaeholder)
                                .error(R.drawable.error)
                                .setScaleType(BaseSliderView.ScaleType.Fit);

//                    textSliderView.bundle(new Bundle());
//                    textSliderView.getBundle()
//                            .putString("extra", keyname);
                        sliderLayout.setDuration(3000);
                        sliderLayout.addSlider(textSliderView);
                    }
                } catch (Exception e)
                {

                }

                progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                //  Toast.makeText(Activity_Show.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void setIntent()
    {

        cat = getIntent().getStringExtra(put.cat);
        visit = getIntent().getStringExtra(put.visit);
        label = getIntent().getStringExtra(put.label);


        titleShow.setText(titleS);
        textColr.setText(colorS);
        garanty.setText(garantyS);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(priceS));
        textprice.setText(price);
        description.setText(getIntent().getStringExtra(put.description));


    }

    private void sendBasket(final String id, final String email, final String title, final String color, final String image, final String garanty, final String price)
    {
        String url = Link.linkBsket;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Show.this);
        progressDialog.show();
        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Toast.makeText(Activity_Show.this, response.toString(), Toast.LENGTH_SHORT).show();
                String count = textCountbasket.getText().toString();
                int total = 0;
                total = Integer.parseInt(count) + 1;
                textCountbasket.setText(String.valueOf(total));
                progressDialog.dismiss();

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Activity_Show.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, id);
                map.put(put.email, email);
                map.put(put.color, color);
                map.put(put.title, title);
                map.put(put.garanty, garanty);
                map.put(put.price, price);
                map.put(put.image, image);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }


}
