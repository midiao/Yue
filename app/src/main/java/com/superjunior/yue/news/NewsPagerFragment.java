package com.superjunior.yue.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superjunior.yue.R;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsPagerFragment extends Fragment {
    public static final String TITLE = "title";
    private String title;

    public static NewsPagerFragment newInstance(String page) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, page);
        NewsPagerFragment fragment = new NewsPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_news_pager, container, false);
        TextView textView = (TextView) contentView.findViewById(R.id.news_text);
        textView.setText(title);
        return contentView;
    }
}
