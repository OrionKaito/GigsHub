package com.example.myfuckingpc.gigshub1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity {
    private Button btn_display;
    private RecyclerView rv_image;
    private List<Bitmap> listImage;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        listImage = new ArrayList<>();
        btn_display = findViewById(R.id.btn_add_image);
        rv_image = findViewById(R.id.rv_display_image);
        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 123);
//
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 123);
            }
        });
        mAdapter = new ImageAdapter(listImage);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_image.setLayoutManager(mLayoutManager);
        rv_image.setItemAnimator(new DefaultItemAnimator());
        rv_image.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
//                if (data.getClipData() != null) {
//                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
//                    for (int i = 0; i < count; i++) {
//                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                        InputStream imageStream = null;
//                        try {
//                            imageStream = getContentResolver().openInputStream(imageUri);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                        listImage.add(selectedImage);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                    //do something with the image (save it to some directory or whatever you need to do with it here)
//                } else if (data.getData() != null) {
//                    String imagePath = data.getData().getPath();
//                    Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
//                    listImage.add(myBitmap);
//                    mAdapter.notifyDataSetChanged();
//                    //do something with the image (save it to some directory or whatever you need to do with it here)
//                }
                if (resultCode == Activity.RESULT_OK) {
                    //pick image from gallery
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                    listImage.add(bitmap);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
