package myshop.sky.com.shop.Activity.Activity.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_about extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_about);

        View aboutPage = new AboutPage(this)
                .isRTL(true)
                .setImage(R.drawable.banner_1)
                .addEmail("elmehdi.sakout@gmail.com")
                .addWebsite("newskylearn20.ir")
                .addInstagram("n.a.mehr")
                .setDescription("خرید راحت و ارزان با فروشگاه اینترنتی فروشگاه من ")
                .create();
        setContentView(aboutPage);

    }
    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
