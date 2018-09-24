package com.pager.pagerapp.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.pager.pagerapp.R;
import com.pager.pagerapp.model.Member;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListTeamAdapter extends RecyclerView.Adapter<ListTeamAdapter.ItemViewHolder> implements Filterable {


    private final Context ctx;
    private final OnItemClickListener listener;
    private List<Member> dataList;
    private List<Member> dataListFiltered;

    public interface OnItemClickListener {
        void onItemClick(Member member);
    }

    public ListTeamAdapter(Context context, List<Member> dataList, OnItemClickListener listener) {
        this.ctx = context;
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        this.listener = listener;

    }

    @Override
    public ListTeamAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_team, parent, false);
        return new ItemViewHolder(ctx, view);
    }

    @Override
    public void onBindViewHolder(ListTeamAdapter.ItemViewHolder holder, int position) {
        holder.bind(dataListFiltered.get(position), listener);
//        holder.txtEmpEmail.setText(dataList.get(position).getEmail());
//        holder.txtEmpPhone.setText(dataList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    ArrayList<Member> filteredList = new ArrayList<>();
                    for (Member row : dataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<Member>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        Context ctx;
        TextView txtName;
        TextView txtPosition;
        TextView txtNickname;
        ImageView imageView;

        ItemViewHolder(Context context, View itemView) {
            super(itemView);
            this.ctx = context;
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPosition = (TextView) itemView.findViewById(R.id.txtPosition);
            txtNickname = (TextView) itemView.findViewById(R.id.txtNickname);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);

        }

        public void bind(final Member item, final OnItemClickListener listener) {
            txtName.setText(item.getName());
            txtNickname.setText(item.getGithub());
            txtPosition.setText(item.getGithub());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            Picasso.with(ctx).load(item.getAvatar()).into(imageView);
            setRoundedCorner(imageView);


        }

        @TargetApi(21)
        private void setRoundedCorner(ImageView imageView) {
            imageView.setClipToOutline(true);
        }
    }
}
