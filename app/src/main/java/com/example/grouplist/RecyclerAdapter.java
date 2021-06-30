package com.example.grouplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouplist.Objects.ListItem;
import com.example.grouplist.Objects.ListObject;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<ListItem> mItemList;
    private ArrayList<ListObject> mAllLists;
    private OnItemClickListener mListener;

    public RecyclerAdapter(ArrayList<ListItem> itemList, ArrayList<ListObject> allLists){
        mItemList = itemList;
        mAllLists = allLists;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onCompleteClick(int position);
    }

    public void setOnItemClickListener(RecyclerAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if(mItemList != null){
            return 0;
        }else if(mAllLists != null){
            return 1;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if(viewType == 0){
            view = inflater.inflate(R.layout.list_item, parent, false);
            return new ItemViewHolder(view, mListener);
        }else if(viewType == 1){
            view = inflater.inflate(R.layout.list_select, parent, false);
            return new ListViewHolder(view, mListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(mItemList != null){
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mItemName.setText(mItemList.get(position).getItemName());
            itemViewHolder.mItemLocation.setText(mItemList.get(position).getLocationName());
            itemViewHolder.mQuantity.setText(mItemList.get(position).getQuantity());
        }else if(mAllLists != null){
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            listViewHolder.mListName.setText(mAllLists.get(position).getListName());
        }
    }

    @Override
    public int getItemCount() {
        if(mItemList != null){
            return mItemList.size();
        }else if(mAllLists != null){
            return mAllLists.size();
        }
        return -1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemName, mItemLocation, mQuantity;
        public ImageButton mCheckButton;

        public ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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

    class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView mListName;

        public ListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mListName = itemView.findViewById(R.id.update_listname);

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
        }
    }
}
