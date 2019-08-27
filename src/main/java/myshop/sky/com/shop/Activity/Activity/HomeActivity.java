package myshop.sky.com.shop.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;
import myshop.sky.com.shop.Activity.Activity.Activity.Activity_Favorit;
import myshop.sky.com.shop.Activity.Activity.Activity.Activity_Login;
import myshop.sky.com.shop.Activity.Activity.Activity.Activity_Profile;
import myshop.sky.com.shop.Activity.Activity.Activity.Activity_Search;
import myshop.sky.com.shop.Activity.Activity.Activity.Activity_basket;
import myshop.sky.com.shop.Activity.Activity.Activity.Activity_category;
import myshop.sky.com.shop.Activity.Activity.Adapter.AdaperVisite;
import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterBanner;
import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterFree;
import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterOnly;
import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterSales;
import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelBanner;
import myshop.sky.com.shop.Activity.Activity.Model.ModelBestSales;
import myshop.sky.com.shop.Activity.Activity.Model.ModelFree;
import myshop.sky.com.shop.Activity.Activity.Model.ModelOnly;
import myshop.sky.com.shop.Activity.Activity.Model.Modelvisit;
import myshop.sky.com.shop.R;

public class HomeActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener
{
    private SliderLayout mDemoSlider;
    AdapterFree adapterFree;
    List<ModelFree> modelFrees = new ArrayList<>();
    RecyclerView recyFree, recyOnly, recyVisit, recySales, recyBanner;
    AdapterOnly adapterOnly;
    List<ModelOnly> modelOnlies = new ArrayList<>();
    AdapterBanner adapterBanner;
    List<ModelBanner> modelBannerList = new ArrayList<>();
    AdaperVisite adapterVisit;
    List<Modelvisit> visits = new ArrayList<>();
    AdapterSales adapterSales;
    List<ModelBestSales> modelBestSales = new ArrayList<>();
    ImageView imageNav, imagebasketHome,imageSearch;
    DrawerLayout drawerLayout;
    TextView txtLogin, textCount;
    CardView cardCategory;
    String session;
    CircleImageView circleImageView;
    String imageUser;
    LinearLayout lnrFav;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        findView();


        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        txtLogin.setText(preferences.getString(put.email, "عضویت و ورود"));
        session = preferences.getString(put.email, "عضویت و ورود");

