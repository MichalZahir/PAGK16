package com.example.michalzahir.pagk16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

public class CategoryChoiceActivity extends AppCompatActivity {
    private Button SportCategoryButton;
    private Button historyCategoryButton;
    private Button scienceCategoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_choice);
        SportCategoryButton = (Button) findViewById(R.id.sportButton);
        historyCategoryButton = (Button) findViewById(R.id.historyButton);
        scienceCategoryButton = (Button) findViewById(R.id.scienceButton);

        scienceCategoryButton .setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        categoryChoiceActivity.class);
                startActivity(i);

            }
        });

        historyCategoryButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Profile2_ScrollingActivity.class);
                startActivity(i);

            }
        });
        SportCategoryButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),
//                        CategoryChoiceActivity.class);
//                startActivity(i);

                //dzialajcy wyciaganie z tabelek
//                Backendless.Persistence.of( QUESTIONS.class).find(new AsyncCallback<BackendlessCollection<QUESTIONS>>() {
//                    @Override
//                    public void handleResponse(BackendlessCollection<QUESTIONS> foundQuestions){
//                        // all Contact instances have been found
//                        for( QUESTIONS q : foundQuestions.getData() )
//                {
//                    System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
//                    Backendless.Persistence.of( QUESTIONS.class ).findById(q.getObjectId(), new AsyncCallback<QUESTIONS>() {
//                        @Override
//                        public void handleResponse(QUESTIONS response) {
//                            // a Contact instance has been found by ObjectId
//                            System.out.println("this is the question from the backendless DB  "+response.getQuestion()
//                                    +".    this is the first answer   "+response.getAnswer_a()+".   Hurrraaa success !!!!");
//                        }
//
//                        @Override
//                        public void handleFault(BackendlessFault fault) {
//                            // an error has occurred, the error code can be retrieved with fault.getCode()
//                        }
//                    });
//
//
//
//                   // + q.getAnswer_a()+ q.getAnswer_b()+q.getAnswer_b(),q.getAnswer_c(),q.getAnswer_d(),q.getCorrect_a(),q.getCorrect_b(),q.getCorrect_c(),q.getCorrect_d()
//                }
//                    }
//                    @Override
//                    public void handleFault(BackendlessFault fault)
//
//                    {
//                       // an error has occurred, the error code can be retrieved with fault.getCode()
//
//                       }
//                });
                String whereClause = " WHERE ID = 1";
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                dataQuery.setWhereClause(whereClause);
                //BackendlessCollection<QUESTIONS> result =
                 Backendless.Persistence.of( QUESTIONS.class ).find(dataQuery, new AsyncCallback<BackendlessCollection<QUESTIONS>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<QUESTIONS> foundQuestions){
                            for( QUESTIONS q : foundQuestions.getData() )
                {
                    System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of( QUESTIONS.class ).findById(q.getObjectId(), new AsyncCallback<QUESTIONS>() {
                        @Override
                        public void handleResponse(QUESTIONS response) {
                            // a Contact instance has been found by ObjectId
                            System.out.println("this is the question from the backendless DB  "+response.getQuestion()
                                    +".    this is the first answer   "+response.getAnswer_a()+".   Hurrraaa success !!!!");
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            // an error has occurred, the error code can be retrieved with fault.getCode()
                        }
                    });
                }
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            // an error has occurred, the error code can be retrieved with fault.getCode()
                        }});


//                for( QUESTIONS q : result.getData() )
//                {
//                    System.out.println(  " The shit  in the table :  '"+ q.getQuestion()+ q.getAnswer_a()+ q.getAnswer_b()+q.getAnswer_b()+q.getAnswer_c()+q.getAnswer_d()+q.getCorrect_a()+q.getCorrect_b()+q.getCorrect_c()+q.getCorrect_d());
//                }
//                Questions savedQuestion = Backendless.Persistence.save(question);
//                Questions lastQuestion = Backendless.Persistence.of( Questions.class ).findById(savedQuestion.getObjectId());
//                Backendless.Persistence.save( question, new AsyncCallback<QUESTIONS>()
//                {
//                    @Override
//                    public void handleResponse( QUESTIONS savedContact )
//                    {
//                        Backendless.Persistence.of( QUESTIONS.class ).findById( savedContact.getObjectId(), new AsyncCallback<QUESTIONS>() {
//                            @Override
//                            public void handleResponse( QUESTIONS response )
//                            {
//                                // a Contact instance has been found by ObjectId
//                                System.out.println("It worked buddy i swear !!");
//                            }
//                            @Override
//                            public void handleFault( BackendlessFault fault )
//                            {
//                                System.out.println("It didnt wor sorry buddy your entity wasn't found it might have been added"+fault.getCode()+"Message"+fault.getMessage()+"Details"+fault.getDetail());
//                                // an error has occurred, the error code can be retrieved with fault.getCode()
//                            }
//                        } );
//                    }
//                    @Override
//                    public void handleFault( BackendlessFault fault )
//                    {
//                        System.out.println("It didnt work sorry buddy your entity wasn't added fault code ="+fault.getCode()+"Message"+fault.getMessage()+"Details"+fault.getDetail());
//
//                        // an error has occurred, the error code can be retrieved with fault.getCode()
//                    }
//                } );
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

}



