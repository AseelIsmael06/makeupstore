package com.example.test345;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.List;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private List<Product> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private FirebaseServices services;
    final long FIVE_MEGABYTE = 1024 * 1024 * 5;
    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<Product> data)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.row, parent, false);
        services = new FirebaseServices();
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mData.get(position);
        services.getStorage().getReference(product.getProPhoto()).getBytes(FIVE_MEGABYTE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if (task.isSuccessful()){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length);
                    holder.picture.setImageBitmap(bitmap);
                    holder.picture.setRotation(90);
                }
                else{
                    Log.d("Download Image:", task.getException().toString());
                }
            }
        });

        holder.Name.setText(product.getProductName());
        holder.price.setText("Price: \n" + product.getProPrice() + " ₪");

    }

    // total number of rows
    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Name;
        ImageView picture;
        TextView price;

        ViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tvNameProductRow);
            picture = itemView.findViewById(R.id.ivPhotoProductRow);
            price = itemView.findViewById(R.id.tvPriceProductRow);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Product getItem(int id)
    {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


