package myshop.sky.com.shop.Activity.Activity.Class;


import android.app.Application;

import myshop.sky.com.shop.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class G extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Vazir-Medium-FD-WOL.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
