package com.example.grouplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouplist.Objects.CompletedListItem;
import com.example.grouplist.Objects.ListItem;
import com.example.grouplist.Objects.UserListObject;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<ListItem> mItemList;
    private ArrayList<UserListObject> mUserLists;
    private ArrayList<CompletedListItem> mCompletedListItem;
    private OnItemClickListener mListener;

    public RecyclerAdapter(ArrayList<ListItem> itemList, ArrayList<UserListObject> userLists, ArrayList<CompletedListItem> completedListItems) {
        mItemList = itemList;
        mUserLists = userLists;
        mCompletedListItem = completedListItems;
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
        }else if(mUserLists != null){
            return 1;
        }else if(mCompletedListItem != null){
            return 2;
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
        }else if(viewType == 2){
            view = inflater.inflate(R.layout.completed_list_item, parent, false);
            return new CompletedListViewHolder(view, mListener);
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
        }else if(mUserLists != null){
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            listViewHolder.mListName.setText(mUserLists.get(position).getListName());
        }else if(mCompletedListItem != null){
            CompletedListViewHolder completedListViewHolder = (CompletedListViewHolder) holder;
            completedListViewHolder.mStaticListName.setText(mCompletedListItem.get(position).getItemName());
            completedListViewHolder.mStaticLocation.setText(mCompletedListItem.get(position).getLocationName());
            completedListViewHolder.mStaticQuantity.setText(mCompletedListItem.get(position).getQuantity());
        }
    }

    @Override
    public int getItemCount() {
        if(mItemList != null){
            return mItemList.size();
        }else if(mUserLists != null){
            return mUserLists.size();
        }else if(mCompletedListItem != null){
            return mCompletedListItem.size();
        }
        return -1;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

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

    static class ListViewHolder extends RecyclerView.ViewHolder {

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

    static class CompletedListViewHolder extends RecyclerView.ViewHolder{

        private TextView mStaticListName, mStaticQuantity, mStaticLocation;
        private ImageButton mRestoreButton;

        public CompletedListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mStaticListName = itemView.findViewById(R.id.staticItemNameText);
            mStaticLocation = itemView.findViewById(R.id.staticLocationText);
            mStaticQuantity = itemView.findViewById(R.id.static_quantity);
            mRestoreButton = itemView.findViewById(R.id.restoreButton);

            mRestoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onCompleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
