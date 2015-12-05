package io.leftshift.androidm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.leftshift.androidm.activity.AccountActivity;
import io.leftshift.sample.R;

/**
 * Created by akshay on 5/12/15.
 */
public class AccountFragment extends BaseFragment {

    @Bind(R.id.auto_email_text_view)
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> mAdapter;
    List<String> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUi();
    }

    public void initUi() {
        mList = ((AccountActivity) getActivity()).getAccounts();
        if (mList != null) {
            autoCompleteTextView.setThreshold(1);
            mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mList);
            autoCompleteTextView.setAdapter(mAdapter);
        }
    }
}
