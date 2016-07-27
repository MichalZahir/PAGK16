package com.example.michalzahir.pagk16.SavedGames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.michalzahir.pagk16.R;

public class SavedGamesActivity extends AppCompatActivity {
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X", "asdasd","asd","asd","asd","asd", "asd","qweqwe","qwe","qwe","qwe","qwe","qwe","qwe"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        GamesLoading.loadSavedGames();
    }
}
