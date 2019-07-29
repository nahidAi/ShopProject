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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Activity.Activity_Item_Category;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelCategory;
import myshop.sky.com.shop.R;

public class AdapterCatrgory extends RecyclerView.Adapter<AdapterCatrgory.viewHolder>{
    Context context;
    List<ModelCategory> modelCategoryList;

    public AdapterCatrgory(Context context, List<ModelCategory> modelCategoryList) {
        this.context = context;
        this.modelCategoryList = modelCategoryList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layoutcategory,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final ModelCategory category = modelCategoryList.get(position);
        holder.textView.setText(category.getTitlecategory());
        Picasso.with(context)
                .load(category.getImage())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdapterCatrgory.this.context, Activity_Item_Category.class);
                intent.putExtra(put.id,category.getId()+"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

               // Toast.makeText(context, category.getId()+"", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelCategoryList.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;
        public viewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewCategory);
            imageView = itemView.findViewById(R.id.imageCategory);
            textView = itemView.findViewById(R.id.texttilteCtegory);
        }
        }
    }


