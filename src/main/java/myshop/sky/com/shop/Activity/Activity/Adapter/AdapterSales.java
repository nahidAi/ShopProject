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
import myshop.sky.com.shop.Activity.Activity.Model.ModelBestSales;
import myshop.sky.com.shop.R;



public class AdapterSales extends RecyclerView.Adapter<AdapterSales.viewHolder> {
    Context context;
    List<ModelBestSales> modelBestSales;

    public AdapterSales(Context context, List<ModelBestSales> modelBestSales) {
        this.context = context;
        this.modelBestSales = modelBestSales;
    }

    @NonNull
    @Override
    public AdapterSales.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutbestsales, parent, false);
        return new AdapterSales.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSales.viewHolder holder, int position) {
        final ModelBestSales sales = modelBestSales.get(position);

        //سه رقمی کردن اعداد
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(sales.getPrice()));
        holder.textprice.setText(price + "  " + "تومان");
        holder.textvisit.setText(sales.getVisit());
        holder.texttitle.setText(sales.getTitle());

        Picasso
                .with(context)
                .load(sales.getImage())
                .into(holder.imageViewfree);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdapterSales.this.context, Activity_wait.class);
                intent.putExtra(put.id,sales.getId()+"");
                intent.putExtra(put.freeprice,"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return modelBestSales.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewfree;
        TextView texttitle, textvisit, textprice;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Vazir-Medium-FD-WOL.ttf");

        public viewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewsalse);
            imageViewfree = itemView.findViewById(R.id.imagesalse);
            texttitle = itemView.findViewById(R.id.texttitlesalse);
            texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitsalse);
            textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpricesalse);
            textprice.setTypeface(typeface);
            //textfree = itemView.findViewById(R.id.textfreeprice);
            //  textfree.setTypeface(typeface);
        }
    }
}

