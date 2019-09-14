package myshop.sky.com.shop.Activity.Activity.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


import myshop.sky.com.shop.Activity.Activity.Activity.Activity_wait;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelOnly;
import myshop.sky.com.shop.R;

public class AdapterOnly  extends RecyclerView.Adapter<AdapterOnly.viewHolder> {
    Context context;
    List<ModelOnly> modelOnlies;


    public AdapterOnly(Context context, List<ModelOnly> modelOnlies) {
        this.context = context;
        this.modelOnlies = modelOnlies;
    }

    @NonNull
    @Override
    public AdapterOnly.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutonly, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        final ModelOnly only = modelOnlies.get(position);

        //سه رقمی کردن اعداد
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(only.getPrice()));
        holder.textprice.setText(price + "  " + "تومان");
        holder.textvisit.setText(only.getVisit());
        holder.texttitle.setText(only.getTitle());
        holder.lnrShimmer.setVisibility(View.GONE);
        if (only.getImage()!=null)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    holder.shimmerFrameLayout.stopShimmer();
                    holder.shimmerFrameLayout.setVisibility(View.GONE);
                    holder.lnrShimmer.setVisibility(View.VISIBLE);
                    holder.shimmerFrameLayout.startShimmer();

                    Picasso
                            .with(context)
                            .load(only.getImage())
                            .into(holder.imageViewfree);

                }
            },2000);



        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdapterOnly.this.context, Activity_wait.class);
                intent.putExtra(put.id,only.getId()+"");
                intent.putExtra(put.freeprice,"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return modelOnlies.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewfree;
        TextView texttitle, textvisit, textprice;
        ShimmerFrameLayout shimmerFrameLayout;
        LinearLayout lnrShimmer;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Vazir-Medium-FD-WOL.ttf");

        public viewHolder(View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            lnrShimmer = itemView.findViewById(R.id.lnrShimmer);
            cardView = itemView.findViewById(R.id.cardviewonly);
            imageViewfree = itemView.findViewById(R.id.imageonly);
            texttitle = itemView.findViewById(R.id.texttitleonly);
              texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitonly);
             textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpriceonly);
             textprice.setTypeface(typeface);
            //textfree = itemView.findViewById(R.id.textfreeprice);
            //  textfree.setTypeface(typeface);
        }
    }
}

