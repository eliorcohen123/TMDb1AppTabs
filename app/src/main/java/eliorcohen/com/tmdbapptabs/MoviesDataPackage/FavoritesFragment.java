package eliorcohen.com.tmdbapptabs.MoviesDataPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eliorcohen.com.tmdbapptabs.CustomAdapterPackage.MovieCustomAdapterMain;
import eliorcohen.com.tmdbapptabs.DataAppPackage.MovieDBHelper;
import eliorcohen.com.tmdbapptabs.DataAppPackage.MovieModel;
import eliorcohen.com.tmdbapptabs.R;

/*
 **
 ***
 ****
 ***** Created by Elior Cohen on 30/05/2019.
 ****
 ***
 **
 */

public class FavoritesFragment extends Fragment {

    private ArrayList<MovieModel> mMovieListMain;  // ArrayList of MovieModel
    private MovieCustomAdapterMain mAdapterMain;  // MovieCustomAdapterInternet of FavoritesFragment
    private MovieDBHelper mMovieDBHelper;  // The SQLiteHelper of the app
    private SwipeRefreshLayout swipeRefreshLayout;  // SwipeRe freshLayout of FavoritesFragment
    private RecyclerView recyclerView;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.favorites_fragment, container, false);

        initUI();
        refreshData();
        myRecyclerView();
        getData();

        return mView;
    }

    private void initUI() {
        recyclerView = mView.findViewById(R.id.recyclerViewMain);  // ID of the ListView of FavoritesFragment
        swipeRefreshLayout = mView.findViewById(R.id.swipe_container);  // ID of the SwipeRefreshLayout of FavoritesFragment

        mMovieDBHelper = new MovieDBHelper(getContext());
        mMovieListMain = new ArrayList<>();
    }

    private void refreshData() {
        if (!isConnected(getContext())) buildDialog(getActivity()).show();

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorOrange));  // Colors of the SwipeRefreshLayout of FavoritesFragment
        // Refresh the MovieDBHelper of app in ListView of FavoritesFragment
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Vibration for 0.1 second
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(100);
                }

                getActivity().finish();
                startActivity(getActivity().getIntent());  // Refresh activity

                Toast toast = Toast.makeText(getContext(), "The list are refreshed!", Toast.LENGTH_SHORT);
                View view = toast.getView();
                view.getBackground().setColorFilter(getResources().getColor(R.color.colorLightBlue), PorterDuff.Mode.SRC_IN);
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.colorBrown));
                toast.show();  // Toast

                MediaPlayer sSearch = MediaPlayer.create(getContext(), R.raw.search_and_refresh_sound);
                sSearch.start();  // Play sound

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void myRecyclerView() {
        mAdapterMain = new MovieCustomAdapterMain(getContext(), mMovieListMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapterMain);
    }

    // This method is to fetch all user records from SQLite
    private void getData() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mMovieListMain.clear();
                mMovieListMain.addAll(mMovieDBHelper.getAllMovies());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mAdapterMain.notifyDataSetChanged();
            }
        }.execute();
    }

    private boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    private AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi To use the Internet services. Press ok to Resume");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return builder;
    }

}
