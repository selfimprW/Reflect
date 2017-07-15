package com.mylibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Description:
 * Created by Jiacheng on 2017/7/12.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ContentHolder> {

    private List<String> actions;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
        notifyDataSetChanged();
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        return new ContentHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        String action = actions.get(position);
        holder.button.setText(action);
    }

    @Override
    public int getItemCount() {
        return actions == null ? 0 : actions.size();
    }

    class ContentHolder extends RecyclerView.ViewHolder {
        Button button;

        public ContentHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.action);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.onItemClickListener(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClick {
        void onItemClickListener(View view, int position);
    }
}
