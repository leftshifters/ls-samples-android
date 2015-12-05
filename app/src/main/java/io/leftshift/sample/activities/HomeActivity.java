package io.leftshift.sample.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.leftshift.androidm.activity.AccountActivity;
import io.leftshift.sample.R;
import io.leftshift.sample.adapter.HomeRecyclerAdapter;
import io.leftshift.sample.adapter.HomeRecyclerAdapter.OnItemClick;

public class HomeActivity extends AppCompatActivity implements OnItemClick {


    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;

    HomeRecyclerAdapter mAdapter;
    List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mList = Arrays.asList(getResources().getStringArray(R.array.examples));

        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new HomeRecyclerAdapter(this, mList, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setOnItemClickListener(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                break;
            case 1:
                startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                break;
        }
    }
}
