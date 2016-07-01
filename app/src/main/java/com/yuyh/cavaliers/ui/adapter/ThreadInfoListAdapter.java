package com.yuyh.cavaliers.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.TextView;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.forum.ThreadListData.ThreadInfo;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ThreadInfoListAdapter extends RecyclerView.Adapter<ThreadInfoListAdapter.ViewHolder> {

    private List<ThreadInfo> threads = new ArrayList<>();
    private OnListItemClickListener listener;

    public ThreadInfoListAdapter() {

    }

    public void bind(List<ThreadInfo> threads) {
        this.threads = threads;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_threads, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ThreadInfo thread = threads.get(position);
        holder.thread = thread;
        if (thread.lightReply > 0) {
            holder.tvLight.setText(String.valueOf(thread.lightReply));
            holder.tvLight.setVisibility(View.VISIBLE);
        } else {
            holder.tvLight.setVisibility(View.GONE);
        }
        holder.tvReply.setText(thread.replies);
        holder.tvTitle.setText(Html.fromHtml(thread.title));
        holder.tvSingleTime.setVisibility(View.VISIBLE);
        holder.tvSummary.setVisibility(View.GONE);
        holder.grid.setVisibility(View.GONE);
        if (thread.forum != null) {
            holder.tvSingleTime.setText(thread.forum.name);
        } else {
            holder.tvSingleTime.setText(thread.time);
        }
        showItemAnim(holder.cardView, position);
        holder.cardView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (listener != null)
                    listener.onItemClick(view, position, thread);
            }
        });

    }

    private int mLastPosition = -1;

    public void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tvTitle)
        TextView tvTitle;
        @InjectView(R.id.tvSummary)
        TextView tvSummary;
        @InjectView(R.id.grid)
        GridLayout grid;
        @InjectView(R.id.tvSingleTime)
        TextView tvSingleTime;
        @InjectView(R.id.tvReply)
        TextView tvReply;
        @InjectView(R.id.tvLight)
        TextView tvLight;
        @InjectView(R.id.cardView)
        CardView cardView;

        ThreadInfo thread;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public void setOnItemClickListener(OnListItemClickListener listener) {
        this.listener = listener;
    }
}
