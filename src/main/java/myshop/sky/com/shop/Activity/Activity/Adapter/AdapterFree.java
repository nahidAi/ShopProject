package myshop.sky.com.shop.Activity.Activity.Adapter;


import android.content.Context;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;
import myshop.sky.com.shop.Activity.Activity.Activity.Activity_wait;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelFree;
import myshop.sky.com.shop.R;

public class AdapterFree extends RecyclerView.Adapter<AdapterFree.viewHolder>
{
    Context context;
    List<ModelFree> modelFrees;

    public AdapterFree(Context context, List<ModelFree> modelFrees)
    {
        this.context = context;
        this.modelFrees = modelFrees;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutfree, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position)
    {
        final ModelFree free = modelFrees.get(position);
        //سه رقمی کردن اعداد
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(free.getFreeprice()));
        holder.textprice.setText(price + " " + "تومان");
        holder.textfree.setPaintFlags(holder.textfree.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        String price2 = decimalFormat.format(Integer.valueOf(free.getPrice()));
        holder.textfree.setText(price2);

        holder.textvisit.setText(free.getVisit());
        holder.texttitle.setText(free.getTitle());
        holder.labelView.setSecondaryText(free.getLable() + "%");
        Picasso
                .with(context)
                .load(free.getImage())
                .into(holder.imageViewfree);
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(context, free.getId() + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdapterFree.this.context, Activity_wait.class);
                intent.putExtra(put.id, free.getId() + "");
                intent.putExtra(put.label, free.getLable());
                intent.putExtra(put.freeprice, free.getFreeprice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return modelFrees.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TriangleLabelView labelView;
        ImageView imageViewfree;
        TextView texttitle, textvisit, textprice, textfree;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Vazir-Medium-FD-WOL.ttf");


        public viewHolder(View itemView)
        {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewfree);
            labelView = itemView.findViewById(R.id.label);

            imageViewfree = itemView.findViewById(R.id.imagefree);
            texttitle = itemView.findViewById(R.id.texttitle);
            texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitfree);
            textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpricefree);
            textprice.setTypeface(typeface);
            textfree = itemView.findViewById(R.id.textfreeprice);
            textfree.setTypeface(typeface);
        }
    }
}
