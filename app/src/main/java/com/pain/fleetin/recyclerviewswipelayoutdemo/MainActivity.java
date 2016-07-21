package com.pain.fleetin.recyclerviewswipelayoutdemo;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Paint paint;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        paint = new Paint();
        initUI();
        initSwipe();




    }

    private void initUI(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new RecyclerViewAdapter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = "Item " + Math.random();
                Item newItem = new Item();
                newItem.setTitle(newTitle);
                adapter.addItem(newItem);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }



    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    adapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        paint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(),
                                (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mode_edit_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width,
                                (float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,
                                (float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,paint);
                    } else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX,
                                (float) itemView.getTop(),(float) itemView.getRight(),
                                (float) itemView.getBottom());
                        c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,
                                (float) itemView.getTop() + width,(float) itemView.getRight() - width,
                                (float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
