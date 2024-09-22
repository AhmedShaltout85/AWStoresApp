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
import com.ao8r.awstoresapp.models.AzonateModel;
import com.ao8r.awstoresapp.models.FavoritesModel;
import com.ao8r.awstoresapp.repository.AddItemsToFavoritesRepo;
import com.ao8r.awstoresapp.repository.CheckItemExistsInFavoritesRepo;
import com.ao8r.awstoresapp.utils.StoresConstants;
import com.ao8r.awstoresapp.views.DetailsScreen;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AzonateHorizontalAdapter extends RecyclerView.Adapter<AzonateHorizontalAdapter.ViewHolder> {


    Context context;

    private final ArrayList<AzonateModel> azonateModelArrayList;

    public AzonateHorizontalAdapter(Context context, ArrayList<AzonateModel> azonateModelArrayList) {
        this.azonateModelArrayList = azonateModelArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public AzonateHorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_advanced_search_horizontal, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AzonateHorizontalAdapter.ViewHolder holder, int position) {
        AzonateModel modelH = azonateModelArrayList.get(position);
        holder.addToFavAdvancedSearch.setImageResource(R.drawable.ic_favorite);
        holder.itemNameTextViewH.setText(modelH.getItemName());
        holder.itemNumberTextViewH.setText(String.valueOf(modelH.getItemNumber()));
        holder.storeTotalQuantityTextViewH.setText("رصيد المخرن:  " + modelH.getStoreTotalQuantity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String extraItemNum = String.valueOf(modelH.getItemNumber());
                    Intent intent = new Intent(context, DetailsScreen.class);
                    intent.putExtra(StoresConstants.EXTRA_ITEM_NUMBER, extraItemNum);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.getStackTrace();
                    Toasty.error(context.getApplicationContext(), "عفو لا يوجد تفاصيل لهذا العنصر", Toasty.LENGTH_SHORT, true).show();
//                    CustomToast.customToast(context.getApplicationContext(), "عفو لا يوجد تفاصيل لهذا العنصر");
                }
            }
        });

        holder.addToFavAdvancedSearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ArrayList<FavoritesModel> favoritesModelArrayList = new ArrayList<>();
                StoresConstants.ITEM_NUMBER = modelH.getItemNumber();
//                TODO: updated 29-04-2023

                try {

                    CheckItemExistsInFavoritesRepo.getItemNoFromFavoritesRepo(context);
                    if (StoresConstants.FAV_ITEM_NUMBER == StoresConstants.ITEM_NUMBER) {
//                holder.addToFavAdvancedSearch.setImageResource(R.drawable.unfavorite);

                    } else {
                        favoritesModelArrayList.add(new FavoritesModel());
//                        holder.addToFavAdvancedSearch.setImageResource(R.drawable.unfavorite);
                        AddItemsToFavoritesRepo.insertItemsToFavorites(context, v);
                        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(context, favoritesModelArrayList);
                        favoritesAdapter.notifyItemInserted(holder.getAdapterPosition());
//
                        notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return azonateModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemNameTextViewH, itemNumberTextViewH, storeTotalQuantityTextViewH;
        private final ImageView addToFavAdvancedSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addToFavAdvancedSearch = itemView.findViewById(R.id.idAddToFavAdvancedSearchImageView);
            itemNameTextViewH = itemView.findViewById(R.id.idItemNameTextViewH);
            itemNumberTextViewH = itemView.findViewById(R.id.idItemNoTextViewH);
            storeTotalQuantityTextViewH = itemView.findViewById(R.id.idStoreTotalQuantityTextViewH);

        }
    }
}
