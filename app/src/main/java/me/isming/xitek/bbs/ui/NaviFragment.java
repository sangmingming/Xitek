package me.isming.xitek.bbs.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.isming.xitek.bbs.R;
import me.isming.xitek.bbs.model.bean.BoardEntity;
import me.isming.xitek.bbs.util.Forums;
import me.isming.xitek.bbs.util.UIUtils;

/**
 * Created by sam on 17/3/7.
 */
public class NaviFragment extends Fragment {

    private RadioGroup mRadioGroup;
    private ListView mListView;
    private View mBlackButton;
    private List<BoardEntity> mBoardEntityList;
    private BoardListAdapter mAdapter;
    private NaviCallback mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof NaviCallback) {
            mCallback = (NaviCallback) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_drawer_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.grp_main);
        mListView = (ListView) view.findViewById(R.id.list_board);
        mBlackButton = view.findViewById(R.id.btn_black);
        mBoardEntityList = new ArrayList<>();
        mBoardEntityList.addAll(Forums.getBoardEntity(getContext()));
        RadioGroup.LayoutParams localLayoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mBoardEntityList.size(); i++) {
            RadioButton button = new RadioButton(getContext());
            BoardEntity entity = mBoardEntityList.get(i);
            button.setText(entity.label);
            int padding = UIUtils.dp2px(12);
            button.setPadding(padding, padding, padding, padding);
            button.setTextAppearance(getContext(), R.style.TextSubTitle);
            button.setBackgroundResource(R.drawable.select_main_board);
            button.setChecked(false);
            button.setButtonDrawable(R.drawable.blank);
            button.setId(i);
            mRadioGroup.addView(button, localLayoutParams);
        }
        mAdapter = new BoardListAdapter(getContext(), mBoardEntityList.get(0).list);
        mListView.setAdapter(mAdapter);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mAdapter.setData(mBoardEntityList.get(i).list);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCallback != null && mAdapter != null) {
                    mCallback.onSelectForum((BoardEntity.BoardItem) mAdapter.getItem(i));
                }
            }
        });
        mRadioGroup.check(0);

    }

    public static class BoardListAdapter extends BaseAdapter {
        private List<BoardEntity.BoardItem> mData;
        private Context mContext;

        public BoardListAdapter(Context context, List<BoardEntity.BoardItem> list) {
            mData = list;
            mContext = context;
        }

        public void setData(List<BoardEntity.BoardItem> list) {
            mData = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int i) {
            return mData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.row_board, viewGroup, false);
            }
            textView = (TextView) view.findViewById(R.id.item);
            BoardEntity.BoardItem item = (BoardEntity.BoardItem) getItem(i);
            textView.setText(item.label);
            return view;
        }
    }


    public interface NaviCallback {
        void onSelectForum(BoardEntity.BoardItem item);
        void onLogin();
    }
}
