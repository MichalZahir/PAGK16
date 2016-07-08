package com.example.michalzahir.pagk16.FacebookUsers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.QUESTIONS;
import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.UsersDB.Users;
import com.example.michalzahir.pagk16.categoryChoiceActivity;
import com.example.michalzahir.pagk16.gameResult;
import com.example.michalzahir.pagk16.playerObejtID;
import com.example.michalzahir.pagk16.questionActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class fbFriendsListActivity extends AppCompatActivity {
    private static final String TAG = "fbFriendsListActivity ";
    private Button InviteFriendsButton;
    public gameResult result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_friends_list);

        InviteFriendsButton = (Button) findViewById(R.id.inviteFriends);
        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata");

        JSONArray friendslist = null;
        ArrayList<String> friends = new ArrayList<String>();

        try {
            friendslist = new JSONArray(jsondata);
            for (int l=0; l < friendslist.length(); l++) {
                friends.add(friendslist.getJSONObject(l).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, friends); // simple textview for list item
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


        InviteFriendsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        invitingFriendsActivity.class);

                startActivity(i);
                finish();

            }
        });

        final JSONArray finalFriendslist = friendslist;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                try {
                    Toast.makeText( fbFriendsListActivity.this, "" + position + finalFriendslist.getJSONObject(position).getString("name")+"      "+finalFriendslist.getJSONObject(position), Toast.LENGTH_SHORT).show();

                     result = new gameResult();
                    FindUsersObjectID(finalFriendslist.getJSONObject(position).getString("name"));
                    result.setFirstUSerObjectID(MainActivity.user.getUserObjectId());
                    result.setFirstUserResult(0);
                    result.setSecondtUserResult(0);
                    playerObejtID.setUserObjectID(MainActivity.user.getUserObjectId());
                    Intent i = new Intent(getApplicationContext(),
                            categoryChoiceActivity.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }

    public void FindUsersObjectID(String name){


        System.out.println(name);
        String whereClause = " name='" + name+"'";
         BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);


        Backendless.Persistence.of (Users.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Users>>() {
            @Override
            public void handleResponse(BackendlessCollection<Users> foundQuestions) {
                for (Users q : foundQuestions.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of(Users.class).findById(q.getObjectId(), new AsyncCallback<Users>() {
                        @Override
                        public void handleResponse(Users response) {
                            Log.d(TAG, "Success trying to fetch FB user object ID using hte name only : the user's object ID" + response.getObjectId()+" The user's Device ID : " +response.getDevice_ID());
                            result.setSecondUSerObjectID(response.getObjectId());
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "fault trying to fetch FB user object ID using hte name only" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
                        }
                    });
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

            }
        });
    }
}
