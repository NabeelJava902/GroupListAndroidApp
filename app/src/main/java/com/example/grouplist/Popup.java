package com.example.grouplist;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.grouplist.Objects.CompletedListItem;
import com.example.grouplist.Objects.ListItem;

import java.util.ArrayList;
import static com.example.grouplist.ListActivity.listManager;

public class Popup {

    private AlertDialog itemDialog, assureDialog;
    private EditText newpopup_itemName, newpopup_itemLocation, newpopup_quantity;
    private Button newpopup_cancel, newpopup_save, yes_button, no_button;

    private final Context mContext;
    private final View mPopup, mAssurePopup;

    public Popup(Context context, View popup, View assurePopup){
        mContext = context;
        mPopup = popup;
        mAssurePopup = assurePopup;
        setupItemDialog();
        setupAssureDialog();
    }

    private void setupItemDialog(){
        AlertDialog.Builder itemDialogBuilder = new AlertDialog.Builder(mContext);
        final View popupView = mPopup;
        newpopup_itemName = popupView.findViewById(R.id.popup_itemname);
        newpopup_itemLocation = popupView.findViewById(R.id.popup_location);
        newpopup_quantity = popupView.findViewById(R.id.popup_quantity);
        newpopup_save = popupView.findViewById(R.id.save_button);
        newpopup_cancel = popupView.findViewById(R.id.cancel_button);

        itemDialogBuilder.setView(popupView);
        itemDialog = itemDialogBuilder.create();
    }

    private void setupAssureDialog(){
        AlertDialog.Builder assureDialogBuilder = new AlertDialog.Builder(mContext);
        final View popupView = mAssurePopup;
        yes_button = popupView.findViewById(R.id.yes);
        no_button = popupView.findViewById(R.id.no);

        assureDialogBuilder.setView(popupView);
        assureDialog = assureDialogBuilder.create();
    }

    public void createAssureDialog(int position){
        assureDialog.show();
        yes_button.setOnClickListener(view -> {
            CompletedListItem currentItem = listManager.getCompletedList().get(position);
            listManager.getCurrentList().getCompletedListItems().remove(currentItem);
            listManager.addItem(currentItem.getItemName(), currentItem.getLocationName(), currentItem.getQuantity());
            assureDialog.dismiss();
        });

        no_button.setOnClickListener(view -> assureDialog.dismiss());
    }

    public void createNewDialog(){
        itemDialog.show();
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
                listManager.addItem(itemText, locationText, quantityText);
                itemDialog.dismiss();
            }
        });

        newpopup_cancel.setOnClickListener(view -> itemDialog.dismiss());
    }

    public void createEditDialog(int position, ArrayList<ListItem> mList){
        itemDialog.show();
        ListItem currentItem = mList.get(position);
        newpopup_itemName.setText(currentItem.getItemName());
        newpopup_itemLocation.setText(currentItem.getLocationName());
        newpopup_quantity.setText(currentItem.getQuantity());
        newpopup_save.setOnClickListener(view -> {
            if(newpopup_itemName.getText().toString().equals("")){
                ActivityHelper.makeToast("Enter an item name", mContext);
            }else{
                listManager.changeValue(position, ValueType.ITEM_NAME, newpopup_itemName.getText().toString());
            }
            if(!(newpopup_itemLocation.getText().toString().equals(""))){
                listManager.changeValue(position, ValueType.ITEM_LOCATION, newpopup_itemLocation.getText().toString());
            }
            if(!(newpopup_quantity.getText().toString().equals(""))){
                listManager.changeValue(position, ValueType.ITEM_QUANTITY, newpopup_quantity.getText().toString());
            }
            itemDialog.dismiss();
        });

        newpopup_cancel.setOnClickListener(view -> itemDialog.dismiss());
    }
}
