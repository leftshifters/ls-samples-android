package io.leftshift.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.leftshift.sample.R;

/**
 * Created by akshay on 5/12/15.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {

    Context mContext;
    List<String> mList;
    public OnItemClick mOnItemClick;

    public HomeRecyclerAdapter(Context mContext, List<String> mList, OnItemClick mOnItemClick) {
        this.mContext = mContext;
        this.mList = mList;
        this.mOnItemClick = mOnItemClick;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_home, viewGroup, false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder homeViewHolder, int position) {
        String name = mList.get(position);
        homeViewHolder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view)
        TextView textView;

        public HomeViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClick != null) {
                        mOnItemClick.setOnItemClickListener(itemView, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClick {
        void setOnItemClickListener(View view, int position);
    }
}
