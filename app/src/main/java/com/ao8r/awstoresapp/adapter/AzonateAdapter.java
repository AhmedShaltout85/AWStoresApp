package com.ao8r.awstoresapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.models.AzonateModel;

import java.util.ArrayList;

public class AzonateAdapter  extends RecyclerView.Adapter<AzonateAdapter.Viewholder>{

//  declare vars

    private final ArrayList<AzonateModel> azonateModelArrayList;

//    initialise instructor

    public AzonateAdapter(ArrayList<AzonateModel> azonateModelArrayList) {

        this.azonateModelArrayList = azonateModelArrayList;
    }

    @NonNull
    @Override
    public AzonateAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_search, parent, false);
        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AzonateAdapter.Viewholder holder, int position) {

        AzonateModel model = azonateModelArrayList.get(position);

        holder.itemNameTextView.setText(model.getItemName());
        holder.storeNameTextView.setText(model.getStoreName());
        holder.storeTotalQuantityTextView.setText(model.getStoreTotalQuantity());
        holder.storeLastDateSendTextView.setText("تاريخ أخر إرسال : " + model.getLastDateSend().substring(0,10));

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return azonateModelArrayList.size();

    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        private final TextView itemNameTextView, storeNameTextView, storeTotalQuantityTextView, storeLastDateSendTextView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            itemNameTextView = itemView.findViewById(R.id.idItemNameTextView);
            storeNameTextView = itemView.findViewById(R.id.idStoreNameTextView);
            storeTotalQuantityTextView = itemView.findViewById(R.id.idStoreTotalQuantityTextView);
            storeLastDateSendTextView = itemView.findViewById(R.id.idStoreLastDateSendTextView);
        }

    }
}
