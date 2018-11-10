package com.example.myfuckingpc.gigshub1;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.Activity.DetailGigsActivity;
import com.example.myfuckingpc.gigshub1.FileUtils.LoadImageInternet;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.CategoryClient;
import com.example.myfuckingpc.gigshub1.api.CreateEventClient;
import com.example.myfuckingpc.gigshub1.api.EventClient;
import com.example.myfuckingpc.gigshub1.api.UpdateEventClient;
import com.example.myfuckingpc.gigshub1.model.Category;
import com.example.myfuckingpc.gigshub1.model.CategoryItem;
import com.example.myfuckingpc.gigshub1.model.Event;
import com.example.myfuckingpc.gigshub1.model.EventItem;
import com.example.myfuckingpc.gigshub1.model.SavedToken;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateGigsActivity extends AppCompatActivity {
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private TimePickerDialog timePickerDialog;
    private String date_time = "";
    private TextView tv_datetime, tv_date_time;

    private List<Bitmap> listImage;
    private ImageView ssv_image;
    private ImageView iv_update_gigs_image;
    private TextView tv_gigs_update_date , category;
    List<CategoryItem> categoryItemList = new ArrayList<>();
    String[] categoryArr;
    Map<String, Integer> categoryMaps = new HashMap<>();
    private DatePickerDialog datePickerDialog;
    private EditText tv_title, tv_description, tv_price, tv_city, tv_address, tv_artist;
    private LinearLayout ll_camera_event;
    private LinearLayout ll_create_event;
    String IMAGE_PATH;
    Uri selectedImage;
    private RadioGroup rb_price;
    boolean isSale;
    private Spinner spn_category;
    private Button btn_save_event;
    private EventItem eventItem = new EventItem();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gigs);
        ll_camera_event = this.findViewById(R.id.ll_camera_create);
        iv_update_gigs_image = this.findViewById(R.id.iv_update_gigs_image);
        ll_camera_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent , 123);
            }
        });
    tv_gigs_update_date = this.findViewById(R.id.tv_gigs_update_date);
    tv_gigs_update_date.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            datePicker();
        }
    });

    rb_price = this.findViewById(R.id.rb_price);
    tv_price = this.findViewById(R.id.et_update_event_price);
        rb_price.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checked = rb_price.getCheckedRadioButtonId();
                switch (checked){
                    case R.id.rb_free:
                        isSale = false;
                        tv_price.setVisibility(View.GONE);
                        break;
                    case R.id.rb_not_free:
                        isSale = true;
                        tv_price.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        spn_category = this.findViewById(R.id.spn_gigs_update_category);
        CategoryClient service = ApiUtils.categoryClient();
        Call<Category> call = service.getAllCategory();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    categoryItemList = response.body().getData();
                    categoryArr = new String[categoryItemList.size()];
                    for(int i = 0 ; i < categoryItemList.size(); i++){
                        categoryArr[i] = categoryItemList.get(i).getName();
                        categoryMaps.put(categoryItemList.get(i).getName(), categoryItemList.get(i).getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_spinner_item , categoryArr);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spn_category.setAdapter(adapter);
                } else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                 return;
            }
        });





        tv_title = this.findViewById(R.id.et_update_event_title);
        tv_city = this.findViewById(R.id.et_update_event_city);
        tv_address = this.findViewById(R.id.et_update_event_address);
        tv_description = this.findViewById(R.id.et_update_event_description);
        tv_artist = this.findViewById(R.id.et_update_event_artist);
        tv_date_time = this.findViewById(R.id.tv_gigs_update_date);
        category = this.findViewById(R.id.tv_category);



        //get data to form
        Bundle bundle = new Bundle();
        long eventId = bundle.getLong("EventId");
        final EventClient Eventservice = ApiUtils.eventClient();
        Call<EventItem> callEventServeice = Eventservice.getEventById(eventId);
        callEventServeice.enqueue(new Callback<EventItem>() {
            @Override
            public void onResponse(Call<EventItem> call, Response<EventItem> response) {
                if(response.isSuccessful()){
                    eventItem = response.body();
                    String url = eventItem.getImgPath();
                    LoadImageInternet load = new LoadImageInternet(ssv_image);
                    load.execute(url);
                    tv_title.setText(eventItem.getTitle());
                    tv_datetime.setText(eventItem.getDate() +"\n" + eventItem.getTime());
                    tv_city.setText(eventItem.getCity());
                    tv_description.setText(eventItem.getDescription());
                    tv_datetime.setText(eventItem.getDate());
                    tv_address.setText(eventItem.getAddress());
                    tv_artist.setText(eventItem.getArtist());
                    category.setText(eventItem.getCategory());
                    if(eventItem.getPrice() != 0) {
                        tv_price.setText(eventItem.getPrice() + "$");
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "CONNECTED", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventItem> call, Throwable t) {
                Toast.makeText(getApplicationContext() , "K", Toast.LENGTH_SHORT).show();
            }
        });
        btn_save_event = this.findViewById(R.id.btn_save);
        btn_save_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvent();
            }
        });



    }

    private void datePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time = (dayOfMonth + "/" + monthOfYear + "/" + year);
                        timePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        mHour = hourOfDay;
                        mMinute = minute;
                        date_time += " " + hourOfDay + ":" + minute;
                        Date date = null;
                        try {
                            date = formatter.parse(date_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm");
                        tv_date_time.setText(formatter2.format(date));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }
    //éo hiểu gì phần này luôn
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 123) {
//            if(resultCode == 123){
//                if(resultCode == 123) {
//                    //pick image from gallery
//                    Uri selectedImage = data.getData();
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                    //get the cursor
//                    Cursor cursor = this.getContentResolver().query(selectedImage , filePathColumn , null , null ,null);
//                    // Move to first row
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String imgDecodableString = cursor.getString(columnIndex);
//                    cursor.close();
//
//                }
//            }
//        }
//    }

    private void updateEvent(){
        Bundle bundle = new Bundle();
        final long eventId = bundle.getLong("EventId");
        final UpdateEventClient updateEventClient = ApiUtils.updateEventClient(eventId);
        if(IMAGE_PATH == null){
            Toast.makeText(getApplicationContext() , "Please choose an image", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(IMAGE_PATH);
        final RequestBody requestFile = RequestBody.create(MediaType.parse(this.getContentResolver().getType(selectedImage)), file);
        final MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        final String eventID = Long.toString(eventId);
        final String titleString = tv_title.getText().toString();
        final String cityString = tv_city.getText().toString();
        final String addressString = tv_address.getText().toString();
        final String descriptionString = tv_description.getText().toString();
        final String artistString = tv_artist.getText().toString();
        final String datetimeString = tv_gigs_update_date.getText().toString().substring(6);
        double priceDouble = 0;
        if(isSale == true){
            priceDouble = Double.parseDouble(tv_price.getText().toString());
            if(priceDouble == 0){
                Toast.makeText(getApplicationContext(), "If price is 0$, WHY don't you choose FREE option?", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String categoryString = spn_category.getSelectedItem().toString();
        final int categoryInt = categoryMaps.get(categoryString);
        ///validate
        if(titleString.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter event's TITLE.", Toast.LENGTH_LONG).show();
            return;
        }
        if (datetimeString.equals(" edit date of event")) {
            Toast.makeText(getApplicationContext(), "Please enter event's Date Time.", Toast.LENGTH_LONG).show();
            return;
        }
        if (cityString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter event's CITY.", Toast.LENGTH_LONG).show();
            return;
        }
        if (addressString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter event's ADDRESS.", Toast.LENGTH_LONG).show();
            return;
        }
        if (descriptionString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter event's DESCRIPTION.", Toast.LENGTH_LONG).show();
            return;
        }
        if (artistString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter event's Artist.", Toast.LENGTH_LONG).show();
            return;
        }
        if(priceDouble<0){
            Toast.makeText(getApplicationContext(), "Price MUST BE greater than 0.", Toast.LENGTH_LONG).show();
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Update Event");
        builder.setMessage("Please check your event's information carefully. \n" +
                "Do you want to update this event?");
        builder.setCancelable(false);
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final double finalPriceDouble = priceDouble;
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final RequestBody eventId = RequestBody.create(okhttp3.MultipartBody.FORM , eventID);
                final RequestBody title = RequestBody.create(okhttp3.MultipartBody.FORM, titleString);
                RequestBody city = RequestBody.create(okhttp3.MultipartBody.FORM, cityString);
                RequestBody address = RequestBody.create(okhttp3.MultipartBody.FORM, addressString);
                RequestBody artist = RequestBody.create(okhttp3.MultipartBody.FORM, artistString);
                RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
                RequestBody datetime = RequestBody.create(okhttp3.MultipartBody.FORM, datetimeString);
                final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                progressDialog.show();
                Call<ResponseBody> call = updateEventClient.upload(eventId,title,city,address,description,artist,datetime,isSale, finalPriceDouble,categoryInt,body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                            tv_title.setText("");
                            tv_address.setText("");
                            tv_artist.setText("");
                            tv_city.setText("");
                            tv_description.setText("");
                            tv_gigs_update_date.setText("Tap to edit date of event");
                            iv_update_gigs_image.setImageBitmap(null);
                            tv_price.setText("");
                            progressDialog.dismiss();
                        }
                        else{

                            Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
