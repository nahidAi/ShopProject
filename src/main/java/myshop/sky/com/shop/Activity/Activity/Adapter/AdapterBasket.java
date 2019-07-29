package myshop.sky.com.shop.Activity.Activity.Adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myshop.sky.com.shop.Activity.Activity.Class.Link;
import myshop.sky.com.shop.Activity.Activity.Class.MySingleton;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelBasket;
import myshop.sky.com.shop.Activity.Activity.OnloadPrice;
import myshop.sky.com.shop.R;

public class AdapterBasket extends RecyclerView.Adapter<AdapterBasket.viewHolder>
{
    Context context;
    List<ModelBasket> modelBasketList;
    private OnloadPrice onloadPrice;

    public AdapterBasket(Context context, List<ModelBasket> modelBaskets)
    {
        this.context = context;
        this.modelBasketList = modelBaskets;
    }

    public void setOnloadPrice(OnloadPrice onloadPrice)
    {
        this.onloadPrice = onloadPrice;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layoutbasket, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position)
    {
        final ModelBasket basket = modelBasketList.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(basket.getPrice()));
        String allprice = decimalFormat.format(Integer.valueOf(basket.getAllprice()));

        holder.textallprice.setText(allprice + " " + "تومان");
        holder.textprice.setText(price + " " + "تومان");
        holder.textgaranty.setText(basket.getGaranty());
        holder.textnumber.setText(basket.getNumber() + " " + "عدد");
        holder.texttitle.setText(basket.getTitle());
        holder.textcolor.setText(basket.getColor());
        Picasso
                .with(context)
                .load(basket.getImage())
                .skipMemoryCache()
                .into(holder.imageBasket);
        holder.textdelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Toast.makeText(context, basket.getId()+"", Toast.LENGTH_SHORT).show();
                if (onloadPrice != null)
                {
                    onloadPrice.onloadprice();
                    deletebasket(basket.getId() + "");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            modelBasketList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, modelBasketList.size());
                        }
                    }, 2000);

                }


            }
        });


    }

    @Override
    public int getItemCount()
    {
        return modelBasketList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageBasket;
        TextView texttitle, textnumber, textcolor, textgaranty, textprice, textallprice, textdelete;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewHolder(View itemView)
        {
            super(itemView);
            texttitle = itemView.findViewById(R.id.textTitleBasket);
            texttitle.setTypeface(typeface);
            textcolor = itemView.findViewById(R.id.textColorBasket);
            textcolor.setTypeface(typeface);
            textnumber = itemView.findViewById(R.id.textNumberBasket);
            textnumber.setTypeface(typeface);
            textgaranty = itemView.findViewById(R.id.textGarantyBasket);
            textgaranty.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.priceBasket);
            textprice.setTypeface(typeface);
            textallprice = itemView.findViewById(R.id.textAllpriceBasket);
            textallprice.setTypeface(typeface);
            textdelete = itemView.findViewById(R.id.textDeleteBasket);
            textdelete.setTypeface(typeface);
            imageBasket = itemView.findViewById(R.id.imageBasket);
        }
    }

    private void deletebasket(final String id)
    {
        String url = Link.linkِDeleteBsket;
        Response.Listener<String> listener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                // Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, id);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
}
