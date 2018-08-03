package com.example.root.appcontest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.appcontest.Item;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {
    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        ArrayList items = new ArrayList<>();

        items.add(new Item(R.drawable.hubble_ultra_deep_field, "hubble"));
        items.add(new Item(R.drawable.hubble_ultra_deep_field, "hubble"));
        items.add(new Item(R.drawable.hubble_ultra_deep_field, "hubble"));
        items.add(new Item(R.drawable.hubble_ultra_deep_field, "hubble"));
        items.add(new Item(R.drawable.hubble_ultra_deep_field, "hubble"));
        items.add(new Item(R.drawable.hubble_ultra_deep_field, "hubble"));
        items.add(new Item(R.drawable.hubble_ultra_deep_field, "hubble"));
        items.add(new Item(R.drawable.hubble_ultra_deep_field, "hubble"));

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        Adapter = new MyAdpater(items,mContext);
        recyclerView.setAdapter(Adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


/* Adapter for RecyclerView */
class MyAdpater extends RecyclerView.Adapter<MyAdpater.ViewHolder> {
    private Context context;
    private ArrayList<Item> mItems;
    private int lastPosition = -1;

    public MyAdpater(ArrayList items, Context mContext) {
        mItems = items;
        context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imageView.setImageResource(mItems.get(position).image);
        holder.textView.setText(mItems.get(position).imagetitle);
        setAnimation(holder.imageView, position);
    }

    @Override public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView; public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.cardimage);
            textView = (TextView) view.findViewById(R.id.cardtext);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation); lastPosition = position;
        }
    }
}
