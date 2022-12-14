/***************************************************************************************
 *    References:
 *
 *    Title: To Do List App Android Studio Tutorial Series
 *    Author: Mohit Singh - Penguin Coders
 *    Date: 2020
 *    Code version: 1.0
 *    Availability: https://www.youtube.com/playlist?list=PLzEWSvaHx_Z2MeyGNQeUCEktmnJBp8136
 *
 ***************************************************************************************/

package com.example.taskappv2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.taskappv2.Model.ToDoModel;
import com.example.taskappv2.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newTaskTxt;
    private Button newTaskSaveBtn;
    private DatabaseHandler db;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newTaskTxt = Objects.requireNonNull(getView()).findViewById(R.id.newTaskTxt);
        newTaskSaveBtn = getView().findViewById(R.id.newTaskSaveBtn);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskTxt.setText(task);
            if(task.length()>0)
                newTaskSaveBtn.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));
        }

        newTaskTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    newTaskSaveBtn.setEnabled(false);
                    newTaskSaveBtn.setTextColor(Color.GRAY);
                }
                else {
                    newTaskSaveBtn.setEnabled(true);
                    newTaskSaveBtn.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //boolean finalIsUpdate = isUpdate;
        final boolean finalIsUpdate = isUpdate;  //**IF DONT WORK TRY THIS INSTEAD**
        newTaskSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = newTaskTxt.getText().toString();
                if(finalIsUpdate) {
                    db.updateTask(bundle.getInt("id"), txt);
                }
                else{
                    ToDoModel task = new ToDoModel();
                    task.setTask(txt);
                    task.setStatus(0);
                    db.insertTask(task);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }

}


// ***DOUBLE CHECK FOR NULLABLES***
