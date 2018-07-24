package es.uniovi.uo252406.talkingfer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(20, 20, 20, 20);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.es, R.drawable.fer,
            R.drawable.secundaria,R.drawable.es, R.drawable.fer,
            R.drawable.secundaria,R.drawable.es, R.drawable.fer,
            R.drawable.secundaria,R.drawable.es, R.drawable.fer,
            R.drawable.secundaria,R.drawable.es, R.drawable.fer,
            R.drawable.secundaria,R.drawable.es, R.drawable.fer,
            R.drawable.secundaria,R.drawable.es, R.drawable.fer,
            R.drawable.secundaria,R.drawable.es, R.drawable.fer,
            R.drawable.secundaria
    };
}