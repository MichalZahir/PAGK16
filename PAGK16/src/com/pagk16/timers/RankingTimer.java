package com.pagk16.timers;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.backendless.servercode.annotation.BackendlessTimer;
import com.intellij.refactoring.changeClassSignature.TypeParameterInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
* RankingTimer is a timer.
* It is executed according to the schedule defined in Backendless Console. The
* class becomes a timer by extending the TimerExtender class. The information
* about the timer, its name, schedule, expiration date/time is configured in
* the special annotation - BackendlessTimer. The annotation contains a JSON
* object which describes all properties of the timer.
*/
@BackendlessTimer("{'startDate':1472726460000,'frequency':{'schedule':'custom','repeat':{'every':3600}},'timername':'Ranking'}")
public class RankingTimer extends com.backendless.servercode.extension.TimerExtender
{
    int usersCount;
    int OldRanking;
    int NewRanking;
    Map OldRankings = new HashMap();
    
  @Override
  public void execute( String appVersionId ) throws Exception
  {
    // add your code here
    System.out.println("before the methods");
    countPoints();
    setRanking();
    System.out.println("after the methods");
  }

  public void countPoints(){
    BackendlessCollection<BackendlessUser> users = Backendless.Data.of( BackendlessUser.class ).find();
    System.out.println("user's collection" + users.getTotalObjects());
    usersCount = users.getTotalObjects();
    users.setPageSize(10);
   // Iterator<BackendlessUser> userIterator = users.getPage(users.getTotalObjects(),0).getData().iterator() ;


      while (users.getCurrentPage().size() > 0)
      {
          System.out.println( "b4 the for loop size: " + users.getCurrentPage().size()  );
          for (BackendlessUser user : users.getCurrentPage()) {


              System.out.println("user's object ID = " + user.getObjectId());
              System.out.println("user's object ID = " + user.getProperty("name"));
              int won = Integer.parseInt(String.valueOf(user.getProperty("WON")));
              System.out.println("user's won= " + won);
              int lost = Integer.parseInt(String.valueOf(user.getProperty("LOST")));
              System.out.println("user's lost  = " + lost);
              int draw = Integer.parseInt(String.valueOf(user.getProperty("DRAW")));
              System.out.println("user's draw = " + draw);
              int points = won * 3 + draw + lost * -1;
              user.setProperty("POINTS", points);
              System.out.println("user's points = " + points);
              updateUserPoints(points, user.getObjectId());
              OldRanking = Integer.parseInt(String.valueOf (user.getProperty("RANKING")));
              OldRankings.put(user.getProperty("name"),OldRanking);


          }
          System.out.println( "after the for loop b4 the nextPage call size: " + users.getCurrentPage().size()  );
          //int size  = users.getCurrentPage().size();
          //System.out.println( "Loaded " + size + " restaurants in the current page" );

          users = users.nextPage();
          System.out.println( "after next page " + users.getCurrentPage().size() + " restaurants in the current page" );
      }
    //System.out.println("userIterator" + userIterator.toString());

//      while (users.getCurrentPage().size() > 0)
//      {
////      System.out.println("users.getData()   " + users.getData().toString());
//    for (BackendlessUser user : users.getCurrentPage()) {
//
//
//            System.out.println("user's object ID = " + user.getObjectId());
//            System.out.println("user's object ID = " + user.getProperty("name"));
//            int won = Integer.parseInt(String.valueOf(user.getProperty("WON")));
//            System.out.println("user's won= " + won);
//            int lost = Integer.parseInt(String.valueOf(user.getProperty("LOST")));
//            System.out.println("user's lost  = " + lost);
//            int draw = Integer.parseInt(String.valueOf(user.getProperty("DRAW")));
//            System.out.println("user's draw = " + draw);
//            int points = won*3 + draw + lost*-1;
//            user.setProperty("POINTS",points);
//            System.out.println("user's points = " + points);
//            updateUserPoints(points,user.getObjectId());
//        if ( users.getCurrentPage().size()==0)
//            break;
//        }
//         if ( users.getCurrentPage().size()==0)
//          users= users.nextPage();
//        //BackendlessUser user = userIterator.next();
//
//
//    }


//
//    System.out.println("Users found in the DB : "+users.toString());
//    while( userIterator.hasNext() )
//    {
//
//   BackendlessUser user = userIterator.next();
////      if (!userIterator.hasNext())
////
////         userIterator =   users.nextPage().getData().iterator();
//      System.out.println("user's object ID = " + user.getObjectId());
//      System.out.println("user's object ID = " + user.getProperty("name"));
//      int won = Integer.parseInt(String.valueOf(user.getProperty("WON")));
//      System.out.println("user's won= " + won);
//      int lost = Integer.parseInt(String.valueOf(user.getProperty("LOST")));
//      System.out.println("user's lost  = " + lost);
//      int draw = Integer.parseInt(String.valueOf(user.getProperty("DRAW")));
//      System.out.println("user's draw = " + draw);
//      int points = won*3 + draw + lost*-1;
//      user.setProperty("POINTS",points);
//      System.out.println("user's points = " + points);
//      updateUserPoints(points,user.getObjectId());

  //  }


  }
  public void updateUserPoints(final int points, String userObjectID){

    BackendlessUser backendlessUser =Backendless.UserService.findById  (userObjectID );

      backendlessUser.setProperty("POINTS", points);
      backendlessUser.setProperty("usersCount",usersCount);
      Backendless.UserService.update(backendlessUser);

  }
  public void setRanking(){
    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
    System.out.println("set Ranking start");

    QueryOptions queryOptions = new QueryOptions();
    queryOptions.addSortByOption( "POINTS DESC" );
    queryOptions.setPageSize(10);
    dataQuery.setQueryOptions( queryOptions );
    // fetch restaurants
    BackendlessCollection<BackendlessUser> users= Backendless.Data.of( BackendlessUser.class ).find( dataQuery );
      System.out.println("user's collection" + users.getTotalObjects());

      System.out.println("Found the users for setting the ranking");
    int i =1;
    //users.setPageSize(users.getTotalObjects());
    //List<BackendlessUser> firstPage = users.getCurrentPage();

    // iterate over the received objects
    //Iterator<BackendlessUser> iterator = firstPage.iterator();

      while (users.getCurrentPage().size() > 0)
      {
          System.out.println( " setRanking b4 the for loop size: " + users.getCurrentPage().size()  );
          for (BackendlessUser user : users.getCurrentPage()) {



              BackendlessUser backendlessUser =Backendless.UserService.findById  (user.getObjectId() );
               int OldRank =   Integer.parseInt(String.valueOf(OldRankings.get( user.getProperty("name"))));
              if (OldRank>i)
                  backendlessUser.setProperty("RANKINGARROW","Green");
                  else if (OldRank<i)
                  backendlessUser.setProperty("RANKINGARROW","Red");
                  else if (OldRank==i)
                  backendlessUser.setProperty("RANKINGARROW","-");

              if (OldRank!=i){

                  backendlessUser.setProperty("OLDRANKING",OldRank);
              }

              backendlessUser.setProperty("RANKING", i);
              Backendless.UserService.update(backendlessUser);
              System.out.println( user.getProperty("name")+", Ranking = " +i);
              i++;


          }
          System.out.println( " setRanking after the for loop b4 the nextPage call size: " + users.getCurrentPage().size()  );
          //int size  = users.getCurrentPage().size();
          //System.out.println( "Loaded " + size + " restaurants in the current page" );

          users = users.nextPage();
          System.out.println( " setRanking after next page " + users.getCurrentPage().size() + " restaurants in the current page" );
      }


//    while( iterator.hasNext() )
//    {
//
//
//
//      BackendlessUser user = iterator.next();
//
//      BackendlessUser backendlessUser =Backendless.UserService.findById  (user.getObjectId() );
//
//      backendlessUser.setProperty("RANKING", i);
//      Backendless.UserService.update(backendlessUser);
//      i++;
//    }


  }

    
}
        