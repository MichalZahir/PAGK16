package com.example.michalzahir.pagk16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    private static final String TAG = " RankingActivity " ;
    ArrayList<String> RankingList = new ArrayList<String>();


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
                while (users.getCurrentPage().size() > 0)
                {
                    System.out.println( "b4 the for loop size: " + users.getCurrentPage().size()  );
                    for (BackendlessUser user : users.getCurrentPage()) {

                        RankingList.add(+i + " : " + user.getProperty("name") + " points : " + user.getProperty("POINTS"));

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
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                Profile2_ScrollingActivity.class);

        startActivity(i);
        finish();
    }
}
