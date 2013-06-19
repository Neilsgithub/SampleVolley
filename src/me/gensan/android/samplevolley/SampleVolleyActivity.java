
package me.gensan.android.samplevolley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class SampleVolleyActivity extends Activity {

    private ImageCache mCache;
    private ImageLoader mLoader;
    private RequestQueue mQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_volley);
        sampleVolley();
    }

    private void sampleVolley() {
        mCache = getCache();
        mQueue = Volley.newRequestQueue(getApplicationContext());
        mLoader = new ImageLoader(mQueue, mCache);
        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.imageView);
        imageView
                .setImageUrl(
                        "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
                        mLoader);
    }

    private ImageCache getCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        final ImageCache cache = new LoaderImageCache(cacheSize);
        return cache;
    }

    private class LoaderImageCache extends LruCache<String, Bitmap> implements ImageCache {

        public LoaderImageCache(final int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }

        @Override
        public Bitmap getBitmap(final String url) {
            return get(url);
        }

        @Override
        public void putBitmap(final String url, final Bitmap bitmap) {
            put(url, bitmap);
        }

    }

}
