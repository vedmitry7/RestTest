package com.vedmitryapps.httptest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vedmitryapps.httptest.R;
import com.vedmitryapps.httptest.api.model.Picture;

import java.util.List;


public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ViewHolder> {

    private List<Picture> pictures;
    private Context context;
    private DisplayMetrics displayMetrics;


    public PicturesAdapter(List<Picture> pictures, Context context) {
        this.pictures = pictures;
        this.context = context;
        displayMetrics = context.getResources().getDisplayMetrics();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picture pic = pictures.get(position);
        String url = pic.getLinks().getDownload();
        if(url != null)
   /*     Picasso.with(context)
                .load(pic.getUrls().getSmall())
                .resize(displayMetrics.widthPixels/3, displayMetrics.widthPixels/3)
                .centerCrop()
                .into(holder.imageView);*/
        Glide
                .with(context)

                .load(pic.getUrls().getSmall())
                .override(displayMetrics.widthPixels/3, displayMetrics.widthPixels/3)
                .centerCrop()
                //.fitCenter() // другой вариант
                .into(holder.imageView);
        holder.textView.setText(""+ position);
    }

    @Override
    public int getItemCount() {
        if (pictures == null)
            return 0;
        return pictures.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textPosition);
        }

        @Override
        public void onClick(View view) {

        }
    }
}