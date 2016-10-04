package com.example.michalzahir.pagk16.SavedGames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.Helper.EndlessScrollListener;
import com.example.michalzahir.pagk16.NewGameActivity;
import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.gameResult;
import com.example.michalzahir.pagk16.gettingQuestions;
import com.example.michalzahir.pagk16.playerObejtID;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SavedGamesActivity extends AppCompatActivity {
    private static final String TAG = "SavedGamesActivity";
      String [] SavedGamesArray;
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X", "asdasd","asd","asd","asd","asd", "asd","qweqwe","qwe","qwe","qwe","qwe","qwe","qwe"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                GamesLoading.loadSavedGames();


            }
        });

        t.start(); // spawn thread
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);
        AdView LoginAdView = (AdView) findViewById(R.id.adViewSavedGame);
        AdRequest adRequest = new AdRequest.Builder().build();
        LoginAdView.loadAd(adRequest);
        //GamesLoading.loadSavedGames();
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                int i =1;
                int d =0;
                 SavedGamesArray = new String[GamesLoading.SavedGameslist.size()];
                Log.d(TAG, "The moment before going into the loop for after loading the games");
                for (Saved_Games sg :GamesLoading.SavedGameslist){
                    SavedGamesArray[d]= "Game " +i +": " +SetWhosTurn(sg);
                    Log.d(TAG, "The saved Games Array object ID = " + sg.getObjectId() + "who's turn" +sg.getWhosTurn());




                    i++;
                    d++;

                }


            }
        });

        a.start(); // spawn thread
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, SavedGamesArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        listView.setAdapter(adapter);
        Profile2_ScrollingActivity.RankingProgreessDialogue.dismiss();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                Toast.makeText(SavedGamesActivity.this, "" + position  + GamesLoading.SavedGameslist.get(position).getQuestionsIDs()   , Toast.LENGTH_SHORT).show();
                if ( SetWhosTurn (GamesLoading.SavedGameslist.get(position) ).equals("It's your turn to play")) {
                    gettingQuestions.getQuestionsForSavedGames(getApplicationContext(), GamesLoading.SavedGameslist.get(position));
                    NewGameActivity.result = new gameResult();
                    NewGameActivity.result.setFirstUserResult(GamesLoading.SavedGameslist.get(position).getFirstUserResult());
                    NewGameActivity.result.setSecondtUserResult(GamesLoading.SavedGameslist.get(position).getSecondUserResult());
                    NewGameActivity.result.setFirstUSerObjectID(GamesLoading.SavedGameslist.get(position).getFirstUserID());
                    NewGameActivity.result.setSecondUSerObjectID(GamesLoading.SavedGameslist.get(position).getSecondUserID());
                    fbFriendsListActivity.FbGame = (GamesLoading.SavedGameslist.get(position).getFbGame());
                    GamesSaving.QuestionsAnswered = GamesLoading.SavedGameslist.get(position).getQuestionsAnswered();
                    gameResult.questionsAnswered = GamesLoading.SavedGameslist.get(position).getQuestionsAnswered();
                    NewGameActivity.AddUserToQueue = GamesLoading.SavedGameslist.get(position).getAddUserToQueue();
                }

            }});


    }
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                Profile2_ScrollingActivity.class);
         startActivity(i);
        finish();
    }
    public String SetWhosTurn(Saved_Games savedGames){
        String WhosTurn = null;
    if (playerObejtID.getUserObjectID().equals(savedGames.getFirstUserID())){
        if (savedGames.getWhosTurn().equals("1st user"))
            WhosTurn = "It's your turn to play";
            else if(savedGames.getWhosTurn().equals("2nd user"))
            WhosTurn = "Your Opponent is playing right now ";

    }else if (playerObejtID.getUserObjectID().equals(savedGames.getSecondUserID())){
        if (savedGames.getWhosTurn().equals("1st user") )
            WhosTurn = "Your Opponent is playing right now";
        else if(savedGames.getWhosTurn().equals("2nd user") )
            WhosTurn = "It's your turn to play";

        }


return WhosTurn;
    }

}
