package org.tensorflow.demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.tensorflow.demo.R;
import org.tensorflow.demo.adapters.StateDepartmentAdapter;


public class StateDepartment extends AppCompatActivity {

    private StateDepartmentAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recycler);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        String[] array=getResources().getStringArray(R.array.States);
        String[] links=getResources().getStringArray(R.array.state_links);

        adapter = new StateDepartmentAdapter(this,array,links);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.progress).setVisibility(View.GONE);
    }
}
