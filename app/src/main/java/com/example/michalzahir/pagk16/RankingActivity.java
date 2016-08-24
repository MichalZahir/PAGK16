package com.example.michalzahir.pagk16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;

import org.json.JSONException;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    private static final String TAG = " RankingActivity " ;
    ArrayList<String> RankingList = new ArrayList<String>();
    static public gameResult result;
    String UsrsobjIDsTab [];
    String UsrsDeviceIDsTab [];
    String UsrsNamesTab [];
    public static Boolean RankingGame = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);









        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                System.out.println("set Ranking start");

                QueryOptions queryOptions = new QueryOptions();
                queryOptions.addSortByOption( "POINTS DESC" );
                queryOptions.setPageSize(10);
                dataQuery.setQueryOptions( queryOptions );

                BackendlessCollection<BackendlessUser> users = Backendless.Data.of( BackendlessUser.class ).find( dataQuery );
                int i =1;
                  UsrsobjIDsTab  = new String[users.getTotalObjects()];
                  UsrsDeviceIDsTab   = new String[users.getTotalObjects()];
                  UsrsNamesTab  = new String[users.getTotalObjects()];

                while (users.getCurrentPage().size() > 0)
                {
                    System.out.println( "b4 the for loop size: " + users.getCurrentPage().size()  );
                    for (BackendlessUser user : users.getCurrentPage()) {

                        RankingList.add(+i + " : " + user.getProperty("name") + " points : " + user.getProperty("POINTS"));
                        UsrsobjIDsTab[i-1] = user.getObjectId();
                        UsrsNamesTab[i-1] = (String) user.getProperty("name");
                        UsrsDeviceIDsTab[i-1] = (String) user.getProperty("Device_ID");
                        i++;
                    }
                    System.out.println( "after the for loop b4 the nextPage call size: " + users.getCurrentPage().size()  );
                    //int size  = users.getCurrentPage().size();
                    //System.out.println( "Loaded " + size + " restaurants in the current page" );

                    users = users.nextPage();
                    System.out.println( "after next page " + users.getCurrentPage().size() + " restaurants in the current page" );
                }
            }
        });

        t.start(); // spawn thread

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, RankingList); // simple textview for list item
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {

                    //Toast.makeText(fbFriendsListActivity.this, "" + position + finalFriendslist.getJSONObject(position).getString("name") + "      " + finalFriendslist.getJSONObject(position), Toast.LENGTH_SHORT).show();
                    MainActivity.userName.setOponnentName(UsrsNamesTab[position]);
                    RankingGame = true;

                    result = new gameResult();
                    MainActivity.userName.setOponnentUserObjectID(UsrsobjIDsTab[position]);
                    result.setSecondUSerObjectID(UsrsobjIDsTab[position]);

                    result.setFirstUSerObjectID(MainActivity.user.getUserObjectId());
                    result.setFirstUserResult(0);
                    result.setSecondtUserResult(0);
                    NewGameActivity.result = result;
                    NewGameActivity.AddUserToQueue = false;
                    fbFriendsListActivity.FbGame=false;
                    pushNotification.OpponentDeviceID= UsrsDeviceIDsTab[position];

                    playerObejtID.setUserObjectID(MainActivity.user.getUserObjectId());
                    gameResult.questionsAnswered =0;

                    com.example.michalzahir.pagk16.gettingQuestions.getQuestions(RankingActivity.this);




            }




    });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                Profile2_ScrollingActivity.class);

        startActivity(i);
        finish();
    }
}
