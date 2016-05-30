package com.example.michalzahir.pagk16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.michalzahir.pagk16.adapter.RecyclerAdapter;
import com.example.michalzahir.pagk16.model.Landscape;

public class categoryChoiceActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static LinearLayout  linearLayoutParentofRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_choice2);
        setUpRecyclerView();


    }
    private void setUpRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutParentofRV = (LinearLayout) findViewById(R.id.recyclerViewLinearLayoutParent);
        RecyclerAdapter adapter = new RecyclerAdapter(Landscape.getData(),this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }
}
