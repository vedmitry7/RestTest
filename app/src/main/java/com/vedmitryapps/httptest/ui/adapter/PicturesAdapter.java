package com.vedmitryapps.httptest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
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

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.post.setText(Html.fromHtml(post.getElementPureHtml(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.post.setText(Html.fromHtml(post.getElementPureHtml()));
        }
        holder.site.setText(post.getSite());*/

        String url = pic.getLinks().getDownload();
        System.out.println("qqqqqqqqqqqqq = " + url);

        if(url != null)
        Picasso.with(context)
                .load(pic.getUrls().getSmall())
                .resize(displayMetrics.widthPixels, displayMetrics.heightPixels)
                .centerInside()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (pictures == null)
            return 0;
        return pictures.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}