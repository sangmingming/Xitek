package me.isming.xitek.bbs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.isming.xitek.bbs.R;
import me.isming.xitek.bbs.model.ApiClient;
import me.isming.xitek.bbs.model.bean.ThreadItem;
import me.isming.xitek.bbs.util.CommonSubscriber;
import me.isming.xitek.bbs.util.Forums;
import me.isming.xitek.bbs.util.TimeUtils;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by sam on 17/3/2.
 */
public class ThreadListFragment extends Fragment {

    public static final String EXTRA_FID = "fid";
    public static final String EXTRA_SHOW_FORUMNAME = "forum_name_show";

    private boolean showForumName = false;
    private String fid;
    private XRecyclerView mRecyclerView;
    private List<ThreadItem> mData;
    private int mCurrentPage;
    private Adapter mAdapter;

    public static ThreadListFragment newInstance(String fid, boolean showForumName) {
        ThreadListFragment listFragment = new ThreadListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FID, fid);
        bundle.putBoolean(EXTRA_SHOW_FORUMNAME, showForumName);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    public void reset(String fid, boolean showForumName) {
        if (getArguments() != null) {
            getArguments().putString(EXTRA_FID, fid);
            getArguments().putBoolean(EXTRA_SHOW_FORUMNAME, showForumName);
        }
        this.fid = fid;
        this.showForumName = showForumName;
        if (mAdapter != null) {
            mAdapter.setShowForumName(this.showForumName);
        }
        mRecyclerView.refresh();

    }

    public static ThreadListFragment newInstance(String fid) {
        return newInstance(fid, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fid = getArguments().getString(EXTRA_FID);
        showForumName = getArguments().getBoolean(EXTRA_SHOW_FORUMNAME);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (XRecyclerView) view.findViewById(android.R.id.list);
        mData = new ArrayList<>();
        mAdapter = new Adapter(mData, showForumName);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 0;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.refresh();
    }

    private void loadData() {
        ApiClient.getApiServices()
                .getThreadList(fid, mCurrentPage, "", "", "1.32")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<ThreadItem>>() {
                    @Override
                    public void onNext(List<ThreadItem> threadItems) {
                        super.onNext(threadItems);
                        mRecyclerView.loadMoreComplete();
                        if (threadItems != null && threadItems.size() > 0) {
                            if (mCurrentPage == 0) {
                                mData.clear();
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                                mRecyclerView.refreshComplete();
                            }
                            mData.addAll(threadItems);
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                            mCurrentPage++;
                        }
                    }
                });

    }


    public static class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        public List<ThreadItem> mDatas;
        public boolean mShowForumName;

        public Adapter(List<ThreadItem> items, boolean showForumName) {
            mDatas = items == null ? new ArrayList<ThreadItem>() : items;
            mShowForumName = showForumName;
        }

        public void setShowForumName(boolean showForumName) {
            this.mShowForumName = showForumName;
            notifyDataSetChanged();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_thread, parent, false));
        }

        @Override
        public void onBindViewHolder(Adapter.Holder holder, int position) {
            ThreadItem item = mDatas.get(position);
            if (!mShowForumName) {
                item.forumid = -1;
            }
            holder.updateView(item);

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }


        public static class Holder extends RecyclerView.ViewHolder {

            public TextView titleView;
            public TextView authorView;
            public TextView timeView;
            public TextView readCountView;
            public TextView commentCountView;


            public Holder(View itemView) {
                super(itemView);
                titleView = (TextView) itemView.findViewById(R.id.title);
                authorView = (TextView) itemView.findViewById(R.id.author);
                timeView = (TextView) itemView.findViewById(R.id.time);
                readCountView = (TextView) itemView.findViewById(R.id.read_count);
                commentCountView = (TextView) itemView.findViewById(R.id.comment_count);

            }

            public void updateView(final ThreadItem item) {
                String forumName = Forums.getForumName(item.forumid);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                if (item.elite == 1) {
                    builder.append("精选");
                    //builder.setSpan(new TextAppearanceSpan(titleView.getContext(), R.style.TextWhite), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //builder.setSpan(new BackgroundColorSpan(titleView.getContext().getResources().getColor(R.color.colorAccent)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (!TextUtils.isEmpty(forumName)) {
                    builder.append("【"+forumName+"】");
                }
                builder.append(item.title);
                titleView.setText(builder);

                authorView.setText(item.postusername);
                timeView.setText(TimeUtils.timeFormat(item.dateline));
                commentCountView.setText(item.replycount);
                readCountView.setText(item.views);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ThreadActivity.show(view.getContext(), item);
                    }
                });
            }
        }

    }
}
