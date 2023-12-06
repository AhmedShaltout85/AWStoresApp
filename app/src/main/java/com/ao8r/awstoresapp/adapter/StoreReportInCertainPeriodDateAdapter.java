package com.ao8r.awstoresapp.adapter;

import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_FINAL_QTY;
import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_INCOME_QTY;
import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_ITEM_CODE;
import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_ITEM_NAME;
import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_OUTCOME_QTY;
import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_START_QTY;
import static com.ao8r.awstoresapp.utils.StoresConstants.REPORT_TOTAL_QTY;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ao8r.awstoresapp.R;
import com.ao8r.awstoresapp.models.StoreReportModel;

import java.util.ArrayList;

public class StoreReportInCertainPeriodDateAdapter extends RecyclerView.Adapter<StoreReportInCertainPeriodDateAdapter.Viewholder> implements Filterable {


//    Context context;
    private ArrayList<StoreReportModel> storeReportModelPeriodicArrayList;
    private ArrayList<StoreReportModel> itemListFull;
    //    initialise instructor

    public StoreReportInCertainPeriodDateAdapter(
//            Context context,
            ArrayList<StoreReportModel> storeReportModelPeriodicArrayList) {
//        this.context = context;
        this.storeReportModelPeriodicArrayList = storeReportModelPeriodicArrayList;
        this.itemListFull = storeReportModelPeriodicArrayList;
    }

    @NonNull
    @Override
    public StoreReportInCertainPeriodDateAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_store_report_periodic, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreReportInCertainPeriodDateAdapter.Viewholder holder, int position) {
        StoreReportModel storeReportModelPeriodic = storeReportModelPeriodicArrayList.get(position);
        holder.itemCodeTextView.setText(REPORT_ITEM_CODE + storeReportModelPeriodic.getItemCode().substring(0,7));
        holder.itemNameReportTextView.setText(REPORT_ITEM_NAME + storeReportModelPeriodic.getItemNameReport());
        holder.totalQtyInStartedPointTextView.setText(REPORT_START_QTY + storeReportModelPeriodic.getTotalQtyInStartedPoint());
        holder.totalQtyIncomeTextView.setText(REPORT_INCOME_QTY + storeReportModelPeriodic.getTotalQtyIncome());
        holder.totalQtyOutcomeTextView.setText(REPORT_OUTCOME_QTY + storeReportModelPeriodic.getTotalQtyOutcome());
        holder.totalQtyEndPointTextView.setText(REPORT_FINAL_QTY + storeReportModelPeriodic.getTotalQtyEndPoint());

    }

    @Override
    public int getItemCount() {

        return storeReportModelPeriodicArrayList.size();
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
            storeReportModelPeriodicArrayList.clear();
            storeReportModelPeriodicArrayList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };


    public static class Viewholder extends RecyclerView.ViewHolder {
        private final TextView  itemCodeTextView,
                                itemNameReportTextView,
                                totalQtyInStartedPointTextView,
                                totalQtyIncomeTextView,
                                totalQtyOutcomeTextView,
                                totalQtyEndPointTextView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemCodeTextView = itemView.findViewById(R.id.idItemCodeReportPeriodicTextView);
            itemNameReportTextView = itemView.findViewById(R.id.idStoreItemNameReportPeriodicTextView);
            totalQtyInStartedPointTextView = itemView.findViewById(R.id.idTotalQtyInStartedPointPeriodicTextView);
            totalQtyIncomeTextView = itemView.findViewById(R.id.idTotalQtyIncomePeriodicTextView);
            totalQtyOutcomeTextView = itemView.findViewById(R.id.idTotalQtyOutcomePeriodicTextView);
            totalQtyEndPointTextView = itemView.findViewById(R.id.idTotalQtyEndPointPeriodicTextView);
        }
    }
}
