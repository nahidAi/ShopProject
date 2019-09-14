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

import jp.shts.android.library.TriangleLabelView;
import myshop.sky.com.shop.Activity.Activity.Activity.Activity_wait;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelLike;
import myshop.sky.com.shop.R;

public class AdapterLike extends RecyclerView.Adapter<AdapterLike.viewHolder> {

    Context context;
    List<ModelLike> modelLikes;

    public AdapterLike(Context context, List<ModelLike> modelLikes) {
        this.context = context;
        this.modelLikes = modelLikes;
    }

    @NonNull
    @Override
    public AdapterLike.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutonly,parent,false);
        return new AdapterLike.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterLike.viewHolder viewHolder, int i) {

        final ModelLike like = modelLikes.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(like.getPrice()));
        viewHolder.textprice.setText(price+" "+"تومان");
        viewHolder.textvisit.setText(like.getVisit());
        viewHolder.texttitle.setText(like.getTitle());

        viewHolder.lnrShimmer.setVisibility(View.GONE);
        viewHolder.shimmerFrameLayout.startShimmer();

        if (like.getImage()!=null)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewHolder.shimmerFrameLayout.stopShimmer();
                    viewHolder.shimmerFrameLayout.setVisibility(View.GONE);
                    viewHolder.lnrShimmer.setVisibility(View.VISIBLE);
                    Picasso.with(context)
                            .load(like.getImage())
                            .skipMemoryCache()
                            .into(viewHolder.imageViewfree);
                }
            },1000);

        }


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdapterLike.this.context,Activity_wait.class);
                intent.putExtra(put.id,like.getId()+"");
                intent.putExtra(put.freeprice,"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelLikes.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ShimmerFrameLayout shimmerFrameLayout;
        LinearLayout lnrShimmer;
        TriangleLabelView labelView;
        ImageView imageViewfree;
        TextView texttitle,textvisit,textprice;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            lnrShimmer = itemView.findViewById(R.id.lnrShimmer);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            cardView = itemView.findViewById(R.id.cardviewonly);
            imageViewfree = itemView.findViewById(R.id.imageonly);
            texttitle = itemView.findViewById(R.id.texttitleonly);
            texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitonly);
            textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpriceonly);
            textprice.setTypeface(typeface);
        }
    }
}

