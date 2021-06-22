package com.example.grouplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ListItem> mItemList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mItemName, mItemLocation, mQuantity;
        public ImageButton mUpButton, mDownButton, mCheckButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.itemName);
            mItemLocation = itemView.findViewById(R.id.locationText);
            mQuantity = itemView.findViewById(R.id.quantity);
            mUpButton = itemView.findViewById(R.id.upButton);
            mDownButton = itemView.findViewById(R.id.downButton);
            mCheckButton = itemView.findViewById(R.id.checkButton);
        }
    }

    public RecyclerAdapter(ArrayList<ListItem> itemList){
        mItemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        ListItem currentItem = mItemList.get(position);

        holder.mItemName.setText(currentItem.getListName());
        holder.mItemLocation.setText(currentItem.getLocation());
        holder.mQuantity.setText(currentItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
