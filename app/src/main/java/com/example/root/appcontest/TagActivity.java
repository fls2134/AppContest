package com.example.root.appcontest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

public class TagActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<String> tags = new ArrayList<>();
    FlexboxLayout flexboxLayout_tags;
    FlexboxLayout flexboxLayout_res;
    EditText tag_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        tag_text = findViewById(R.id.tag_text);
        String[] strings = getResources().getStringArray(R.array.rest_tags);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("태그 설정");

        flexboxLayout_tags = findViewById(R.id.flexbox_layout_tags);
        flexboxLayout_res = findViewById(R.id.flexbox_layout_res);
        for (int i = 0; i < strings.length; i++) {
            TagButton button = new TagButton(getApplicationContext());
            button.setText(strings[i]);
            button.setOnClickListener(this);
            flexboxLayout_tags.addView(button);
        }

    }

    @Override
    public void onClick(View view) {
        String tag = ((Button)view).getText().toString();
        if(tags.contains(tag))
        {
            tags.remove(tag);
            flexboxLayout_res.removeView(view);
            flexboxLayout_tags.addView(view);
            view.setBackgroundResource(R.drawable.capsule);
            ((Button) view).setTextColor(Color.parseColor("#000000"));
        }
        else
        {
            tags.add(tag);
            flexboxLayout_tags.removeView(view);
            flexboxLayout_res.addView(view);
            view.setBackgroundResource(R.drawable.capsule_selected);
            ((Button) view).setTextColor(Color.parseColor("#FFFFFF"));
        }
    }
    public void Add_Tag(View v)
    {
        String tag = tag_text.getText().toString();
        tags.add(tag);
        TagButton button = new TagButton(getApplicationContext());
        button.setText(tag);
        button.setOnClickListener(this);
        button.setBackgroundResource(R.drawable.capsule_selected);
        button.setTextColor(Color.parseColor("#FFFFFF"));
        flexboxLayout_res.addView(button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_complete:
                String tag_string = "";
                for (int i = 0; i < tags.size(); i++) {
                    if(i == tags.size()-1)
                        tag_string += tags.get(i);
                    else
                        tag_string = tag_string + tags.get(i) + ",";
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("tag", tag_string);
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
