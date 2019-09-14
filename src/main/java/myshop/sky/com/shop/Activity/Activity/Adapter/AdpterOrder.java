package myshop.sky.com.shop.Activity.Activity.Adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import myshop.sky.com.shop.Activity.Activity.Model.ModelOrder;
import myshop.sky.com.shop.R;

public class AdpterOrder extends RecyclerView.Adapter<AdpterOrder.viewholder>
{

    Context context;
    List<ModelOrder> modelOrders;

    public AdpterOrder(Context context, List<ModelOrder> modelOrders) {
        this.context = context;
        this.modelOrders = modelOrders;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutorder,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {


        ModelOrder order = modelOrders.get(i);
        viewholder.textemail.setText(order.getEmail());
        viewholder.textnumber.setText(order.getNumber());
        viewholder.textprice.setText(order.getAllprice()+" "+"تومان");
        viewholder.textdate.setText(order.getDate());
        viewholder.textrefid.setText(order.getRefid());
    }

    @Override
    public int getItemCount() {
        return modelOrders.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textrefid,textemail,textprice,textnumber,textdate;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewholder(@NonNull View itemView) {

            super(itemView);

            cardView = itemView.findViewById(R.id.cardvieworder);
            textdate = itemView.findViewById(R.id.textdateorder);
            textdate.setTypeface(typeface);
            textemail = itemView.findViewById(R.id.textemailorder);
            textemail.setTypeface(typeface);
            textnumber = itemView.findViewById(R.id.textnumberorder);
            textnumber.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpriceorder);
            textprice.setTypeface(typeface);
            textrefid = itemView.findViewById(R.id.textrefidorder);
            textrefid.setTypeface(typeface);
        }
    }
}
