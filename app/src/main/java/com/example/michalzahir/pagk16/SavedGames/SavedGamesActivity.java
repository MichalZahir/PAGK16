package com.example.michalzahir.pagk16.SavedGames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.R;

public class SavedGamesActivity extends AppCompatActivity {
    private static final String TAG = "SavedGamesActivity";
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X", "asdasd","asd","asd","asd","asd", "asd","qweqwe","qwe","qwe","qwe","qwe","qwe","qwe"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);
        GamesLoading.loadSavedGames();
        int i =1;
        int d =0;
        String [] SavedGamesArray = new String[GamesLoading.SavedGameslist.size()];
        Log.d(TAG, "THe moment before going into the loop for after loading the games");
         for (Saved_Games sg :GamesLoading.SavedGameslist){
          SavedGamesArray[d]= "Game: " +i ;
             Log.d(TAG, "The saved Games Array object ID = " + sg.getObjectId() + "who's turn" +sg.getWhosTurn());




             i++;
             d++;

         }


        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, SavedGamesArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
     }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                Profile2_ScrollingActivity.class);
         startActivity(i);
        finish();
    }
}
