package com.example.myfuckingpc.gigshub1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.R;

public class RequestVerificationActivity extends AppCompatActivity {
    private ImageView iv_id;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_verification);
        iv_id = findViewById(R.id.iv_user_id);
    }

    public void clickToReturnVerification(View view) {
        finish();
    }

    public void clickToSendRequestVerify(View view) {
        Toast.makeText(this, "Your verify request has been sent. Please wait for a weeks for us to gave a result. ", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void clickToUploadImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(galleryIntent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                //pick image from gallery
                selectedImage = data.getData();
//                    IMAGE_PATH = ReadPath.getPath(getActivity(), selectedImage);
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
}
