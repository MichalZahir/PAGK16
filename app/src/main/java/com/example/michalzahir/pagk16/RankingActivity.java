package com.example.michalzahir.pagk16;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.GpsStatus;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.Helper.EndlessScrollListener;
import com.example.michalzahir.pagk16.Helper.myArrayAdapter;
import com.example.michalzahir.pagk16.UsersDB.Users;
import com.example.michalzahir.pagk16.model.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private static final String TAG = " RankingActivity " ;
    ArrayList<String> RankingList = new ArrayList< >();
    public static ArrayList<Integer> Highlighted = new ArrayList<Integer>();
    static public gameResult result;
    String UsrsobjIDsTab [];
    String UsrsDeviceIDsTab [];
    String UsrsNamesTab [];
    String FbProfileID [];
    Users usrTable[];
    String AnsweredQuestionsIds [];
    //String RankingArrows [];
    JSONArray Friends;
    boolean alreadySeen = false;
    public static Boolean RankingGame = false ;
    String UserName;
    ListView listView;
    BackendlessCollection<BackendlessUser> users = null;
    static int iCounter = 101;
    //View wantedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        final AdView LoginAdView = (AdView) findViewById(R.id.adViewRanking);
        AdRequest adRequest = new AdRequest.Builder().build();
        LoginAdView.loadAd(adRequest);
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
//        ImageLoader.getInstance().init(config);

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                if (MainActivity.LoggedInWithFB) {
                    Friends = getFriendList(RankingActivity.this);
                }


                Highlighted.clear();

                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                System.out.println("set Ranking start");

                QueryOptions queryOptions = new QueryOptions();
                queryOptions.addSortByOption( "POINTS DESC" );
                queryOptions.setPageSize(100);
                dataQuery.setQueryOptions( queryOptions );


                try {
                    users = Backendless.Data.of( BackendlessUser.class ).find( dataQuery );
                } catch (BackendlessException e) {
                    e.printStackTrace();
                }
                int i =1;
                int TableSize = users.getTotalObjects();
                  UsrsobjIDsTab  = new String[TableSize];
                  UsrsDeviceIDsTab   = new String[TableSize];
                  UsrsNamesTab  = new String[TableSize];
                AnsweredQuestionsIds = new String[TableSize];
                FbProfileID = new String[TableSize];
                //RankingArrows = new String[TableSize];
                //while (users.getCurrentPage().size() > 0)
                //{

                    System.out.println( "b4 the for loop size: " + users.getCurrentPage().size()  );
                    for (BackendlessUser user : users.getCurrentPage()) {
                        UserName = (String) user.getProperty("name");
                        RankingList.add(+i + " : " + UserName + " points : " + user.getProperty("POINTS"));
//                        TextView tv=new TextView(getApplicationContext());
//                        tv.setText(+i + " : " + user.getProperty("name") + " points : " + user.getProperty("POINTS"));
//                            RankingList.add(i-1,tv);
                       if (MainActivity.LoggedInWithFB) {
                           try {

                               for (int l = 0; l < Friends.length(); l++) {

                                   if (Friends.getJSONObject(l).getString("name").equals(UserName)) {
                                       Highlighted.add(i - 1);
                                       System.out.println("Friends names = " + UserName);

                                   }
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }

                        UsrsobjIDsTab[i-1] = user.getObjectId();
                        UsrsNamesTab[i-1] = UserName;
                        UsrsDeviceIDsTab[i-1] = (String) user.getProperty("Device_ID");
                        AnsweredQuestionsIds[i-1] = (String) user.getProperty("AnsweredQuestionsIDs");
                        FbProfileID[i-1] = (String) user.getProperty("FbProfile_ID");
                        //usrTable[i-1] = (Users) user;
                       // RankingArrows[i-1] = (String) user.getProperty("RANKINGARROW");
                        i++;

                    }
                    int currentUserPosition = 0;
                    List <String >myList = new LinkedList<>();
                    //new String (String.valueOf(Arrays.asList(UsrsobjIDsTab))).indexOf(MainActivity.user.getUserObjectId());
                     if (Arrays.asList(UsrsobjIDsTab).contains(MainActivity.user.getUserObjectId())){

                         //UsrsobjIDsTab = clean(UsrsobjIDsTab);
                            myList= Arrays.asList(UsrsobjIDsTab);


                            //myList.removeAll(null);

                         //currentUserPosition = new String (String.valueOf(Arrays.asList(UsrsobjIDsTab))).indexOf(MainActivity.user.getUserObjectId());
                currentUserPosition = myList.indexOf(MainActivity.user.getUserObjectId());
                     }
                         System.out.println("currentUserPosition 1 = " + currentUserPosition);
                    // Load one page into listView
                      listView = (ListView) findViewById(R.id.listView);
                    // Creating a button - Load More
                    Button btnLoadMore = new Button(RankingActivity.this);
                    btnLoadMore.setText("Load More");
                    listView.addFooterView(btnLoadMore);
                    final LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    final ArrayAdapter adapter = new ArrayAdapter< String>(RankingActivity.this, R.layout.activity_listview,R.id.itemTextView , RankingList) {

                        ImageLoader imageLoader = ImageLoader.getInstance();

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = convertView;
//                if (view == null) {
                            view = inflater.inflate(R.layout.item_layout, parent, false);
//                }


                            TextView tvName = (TextView)  view.findViewById(R.id.itemTextView);

                            //ImageView Rankingarrow = (ImageView) view.findViewById(R.id.RankingArrow);
                            tvName.setText(getItem(position));
                            //Rankingarrow.setBackgroundResource(R.drawable.redarraw);
                            tvName.setTextColor(Color.parseColor("#424242"));
                            System.out.println(" Outside the if see if there is text, the item at the position in the list view : " + getItem(position));

                            if (RankingActivity.Highlighted.contains(position)) {
                                ImageView fbProfilePic = (ImageView) view.findViewById(R.id.fbProfilePic);
                                String airi  =  getItem(position);
                                tvName.setBackgroundColor(Color.parseColor("#BBDEFB"));

                                //fbProfilePic.setImageBitmap(getFacebookProfilePicture(FbProfileID[position]));
                                imageLoader.displayImage(getFacebookProfilePicture(FbProfileID[position]),fbProfilePic);
                                System.out.println(" the item at the position in the list view : " + airi);

                            }

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

                    listView.setAdapter(adapter);
                    Profile2_ScrollingActivity.RankingProgreessDialogue.dismiss();
                    //final BackendlessCollection<BackendlessUser> finalUsers = users;
                    btnLoadMore.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // Starting a new async task
                            //new loadMoreListView().execute();
                            new loadMoreListView().execute();

                        }
                    });
                    // the end of loading one page
                    System.out.println("currentUserPosition 2 = " + currentUserPosition);
                    listView.setSelection(currentUserPosition);

                    System.out.println( "after the for loop b4 the nextPage call size: " + users.getCurrentPage().size()  );
                    //int size  = users.getCurrentPage().size();
                    //System.out.println( "Loaded " + size + " restaurants in the current page" );

                 //   users = users.nextPage();
                    System.out.println( "after next page " + users.getCurrentPage().size() + " restaurants in the current page" );
                //}
            }
        });

        t.start(); // spawn thread

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {

                    //Toast.makeText(fbFriendsListActivity.this, "" + position + finalFriendslist.getJSONObject(position).getString("name") + "      " + finalFriendslist.getJSONObject(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),RankingProfileActivityTemp.class);

                i.putExtra("UsrsobjIDsTab",  UsrsobjIDsTab[position]);
                i.putExtra("UsrsNamesTab",UsrsNamesTab[position]);
                i.putExtra("AnsweredQuestionsIds",  AnsweredQuestionsIds[position]);
                i.putExtra("UsrsDeviceIDsTab",  UsrsDeviceIDsTab[position]);
                i.putExtra("FbProfileID",  FbProfileID[position]);
                startActivity(i);


//                    MainActivity.userName.setOponnentName(UsrsNamesTab[position]);
//                    RankingGame = true;
//
//                    result = new gameResult();
//                    MainActivity.userName.setOponnentUserObjectID(UsrsobjIDsTab[position]);
//                    result.setSecondUSerObjectID(UsrsobjIDsTab[position]);
//                    Profile2_ScrollingActivity.OpponentAnsweredQuestonsIds = AnsweredQuestionsIds[position];
//
//                    result.setFirstUSerObjectID(MainActivity.user.getUserObjectId());
//                    result.setFirstUserResult(0);
//                    result.setSecondtUserResult(0);
//                    NewGameActivity.result = result;
//                    NewGameActivity.AddUserToQueue = false;
//                    fbFriendsListActivity.FbGame=false;
//                    pushNotification.OpponentDeviceID= UsrsDeviceIDsTab[position];
//
//                    playerObejtID.setUserObjectID(MainActivity.user.getUserObjectId());
//                    gameResult.questionsAnswered =0;
//
//                    com.example.michalzahir.pagk16.gettingQuestions.getQuestions(RankingActivity.this);




            }




    });


    }
    public void LoadMoreData(final BackendlessCollection<BackendlessUser> users){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


        System.out.println( "after the for loop b4 the nextPage call size: " + users.getCurrentPage().size()  );
        //int size  = users.getCurrentPage().size();
        //System.out.println( "Loaded " + size + " restaurants in the current page" );


                    users.nextPage();


                //   System.out.println(e.getMessage() + "     detail " + e.getDetail());

        System.out.println( "after next page " + users.getCurrentPage().size() + " restaurants in the current page" );
        // Load one page into listView
        //listView = (ListView) findViewById(R.id.listView);
        // Creating a button - Load More
        Button btnLoadMore = new Button(RankingActivity.this);
        btnLoadMore.setText("Load More");
        listView.addFooterView(btnLoadMore);

        final LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ArrayAdapter adapter = new ArrayAdapter< String>(RankingActivity.this, R.layout.activity_listview,R.id.itemTextView , RankingList) {
            ImageLoader imageLoader = ImageLoader.getInstance();
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = convertView;
//                if (view == null) {
                view = inflater.inflate(R.layout.item_layout, parent, false);
//                }


                TextView tvName = (TextView)  view.findViewById(R.id.itemTextView);
                //ImageView Rankingarrow = (ImageView) view.findViewById(R.id.RankingArrow);
                tvName.setText(getItem(position));
                //Rankingarrow.setBackgroundResource(R.drawable.redarraw);
                tvName.setTextColor(Color.parseColor("#424242"));
                System.out.println(" Outisede the if see if there is text, the item at the position in the list view : " + getItem(position));

                if (RankingActivity.Highlighted.contains(position)) {
                    ImageView fbProfilePic = (ImageView) view.findViewById(R.id.fbProfilePic);

                    String airi  =  getItem(position);
                    tvName.setBackgroundColor(Color.parseColor("#BBDEFB"));
                    imageLoader.displayImage(getFacebookProfilePicture(FbProfileID[position]),fbProfilePic);
                    System.out.println(" the item at the position in the list view : " + airi);

                }

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

        listView.setAdapter(adapter);
                listView.setFastScrollEnabled(true);
                listView.setScrollingCacheEnabled(false);
        Profile2_ScrollingActivity.RankingProgreessDialogue.dismiss();
        btnLoadMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Starting a new async task
                //new loadMoreListView().execute();


            }
        });
        // the end of loading one page
            }
        });
        t.run();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static String getFacebookProfilePicture(final String userID){
        final Bitmap[] bitmap = {null};
        String imageURL = "https://graph.facebook.com/" + userID + "/picture?type=large";
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String imageURL;
//
//                imageURL = "https://graph.facebook.com/" + userID + "/picture?type=small";
//                InputStream in = null;
//                try {
//                    in = (InputStream) new URL(imageURL).getContent();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                bitmap[0] = BitmapFactory.decodeStream(in);
//
//
//            }
//        });
//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return imageURL;
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
                    System.out.println( "after the for loop b4 the nextPage call size: " + users.getCurrentPage().size()  );
                    //int size  = users.getCurrentPage().size();
                    //System.out.println( "Loaded " + size + " restaurants in the current page" );


                    try {
                       users= users.nextPage();
                    } catch (BackendlessException e) {
                        e.printStackTrace();
                    }
                     //iCounter = users.getCurrentPage().size()+iCounter;
                    final int currentScrlPosition = iCounter;
                    for (BackendlessUser user : users.getCurrentPage()) {
                        UserName = (String) user.getProperty("name");
                        RankingList.add(+iCounter + " : " + UserName + " points : " + user.getProperty("POINTS"));
//                        TextView tv=new TextView(getApplicationContext());
//                        tv.setText(+i + " : " + user.getProperty("name") + " points : " + user.getProperty("POINTS"));
//                            RankingList.add(i-1,tv);
                        if (MainActivity.LoggedInWithFB) {
                            try {

                                for (int l = 0; l < Friends.length(); l++) {

                                    if (Friends.getJSONObject(l).getString("name").equals(UserName)) {
                                        Highlighted.add(iCounter - 1);
                                        System.out.println("Friends names = " + UserName);

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        UsrsobjIDsTab[iCounter-1] = user.getObjectId();
                        UsrsNamesTab[iCounter-1] = UserName;
                        UsrsDeviceIDsTab[iCounter-1] = (String) user.getProperty("Device_ID");
                        AnsweredQuestionsIds[iCounter-1] = (String) user.getProperty("AnsweredQuestionsIDs");
                        FbProfileID[iCounter-1] = (String) user.getProperty("FbProfile_ID");
                        //usrTable[i-1] = (Users) user;
                        // RankingArrows[i-1] = (String) user.getProperty("RANKINGARROW");
                        iCounter++;

                    }
                    int currentUserPosition = 0;
                    List <String >myList = new LinkedList<>();
                    //new String (String.valueOf(Arrays.asList(UsrsobjIDsTab))).indexOf(MainActivity.user.getUserObjectId());
                    if (Arrays.asList(UsrsobjIDsTab).contains(MainActivity.user.getUserObjectId())) {

                        //UsrsobjIDsTab = clean(UsrsobjIDsTab);
                        myList = Arrays.asList(UsrsobjIDsTab);


                        //myList.removeAll(null);

                        //currentUserPosition = new String (String.valueOf(Arrays.asList(UsrsobjIDsTab))).indexOf(MainActivity.user.getUserObjectId());
                        currentUserPosition = myList.indexOf(MainActivity.user.getUserObjectId());
                    }

                    //   System.out.println(e.getMessage() + "     detail " + e.getDetail());

                    System.out.println( "after next page " + users.getCurrentPage().size() + " restaurants in the current page" );
                    // Load one page into listView
                    //listView = (ListView) findViewById(R.id.listView);
                    // Creating a button - Load More
                   // Button btnLoadMore = new Button(RankingActivity.this);
                   // btnLoadMore.setText("Load More");
                    //listView.addFooterView(btnLoadMore);

                    final LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    final ArrayAdapter adapter = new ArrayAdapter< String>(RankingActivity.this, R.layout.activity_listview,R.id.itemTextView , RankingList) {
                        ImageLoader imageLoader = ImageLoader.getInstance();

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = convertView;
//                if (view == null) {
                            view = inflater.inflate(R.layout.item_layout, parent, false);
//                }


                            TextView tvName = (TextView)  view.findViewById(R.id.itemTextView);
                            //ImageView Rankingarrow = (ImageView) view.findViewById(R.id.RankingArrow);
                            tvName.setText(getItem(position));
                            //Rankingarrow.setBackgroundResource(R.drawable.redarraw);
                            tvName.setTextColor(Color.parseColor("#424242"));
                            System.out.println(" Outisede the if see if there is text, the item at the position in the list view : " + getItem(position));

                            if (RankingActivity.Highlighted.contains(position)) {


                                ImageView fbProfilePic = (ImageView) view.findViewById(R.id.fbProfilePic);

                                String airi  =  getItem(position);
                                tvName.setBackgroundColor(Color.parseColor("#BBDEFB"));
                                imageLoader.displayImage(getFacebookProfilePicture(FbProfileID[position]),fbProfilePic);
                                System.out.println(" the item at the position in the list view : " + airi);
                            }

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
                    final int finalCurrentUserPosition = currentUserPosition;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(adapter);
                            if (finalCurrentUserPosition !=0 && !alreadySeen) {
                                listView.setSelection(finalCurrentUserPosition);
                                alreadySeen =true;
                            }
                            else
                            listView.setSelectionFromTop(currentScrlPosition -2, 0);

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
    }


}
