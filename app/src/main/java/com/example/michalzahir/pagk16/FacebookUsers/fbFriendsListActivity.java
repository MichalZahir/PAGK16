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
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.NewGameActivity;
import com.example.michalzahir.pagk16.QUESTIONS;
import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.UsersDB.Users;
import com.example.michalzahir.pagk16.categoryChoiceActivity;
import com.example.michalzahir.pagk16.gameResult;
import com.example.michalzahir.pagk16.playerObejtID;
import com.example.michalzahir.pagk16.pushNotification;
import com.example.michalzahir.pagk16.questionActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class fbFriendsListActivity extends AppCompatActivity {
    private static final String TAG = "fbFriendsListActivity ";
    private Button InviteFriendsButton;
    static public gameResult result;
    static public boolean FbGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_friends_list);
        FbGame = true;
        InviteFriendsButton = (Button) findViewById(R.id.inviteFriends);
        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata");

        JSONArray friendslist = null;
        ArrayList<String> friends = new ArrayList<String>();

        try {
            friendslist = new JSONArray(jsondata);
            for (int l = 0; l < friendslist.length(); l++) {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                try {
                    Toast.makeText(fbFriendsListActivity.this, "" + position + finalFriendslist.getJSONObject(position).getString("name") + "      " + finalFriendslist.getJSONObject(position), Toast.LENGTH_SHORT).show();

                    result = new gameResult();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                FindUsersObjectID(finalFriendslist.getJSONObject(position).getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                    t.start(); // spawn thread
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    result.setFirstUSerObjectID(MainActivity.user.getUserObjectId());
                    result.setFirstUserResult(0);
                    result.setSecondtUserResult(0);
                    NewGameActivity.result = result;

                    pushNotification.GetOpponentUserObjID(getApplicationContext());

                    playerObejtID.setUserObjectID(MainActivity.user.getUserObjectId());
                    com.example.michalzahir.pagk16.gettingQuestions.getQuestions(fbFriendsListActivity.this);
//                    Intent i = new Intent(getApplicationContext(),
//                            categoryChoiceActivity.class);
//                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public void FindUsersObjectID(String name) {

        String userObjectID = null;
        final String[] UserObjcetID = new String[1];
        System.out.println(name);
        String whereClause = " name='" + name + "'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        try {
            BackendlessCollection<Users> BU = Backendless.Persistence.of(Users.class).find(dataQuery);
            for (Users q : BU.getData()) {
                //Users Response = Backendless.Persistence.of(Users.class).findById(q.getObjectId());
                userObjectID = q.getObjectId();
                result.setSecondUSerObjectID(userObjectID);
            }
        } catch (BackendlessException fault) {
            Log.d(TAG, "fault trying to get FB users object ID" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

        }

    }

    @Override
    public void onBackPressed() {
        FbGame = false;
        super.onBackPressed();
    }
}
