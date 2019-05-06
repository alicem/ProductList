package com.testt.productlist.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.testt.productlist.R;
import com.testt.productlist.zomponents.TouchImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link ImageActivity} is full screen image presentation
 * {@link android.app.Activity}
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.ui
 */
public class ImageActivity extends AppCompatActivity {

    /**
     * butterknife {@link ButterKnife}
     * UI binded  {@link TouchImageView}
     * <p>
     * image will be placed in
     */
    @BindView(R.id.imageView)
    TouchImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Picasso.get().load(getIntent().getExtras().getString("url"))
                .into(imageView);

    }
}
