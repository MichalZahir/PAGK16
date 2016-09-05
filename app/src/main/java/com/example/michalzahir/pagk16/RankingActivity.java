package com.example.michalzahir.pagk16;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.Helper.myArrayAdapter;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    private static final String TAG = " RankingActivity " ;
    ArrayList<String> RankingList = new ArrayList< >();
    public static ArrayList<Integer> Highlighted = new ArrayList<Integer>();
    static public gameResult result;
    String UsrsobjIDsTab [];
    String UsrsDeviceIDsTab [];
    String UsrsNamesTab [];
    JSONArray Friends;
    public static Boolean RankingGame = false ;
    View wantedView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

       if (MainActivity.LoggedInWithFB) {
             Friends = getFriendList(this);
       }


            Highlighted.clear();




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
//                        TextView tv=new TextView(getApplicationContext());
//                        tv.setText(+i + " : " + user.getProperty("name") + " points : " + user.getProperty("POINTS"));
//                            RankingList.add(i-1,tv);
                       if (MainActivity.LoggedInWithFB) {
                           try {

                               for (int l = 0; l < Friends.length(); l++) {

                                   if (Friends.getJSONObject(l).getString("name").equals(user.getProperty("name"))) {
                                       Highlighted.add(i - 1);
                                       System.out.println("Friends names = " + user.getProperty("name"));

                                   }
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
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
        final ListView listView = (ListView) findViewById(R.id.listView);


        final LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ArrayAdapter adapter = new ArrayAdapter< String>(this, R.layout.activity_listview,R.id.itemTextView , RankingList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                 LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                       .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = convertView;
//                if (view == null) {
                   view = inflater.inflate(R.layout.item_layout, parent, false);
//                }


                TextView tvName = (TextView)  view.findViewById(R.id.itemTextView);

                tvName.setText(getItem(position));
                tvName.setTextColor(Color.parseColor("#424242"));
                System.out.println(" Outisede the if see if there is text, the item at the position in the list view : " + getItem(position));

                if (RankingActivity.Highlighted.contains(position)) {

                    String airi  =  getItem(position);
                    tvName.setBackgroundColor(Color.parseColor("#039be5"));

                       System.out.println(" the item at the position in the list view : " + airi);

                }

                return view;
            }
        }; // simple textview for list item
        listView.setAdapter(adapter);
        Profile2_ScrollingActivity.RankingProgreessDialogue.dismiss();




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
    public static JSONArray getFriendList(final Context c ){
        final JSONArray[] rawName = new JSONArray[1];
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                AccessToken ac = AccessToken.getCurrentAccessToken();
        /* make the API call */
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
            /* handle the result */
                                //Intent intent = new Intent(c,fbFriendsListActivity.class);
                                try {
                                    rawName[0] = response.getJSONObject().getJSONArray("data");
                                    System.out.println("get friend list for ranking"+response.toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAndWait();


            }
        });

        t.start(); // spawn thread

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        return rawName[0];
    }
    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}
