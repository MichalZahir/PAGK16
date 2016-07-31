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
import com.example.michalzahir.pagk16.NewGameActivity;
import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.gameResult;
import com.example.michalzahir.pagk16.gettingQuestions;
import com.example.michalzahir.pagk16.playerObejtID;
import com.example.michalzahir.pagk16.questionActivity;

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
        final String [] SavedGamesArray = new String[GamesLoading.SavedGameslist.size()];
        Log.d(TAG, "THe moment before going into the loop for after loading the games");
         for (Saved_Games sg :GamesLoading.SavedGameslist){
          SavedGamesArray[d]= "Game " +i +": " +SetWhosTurn(sg);
             Log.d(TAG, "The saved Games Array object ID = " + sg.getObjectId() + "who's turn" +sg.getWhosTurn());




             i++;
             d++;

         }


        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, SavedGamesArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                Toast.makeText(SavedGamesActivity.this, "" + position  + GamesLoading.SavedGameslist.get(position).getQuestionsIDs()   , Toast.LENGTH_SHORT).show();
                 gettingQuestions.getQuestionsForSavedGames(getApplicationContext(),GamesLoading.SavedGameslist.get(position));
                NewGameActivity.result = new gameResult();
                NewGameActivity.result.setFirstUserResult(GamesLoading.SavedGameslist.get(position).getFirstUserResult());
                NewGameActivity.result.setSecondtUserResult(GamesLoading.SavedGameslist.get(position).getSecondUserResult());
                NewGameActivity.result.setFirstUSerObjectID(GamesLoading.SavedGameslist.get(position).getFirstUserID());
                NewGameActivity.result.setSecondUSerObjectID(GamesLoading.SavedGameslist.get(position).getSecondUserDeviceID());
                fbFriendsListActivity.FbGame= (GamesLoading.SavedGameslist.get(position).getFbGame());
                GamesSaving.QuestionsAnswered= GamesLoading.SavedGameslist.get(position).getQuestionsAnswered();
                gameResult.questionsAnswered = GamesLoading.SavedGameslist.get(position).getQuestionsAnswered();
                NewGameActivity.AddUserToQueue = GamesLoading.SavedGameslist.get(position).getAddUserToQueue();

            }});


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
            WhosTurn = "It's your turn to play ";

        }


return WhosTurn;
    }

}
