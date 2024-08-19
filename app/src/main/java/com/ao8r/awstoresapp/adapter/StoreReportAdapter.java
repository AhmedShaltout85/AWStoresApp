package com.ao8r.awstoresapp.adapter;

import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_ITEM_CODE;
import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_ITEM_NAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_TOTAL_QTY;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.models.StoreReportModel;

import java.util.ArrayList;

public class StoreReportAdapter extends RecyclerView.Adapter<StoreReportAdapter.Viewholder> implements Filterable {

    //    Context context;
    private ArrayList<StoreReportModel> storeReportModelArrayList;
    private ArrayList<StoreReportModel> itemListFull;
    //    initialise instructor

    public StoreReportAdapter(
//            Context context,
            ArrayList<StoreReportModel> storeReportModelArrayList) {
//        this.context = context;
        this.storeReportModelArrayList = storeReportModelArrayList;
        this.itemListFull = storeReportModelArrayList;


    }

    @NonNull
    @Override
    public StoreReportAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_store_report, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreReportAdapter.Viewholder holder, int position) {
        StoreReportModel storeReportModel = storeReportModelArrayList.get(position);
        holder.itemCodeTextView.setText(REPORT_ITEM_CODE + String.valueOf(storeReportModel.getItemCode()).substring(0, 7));
        holder.itemNameTextView.setText(REPORT_ITEM_NAME + String.valueOf(storeReportModel.getItemNameReport()));
        holder.totalQtyNowTextView.setText(REPORT_TOTAL_QTY + String.valueOf(storeReportModel.getTotalQtyNow()));
//        TODO: add last date send 19-08-2024
        holder.lastDateSendTextView.setText("تاريخ أخر إرسال : " + storeReportModel.getLastDate().substring(0, 10));


    }

    @Override
    public int getItemCount() {
        return storeReportModelArrayList.size();
    }

    @Override
    public Filter getFilter() {

        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<StoreReportModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (StoreReportModel item : itemListFull) {
                    if (item.getItemNameReport().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            storeReportModelArrayList.clear();
            storeReportModelArrayList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };


//    get filter

  // ...

//    @Override
//    public Filter getFilter() {
//        return exampleFilter;
//    }
//
//    private final Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            ArrayList<StoreReportModel> filteredList = new ArrayList<>();
//
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(storeReportModelArrayListFiltered);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//
//                for (StoreReportModel item : storeReportModelArrayListFiltered) {
//                    if (item.getItemNameReport().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//                storeReportModelArrayListFiltered = filteredList;
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = storeReportModelArrayListFiltered;
//
//            return results;
//        }
//
//        @SuppressLint("NotifyDataSetChanged")
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
////            storeReportModelArrayList.clear();
//            storeReportModelArrayListFiltered.addAll((Collection<? extends StoreReportModel>) results.values);
//            notifyDataSetChanged();
//        }
//    };


//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    storeReportModelArrayListFiltered = storeReportModelArrayList;
//                } else {
//                    ArrayList<StoreReportModel> filteredList = new ArrayList<>();
//                    for (StoreReportModel storeReportModel : storeReportModelArrayList) {
//                        if (storeReportModel.getItemNameReport().toLowerCase().trim().contains(charString.toLowerCase().trim())) {
//                            filteredList.add(storeReportModel);
//                        }
//                    }
//                    storeReportModelArrayListFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = storeReportModelArrayListFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                storeReportModelArrayListFiltered = (ArrayList<StoreReportModel>) filterResults.values;
//
//                notifyDataSetChanged();
//            }
//        };
//    }


    public static class Viewholder extends RecyclerView.ViewHolder {
        private final TextView itemCodeTextView, itemNameTextView, totalQtyNowTextView, lastDateSendTextView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemCodeTextView = itemView.findViewById(R.id.idStoreItemCodeReportTextView);
            itemNameTextView = itemView.findViewById(R.id.idStoreItemNameReportTextView);
            totalQtyNowTextView = itemView.findViewById(R.id.idStoreTotalQtyReportTextView);
            //TODO: add last date send 19-08-2024
            lastDateSendTextView = itemView.findViewById(R.id.idStoreLastDateSendReportTextView);
        }
    }
}
