package myshop.sky.com.shop.Activity.Activity.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Activity.Activity_wait;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelFav;
import myshop.sky.com.shop.Activity.Activity.Model.ModelItembanner;
import myshop.sky.com.shop.R;

public class AdapterItemBanner extends RecyclerView.Adapter<AdapterItemBanner.viewHolder>
{
    Context context;
    List<ModelItembanner> modelItembannerList;

    public AdapterItemBanner(Context context, List<ModelItembanner>modelItembannerList )
    {
        this.context = context;
        this.modelItembannerList = modelItembannerList;
    }

    @NonNull
    @Override
    public AdapterItemBanner.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int holder)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layoutitem, parent, false);
        return new AdapterItemBanner.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemBanner.viewHolder holder, int position)
    {

        final ModelItembanner modelItembanner = modelItembannerList.get(position);
        holder.textfreeprice.setVisibility(View.GONE);
        holder.textvisite.setText(modelItembanner.getVisit());
        holder.texttitle.setText(modelItembanner.getTitle());
        holder.textprice.setText(modelItembanner.getPrice() + " " + "تومان");


        if (modelItembanner.getLabel().equals("0"))
        {
            holder.textfreeprice.setVisibility(View.GONE);
            holder.textprice.setTextColor(Color.GREEN);
        } else
        {
            holder.textfreeprice.setTextColor(Color.GREEN);
            holder.textprice.setPaintFlags(holder.textprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textfreeprice.setVisibility(View.VISIBLE);
            holder.textfreeprice.setText(modelItembanner.getFreeprice() + " " + "تومان");
            holder.textprice.setTextColor(Color.RED);

        }
        Picasso
                .with(context)
                .load(modelItembanner.getImage())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, modelItembanner.getId()+"", Toast.LENGTH_SHORT).show();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdapterItemBanner.this.context, Activity_wait.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(put.id, modelItembanner.getId() + "");
                int freeprice = Integer.parseInt(modelItembanner.getFreeprice());
                int price = Integer.parseInt(modelItembanner.getPrice());
                if (freeprice == price)
                {
                    intent.putExtra(put.freeprice, "");
                } else
                {
                    intent.putExtra(put.freeprice, modelItembanner.getFreeprice());
                }
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return modelItembannerList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        TextView texttitle, textvisite, textprice, textfreeprice;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");


        public viewHolder(View itemView)
        {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewitem);
            imageView = itemView.findViewById(R.id.imageitem);
            textfreeprice = itemView.findViewById(R.id.textfreeitemfree);
            textfreeprice.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textitemprice);
            textprice.setTypeface(typeface);
            texttitle = itemView.findViewById(R.id.texttitleitem);
            texttitle.setTypeface(typeface);
            textvisite = itemView.findViewById(R.id.textvisiteitem);
            textvisite.setTypeface(typeface);
        }
    }
}
