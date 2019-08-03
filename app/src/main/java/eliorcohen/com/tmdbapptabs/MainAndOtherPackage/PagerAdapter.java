package eliorcohen.com.tmdbapptabs.MainAndOtherPackage;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import eliorcohen.com.tmdbapptabs.MoviesDataPackage.FavoritesFragment;
import eliorcohen.com.tmdbapptabs.MoviesDataPackage.SearchMovieFromInternetFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                return favoritesFragment;
            case 1:
                SearchMovieFromInternetFragment searchMovieFromInternetFragment = new SearchMovieFromInternetFragment();
                return searchMovieFromInternetFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
