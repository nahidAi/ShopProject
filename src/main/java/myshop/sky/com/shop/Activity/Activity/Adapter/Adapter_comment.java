package myshop.sky.com.shop.Activity.Activity.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import myshop.sky.com.shop.Activity.Activity.Model.ModelComment;
import myshop.sky.com.shop.R;

public class Adapter_comment extends RecyclerView.Adapter<Adapter_comment.viewholder>
{

    Context context;
    List<ModelComment> modelCommentList;

    public Adapter_comment(Context context, List<ModelComment> modelCommentList)
    {
        this.context = context;
        this.modelCommentList = modelCommentList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutcomment, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i)
    {
        ModelComment comment = modelCommentList.get(i);
        viewholder.textuser.setText(comment.getUser());
        viewholder.textpo.setText(comment.getPosotive());
        viewholder.textne.setText(comment.getNegative());
        viewholder.textcomment.setText(comment.getComment());
        Picasso
                .with(context)
                .load(comment.getImage())
                .skipMemoryCache()
                .into(viewholder.circleImageView);
        viewholder.ratingBar.setRating(comment.getRating());

    }

    @Override
    public int getItemCount()
    {
        return modelCommentList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {

        TextView textcomment, textuser, textpo, textne;
        CircleImageView circleImageView;
        ScaleRatingBar ratingBar;

        public viewholder(@NonNull View itemView)
        {
            super(itemView);

            textcomment = itemView.findViewById(R.id.textCommnet);
            textne = itemView.findViewById(R.id.textNegativeComment);
            textpo = itemView.findViewById(R.id.textPosotiveComment);
            textuser = itemView.findViewById(R.id.textUserComment);
            ratingBar = itemView.findViewById(R.id.ratingbarcomment);
            circleImageView = itemView.findViewById(R.id.imageUserComment);
        }
    }
}
