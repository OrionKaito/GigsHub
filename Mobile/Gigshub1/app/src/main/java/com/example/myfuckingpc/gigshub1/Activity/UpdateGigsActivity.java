package com.example.myfuckingpc.gigshub1.Activity;

import android.app.Activity;
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
import com.example.myfuckingpc.gigshub1.FileUtils.ReadPath;
import com.example.myfuckingpc.gigshub1.R;
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
    private ImageView iv_update_gigs_image;
    private TextView tv_gigs_update_date,tv_title, tv_delete;
    String[] categoryArr;
    Map<String, Integer> categoryMaps = new HashMap<>();
    private DatePickerDialog datePickerDialog;
    private EditText tv_description, tv_price, tv_city, tv_address, tv_artist;
    private LinearLayout ll_camera_event, btn_save_event, ll_detete;
    private LinearLayout ll_create_event;
    String IMAGE_PATH;
    Uri selectedImage;
    private RadioGroup rb_price;
    boolean isSale;
    private Spinner spn_category;
    private List<EventItem> eventItems;
    List<CategoryItem> categoryList = new ArrayList<>();
    private long eventId= 0;
    ProgressDialog progressDialog;
    private String dateInput = null;
    private String timeInput = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gigs);
        progressDialog = new ProgressDialog(this);

        tv_delete = findViewById(R.id.tv_delete_event);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] userInfoArr = SavedToken.getUserInfo(getApplication()).split("[|]");
                String token = userInfoArr[0];
                CreateEventClient deleteService = ApiUtils.createEventClient(token);
                Call<ResponseBody> call = deleteService.delete(eventId);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(UpdateGigsActivity.this, "Delete Success.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(UpdateGigsActivity.this, "Delete Fail.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(UpdateGigsActivity.this, "Please check your network connection.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ll_camera_event = this.findViewById(R.id.ll_camera_update);
        iv_update_gigs_image = this.findViewById(R.id.iv_update_gigs);
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
        //category
        spn_category = this.findViewById(R.id.spn_gigs_update_category);
        CategoryClient service = ApiUtils.categoryClient();
        Call<Category> call = service.getAllCategory();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body().getData();
                    categoryArr = new String[categoryList.size()];
                    for(int i = 0; i<categoryList.size();i++){
                        categoryArr[i] = categoryList.get(i).getName();
                        categoryMaps.put(categoryList.get(i).getName(),categoryList.get(i).getId());

                    }
                    ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,categoryArr);
                    adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spn_category.setAdapter(adapterCategory);
                }
                else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                return;
            }
        });





        tv_title = this.findViewById(R.id.tv_update_event_title);
        tv_city = this.findViewById(R.id.et_update_event_city);
        tv_address = this.findViewById(R.id.et_update_event_address);
        tv_description = this.findViewById(R.id.et_update_event_description);
        tv_artist = this.findViewById(R.id.et_update_event_artist);
        tv_date_time = this.findViewById(R.id.tv_gigs_update_date);




        //get data to form
        eventItems = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        eventId = bundle.getLong("EventID");
        final EventClient eventService = ApiUtils.eventClient();
        Call<Event> callEventService = eventService.getEventById(eventId);
        callEventService.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful()) {
                    eventItems = response.body().getData();
                    String url = eventItems.get(0).getImgPath();
                    LoadImageInternet load = new LoadImageInternet(iv_update_gigs_image);
                    load.execute(url);
                    tv_title.setText(eventItems.get(0).getTitle());
                    tv_date_time.setText(eventItems.get(0).getDate() +" " + eventItems.get(0).getTime());
                    tv_city.setText(eventItems.get(0).getCity());
                    tv_description.setText(eventItems.get(0).getDescription());
                    tv_address.setText(eventItems.get(0).getAddress());
                    tv_artist.setText(eventItems.get(0).getArtist());
                    String categoryString = eventItems.get(0).getCategory();
                    final int categoryInt = categoryMaps.get(categoryString);
                    spn_category.setSelection(categoryInt);
                    if(eventItems.get(0).getPrice() != 0) {
                        tv_price.setText(eventItems.get(0).getPrice() + "$");
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "CONNECTED", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
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
                    }
                }, mYear, mMonth, mDay);
        timePicker();
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
                        SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        dateInput = formatter3.format(date);
                        tv_date_time.setText(formatter2.format(date));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                if (resultCode == Activity.RESULT_OK) {
                    //pick image from gallery
                    selectedImage = data.getData();
                    IMAGE_PATH = ReadPath.getPath(getApplication(), selectedImage);
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getApplication().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                    iv_update_gigs_image.setImageBitmap(bitmap);

                }
            }
        }
    }

    private void updateEvent(){
        String[] userInfoArr = SavedToken.getUserInfo(this).split("[|]");
        String token = userInfoArr[0];
        final UpdateEventClient updateEventClient = ApiUtils.updateEventClient(token);
        if(IMAGE_PATH == null){
            Toast.makeText(getApplicationContext() , "Please choose an image", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(IMAGE_PATH);
        final RequestBody requestFile = RequestBody.create(MediaType.parse(this.getContentResolver().getType(selectedImage)), file);
        final MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        final int eventID = (int) eventId;
        final String titleString = tv_title.getText().toString();
        final String cityString = tv_city.getText().toString();
        final String addressString = tv_address.getText().toString();
        final String descriptionString = tv_description.getText().toString();
        final String artistString = tv_artist.getText().toString();
        final String datetimeString = tv_gigs_update_date.getText().toString();
        //SimpleDateFormat formarterDate = new SimpleDateFormat("yyyy/MM/dd");
        //SimpleDateFormat formarterTime = new SimpleDateFormat("HH:mm");
        //dateInput = formarterDate.format(eventItems.get(0).getDate());
        //timeInput = formarterTime.format(eventItems.get(0).getTime());
        final String datetimeStringInput = dateInput+" "+timeInput;

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


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


                RequestBody city = RequestBody.create(okhttp3.MultipartBody.FORM, cityString);
                RequestBody address = RequestBody.create(okhttp3.MultipartBody.FORM, addressString);
                RequestBody artist = RequestBody.create(okhttp3.MultipartBody.FORM, artistString);
                RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
                RequestBody datetime = RequestBody.create(okhttp3.MultipartBody.FORM, dateInput);

                progressDialog.show();
                Call<ResponseBody> call = updateEventClient.upload(eventID,city,address,description,artist,datetime,isSale, finalPriceDouble,categoryInt,body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Update Success!", Toast.LENGTH_SHORT).show();
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