        imageUser = preferences.getString(put.image, "");
        if (imageUser.equals("")) {
            Picasso
                    .with(getApplicationContext())
                    .load(R.drawable.userprofile)
                    .into(circleImageView);
        } else {
            Picasso
                    .with(getApplicationContext())
                    .load(imageUser)
                    .into(circleImageView);
        }


    }


    public void findView()
    {
        lnrFav = findViewById(R.id.lnrFav);
        imageSearch = findViewById(R.id.imagesearch);
        circleImageView = findViewById(R.id.circleImageUser);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        recyFree = findViewById(R.id.recyfree);
        recyOnly = findViewById(R.id.recyonly);
        recyBanner = findViewById(R.id.recybanner);
        recyVisit = findViewById(R.id.recyvisit);
        recySales = findViewById(R.id.recysales);
        imageNav = findViewById(R.id.imageNav);
        drawerLayout = findViewById(R.id.drawer);
        txtLogin = findViewById(R.id.textLogin);
        cardCategory = findViewById(R.id.cardCat);
        textCount = findViewById(R.id.textCount);
        imagebasketHome = findViewById(R.id.imagebasketHome);
        onClick();
        getFreeProduct();
        getOnlyProduct();
        getVisitProduct();
        getsaleProduct();
        installSliderLayout();
        setUpRecyclerview();
        getbannerData();

    }

    public void onClick()
    {
        lnrFav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, Activity_Favorit.class));
               // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        imageSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HomeActivity.this, Activity_Search.class);
                startActivity(intent);
            }
        });
        imageNav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawerLayout.openDrawer(Gravity.RIGHT);

            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (txtLogin.getText().equals("عضویت و ورود"))
                {

                    Intent intent = new Intent(HomeActivity.this, Activity_Login.class);
                    //------1
                    startActivityForResult(intent, put.REQUEST_CODE);
                } else
                {
                    Intent intent = new Intent(HomeActivity.this, Activity_Profile.class);
                    startActivityForResult(intent, put.REQUEST_EXITE);

                }


            }
        });
        cardCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, Activity_category.class));
            }
        });
        imagebasketHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, Activity_basket.class));
            }
        });
    }

    public void installSliderLayout()
    {
        Map<String, String> url_image = new TreeMap<>();
        url_image.put("image1", "http://uupload.ir/files/2zds_s4.png");
        url_image.put("image2", "http://uupload.ir/files/a7xa_s2.png");
        url_image.put("image3", "http://uupload.ir/files/yua_s6.png");
        url_image.put("image4", "http://uupload.ir/files/eid_s7.png");

        for (String name : url_image.keySet())
        {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_image.get(name))
                    .setOnSliderClickListener(this)
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            mDemoSlider.setDuration(3000);
            mDemoSlider.addSlider(textSliderView);

        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider)
    {
        String s = slider.getBundle().get("extra") + "";
        if (s.equals("image1"))
        {
            Toast.makeText(this, "image1 is clicked", Toast.LENGTH_SHORT).show();
        } else if (s.equals("image2"))
        {
            Toast.makeText(this, "image2 is clicked", Toast.LENGTH_SHORT).show();
        } else if (s.equals("image3"))
        {
            Toast.makeText(this, "image3 is clicked", Toast.LENGTH_SHORT).show();
        } else if (s.equals("image4"))
        {
            Toast.makeText(this, "image4 is clicked", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {

    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    private void setUpRecyclerview()
    {
        adapterFree = new AdapterFree(getApplicationContext(), modelFrees);
        recyFree.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false));
        recyFree.setAdapter(adapterFree);

      /*  modelFrees.add(new ModelFree(1, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "5667777", "20"));
        modelFrees.add(new ModelFree(2, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "56777", "30"));
        modelFrees.add(new ModelFree(3, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "56677", "40"));
        modelFrees.add(new ModelFree(4, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "566777", "56"));
        modelFrees.add(new ModelFree(5, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "566777", "56"));
        modelFrees.add(new ModelFree(6, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "566777", "56"));*/


        adapterOnly = new AdapterOnly(getApplicationContext(), modelOnlies);
        recyOnly.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false));
        recyOnly.setAdapter(adapterOnly);

       /* modelOnlies.add(new ModelOnly(1, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "566777"));
        modelOnlies.add(new ModelOnly(2, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "5667777"));
        modelOnlies.add(new ModelOnly(3, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "5667"));
        modelOnlies.add(new ModelOnly(4, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "566777"));
        modelOnlies.add(new ModelOnly(5, "http://uupload.ir/files/eid_s7.png", "گوشی ایفون", "18", "5667777"));*/


//
        adapterVisit = new AdaperVisite(getApplicationContext(), visits);
        recyVisit.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false));
        recyVisit.setAdapter(adapterVisit);
      /*  visits.add(new Modelvisit(1, "http://uupload.ir/files/eid_s7.png", "لپ تاپ", "56", "2346666"));
        visits.add(new Modelvisit(2, "http://uupload.ir/files/eid_s7.png", "لپ تاپ", "56", "2346666"));
        visits.add(new Modelvisit(3, "http://uupload.ir/files/eid_s7.png", "لپ تاپ", "56", "2346666"));
        visits.add(new Modelvisit(4, "http://uupload.ir/files/eid_s7.png", "لپ تاپ", "56", "2346666"));
        visits.add(new Modelvisit(5, "http://uupload.ir/files/eid_s7.png", "لپ تاپ", "56", "2346666"));*/
//
        adapterSales = new AdapterSales(getApplicationContext(), modelBestSales);
        recySales.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false));
        recySales.setAdapter(adapterSales);
       /* modelBestSales.add(new ModelBestSales(1, "http://uupload.ir/files/eid_s7.png", "گوشی سامسونگ", "43", "676767"));
        modelBestSales.add(new ModelBestSales(2, "http://uupload.ir/files/eid_s7.png", "گوشی سامسونگ", "43", "676767"));
        modelBestSales.add(new ModelBestSales(3, "http://uupload.ir/files/eid_s7.png", "گوشی سامسونگ", "43", "676767"));
        modelBestSales.add(new ModelBestSales(4, "http://uupload.ir/files/eid_s7.png", "گوشی سامسونگ", "43", "676767"));*/
