package com.example.conferenceapp;

import android.accounts.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountCardViewHolder> {
    private ArrayList<AccountCard> mAccountList;
    private Context context;

    public static class AccountCardViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView usernameView;
        public TextView vipView;

        Context context;

        public AccountCardViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            nameView = itemView.findViewById(R.id.namecard_text);
            usernameView = itemView.findViewById(R.id.username_text);
            vipView = itemView.findViewById(R.id.vipcardtext);
            this.context = context;
        }
    }

    public AccountsAdapter(Context context, ArrayList<AccountCard> accountlist) {
        this.context = context;
        mAccountList = accountlist;
    }

    @NonNull
    @Override
    public AccountCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        AccountCardViewHolder cvh = new AccountCardViewHolder(v, context);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountCardViewHolder holder, int position) {
        AccountCard currItem = mAccountList.get(position);
        holder.nameView.setText(currItem.getName());
        holder.usernameView.setText(currItem.getUsername());
        if (currItem.isVip()){
            holder.vipView.setText("VIP");
            holder.vipView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_stars_24, 0, 0, 0);
        }
        //holder.vipView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    @Override
    public int getItemCount() {
        return mAccountList.size();
    }

    public void updateData(ArrayList<AccountCard> AccountList){
        mAccountList.clear();
        mAccountList.addAll(AccountList);
        notifyDataSetChanged();
    }


}