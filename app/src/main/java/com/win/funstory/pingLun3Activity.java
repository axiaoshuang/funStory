package com.win.funstory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.win.funstory.adapters.CircleTransfrom;
import com.win.funstory.adapters.pingLun3Adapter;
import com.win.funstory.domain.PingLun3;
import com.win.funstory.domain.chunwen_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class pingLun3Activity extends AppCompatActivity implements Callback {

    private ImageView ping_user_icon2,ping_images2;
    private TextView ping_user_name2,ping_content2,ping_up2,ping_comments_count2,ping_share_count2;
    private  ListView listView;

    private List<PingLun3> list;
    private pingLun3Adapter adapter;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_lun3);

        ping_user_icon2=(ImageView)findViewById(R.id.ping_user_icon2);
        ping_images2=(ImageView)findViewById(R.id.ping_images2);

        ping_user_name2=(TextView)findViewById(R.id.ping_user_name2);
        ping_content2=(TextView)findViewById(R.id.ping_content2);
        ping_up2=(TextView)findViewById(R.id.ping_up2);
        ping_comments_count2=(TextView)findViewById(R.id.ping_comments_count2);
        ping_share_count2=(TextView)findViewById(R.id.ping_share_count2);

        listView=(ListView)findViewById(R.id.pinglun3_listView);

        adapter=new pingLun3Adapter(this);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        chunwen_item chunwen_items = (chunwen_item) intent.getSerializableExtra("chunwen_items");


                 long pid=   chunwen_items.getPid();

        ping_user_name2.setText(chunwen_items.getUsername());
        ping_content2.setText(chunwen_items.getContent());
        ping_up2.setText("好笑:"+chunwen_items.getUp());
        ping_comments_count2.setText("评论:"+chunwen_items.getComments_count());
        ping_share_count2.setText("分享:"+chunwen_items.getShare_count());


        if(chunwen_items.getUsername()!=null){
            ping_user_name2.setText(chunwen_items.getUsername());
            Picasso.with(this).load(getIconURL(chunwen_items.getUserId(),chunwen_items.getUserIcon()))
                    .transform(new CircleTransfrom())
                    .into(ping_user_icon2);
        }else {
            ping_user_name2.setText("匿名用户");
            ping_user_icon2.setImageResource(R.mipmap.ic_launcher);
        }

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url("http://m2.qiushibaike.com/article/"+pid+"/comments?page=0").get().build();

        call=client.newCall(request);
        //同步请求
        // Response
        call.enqueue(this);


    }


    private  static  String getIconURL(long id,String icon){
        String url="http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";

        return  String.format(url,id/10000,id,icon);
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
                list.add(new PingLun3(items.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
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
}
