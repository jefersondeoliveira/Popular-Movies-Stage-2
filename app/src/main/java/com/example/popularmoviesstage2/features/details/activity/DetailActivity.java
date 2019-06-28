package com.example.popularmoviesstage2.features.details.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.source.repository.MovieRepositoryImpl;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;
import com.example.popularmoviesstage2.features.details.adapter.ReviewAdapter;
import com.example.popularmoviesstage2.features.details.adapter.TrailerAdapter;
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
    private FloatingActionButton mButton;
    private RecyclerView mRvTrailers;
    private RecyclerView mRvReviews;
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    private ProgressBar mPbTrailers;
    private ProgressBar mPbReviews;

    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bindViews();
        setupActionBarAndWindow();
        setTrailerAdapter();
        setReviewAdapter();

        MoviesRepository repository = new MovieRepositoryImpl(getApplication());
        DetailViewModelFactory factory = new DetailViewModelFactory(repository);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);

        observableBinds();

        mViewModel.setMovieFromExtras(getIntent());

    }

    private void bindViews(){
        mTitle = findViewById(R.id.title);
        mRelease = findViewById(R.id.release);
        mDescription = findViewById(R.id.description);
        mRate = findViewById(R.id.rate);
        mImage = findViewById(R.id.image);
        mButton = findViewById(R.id.fab);
        mRvTrailers = findViewById(R.id.rvTrailers);
        mRvReviews = findViewById(R.id.rvReviews);
        mPbTrailers = findViewById(R.id.pbTrailers);
        mPbReviews = findViewById(R.id.pbReviews);
    }

    private void observableBinds(){
        mViewModel.getMovieLiveData().observe(this, movie -> {

            observableTrailers();
            observableReviews();

            assert movie != null;
            mViewModel.getTrailersObservable(movie.getId());
            mViewModel.getReviewsObservable(movie.getId());

            setMovieContent(movie);

            //https://stackoverflow.com/questions/30969455/android-changing-floating-action-button-color
            mViewModel.getMovieById(movie.getId()).observe(this,
                    movie1 -> {
                        mButton.setTag(movie1!=null);
                        mButton.setBackgroundTintList(ColorStateList
                                        .valueOf(getResources().getColor(movie1!=null?R.color.colorAccent:R.color.colorPrimary)));
            });

            mButton.setOnClickListener(v -> {
                if((Boolean) v.getTag()){
                    mViewModel.deleteMovie(movie);
                }else{
                    mViewModel.saveMovie(movie);
                }
            });

        });

    }

    private void setMovieContent(Movie movie){
        mTitle.setText(movie.getTitle());
        mRelease.setText(movie.getRelease());
        mDescription.setText(movie.getOverview());
        mRate.setText(String.format(getString(R.string.detail_rate), movie.getRate()));
        Picasso.get()
                .load(String.format(getString(R.string.poster_base_url), movie.getPoster()))
                .fit().centerCrop()
                .into(mImage);
    }

    private void observableTrailers(){
        mViewModel.getShowTrailerLoad().observe(this, showLoad ->
                mPbTrailers.setVisibility(showLoad != null && showLoad ? View.VISIBLE : View.GONE));
        mViewModel.getTrailers().observe(this, trailers -> mTrailerAdapter.updateData(trailers));
    }

    private void observableReviews(){
        mViewModel.getShowReviewLoad().observe(this, showLoad ->
                mPbReviews.setVisibility(showLoad != null && showLoad ? View.VISIBLE : View.GONE));
        mViewModel.getReviews().observe(this, reviews -> mReviewAdapter.updateData(reviews));
    }

    private void setTrailerAdapter(){
        mTrailerAdapter = new TrailerAdapter(trailer -> {
            Uri openTrailerVideo = Uri.parse(String.format(getString(R.string.youtube_watch),trailer.getKey()));
            Intent intent = new Intent(Intent.ACTION_VIEW, openTrailerVideo);
            startActivity(intent);
        }, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        mRvTrailers.setLayoutManager(layoutManager);
        mRvTrailers.setAdapter(mTrailerAdapter);
    }

    private void setReviewAdapter(){
        mReviewAdapter = new ReviewAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRvReviews.setLayoutManager(layoutManager);
        mRvReviews.setAdapter(mReviewAdapter);
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
