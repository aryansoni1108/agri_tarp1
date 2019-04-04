package org.tensorflow.demo.activities;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.demo.R;
import org.tensorflow.demo.adapters.BazaarRatesAdapter;
import org.tensorflow.demo.application.MySingleton;
import org.tensorflow.demo.modals.BazaarRates;
import org.tensorflow.demo.utilities.SqliteHelper;
import org.tensorflow.demo.utilities.onFetchTaskCompleted;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BazaarActivity extends AppCompatActivity {
    Spinner select_state_spinner;
    RecyclerView rv;
    private SqliteHelper helper;
    ArrayList<String> list1;
    ArrayAdapter<String> arrayAdapter1;
    Button btx;

    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazaaractivity);
        select_state_spinner = findViewById(R.id.select_state);
        rv = (RecyclerView) findViewById(R.id.rates_recyclerview);
        btx=findViewById(R.id.next_btx);
        helper=new SqliteHelper(this);
        list1=new ArrayList<>();



        list1=helper.getDistinctStates();

        arrayAdapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_state_spinner.setAdapter(arrayAdapter1);

        select_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int position, long l) {
                Log.v("positon1", "" + position);
                final String state=makeFirstLetterCaps(list1.get(position));
                url="https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=json&limit=100&filters[state]="+state;

                Log.e("url",""+url);
                rv.setLayoutManager(new LinearLayoutManager(BazaarActivity.this));
                fetchrates(url, new onFetchTaskCompleted() {
                    @Override
                    public void bazaarrates_oncomplete(List<BazaarRates> bazaarrates) {
                        BazaarRatesAdapter bz = new BazaarRatesAdapter(BazaarActivity.this,bazaarrates);
                        rv.setAdapter(bz);
                    }
                },BazaarActivity.this);
                btx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String state=makeFirstLetterCaps(list1.get(position));
                        url="https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=json&offset=2&limit=100&filters[state]="+state;

                        Log.e("url",""+url);
                        rv.setLayoutManager(new LinearLayoutManager(BazaarActivity.this));
                        fetchrates(url, new onFetchTaskCompleted() {
                            @Override
                            public void bazaarrates_oncomplete(List<BazaarRates> bazaarrates) {
                                BazaarRatesAdapter bz = new BazaarRatesAdapter(BazaarActivity.this,bazaarrates);
                                rv.setAdapter(bz);
                            }
                        },BazaarActivity.this);
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public String makeFirstLetterCaps(String a){


        StringBuilder result = new StringBuilder(a.length());
        a=a.toLowerCase();
        String words[] = a.split("\\ ");
        for (int i = 0; i < words.length; i++)
        {
            result.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1)).append(" ");

        }
        return result.toString();
    }
    public void fetchrates(String url, final onFetchTaskCompleted l, final Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("records");
                    Log.e("pp","pp"+jsonArray.toString());
                    List<BazaarRates> ratesList= Arrays.asList(gson.fromJson(String.valueOf(jsonArray),BazaarRates[].class));


                    l.bazaarrates_oncomplete(ratesList);
                    Log.e("lff", "onResponse: "+ratesList.size() );


                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error",Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}