//
        adapterBanner = new AdapterBanner(getApplicationContext(), modelBannerList);
        recyBanner.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyBanner.setAdapter(adapterBanner);


       /* modelBannerList.add(new ModelBanner(1, "http://uupload.ir/files/eid_s7.png"));
        modelBannerList.add(new ModelBanner(2, "http://uupload.ir/files/eid_s7.png"));
        modelBannerList.add(new ModelBanner(3, "http://uupload.ir/files/eid_s7.png"));
        modelBannerList.add(new ModelBanner(4, "http://uupload.ir/files/eid_s7.png"));*/


    }

    @Override
    ////------3
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == put.REQUEST_CODE && resultCode == RESULT_OK)
        {
            String email = data.getStringExtra(put.email);
            String image = data.getStringExtra(put.image);
            txtLogin.setText(email);
// گفتیم مقدار این ایمیل رو که لاگین کرد توی شیردپرفرنس نگهش دار
            SharedPreferences sharedPreferences = getSharedPreferences(put.shared, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(put.email, email);
            editor.putString(put.image, image);
           // Toast.makeText(this,"link url is ..."+ image, Toast.LENGTH_SHORT).show();
            editor.commit();
            recreate();

        } else if (requestCode == put.REQUEST_EXITE && resultCode == RESULT_OK)
        {
            String email = data.getStringExtra(put.email);
            String image = data.getStringExtra(put.image);
            if (image.equals("")) {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.userprofile)
                        .into(circleImageView);
            } else {
                Picasso
                        .with(getApplicationContext())
                        .load(image)
                        .into(circleImageView);

            }

            txtLogin.setText(email);
            recreate();

        }
    }

    private void getFreeProduct()
    {
        String url = Link.linkFreeProduct;
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage(" در حال دریافت اطلاعات");
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                Gson gson = new Gson();
                ModelFree[] frees = gson.fromJson(response.toString(), ModelFree[].class);
                for (int i = 0; i < frees.length; i++)
                {
                    modelFrees.add(frees[i]);
                    adapterFree.notifyDataSetChanged();

                }
                progressDialog.dismiss();


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    private void getOnlyProduct()
    {
        String url = Link.linkOnlyProduct;
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage(" در حال دریافت اطلاعات");
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                Gson gson = new Gson();
                ModelOnly[] onlies = gson.fromJson(response.toString(), ModelOnly[].class);
                for (int i = 0; i < onlies.length; i++)
                {
                    modelOnlies.add(onlies[i]);
                    adapterOnly.notifyDataSetChanged();

                }
                progressDialog.dismiss();


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    private void getVisitProduct()
    {
        String url = Link.linkVisitProduct;
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage(" در حال دریافت اطلاعات");
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                Gson gson = new Gson();
                Modelvisit[] modelvisits = gson.fromJson(response.toString(), Modelvisit[].class);
                for (int i = 0; i < modelvisits.length; i++)
                {
                    visits.add(modelvisits[i]);
                    adapterVisit.notifyDataSetChanged();

                }
                progressDialog.dismiss();


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    private void getbannerData()
    {
        String url = Link.linkbannerData;
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage(" در حال دریافت اطلاعات");
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                Gson gson = new Gson();
                ModelBanner[] modelBanners = gson.fromJson(response.toString(), ModelBanner[].class);
                for (int i = 0; i < modelBanners.length; i++)
                {
                    modelBannerList.add(modelBanners[i]);
                    adapterVisit.notifyDataSetChanged();

                }
                progressDialog.dismiss();


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
    private void getsaleProduct()
    {
        String url = Link.linksaleProduct;
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage(" در حال دریافت اطلاعات");
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                Gson gson = new Gson();
                ModelBestSales[] modelSales = gson.fromJson(response.toString(), ModelBestSales[].class);
                for (int i = 0; i < modelSales.length; i++)
                {
                    modelBestSales.add(modelSales[i]);
                    adapterVisit.notifyDataSetChanged();

                }
                progressDialog.dismiss();


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    private void getCount(final String email)
    {
        String url = Link.linkِGetCount;
        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                textCount.setText(response.toString());
                SharedPreferences preferences = getSharedPreferences("c", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("count", textCount.getText().toString());
                editor.apply();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getCount(session);
    }
}
