package com.testt.productlist.ui;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.testt.productlist.R;
import com.testt.productlist.entity.ProductEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for {@link ListActivity#recyclerView}
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.ui
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    /**
     * {@link ProductAdapter} inner actions callback
     */
    public interface Listener {
        void itemClicked(ProductEntity productEntity);
    }

    /**
     * callback instance
     */
    private Listener mListener;

    /**
     * data list
     */
    private List<ProductEntity> mProductModelList;

    /**
     * ui elements
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.nameTextView)
        TextView nameTextView;
        @BindView(R.id.brandTextView)
        TextView brandTextView;
        @BindView(R.id.ordinaryPriceTextView)
        TextView ordinaryPriceTextView;
        @BindView(R.id.currentPriceTextView)
        TextView currentPriceTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     *
     * @param mProductModelList item list
     * @param listener context - make sure {@link Listener} is
     *                 implemented
     */
    public ProductAdapter(List<ProductEntity> mProductModelList, Listener listener) {
        this.mProductModelList = mProductModelList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ProductEntity item = mProductModelList.get(position);
        Picasso.get().load(item.getUrl())
                .resize(240, 240)
                .centerCrop()
                .into(holder.imageView);

        holder.brandTextView.setText(item.getBrand());
        holder.nameTextView.setText(" - " + item.getName());
        holder.ordinaryPriceTextView.setText(item.getOriginal_price() + " " + item.getCurrency());
        holder.currentPriceTextView.setText(item.getCurrent_price() + " " + item.getCurrency());

        if (item.getOriginal_price() == item.getCurrent_price()) {
            holder.ordinaryPriceTextView.setVisibility(View.INVISIBLE);
        } else {
            holder.ordinaryPriceTextView.setVisibility(View.VISIBLE);
        }

        holder.ordinaryPriceTextView.setPaintFlags(holder.ordinaryPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.itemClicked(item);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductModelList.size();
    }
}