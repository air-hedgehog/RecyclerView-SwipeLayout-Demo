package com.pain.fleetin.recyclerviewswipelayoutdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>{

    List<Item> items;

    public RecyclerViewAdapter(){
        items = new ArrayList<>();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }



    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView title;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.item_tvTitle);
        }
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemView.setEnabled(true);
        final Item newItem = (Item) item;
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        if (newItem.getTitle() != null){
            itemViewHolder.title.setText(newItem.getTitle());
        }else {
            itemViewHolder.title.setText("Empty title");
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Item getItem(int position){
        return items.get(position);
    }

    public void addItem(Item item){
        items.add(item);

        notifyItemInserted(getItemCount() - 1);
    }

    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            this.items.remove(location);
            notifyItemRemoved(location);

        }
    }

}
