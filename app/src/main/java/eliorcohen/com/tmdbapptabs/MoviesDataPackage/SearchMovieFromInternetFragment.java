package eliorcohen.com.tmdbapptabs.MoviesDataPackage;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eliorcohen.com.tmdbapptabs.CustomAdapterPackage.MovieCustomAdapterInternet;
import eliorcohen.com.tmdbapptabs.DataAppPackage.MovieModel;
import eliorcohen.com.tmdbapptabs.RetrofitPackage.GetDataService;
import eliorcohen.com.tmdbapptabs.DataAppPackage.JSONResponse;
import eliorcohen.com.tmdbapptabs.R;
import eliorcohen.com.tmdbapptabs.RetrofitPackage.RetrofitClientInstance;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchMovieFromInternetFragment extends Fragment {

    private static ArrayList<MovieModel> mMovieListInternet;  // ArrayList of MovieModel
    private MovieCustomAdapterInternet mAdapterInternet;
    private RecyclerView recyclerView;
    private EditText editTextSearch;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.add_movie_internet_fragment, container, false);

        initUI();
        mySearch();

        return mView;
    }

    private void initUI() {
        recyclerView = mView.findViewById(R.id.recyclerViewInternet);
        editTextSearch = mView.findViewById(R.id.editTextSearch);
    }

    private void mySearch() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                performSearch(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void performSearch(String query) {
        MediaPlayer sSearch = MediaPlayer.create(getContext(), R.raw.search_and_refresh_sound);
        sSearch.start();  // Play sound

        GetDataService apiService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Observable<JSONResponse> observable = apiService.getAllMovies("/3/search/movie?/&query="
                + query +
                "&api_key=4e0be2c22f7268edffde97481d49064a&language=en-US").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<JSONResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONResponse products) {
                mMovieListInternet = new ArrayList<MovieModel>(Arrays.asList(products.getResults()));
                generateDataList(mMovieListInternet);
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom mAdapterInternet*/
    private void generateDataList(List<MovieModel> photoList) {
        mAdapterInternet = new MovieCustomAdapterInternet(getContext(), photoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapterInternet);
    }

}
