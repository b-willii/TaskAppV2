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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskappv2.Adapter.ToDoAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ToDoAdapter adapter;

    public RecyclerItemTouchHelper(ToDoAdapter adapter){
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction){
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Discard Task");
            builder.setMessage("Permanently discard this task?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            adapter.deleteItem(position);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            adapter.editItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive){
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 40;

        if(dX > 0){
           icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit);
           background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.colorPrimaryDark));
        } else{
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete);
            background = new ColorDrawable(Color.RED);
        }

        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
        int iconTop = (itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight())) /2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0 ){ //SWIPING TO THE RIGHT
           int iconLeft = itemView.getLeft() + iconMargin;
           int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
           icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

           background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() +
                   ((int) dX) + backgroundCornerOffset, itemView.getBottom());

        } else if (dX < 0){ //SWIPING TO THE LEFT
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(),
                    itemView.getRight(), itemView.getBottom());

        } else { //VIEW IS UNSWIPED
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);

    }

}
