package com.example.root.appcontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    final int MY_PERMISSIONS_REQUEST_CUR_PLACE = 3;
    final int MY_PERMISSIONS_REQUEST_GALLERY = 108;
    final int GALLERY_CODE = 1112;
    final int LOCATION_CDDE = 1113;
    final int TAG_CODE = 1114;
    //datas will be sent to server db
    String title;
    String content;
    String link;
    String tag;
    Bitmap img;
    int type;

    double longitude;
    double latitude;
    String addr;

    RadioGroup rg;
    EditText editText_title;
    EditText editText_content;
    EditText editText_link;
    EditText editText_tag;
    ImageView imageView;
    Button locationButton;
    Button tagButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        rg = findViewById(R.id.type_group);
        editText_title = findViewById(R.id.title_add);
        editText_content = findViewById(R.id.content_add);
        editText_link = findViewById(R.id.link_add);
        imageView = findViewById(R.id.imageAdd);
        locationButton= findViewById(R.id.location_add);
        tagButton = findViewById(R.id.tag_add);

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
        //longitude
        //latitude
        //tag

        //서버에 다올리고 난뒤
        finish();
    }
    public void onClickImage(View v)// 카메라나 여러개 이미지 업로드 여부도 생각해보자.
    {
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
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent,GALLERY_CODE);
            }
            else
                Toast.makeText(AddActivity.this, "읽기 권한이 없어서 지도를 표시할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickLocation(View v)
    {
        Intent intent = new Intent(getApplicationContext(), GetLocationActivity.class);
        int permissionCheck = ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CUR_PLACE);
            //if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))
        }
        else
        {
            if(permissionCheck == PackageManager.PERMISSION_GRANTED)
                startActivityForResult(intent, LOCATION_CDDE);
            else
                Toast.makeText(AddActivity.this, "위치 권한이 없어서 지도를 표시할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickTag(View v)
    {
        Intent intent = new Intent(getApplicationContext(), TagActivity.class);
        startActivityForResult(intent, TAG_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case GALLERY_CODE:
                    sendPicture(data.getData());
                    break;
                case LOCATION_CDDE:
                    longitude = data.getDoubleExtra("longitude", 0);
                    latitude = data.getDoubleExtra("latitude", 0);
                    addr = data.getStringExtra("addr");
                    locationButton.setText(addr);
                    locationButton.setBackgroundColor(0);
                    break;
                case TAG_CODE:
                    tag = data.getStringExtra("tag");
                    tagButton.setText(tag);
                    tagButton.setBackgroundColor(0);
                    break;
            }
        }
    }
    private void sendPicture(Uri imguri)
    {
        String imagePath = getRealPathFromURI(imguri);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        }catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "sibal", Toast.LENGTH_SHORT).show();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap;
        BitmapFactory.Options options;
        try{
            bitmap = BitmapFactory.decodeFile(imagePath);
        }catch (OutOfMemoryError e)
        {
            options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
        }
        imageView.setImageBitmap(bitmap);
    }
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }


}
