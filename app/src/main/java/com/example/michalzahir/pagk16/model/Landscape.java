package com.example.michalzahir.pagk16.model;

import com.example.michalzahir.pagk16.R;

import java.util.ArrayList;

/**
 * Created by mike on 27.05.16.
 */
public class Landscape {
    private int imageID;
    private String Category;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
    public static ArrayList<Landscape> getData(){

       ArrayList<Landscape> dataList = new ArrayList<>();
        int[] images = getImages();
        String [] Categories = getCategories();
        for (int i = 0 ; i < images.length; i++ ){

            Landscape landscape = new Landscape();
            landscape.setImageID(images[i]);
            landscape.setCategory(Categories[i]);
            dataList.add(landscape);


        }
        return dataList;

    }
        public static int[] getImages(){

            int [] images = {R.drawable.sportcategory,R.drawable.histroycategory,
                    R.drawable.chemistrycategory,R.drawable.geographycategory,R.drawable.astronomycategory,
                    R.drawable.lawcategory,R.drawable.literaturecategory,R.drawable.mathcategory,R.drawable.medicalcategory,
                    R.drawable.musiccategory,R.drawable.footballcategory
                    ,R.drawable.physicscategory,R.drawable.politicscategory,R.drawable.sciencecategory,R.drawable.tvcategory};
            return images;
        }
    public static String[] getCategories(){

        String [] Categories = {"Sport","History","Chemistry","Geography","Astronomy","Law",
                "Literature","Math","Medical","Music","Football","Physics","Politics","Science","TV"};
        return Categories;
    }
}
