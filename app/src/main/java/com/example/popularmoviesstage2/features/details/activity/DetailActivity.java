package com.example.popularmoviesstage2.features.details.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static String MOVIE_KEY = "movie";

    private TextView mTitle;
    private TextView mRelease;
    private TextView mDescription;
    private TextView mRate;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bindViews();
        setupActionBarAndWindow();

        Movie mMovie = getExtraMovie();

        mTitle.setText(mMovie.getTitle());
        mRelease.setText(mMovie.getRelease());
        mDescription.setText(mMovie.getOverview());
        mRate.setText(String.format(getString(R.string.detail_rate), mMovie.getRate()));
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500"+ mMovie.getPoster())
                .fit().centerCrop()
                .into(mImage);

//        fab.setRippleColor(lightVibrantColor);
//        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
        //ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);

    }

    private void bindViews(){
        mTitle = findViewById(R.id.title);
        mRelease = findViewById(R.id.release);
        mDescription = findViewById(R.id.description);
        mRate = findViewById(R.id.rate);
        mImage = findViewById(R.id.image);
    }

    //https://antonioleiva.com/collapsing-toolbar-layout/
    private void setupActionBarAndWindow(){
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        //https://stackoverflow.com/questions/36021008/an-imageview-behind-the-actionbar-and-the-statusbar-in-transparent-mode
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private Movie getExtraMovie(){
        return (Movie) getIntent().getSerializableExtra(MOVIE_KEY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
