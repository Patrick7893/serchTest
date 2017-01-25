package com.example.stasenkopavel.searchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.searchEditText) EditText searchEditText;
    @BindView(R.id.resultsRecycerView) RecyclerView resultsRecyclerView;
    @BindView(R.id.spinner) CircularProgressView spinner;

    private Realm realm;
    SearchAdapter adapter;
    ArrayList<SearchItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new SearchAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        resultsRecyclerView.setLayoutManager(layoutManager);
        resultsRecyclerView.setAdapter(adapter);
        realm = Realm.getDefaultInstance();
        getFromRealm();
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(searchEditText.getText().toString())) {
                        spinner.setVisibility(View.VISIBLE);
                        adapter.setEmptyAdapter();
                        makeRequest(searchEditText.getText().toString(), 1);
                        makeRequest(searchEditText.getText().toString(), 10);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void makeRequest(final String query, final int start) {
        ApiFactory.getInstance().getApiInterface().search(ApiInterface.apiKey, ApiInterface.cx, start, query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                    }

                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        spinner.setVisibility(View.GONE);
                        searchResponse.query = query;
                        saveToRealm(searchResponse);
                        if (searchResponse.getItems().size() > 0) {
                            if (start == 1)
                                adapter.addItems(new ArrayList<>(searchResponse.getItems()), false);
                            else
                                adapter.addItems(new ArrayList<>(searchResponse.getItems()), true);
                        }
                    }
                });
    }

    private void saveToRealm(SearchResponse response) {
        realm.beginTransaction();
        realm.deleteAll();
        realm.copyToRealm(response);
        realm.commitTransaction();
    }

    private void getFromRealm() {
        SearchResponse searchResponse = realm.where(SearchResponse.class).findFirst();
        if (searchResponse != null) {
            searchEditText.setText(searchResponse.query);
            adapter.addItems(new ArrayList<>(searchResponse.getItems()), true);
        }
    }

    @Override
    protected void onDestroy() {
        realm.close();
    }
}
