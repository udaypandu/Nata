package nata.com.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import nata.com.nata.R;


public class ImageAdapter extends BaseAdapter {
    private Context context;
    private Integer[] images = {R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,R.drawable.sponsor1, R.drawable.sponsor3,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5
           };

    public ImageAdapter(Context c) {
        context = c;
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new GridView.LayoutParams(220, 200));
        imageView.setPadding(1, 1, 1, 1);
        return imageView;
    }
}
