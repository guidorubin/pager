package com.pager.pagerapp.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.Visibility;
import android.support.v7.widget.CardView;
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
import java.util.Map;

public class ListTeamAdapter extends RecyclerView.Adapter<ListTeamAdapter.ItemViewHolder> implements Filterable {


    private final Context ctx;
    private final OnItemClickListener listener;
    private Map<String, String> roles;
    private List<Member> dataList = new ArrayList<Member>();
    private List<Member> dataListFiltered = new ArrayList<Member>();

    public void updateStatus(String status, String memberStr) {

        if(dataList!=null) {
            for (int i = 0; i < dataList.size(); i++) {
                Member member = dataList.get(i);
                if(member.getGithub().equalsIgnoreCase(memberStr)) {
                    member.setStatus(status);
                    notifyItemChanged(i);
                    break;
                }
            }

            for (int i = 0; i < dataListFiltered.size(); i++) {
                Member member = dataListFiltered.get(i);
                if(member.getGithub().equalsIgnoreCase(memberStr)) {
                    member.setStatus(status);
                    notifyItemChanged(i);
                    break;
                }
            }

        }


    }

    public void addMember(Member newMember) {
        dataList.add(newMember);
        notifyItemChanged(dataList.size()-1);
    }

    public interface OnItemClickListener {
        void onItemClick(Member member);
    }

    public ListTeamAdapter(Context context,OnItemClickListener listener) {
        this.ctx = context;
//        this.dataList = dataList;
//        this.dataListFiltered = dataList;
        this.listener = listener;
    }

    public void setDataList(List<Member> dataList) {
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ListTeamAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_team, parent, false);
        return new ItemViewHolder(ctx, view);
    }

    @Override
    public void onBindViewHolder(ListTeamAdapter.ItemViewHolder holder, int position) {
        holder.bind(dataListFiltered.get(position), roles, listener);
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
        TextView txtStatus;

        TextView txtLanguage;
        TextView txtSkills;
        TextView txtLocation;

        ImageView imageView;

        ItemViewHolder(Context context, View itemView) {
            super(itemView);
            this.ctx = context;
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPosition = (TextView) itemView.findViewById(R.id.txtPosition);
            txtNickname = (TextView) itemView.findViewById(R.id.txtNickname);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);


        }

        public void bind(final Member item, final Map<String, String> roles, final OnItemClickListener listener) {
            txtName.setText(item.getName());
            txtNickname.setText(item.getGithub());
            if(roles!=null) {
                txtPosition.setText(roles.get("" + item.getRoleId()));
            }

            if(item.getStatus()!= null) {
                txtStatus.setText(item.getStatus());
            }


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

    public void updateRoles(Map<String, String> roles){
        this.roles = roles;
        if(dataList !=null && dataList.size()>0) {
            notifyDataSetChanged();
        }
    }

}
