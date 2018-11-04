package com.example.myfuckingpc.gigshub1;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.example.myfuckingpc.gigshub1.FileUtils.ReadPath;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.FileUploadService;
import com.example.myfuckingpc.gigshub1.model.SavedToken;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    private ImageView iv_create_gigs_image;
    private TextView tv_gigs_create_date;
    private EditText title, description, price, city, address, artist;
    private String date_time = "";
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private TimePickerDialog timePickerDialog;
    private LinearLayout ll_camera_create;
    private LinearLayout ll_create_event;
    String IMAGE_PATH;
    Uri selectedImage;
    private RadioGroup rb_price;
    boolean isSale;
    private Spinner spn_category;


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
        price = getActivity().findViewById(R.id.et_create_event_price);
        rb_price.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checked = rb_price.getCheckedRadioButtonId();
                switch (checked){
                    case R.id.rb_free:
                        isSale = false;
                        price.setVisibility(View.GONE);
                        break;
                    case R.id.rb_not_free:
                        isSale = true;
                        price.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        //category
        String[] categoryArr = {"Rock", "Pop", "EDM", "Rap"};
        spn_category = getActivity().findViewById(R.id.spn_gigs_create_category);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,categoryArr);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_category.setAdapter(adapterCategory);
        //city




        //create event handler
        title = getActivity().findViewById(R.id.et_create_event_title);
        city = getActivity().findViewById(R.id.et_create_event_city);
        address = getActivity().findViewById(R.id.et_create_event_address);
        description = getActivity().findViewById(R.id.et_create_event_description);
        artist = getActivity().findViewById(R.id.et_create_event_artist);
        ll_create_event = getActivity().findViewById(R.id.ll_create_event);
        ll_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGigs();
            }
        });
    }

    private void createGigs() {
        String token = SavedToken.getUserToken(getActivity());
        FileUploadService service = ApiUtils.createEventClient(token);

        if(IMAGE_PATH == null){
            Toast.makeText(getContext(), "Please choose an image.", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(IMAGE_PATH);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selectedImage)),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        String titleString = title.getText().toString();
        String cityString = city.getText().toString();
        String addressString = address.getText().toString();
        String descriptionString = description.getText().toString();
        String artistString = artist.getText().toString();
        String datetimeString = tv_gigs_create_date.getText().toString().substring(6);
        double priceDouble = Double.parseDouble(price.getText().toString());
        String categoryString = spn_category.getSelectedItem().toString();
        int categoryInt = 0;
        switch (categoryString){
            case "EDM":
                categoryInt = 1;
                break;
            case "Rock":
                categoryInt = 5;
                break;
            case "Rap":
                categoryInt = 8;
                break;
            case "Pop":
                categoryInt = 9;
                break;

        }


        RequestBody title = RequestBody.create(okhttp3.MultipartBody.FORM, titleString);
        RequestBody city = RequestBody.create(okhttp3.MultipartBody.FORM, cityString);
        RequestBody address = RequestBody.create(okhttp3.MultipartBody.FORM, addressString);
        RequestBody artist = RequestBody.create(okhttp3.MultipartBody.FORM, artistString);

        RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
        RequestBody datetime = RequestBody.create(okhttp3.MultipartBody.FORM, datetimeString);

        Call<ResponseBody> call = service.upload(title,city,address,description,artist,datetime,isSale,priceDouble,categoryInt,body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
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
                        date_time = (dayOfMonth + "/" + monthOfYear + "/" + year);
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
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
                        tv_gigs_create_date.setText(formatter2.format(date));
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
    }
}
