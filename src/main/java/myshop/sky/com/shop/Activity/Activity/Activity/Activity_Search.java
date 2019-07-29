package myshop.sky.com.shop.Activity.Activity.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Adapter.AdapterSearch;
import myshop.sky.com.shop.Activity.Activity.Model.ModelSearch;
import myshop.sky.com.shop.R;

public class Activity_Search extends AppCompatActivity
{
    EditText edsearch;
    RecyclerView recySearch;
    AdapterSearch adapterSearch;
    List<ModelSearch> modelSearches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);
        findview();
    }

    private void findview()
    {
        edsearch = findViewById(R.id.edsearch);
        recySearch = findViewById(R.id.recysearch);
        adapterSearch = new AdapterSearch(getApplicationContext(), modelSearches);
        recySearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recySearch.setAdapter(adapterSearch);
        recySearch.hasFixedSize();
        editor();
    }

    // برای اینکه رویداد کایک ادیت تکست کار کنه این متد را مینویسیم
    private void editor()
    {
        edsearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {

                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                   /* array=modelSearches.isEmpty();
                    if (array)
                    {
                        getsearch(edsearch.getText().toString());
                        edsearch.setText("");
                    }
                    else
                    {
                        modelSearches.clear();
                        adapterSearch.notifyDataSetChanged();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                getsearch(edsearch.getText().toString());
                                edsearch.setText("");
                            }
                        },100);
                    }*/

                    Toast.makeText(Activity_Search.this, edsearch.getText().toString(), Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });

    }
}
