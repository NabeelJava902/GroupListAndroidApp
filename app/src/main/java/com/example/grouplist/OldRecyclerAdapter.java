/*package com.example.grouplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouplist.Objects.ListItem;
import com.example.grouplist.R;

import java.util.ArrayList;

//no longer using this class
//because it is an outdated version of
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ListItem> mItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onCompleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mItemName, mItemLocation, mQuantity;
        public ImageButton mCheckButton;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.itemName);
            mItemLocation = itemView.findViewById(R.id.locationText);
            mQuantity = itemView.findViewById(R.id.quantity);
            mCheckButton = itemView.findViewById(R.id.completedButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mCheckButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onCompleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerAdapter(ArrayList<ListItem> itemList){
        mItemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        ListItem currentItem = mItemList.get(position);

        holder.mItemName.setText(currentItem.getItemName());
        holder.mItemLocation.setText(currentItem.getLocationName());
        holder.mQuantity.setText(currentItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
*/