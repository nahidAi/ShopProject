package myshop.sky.com.shop.Activity.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
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

import myshop.sky.com.shop.Activity.Activity.Activity.Activity_wait;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.Modelvisit;
import myshop.sky.com.shop.R;


public class AdaperVisite extends RecyclerView.Adapter<AdaperVisite.viewHolder> {
    Context context;
    List<Modelvisit> modelvisits;

    public AdaperVisite(Context context, List<Modelvisit> modelvisits) {
        this.context = context;
        this.modelvisits = modelvisits;
    }

    @NonNull
    @Override
    public AdaperVisite.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutvisit, parent, false);
        return new AdaperVisite.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaperVisite.viewHolder holder, int position) {
        final Modelvisit free = modelvisits.get(position);
        //سه رقمی کردن اعداد
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(free.getPrice()));
        holder.textprice.setText(price+" "+"تومان");

        holder.textvisit.setText(free.getVisit());
        holder.texttitle.setText(free.getTitle());
        Picasso
                .with(context)
                .load(free.getImage())
                .into(holder.imageViewfree);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdaperVisite.this.context, Activity_wait.class);
                intent.putExtra(put.id,free.getId()+"");
                intent.putExtra(put.freeprice,"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelvisits.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewfree;
        TextView texttitle, textvisit, textprice;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Vazir-Medium-FD-WOL.ttf");


        public viewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewvisit);
            imageViewfree = itemView.findViewById(R.id.imagevisit);
            texttitle = itemView.findViewById(R.id.texttitlevisit);
            texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitvisit);
            textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpricevisit);
            textprice.setTypeface(typeface);
            //textfree = itemView.findViewById(R.id.textfreeprice);
            //  textfree.setTypeface(typeface);
        }
    }
}

