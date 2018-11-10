package com.example.myfuckingpc.gigshub1;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDialogFragment extends DialogFragment {
    private EditText editText;
    private DialogFragmentInterface dfi;

    public CategoryDialogFragment() {
        // Required empty public constructor
    }

    public static CategoryDialogFragment newInstance(String title) {
        CategoryDialogFragment frag = new CategoryDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        editText = view.findViewById(R.id.et_category);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder createProjectAlert = new AlertDialog.Builder(getActivity());

        createProjectAlert.setTitle("Create Project");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        createProjectAlert.setView(inflater.inflate(R.layout.fragment_category_dialog, null))

                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dfi.onDialogPositiveClick(CategoryDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dfi.onDialogNegativeClick(CategoryDialogFragment.this);

                    }
                });

        return createProjectAlert.create();


    }

    @Override
    public void onAttach(Context context) {
        dfi = (DialogFragmentInterface) context;
        super.onAttach(context);
    }



    public String getText() {
        return editText.getText().toString();
    }
}
