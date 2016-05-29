package com.example.michalzahir.pagk16.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.categoryChoiceActivity;
import com.example.michalzahir.pagk16.model.Landscape;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

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
            int itemPosition = categoryChoiceActivity.recyclerView.getChildLayoutPosition(v);
            Landscape item = mdata.get(itemPosition);

            Toast.makeText(context, "Item Clicked  item position : "+itemPosition +"Image ID:    "+item.getImageID()+"Category:   "+item.getCategory(), Toast.LENGTH_LONG).show();

            //v.getVerticalScrollbarPosition();
            //Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();

        }
    }

    public static interface MyRecyclerViewListner {
        public void onPotato(View caller);

        public void onTomato(ImageView callerImage);
    }

}
