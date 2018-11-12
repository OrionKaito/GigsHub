package com.example.myfuckingpc.gigshub1.Fragment;


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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.Activity.CreateMapActivity;
import com.example.myfuckingpc.gigshub1.FileUtils.ReadPath;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.CategoryClient;
import com.example.myfuckingpc.gigshub1.api.CreateEventClient;
import com.example.myfuckingpc.gigshub1.model.Category;
import com.example.myfuckingpc.gigshub1.model.CategoryItem;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {
    //category
    List<CategoryItem> categoryList = new ArrayList<>();
    String[] categoryArr;
    Map<String, Integer> categoryMaps = new HashMap<>();
    //
    String inputDate = null;
    private ImageView iv_create_gigs_image, iv_open_maps;
    private TextView tv_gigs_create_date, tv_city, tv_address;
    private EditText tv_title, tv_description, tv_price, tv_artist;
    private String date_time = "";
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog timePickerDialog;
    private LinearLayout ll_camera_create;
    private LinearLayout ll_create_event;
    String IMAGE_PATH;
    Uri selectedImage;
    private RadioGroup rb_price;
    boolean isSale;
    private Spinner spn_category;
    private final int CREATE_LOCATION = 1;
    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(int page, String title) {
        CreateFragment createFragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        createFragment.setArguments(args);
        return createFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_camera_create = getActivity().findViewById(R.id.ll_camera_create);
        iv_create_gigs_image = getActivity().findViewById(R.id.iv_create_gigs);
        ll_camera_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, 123);
            }
        });
        tv_gigs_create_date = getActivity().findViewById(R.id.tv_gigs_create_date);
        tv_gigs_create_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();

            }
        });
        //price
        rb_price = getActivity().findViewById(R.id.rb_price);
        tv_price = getActivity().findViewById(R.id.et_create_event_price);
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
        spn_category = getActivity().findViewById(R.id.spn_gigs_create_category);
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
                    ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,categoryArr);
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



        //datetime picker







        //create event handler
        tv_title = getActivity().findViewById(R.id.et_create_event_title);
        tv_city = getActivity().findViewById(R.id.et_create_event_city);
        tv_address = getActivity().findViewById(R.id.et_create_event_address);

        tv_description = getActivity().findViewById(R.id.et_create_event_description);
        tv_artist = getActivity().findViewById(R.id.et_create_event_artist);

        iv_open_maps = getActivity().findViewById(R.id.iv_open_maps);
        iv_open_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), CreateMapActivity.class);
                startActivityForResult(intent, CREATE_LOCATION);
            }
        });
        ll_create_event = getActivity().findViewById(R.id.ll_create_event);
        ll_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createGigs();
            }
        });
    }



    private void createGigs() {
        String userInfo = SavedToken.getUserInfo(getContext());
        String[] arrInfo = userInfo.split("[|]",2);
        String token = arrInfo[0];
        final CreateEventClient service = ApiUtils.createEventClient(token);

        if(IMAGE_PATH == null){
            Toast.makeText(getContext(), "Please choose an image.", Toast.LENGTH_SHORT).show();

            return;
        }
        File file = new File(IMAGE_PATH);

        final RequestBody requestFile =
                RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selectedImage)),file);

        final MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        final String titleString = tv_title.getText().toString();
        final String cityString = tv_city.getText().toString();
        final String addressString = tv_address.getText().toString();
        final String descriptionString = tv_description.getText().toString();
        final String artistString = tv_artist.getText().toString();
        final String datetimeString = tv_gigs_create_date.getText().toString().substring(6);
        double priceDouble = 0;
        if(isSale == true){
            priceDouble = Double.parseDouble(tv_price.getText().toString());
            if(priceDouble == 0){
                Toast.makeText(getActivity(), "If price is 0$, WHY don't you choose FREE option?", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String categoryString = spn_category.getSelectedItem().toString();
        final int categoryInt = categoryMaps.get(categoryString);
        ///validate
        if(titleString.equals("")){
            Toast.makeText(getActivity(), "Please enter event's TITLE.", Toast.LENGTH_LONG).show();
            return;
        }
        if (datetimeString.equals(" edit date of event")) {
            Toast.makeText(getActivity(), "Please enter event's Date Time.", Toast.LENGTH_LONG).show();
            return;
        }
        if (cityString.equals("")) {
            Toast.makeText(getActivity(), "Please enter event's CITY.", Toast.LENGTH_LONG).show();
            return;
        }
        if (addressString.equals("")) {
            Toast.makeText(getActivity(), "Please enter event's ADDRESS.", Toast.LENGTH_LONG).show();
            return;
        }
        if (descriptionString.equals("")) {
            Toast.makeText(getActivity(), "Please enter event's DESCRIPTION.", Toast.LENGTH_LONG).show();
            return;
        }
        if (artistString.equals("")) {
            Toast.makeText(getActivity(), "Please enter event's Artist.", Toast.LENGTH_LONG).show();
            return;
        }
        if(priceDouble<0){
            Toast.makeText(getActivity(), "Price MUST BE greater than 0.", Toast.LENGTH_LONG).show();
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create Event");
        builder.setMessage("Please check your event's information carefully. \n" +
                "Do you want to create this event?");
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
                final RequestBody title = RequestBody.create(okhttp3.MultipartBody.FORM, titleString);
                RequestBody city = RequestBody.create(okhttp3.MultipartBody.FORM, cityString);
                RequestBody address = RequestBody.create(okhttp3.MultipartBody.FORM, addressString);
                RequestBody artist = RequestBody.create(okhttp3.MultipartBody.FORM, artistString);
                RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
                RequestBody datetime = RequestBody.create(okhttp3.MultipartBody.FORM, inputDate);
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.show();
                Call<ResponseBody> call = service.upload(title,city,address,description,artist,datetime,isSale, finalPriceDouble,categoryInt,body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                            tv_title.setText("");
                            tv_address.setText("");
                            tv_artist.setText("");
                            tv_city.setText("");
                            tv_description.setText("");
                            tv_gigs_create_date.setText("Tap to edit date of event");
                            iv_create_gigs_image.setImageBitmap(null);
                            tv_price.setText("");
                            progressDialog.dismiss();
                        }
                        else{

                            Toast.makeText(getActivity(), response.message().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }


    private void datePicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;

                    }
                }, mYear, mMonth, mDay);
        tiemPicker();
        datePickerDialog.show();

    }

    private void tiemPicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(getActivity(),
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
                        SimpleDateFormat formarter3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        tv_gigs_create_date.setText(formatter2.format(date));
                        inputDate = formarter3.format(date);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                if (resultCode == Activity.RESULT_OK) {
                    //pick image from gallery
                    selectedImage = data.getData();
                    IMAGE_PATH = ReadPath.getPath(getActivity(), selectedImage);
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                    iv_create_gigs_image.setImageBitmap(bitmap);
                }
            }
        }
        else if (requestCode == CREATE_LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                String addressFrom = data.getStringExtra("Address");
                Double addressLat = data.getDoubleExtra("Lat",0);
                Double addressLng = data.getDoubleExtra("Lng",0);
                String[] addressArr = addressFrom.split("[,]");

                tv_address.setText(addressArr[0]+", "+addressArr[1]+", "+addressArr[2]);
                tv_city.setText(addressArr[3]);
            }
        }
    }


}
