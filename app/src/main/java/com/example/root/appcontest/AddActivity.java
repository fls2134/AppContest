package com.example.root.appcontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {

    final int TYPE_CONCERT = 0;
    final int TYPE_PARTY = 1;
    final int TYPE_MUSICAL = 2;
    final int TYPE_PLAY = 3;
    final int TYPE_GALLERY = 4;
    final int TYPE_FOOD = 5;
    final int TYPE_BAR = 6;
    final int TYPE_EVENT = 7;

    final int MY_PERMISSIONS_REQUEST_GALLERY = 111;
    final int GALLERY_CODE = 1112;
    //datas will be sent to server db
    String title;
    String content;
    String link;
    String tag;
    Bitmap img;
    int type;
    double longitude;
    double latitude;

    RadioGroup rg;
    EditText editText_title;
    EditText editText_content;
    EditText editText_link;
    EditText editText_tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        rg = findViewById(R.id.type_group);
        editText_title = findViewById(R.id.title_add);
        editText_content = findViewById(R.id.content_add);
        editText_link = findViewById(R.id.link_add);
    }
    public void OneRadioButtonClicked(View v)
    {
        rg.clearCheck();
        rg.check(v.getId());
        switch (v.getId())
        {
            case R.id.radioButton_concert:
                type = TYPE_CONCERT;
                break;

            case R.id.radioButton_party:
                type = TYPE_PARTY;
                break;

            case R.id.radioButton_musical:
                type = TYPE_MUSICAL;
                break;

            case R.id.radioButton_play:
                type = TYPE_PLAY;
                break;

            case R.id.radioButton_gallery:
                type = TYPE_GALLERY;
                break;

            case R.id.radioButton_food:
                type = TYPE_FOOD;
                break;

            case R.id.radioButton_bar:
                type = TYPE_BAR;
                break;

            case R.id.radioButton_event:
                type = TYPE_EVENT;
                break;

        }
    }
    public void onClickComplete(View v)
    {
        title = editText_title.getText().toString();
        content = editText_content.getText().toString();
        link = editText_link.getText().toString();
        //tag
    }
    public void onClickImage(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        int permissionCheck = ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_GALLERY);
            //if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))
        }
        else
        {
            if(permissionCheck == PackageManager.PERMISSION_GRANTED)
            {
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent,GALLERY_CODE);
            }
            else
                Toast.makeText(AddActivity.this, "읽기 권한이 없어서 지도를 표시할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case GALLERY_CODE:
                    sendPicture(data.getData());
                    break;
            }
        }
    }
    private void sendPicture(Uri imguri)
    {
        String imagePath = imguri.getPath();
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(imagePath);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Bitmap bitmap =

    }
}
