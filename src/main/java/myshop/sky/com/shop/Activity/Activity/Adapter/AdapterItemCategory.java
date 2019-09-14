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

import myshop.sky.com.shop.Activity.Activity.Activity.Activity_Item;
import myshop.sky.com.shop.Activity.Activity.Class.put;
import myshop.sky.com.shop.Activity.Activity.Model.ModelItemCategory;
import myshop.sky.com.shop.R;

public class AdapterItemCategory extends RecyclerView.Adapter<AdapterItemCategory.viewHolder> {


    Context context;
    List<ModelItemCategory> itemCategoryList;

    public AdapterItemCategory(Context context, List<ModelItemCategory> itemCategories) {
        this.context = context;
        this.itemCategoryList = itemCategories;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitemcategory, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        final ModelItemCategory category = itemCategoryList.get(i);
        viewHolder.textView.setText(category.getTitle());
        Picasso
                .with(context)
                .load(category.getImage())
                .into(viewHolder.circleImageView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdapterItemCategory.this.context, Activity_Item.class);
                intent.putExtra(put.id, category.getId() + "");
                intent.putExtra(put.title, category.getTitle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemCategoryList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView circleImageView;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardItemcategory);
            circleImageView = itemView.findViewById(R.id.imageitemcategory);
            textView = itemView.findViewById(R.id.textitemcategory);
        }
    }
}

