package com.ao8r.awstoresapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;

import com.ao8r.awstoresapp.models.FavoritesModel;
import com.ao8r.awstoresapp.utils.StoresConstants;
import com.ao8r.awstoresapp.repository.RemoveItemsFromFavRepo;
import com.ao8r.awstoresapp.views.DetailsScreen;

import java.util.ArrayList;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.Viewholder> {

    //  declare vars


    Context context;
    private ArrayList<FavoritesModel> favoritesModelArrayList;

    //    initialise instructor
    public FavoritesAdapter(Context context, ArrayList<FavoritesModel> favoritesModelArrayList) {
        this.favoritesModelArrayList = favoritesModelArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_favorites, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        FavoritesModel favoritesModel = favoritesModelArrayList.get(position);
        holder.removeFromFav.setImageResource(R.drawable.ic_delete);
        holder.favItemNameTextView.setText(favoritesModel.getFavItemName());
        holder.favItemNumberTextView.setText(favoritesModel.getFavItemNumber());
        holder.favStoreTotalQuantityTextView.setText(favoritesModel.getFavTotalAllStoreQuantity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String extraTQty = favoritesModel.getFavTotalAllStoreQuantity();
                String extraItemName = favoritesModel.getFavItemName();
                String extraItemNum = favoritesModel.getFavItemNumber();

                Intent intent = new Intent(context, DetailsScreen.class);
                intent.putExtra(StoresConstants.EXTRA_TOTAL_QUANTITY, extraTQty);
                intent.putExtra(StoresConstants.EXTRA_ITEM_NAME, extraItemName);
                intent.putExtra(StoresConstants.EXTRA_ITEM_NUMBER, extraItemNum);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//
            }
        });
        holder.removeFromFav.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                favoritesModelArrayList.remove(holder.getAdapterPosition());
                FavoritesAdapter favoritesAdapter = new FavoritesAdapter(context, favoritesModelArrayList);
                favoritesAdapter.notifyItemRemoved(holder.getAdapterPosition());
                notifyDataSetChanged();
//                favoritesAdapter.notifyItemChanged(favoritesModelArrayList.size() -1);
                RemoveItemsFromFavRepo.removeItemsFromFavRepo(context, view, favoritesModel.getFavItemNumber());
            }
        });


    }

    @Override
    public int getItemCount() {
        return favoritesModelArrayList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        private final ImageView removeFromFav;
        private final TextView favItemNameTextView, favStoreTotalQuantityTextView, favItemNumberTextView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);

            removeFromFav = itemView.findViewById(R.id.idRemoveFavImageView);
            favItemNameTextView = itemView.findViewById(R.id.idItemNameFavTextView);
            favItemNumberTextView = itemView.findViewById(R.id.idItemNumberFavTextView);
            favStoreTotalQuantityTextView = itemView.findViewById(R.id.idStoresTotalQuantityFavTextView);


        }

    }
}
