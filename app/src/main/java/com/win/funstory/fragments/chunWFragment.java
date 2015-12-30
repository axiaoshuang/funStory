package com.win.funstory.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.win.funstory.R;
import com.win.funstory.adapters.chunwenAdapter;
import com.win.funstory.adapters.enjoyAdapter;
import com.win.funstory.domain.Item;
import com.win.funstory.domain.chunwen_item;
import com.win.funstory.pingLun3Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class chunWFragment extends Fragment implements Callback, AdapterView.OnItemClickListener {

    private ListView listView;
    private chunwenAdapter adapter;
    private Call call;
    private ArrayList<chunwen_item> list;


    public chunWFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_chun_w, container, false);

        listView=(ListView)view.findViewById(R.id.list_chunwen);
        adapter=new chunwenAdapter(getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url("http://m2.qiushibaike.com/article/list/text?page=").get().build();

        call=client.newCall(request);
        //同步请求
        // Response
        call.enqueue(this);

        return view;
    }


    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {
        final String s = response.body().string();

        try {
            JSONObject object=new JSONObject(s);
            JSONArray items = object.getJSONArray("items");
            list=new ArrayList<>();
            for (int i = 0; i <items.length() ; i++) {
//                 list.add(items.getJSONObject(i).getString("content"));
                list.add(new chunwen_item(items.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                adapter.addAll(list);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        call.cancel();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        chunwen_item    chunwen_items  =list.get(position);
        Bundle bundle=new Bundle();
        bundle.putSerializable("chunwen_items",chunwen_items);
        Intent intent=new Intent(getActivity(),pingLun3Activity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
