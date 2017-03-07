package me.isming.xitek.bbs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.isming.xitek.bbs.R;
import me.isming.xitek.bbs.glide.GlideImageGetter;
import me.isming.xitek.bbs.model.ApiClient;
import me.isming.xitek.bbs.model.bean.PostItem;
import me.isming.xitek.bbs.model.bean.ThreadItem;
import me.isming.xitek.bbs.util.CommonSubscriber;
import me.isming.xitek.bbs.util.HtmlUtil;
import me.isming.xitek.bbs.util.StringUtils;
import me.isming.xitek.bbs.util.TimeUtils;
import rx.android.schedulers.AndroidSchedulers;

public class ThreadActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {

    public final static String EXTRA_THREAD = "thread_item";
    private ThreadItem mThreadItem;
    private XRecyclerView mRecyclerView;
    private List<PostItem> mDatas;
    private int mPage;

    public static void show(Context context, ThreadItem threadItem) {
        Intent intent = new Intent(context, ThreadActivity.class);
        intent.putExtra(EXTRA_THREAD, threadItem);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        mThreadItem = getIntent().getParcelableExtra(EXTRA_THREAD);
        if (mThreadItem == null || TextUtils.isEmpty(mThreadItem.threadid)) {
            finish();
            return;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mThreadItem.title);

        mRecyclerView = (XRecyclerView) findViewById(android.R.id.list);
        mRecyclerView.setLoadingListener(this);
        mDatas = new ArrayList<>();
        mRecyclerView.setLoadingMoreEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new Adapter(mDatas));
        mRecyclerView.refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }

    private void loadData() {
        ApiClient.getApiServices()
                .getPostList(mThreadItem.threadid, mPage, 0, 0, "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<PostItem>>() {
                    @Override
                    public void onNext(List<PostItem> postItems) {
                        super.onNext(postItems);
                        mRecyclerView.loadMoreComplete();
                        mRecyclerView.refreshComplete();
                        if (postItems != null && postItems.size() > 0) {
                            if (mPage == 0) {
                                mDatas.clear();
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                            mDatas.addAll(postItems);
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                            mPage++;
                        } else if (postItems != null && postItems.size() == 0) {
                            mRecyclerView.setNoMore(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRecyclerView.loadMoreComplete();
                        mRecyclerView.refreshComplete();
                    }
                });
    }

    public static class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        public List<PostItem> mDatas;

        public Adapter(List<PostItem> data) {
            mDatas = data == null ? new ArrayList<PostItem>() : data;
        }


        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            PostItem item = mDatas.get(position);
            holder.updateView(item);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public static class Holder extends RecyclerView.ViewHolder {

            public TextView timeView, positionView, authorView, contentView;

            public Holder(View itemView) {
                super(itemView);
                timeView = (TextView) itemView.findViewById(R.id.time);
                positionView = (TextView) itemView.findViewById(R.id.position);
                authorView = (TextView) itemView.findViewById(R.id.author);
                contentView = (TextView) itemView.findViewById(R.id.content);
            }

            public void updateView(final PostItem item) {
                positionView.setText(item.getPositionText());
                timeView.setText(TimeUtils.timeFormat(item.dateline));
                authorView.setText(item.username);
                String s = StringUtils.bbcode(item.content);
                if (!TextUtils.isEmpty(item.uploadfp) && item.uploadfp.endsWith("jpg")) {
                    s = s + "<p><img src='" + item.uploadfp + "'/> </p>";
                }
                if (!TextUtils.isEmpty(item.exif)) {
                    s = s + "<br/><font color='grey'>" + item.exif + "</font>";
                }
                HtmlUtil.setHtml(contentView, s);
            }
        }
    }
}
