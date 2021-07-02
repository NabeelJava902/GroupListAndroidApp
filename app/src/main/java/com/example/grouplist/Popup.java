package com.example.grouplist;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.grouplist.Objects.CompletedListItem;
import com.example.grouplist.Objects.ListItem;

import java.util.ArrayList;
import static com.example.grouplist.ListActivity.addItem;
import static com.example.grouplist.ListActivity.changeValue;
import static com.example.grouplist.ListActivity.mCompletedList;

public class Popup {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newpopup_itemName, newpopup_itemLocation, newpopup_quantity;
    private Button newpopup_cancel, newpopup_save, yes_button, no_button;

    private Context mContext;
    private View mPopup, mAssurePopup;

    public Popup(Context context, View popup, View assurePopoup){
        mContext = context;
        mPopup = popup;
        mAssurePopup = assurePopoup;
    }

    private void setupDialog1(){
        dialogBuilder = new AlertDialog.Builder(mContext);
        final View popupView = mPopup;
        newpopup_itemName = popupView.findViewById(R.id.popup_itemname);
        newpopup_itemLocation = popupView.findViewById(R.id.popup_location);
        newpopup_quantity = popupView.findViewById(R.id.popup_quantity);
        newpopup_save = popupView.findViewById(R.id.save_button);
        newpopup_cancel = popupView.findViewById(R.id.cancel_button);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private void setupDialog2(){
        dialogBuilder = new AlertDialog.Builder(mContext);
        final View popupView = mAssurePopup;
        yes_button = popupView.findViewById(R.id.yes);
        no_button = popupView.findViewById(R.id.no);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    public void createAssureDialog(int position){
        setupDialog2();
        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompletedListItem currentItem = mCompletedList.get(position);
                mCompletedList.remove(currentItem);
                addItem(currentItem.getItemName(), currentItem.getLocationName(), currentItem.getQuantity());
                dialog.dismiss();
            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void createNewDialog(){
        setupDialog1();
        newpopup_itemName.setText("");
        newpopup_itemLocation.setText("");
        newpopup_quantity.setText("");
        newpopup_save.setOnClickListener(new View.OnClickListener() {
            String locationText, itemText, quantityText;
            @Override
            public void onClick(View view) {
                if(newpopup_itemName.getText().toString().equals("")){
                    ActivityHelper.makeToast("Enter an item name", mContext);
                    return;
                }else{
                    itemText = newpopup_itemName.getText().toString();
                }
                if(newpopup_itemLocation.getText().toString().equals("")){
                    locationText = "None";
                }else{
                    locationText = newpopup_itemLocation.getText().toString();
                }
                if(newpopup_quantity.getText().toString().equals("")){
                    quantityText = "1";
                }else{
                    quantityText = newpopup_quantity.getText().toString();
                }
                addItem(itemText, locationText, quantityText);
                dialog.dismiss();
            }
        });

        newpopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void createEditDialog(int position, ArrayList<ListItem> mList){
        setupDialog1();
        ListItem currentItem = mList.get(position);
        newpopup_itemName.setText(currentItem.getItemName());
        newpopup_itemLocation.setText(currentItem.getLocationName());
        newpopup_quantity.setText(currentItem.getQuantity());
        newpopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newpopup_itemName.getText().toString().equals("")){
                    ActivityHelper.makeToast("Enter an item name", mContext);
                }else{
                    changeValue(position, ValueType.ITEM_NAME, newpopup_itemName.getText().toString());
                }
                if(!(newpopup_itemLocation.getText().toString().equals(""))){
                    changeValue(position, ValueType.ITEM_LOCATION, newpopup_itemLocation.getText().toString());
                }
                if(!(newpopup_quantity.getText().toString().equals(""))){
                    changeValue(position, ValueType.ITEM_QUANTITY, newpopup_quantity.getText().toString());
                }
                dialog.dismiss();
            }
        });

        newpopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
