package com.company.zicure.payment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.zicure.payment.R;
import com.company.zicure.payment.interfaces.ItemClickListener;
import com.joooonho.SelectableRoundedImageView;
import com.zicure.company.com.model.util.ModelCart;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 4GRYZ52 on 2/7/2017.
 */

abstract public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.StatementHolder> {

    private Context context = null;
    public StatementAdapter(Context context){
        this.context = context;
    }

    @Override
    public StatementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_statement, null);
        StatementHolder statementHolder = new StatementHolder(view);
        return statementHolder;
    }

    @Override
    public int getItemCount() {
        return ModelCart.getInstance().getModel().option.size();
    }

    public class StatementHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //View
        public TextView date;
        public TextView cash;
        public TextView rating;
        public SelectableRoundedImageView imgProfile;

        public ItemClickListener itemClickListener;

        public StatementHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.statement_date);
            cash = (TextView) itemView.findViewById(R.id.statement_cash);
            rating = (TextView) itemView.findViewById(R.id.statement_point);
            imgProfile = (SelectableRoundedImageView) itemView.findViewById(R.id.img_profile);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        public void setItemOnClickListener(ItemClickListener itemClick){
            itemClickListener = itemClick;
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getLayoutPosition());
        }
    }
}
