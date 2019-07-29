package myshop.sky.com.shop.Activity.Activity.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Activity.Activity_Banner;
import myshop.sky.com.shop.Activity.Activity.Model.ModelBanner;
import myshop.sky.com.shop.R;

public class AdapterBanner extends RecyclerView.Adapter<AdapterBanner.viewHolder> {


    Context context;
    List<ModelBanner> modelBanners;

    public AdapterBanner(Context context, List<ModelBanner> modelBanners) {
        this.context = context;
        this.modelBanners = modelBanners;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutbanner,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        final ModelBanner banner = modelBanners.get(i);
        Picasso.with(context)
                .load(banner.getImage())
                .into(viewHolder.imageView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdapterBanner.this.context,Activity_Banner.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("banner",banner.getId()+"");
                //intent.putExtra(put.freeprice,"");
                context.startActivity(intent);
                //Toast.makeText(context, banner.getId()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelBanners.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardviewBanner);
            imageView = itemView.findViewById(R.id.imageBanner);
        }
    }
}
