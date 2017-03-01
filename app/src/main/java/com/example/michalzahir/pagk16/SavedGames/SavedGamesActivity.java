package com.example.michalzahir.pagk16.SavedGames;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.BackendlessUser;
import com.backendless.exceptions.BackendlessException;
import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.Helper.EndlessScrollListener;
import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.NewGameActivity;
import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.RankingActivity;
import com.example.michalzahir.pagk16.gameResult;
import com.example.michalzahir.pagk16.gettingQuestions;
import com.example.michalzahir.pagk16.playerObejtID;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SavedGamesActivity extends AppCompatActivity {
    private static final String TAG = "SavedGamesActivity";
    String[] SavedGamesArray;
    static int iCounter = 11;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                iCounter = 11;
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
                int i = 1;
                int d = 0;
                SavedGamesArray = new String[GamesLoading.SavedGameslist.size()];
                Log.d(TAG, "The moment before going into the loop for after loading the games");
                for (Saved_Games sg : GamesLoading.SavedGameslist) {
                    SavedGamesArray[d] = "Game " + i + ": " + SetWhosTurn(sg);
                    Log.d(TAG, "The saved Games Array object ID = " + sg.getObjectId() + "who's turn" + sg.getWhosTurn());


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

        listView = (ListView) findViewById(R.id.mobile_list);
        Button btnLoadMore = new Button(SavedGamesActivity.this);
        btnLoadMore.setText("Load More");
        listView.addFooterView(btnLoadMore);
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
        btnLoadMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Starting a new async task
                //new loadMoreListView().execute();
                new loadMoreListView().execute();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                //Toast.makeText(SavedGamesActivity.this, "" + position + GamesLoading.SavedGameslist.get(position).getQuestionsIDs(), Toast.LENGTH_SHORT).show();
                if (SetWhosTurn(GamesLoading.SavedGameslist.get(position)).contains("It's your turn to play")) {
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
                    if (MainActivity.userName.getUserName().equals(GamesLoading.SavedGameslist.get(position).getFirstUserName()))
                        MainActivity.userName.setOponnentName(GamesLoading.SavedGameslist.get(position).getSecondUserName());
                    else if (MainActivity.userName.getUserName().equals(GamesLoading.SavedGameslist.get(position).getSecondUserName()))
                        MainActivity.userName.setOponnentName(GamesLoading.SavedGameslist.get(position).getFirstUserName());
                }

            }
        });


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

    public String SetWhosTurn(Saved_Games savedGames) {
        String WhosTurn = null;
        if (playerObejtID.getUserObjectID().equals(savedGames.getFirstUserID())) {
            if (savedGames.getWhosTurn().equals("1st user"))
                WhosTurn = "It's your turn to play against : " + savedGames.getSecondUserName();
            else if (savedGames.getWhosTurn().equals("2nd user"))
                WhosTurn = "Your Opponent " + savedGames.getSecondUserName() + " is playing right now ";

        } else if (playerObejtID.getUserObjectID().equals(savedGames.getSecondUserID())) {
            if (savedGames.getWhosTurn().equals("1st user"))
                WhosTurn = "Your Opponent  " + savedGames.getSecondUserName() + "  is playing right now";
            else if (savedGames.getWhosTurn().equals("2nd user"))
                WhosTurn = "It's your turn to play against : " + savedGames.getFirstUserName();

        }


        return WhosTurn;
    }


    private class loadMoreListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
//            pDialog = new ProgressDialog(
//                    AndroidListViewWithLoadMoreButtonActivity.this);
//            pDialog.setMessage("Please wait..");
//            pDialog.setIndeterminate(true);
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        protected Void doInBackground(Void... unused) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    // increment current page
                    System.out.println("after the for loop b4 the nextPage call size: " + GamesLoading.foundGames[0].getCurrentPage().size());
                    //int size  = users.getCurrentPage().size();
                    //System.out.println( "Loaded " + size + " restaurants in the current page" );


                    try {
                        GamesLoading.foundGames[0] = GamesLoading.foundGames[0].nextPage();
                    } catch (BackendlessException e) {
                        e.printStackTrace();
                    }
                    //iCounter = users.getCurrentPage().size()+iCounter;
                    final int currentScrlPosition = iCounter;
                    expand(GamesLoading.foundGames[0].getCurrentPage().size());
                    for (Saved_Games q: GamesLoading.foundGames[0].getCurrentPage()) {
                        Log.d(TAG, "The saved Games were Found  First user ID  " +q.getFirstUserID()+" SecondUserID" +q.getSecondUserID() + " Saved Games Object ID:" +q.getObjectId());
                        GamesLoading.SavedGameslist.add(q);

                        SavedGamesArray[iCounter-1] ="Game " + iCounter + ": " + SetWhosTurn(q);
                        Log.d(TAG, "The saved Games Array object ID = " + q.getObjectId() + "who's turn" + q.getWhosTurn());
                        iCounter++;

                    }


                    //   System.out.println(e.getMessage() + "     detail " + e.getDetail());

                    System.out.println("after next page " + GamesLoading.foundGames[0].getCurrentPage().size() + " Saved Games in the current page");
                    // Load one page into listView
                    //listView = (ListView) findViewById(R.id.listView);
                    // Creating a button - Load More
                    // Button btnLoadMore = new Button(RankingActivity.this);
                    // btnLoadMore.setText("Load More");
                    //listView.addFooterView(btnLoadMore);

                    final LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    final ArrayAdapter adapter = new ArrayAdapter<String>(SavedGamesActivity.this, R.layout.activity_listview, R.id.itemTextView, SavedGamesArray) {

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = convertView;
//                if (view == null) {
                            view = inflater.inflate(R.layout.item_layout, parent, false);
//                }


                            TextView tvName = (TextView) view.findViewById(R.id.itemTextView);
                            //ImageView Rankingarrow = (ImageView) view.findViewById(R.id.RankingArrow);
                            tvName.setText(getItem(position));
                            //Rankingarrow.setBackgroundResource(R.drawable.redarraw);
                            tvName.setTextColor(Color.parseColor("#424242"));
                            System.out.println(" Outisede the if see if there is text, the item at the position in the list view : " + getItem(position));



                            return view;
                        }
                    }; // simple textview for list item

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
                     runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(adapter);
                            listView.setSelectionFromTop(currentScrlPosition - 2, 0);

                        }
                    });


                    //  Profile2_ScrollingActivity.RankingProgreessDialogue.dismiss();
//                    btnLoadMore.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View arg0) {
//                            // Starting a new async task
//                            //new loadMoreListView().execute();
//
//
//                        }
//                    });
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (null);
        }


        protected void onPostExecute(Void unused) {
            // closing progress dialog
            //pDialog.dismiss();
        }
        public void expand(int expandingSize) {

            String[] newArray = new String[SavedGamesArray.length + expandingSize];
            System.arraycopy(SavedGamesArray , 0, newArray, 0, SavedGamesArray.length);

            //an alternative to using System.arraycopy would be a for-loop:
            // for(int i = 0; i < OrigArray.length; i++)
            //     newArray[i] = OrigArray[i];
            SavedGamesArray = newArray;
        }
    }
}
