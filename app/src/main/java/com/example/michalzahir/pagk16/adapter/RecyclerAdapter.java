package com.example.michalzahir.pagk16.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.ConstantsClass;
import com.example.michalzahir.pagk16.QUESTIONS;
import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.categoryChoiceActivity;
import com.example.michalzahir.pagk16.gettingQuestions;
import com.example.michalzahir.pagk16.model.Landscape;
import com.example.michalzahir.pagk16.questionActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by mike on 27.05.16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    private List<Landscape> mdata;
    private LayoutInflater mInflater;
    private Context context;


    public RecyclerAdapter(List<Landscape> mdata, Context context) {
        this.mdata = mdata;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, new RecyclerAdapter.MyRecyclerViewListner(){


            @Override
            public void onPotato(View caller) {
                Log.d("VEGETABLES", "Poh-tah-tos");
            }

            @Override
            public void onTomato(ImageView callerImage) {
                Log.d("VEGETABLES", "To-m8-tohs");
            }
        });


        return holder;
    }

    @Override
    public int getItemCount() {

        return mdata.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder" + position);
        Landscape currentObj = mdata.get(position);
        holder.setData(currentObj, position);


    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Category;
        ImageView imgThumb;
        int position;
        Landscape current;
        public MyRecyclerViewListner mListner;

        public MyViewHolder(View itemView, MyRecyclerViewListner listener) {
            super(itemView);
            mListner = listener;

            Category = (TextView) itemView.findViewById(R.id.CategoryChoice);
            imgThumb = (ImageView) itemView.findViewById(R.id.categoryImage);

            imgThumb.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void setData(Landscape currentObj, int position) {
            this.Category.setText(currentObj.getCategory());
            //this.imgThumb.setImageResource(currentObj.getImageID());
            Picasso.with(context).load(currentObj.getImageID()).into(this.imgThumb);
            this.position = position;
            this.current = currentObj;

        }

        @Override
        public void onClick(View v) {
            int itemPosition = categoryChoiceActivity.recyclerView.indexOfChild(v);
            //int itemPosition1 = categoryChoiceActivity.recyclerView.getChildAdapterPosition(v);
            //int itemPosition2 = categoryChoiceActivity.recyclerView.getChildLayoutPosition(v);
            System.out.println(" 1   "+itemPosition    );
            Landscape item = mdata.get(itemPosition);

            Toast.makeText(context, "Item Clicked  item position : "+itemPosition +"Image ID:    "+item.getImageID()+"Category:   "+item.getCategory(), Toast.LENGTH_LONG).show();
//            Bundle bundle = new Bundle();
//            gettingQuestions gt  = new gettingQuestions(bundle, context);
//            System.out.println(context);
//            bundle = gt.getQuestions();

            Random rn = new Random();
            int ID = rn.nextInt(ConstantsClass.QuestionsQuestSize) + 1;
            System.out.println(ID);
            String whereClause = " ID="+ID;
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(whereClause);
            //BackendlessCollection<QUESTIONS> result =
            Backendless.Persistence.of( QUESTIONS.class ).find(dataQuery, new AsyncCallback<BackendlessCollection<QUESTIONS>>() {
                @Override
                public void handleResponse(BackendlessCollection<QUESTIONS> foundQuestions){
                    for( QUESTIONS q : foundQuestions.getData() )
                    {
                        //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                        Backendless.Persistence.of( QUESTIONS.class ).findById(q.getObjectId(), new AsyncCallback<QUESTIONS>() {
                            @Override
                            public void handleResponse(QUESTIONS response) {
                                // a Contact instance has been found by ObjectId
                                Bundle insideBundle = new Bundle();
                                System.out.println("this is the question from the backendless DB  "+response.getQuestion()
                                        +".    this is the first answer   "+response.getAnswer_a()+".   Hurrraaa success !!!!"+response.getCORRECT_A()+" B boolean:"+response.getCORRECT_B()+" D boolean:"+response.getCORRECT_D()+" C boolean:"+response.getCORRECT_C()+"AA"+response.getAnswer_a()+"bA"+response.getANSWER_B()+"cA"+response.getANSWER_C()+"DA"+response.getANSWER_D());
                                insideBundle.putString("Question",response.getQuestion());
                                insideBundle.putString("Answer_A",response.getAnswer_a());
                                insideBundle.putString("Answer_B",response.getANSWER_B());
                                insideBundle.putString("Answer_C",response.getANSWER_C());
                                insideBundle.putString("Answer_D",response.getANSWER_D());
                                insideBundle.putBoolean("correct_A",response.getCORRECT_A());
                                insideBundle.putBoolean("correct_B",response.getCORRECT_B());
                                insideBundle.putBoolean("correct_D",response.getCORRECT_C());
                                insideBundle.putBoolean("correct_C",response.getCORRECT_D());

                                Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " +insideBundle);
                                //StartActivity(bundle ,context);
                                System.out.println("bundle from the middle tier : "+insideBundle);
                                Intent i = new Intent(context, questionActivity.class);
                                i.putExtras(insideBundle);
                                context.startActivity(i);

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass());
                            }
                        });
                    }
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass());

                }});









        }
    }

    public static interface MyRecyclerViewListner {
        public void onPotato(View caller);

        public void onTomato(ImageView callerImage);
    }

}
