package eliorcohen.com.tmdbapptabs.MainAndOtherPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import eliorcohen.com.tmdbapptabs.CustomAdapterPackage.PagerAdapter;
import eliorcohen.com.tmdbapptabs.LoginPackage.UsersListActivity;
import eliorcohen.com.tmdbapptabs.MoviesDataPackage.AddMovie;
import eliorcohen.com.tmdbapptabs.MoviesDataPackage.DeleteAllData;
import eliorcohen.com.tmdbapptabs.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        showUI();
    }

    private void initUI() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);

        AppRater.app_launched(this);
    }

    private void showUI() {
        tabLayout.addTab(tabLayout.newTab().setText("Favorites").setIcon(R.drawable.ic_favorite_border_lightblue_24dp));
        tabLayout.addTab(tabLayout.newTab().setText("Search").setIcon(R.drawable.ic_search_lightblue_24dp));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // Sets off the menu of activity_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    // Options in the activity_menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mute:  // Mute all the sound in app
                AudioManager managerYes = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                managerYes.setStreamMute(AudioManager.STREAM_MUSIC, true);

                Toast toastMute = Toast.makeText(this, "The sound are mute!", Toast.LENGTH_SHORT);
                View viewMute = toastMute.getView();
                viewMute.getBackground().setColorFilter(getResources().getColor(R.color.colorLightBlue), PorterDuff.Mode.SRC_IN);
                TextView textMute = viewMute.findViewById(android.R.id.message);
                textMute.setTextColor(getResources().getColor(R.color.colorBrown));
                toastMute.show();  // Toast
                break;
            case R.id.unMute:  // Allow all the sound in app
                AudioManager managerNo = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                managerNo.setStreamMute(AudioManager.STREAM_MUSIC, false);

                MediaPlayer sUnMute = MediaPlayer.create(this, R.raw.cancel_and_move_sound);
                sUnMute.start();  // Play sound

                Toast toastUnMute = Toast.makeText(this, "The sound are on!", Toast.LENGTH_SHORT);
                View viewUnMute = toastUnMute.getView();
                viewUnMute.getBackground().setColorFilter(getResources().getColor(R.color.colorLightBlue), PorterDuff.Mode.SRC_IN);
                TextView textUnMute = viewUnMute.findViewById(android.R.id.message);
                textUnMute.setTextColor(getResources().getColor(R.color.colorBrown));
                toastUnMute.show();  // Toast
                break;
            case R.id.addManually:  // Pass from FavoritesFragment to AddMovie for add movies
                MediaPlayer sMove = MediaPlayer.create(this, R.raw.cancel_and_move_sound);
                sMove.start();  // Play sound

                Intent intentAddManually = new Intent(this, AddMovie.class);
                startActivity(intentAddManually);
                break;
            case R.id.credits:  // Credits of the creator of the app
                MediaPlayer sMoveCredits = MediaPlayer.create(this, R.raw.cancel_and_move_sound);
                sMoveCredits.start();  // Play sound

                Intent intentCredits = new Intent(this, Credits.class);
                startActivity(intentCredits);
                break;
            case R.id.accounts:
                MediaPlayer sAccounts = MediaPlayer.create(this, R.raw.cancel_and_move_sound);
                sAccounts.start();  // Play sound

                Intent intentAccounts = new Intent(this, UsersListActivity.class);
                startActivity(intentAccounts);
                break;
            case R.id.deleteAllData:  // Delete all data of the app for delete all the data of the app
                MediaPlayer sMoveDeleteData = MediaPlayer.create(this, R.raw.cancel_and_move_sound);
                sMoveDeleteData.start();  // Play sound

                Intent intentDeleteAllData = new Intent(this, DeleteAllData.class);
                startActivity(intentDeleteAllData);
                break;
            case R.id.shareIntentApp:
                MediaPlayer sMoveShare = MediaPlayer.create(this, R.raw.cancel_and_move_sound);
                sMoveShare.start();  // Play sound

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=eliorcohen.com.tmdbapp");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.exit:  // Exit from the app
                MediaPlayer sExit = MediaPlayer.create(this, R.raw.exit_sound);
                sExit.start();  // Play sound

                ActivityCompat.finishAffinity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
