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
import myshop.sky.com.shop.Activity.Activity.Model.ModelItem;
import myshop.sky.com.shop.R;

public class Adapter_fav extends RecyclerView.Adapter<Adapter_fav.viewHolder>
{
    Context context;
    List<ModelFav> modelFavList;

    public Adapter_fav(Context context, List<ModelFav> modelFavList)
    {
        this.context = context;
        this.modelFavList = modelFavList;
    }

    @NonNull
    @Override
    public Adapter_fav.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int holder)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layoutitem, parent, false);
        return new Adapter_fav.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_fav.viewHolder holder, int position)
    {

        final ModelFav fav = modelFavList.get(position);
        holder.textfreeprice.setVisibility(View.GONE);
        holder.textvisite.setText(fav.getVisit());
        holder.texttitle.setText(fav.getTitle());
        holder.textprice.setText(fav.getPrice() + " " + "تومان");


        if (fav.getLabel().equals("0"))
        {
            holder.textfreeprice.setVisibility(View.GONE);
            holder.textprice.setTextColor(Color.GREEN);
        } else
        {
            holder.textfreeprice.setTextColor(Color.GREEN);
            holder.textprice.setPaintFlags(holder.textprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textfreeprice.setVisibility(View.VISIBLE);
            holder.textfreeprice.setText(fav.getFreeprice() + " " + "تومان");
            holder.textprice.setTextColor(Color.RED);

        }
        Picasso
                .with(context)
                .load(fav.getImage())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, fav.getId()+"", Toast.LENGTH_SHORT).show();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Adapter_fav.this.context, Activity_wait.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(put.id, fav.getId() + "");
                int freeprice = Integer.parseInt(fav.getFreeprice());
                int price = Integer.parseInt(fav.getPrice());
                if (freeprice == price)
                {
                    intent.putExtra(put.freeprice, "");
                } else
                {
                    intent.putExtra(put.freeprice, fav.getFreeprice());
                }
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return modelFavList.size();
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
