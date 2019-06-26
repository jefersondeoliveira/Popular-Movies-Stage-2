package com.example.popularmoviesstage2.features.details.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.source.repository.MovieRepositoryImpl;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;
import com.example.popularmoviesstage2.features.details.viewmodel.DetailViewModel;
import com.example.popularmoviesstage2.features.details.viewmodel.DetailViewModelFactory;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static String MOVIE_KEY = "movie";

    private TextView mTitle;
    private TextView mRelease;
    private TextView mDescription;
    private TextView mRate;
    private ImageView mImage;

    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bindViews();
        setupActionBarAndWindow();

        MoviesRepository repository = new MovieRepositoryImpl();
        DetailViewModelFactory factory = new DetailViewModelFactory(repository);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);

        observableBinds();

        mViewModel.setMovieFromExtras(getIntent());

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

    private void observableBinds(){
        mViewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                mTitle.setText(movie.getTitle());
                mRelease.setText(movie.getRelease());
                mDescription.setText(movie.getOverview());
                mRate.setText(String.format(getString(R.string.detail_rate), movie.getRate()));
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w500"+ movie.getPoster())
                        .fit().centerCrop()
                        .into(mImage);
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
