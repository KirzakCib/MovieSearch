package com.example.moviesearch;

import android.content.Context;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

class MySimpleAdapter extends SimpleAdapter {
        private Context context;

    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
    }


    @Override
    public void setViewImage(ImageView v, String value) {
        super.setViewImage(v, value);
            Picasso.with(context).load(value).fit().centerCrop().error(R.drawable.image).into(v);
    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
    }
}
