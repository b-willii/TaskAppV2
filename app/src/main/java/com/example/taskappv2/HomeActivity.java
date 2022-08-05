package com.example.taskappv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.taskappv2.Adapter.ToDoAdapter;
import com.example.taskappv2.Model.ToDoModel;
import com.example.taskappv2.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView taskRecycleView;
    private ToDoAdapter taskadapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        taskRecycleView = findViewById(R.id.taskRecycleview);
        taskRecycleView.setLayoutManager(new LinearLayoutManager(this));
        taskadapter = new ToDoAdapter(db, HomeActivity.this);
        taskRecycleView.setAdapter(taskadapter);

        fab = findViewById(R.id.addTaskBtn);

        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new RecyclerItemTouchHelper(taskadapter));
        itemTouchHelper.attachToRecyclerView(taskRecycleView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        taskadapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskadapter.setTasks(taskList);
        taskadapter.notifyDataSetChanged();
    }

    /***************************************************************************************
     *    Title: How to exit an app when Back Button is pressed
     *    Author: Mahdi Safarmohammadloo
     *    Date: 2018
     *    Code version: 1.0
     *    Availability: https://stackoverflow.com/questions/52375281/how-to-exit-an-app-when-back-button-is-pressed
     *
     ***************************************************************************************/

    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // or finish();
    } // stack overflow 'onBackPressed().. close app'

}