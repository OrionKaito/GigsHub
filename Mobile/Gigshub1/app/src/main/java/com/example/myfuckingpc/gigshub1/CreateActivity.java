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
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {
    private Button btn_display;
    private RecyclerView rv_image;
    private List<Bitmap> listImage;
    private ImageAdapter mAdapter;
    private String imgDecodableString;
    Uri selectedImage;
    String IMAGE_PATH;

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
                    selectedImage = data.getData();
                    IMAGE_PATH = ReadPath.getPath(CreateActivity.this, selectedImage);

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                    listImage.add(bitmap);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void clickToUpload(View view) {
        String token = "NWUwmeSKpj1jmM4ifQohOZAoN1EFzc0hguI7qeh4CWlmQ9AS1ZODi85P7MedRHMZBBYrUpUgnujVp-Z-jLl5QL4WLen09w0bridzVHyYAUQvH58cvY5qIcxgiqPROWvbc7SJEjepRqIcB9VX_LaQwl7iH0W6WDmHbvghhBG4UQBtx-5jQfdwqvrgMRc4yEEtVpBtUFmrBxGwzamBsZjAVcIvP7bhiBXb35POsn0MCBDo-e4dEjcEAcjAF30bXLr_wK-IN8KUtBzbYbCo8BXnMbEpJKCZrPj6KGr9PZdUUHCsKHNu1XYjqRqBbYD-ZaOySE-9-2uyQ67FfT8LoI64sOm8JTuTbckH2nQPNEJD11SfHFkj20_SrVvb7bQwpSB_mx4x9CrkbPWklcxGTqgpw9Axqe7oLm9cyghPkSd9XuAWB43kxeekwIe9YNam3YH6iJMda3n90JX7PYVkzfJI0QzAGG_2JK0WtjZS-Ayzyv-2Z8pQb2DjZblWRd08mah8NRL1iwJkxKxnqkq0YLkTBA";
        FileUploadService service = ApiUtils.createEventClient(token);


        File file = new File(IMAGE_PATH);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        String idString = "4";
        String nameString = "name1";
        String titleString = "title1";
        String locationString = "location1";
        String descriptionString = "description1";
        String datetimeString = "11/11/2018 11:10:10";

        RequestBody id = RequestBody.create(okhttp3.MultipartBody.FORM, idString);
        RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, nameString);
        RequestBody title = RequestBody.create(okhttp3.MultipartBody.FORM, titleString);
        RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, locationString);
        RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
        RequestBody datetime = RequestBody.create(okhttp3.MultipartBody.FORM, datetimeString);


        Call<ResponseBody> call = service.upload(id,name,title,location,description,datetime,body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CreateActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CreateActivity.this, "VÃI LỒN", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreateActivity.this, "VÃI LỒN", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void clickToDelete(View view) {

    }
}
