package com.example.myfuckingpc.gigshub1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.FileUtils.ReadPath;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.EditProfileClient;
import com.example.myfuckingpc.gigshub1.model.SavedToken;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView iv_id;
    private Uri selectedImage;
    private EditText edt_fullname;
    private String IMAGE_PATH = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        iv_id = findViewById(R.id.iv_edit_image);
        edt_fullname = findViewById(R.id.edt_edit_fullname);
    }

    public void clickToUploadImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(galleryIntent, 123);
    }

    public void clickToDone(View view) {
        String fullnameString = edt_fullname.getText().toString();
        String userInfo = SavedToken.getUserInfo(this);
        String[] arrInfo = userInfo.split("[|]",2);
        String token = arrInfo[0];


        if(IMAGE_PATH == null){
            Toast.makeText(this, "Please choose an image.", Toast.LENGTH_SHORT).show();

            return;
        }
        File file = new File(IMAGE_PATH);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse(this.getContentResolver().getType(selectedImage)),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        RequestBody fullname = RequestBody.create(okhttp3.MultipartBody.FORM, fullnameString);

        EditProfileClient service = ApiUtils.editProfileClient(token);
        Call<ResponseBody> call = service.editProfile(fullname,body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Edit profile successful.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(EditProfileActivity.this, "Something wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Network fail.", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                //pick image from gallery
                selectedImage = data.getData();
                IMAGE_PATH = ReadPath.getPath(this, selectedImage);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                iv_id.setImageBitmap(bitmap);
                iv_id.setBackground(getResources().getDrawable(R.color.white));
            }
        }
    }

    public void clickToReturnVerification(View view) {
        onBackPressed();
    }
}
