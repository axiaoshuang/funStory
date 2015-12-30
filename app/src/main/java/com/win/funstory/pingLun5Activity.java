package com.win.funstory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
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
import com.win.funstory.adapters.pingLun4Adapter;
import com.win.funstory.adapters.pingLun5Adapter;
import com.win.funstory.domain.PingLun4;
import com.win.funstory.domain.PingLun5;
import com.win.funstory.domain.zuiXItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pingLun5Activity extends AppCompatActivity implements Callback {

    private ImageView ping_user_icon4,ping_images4;
    private TextView ping_user_name4,ping_content4,ping_up4,ping_comments_count4,ping_share_count4;
    private ListView listView;
    private Call call;

    private List<PingLun5> list;
    private pingLun5Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_lun5);


        adapter=new pingLun5Adapter(this);
        listView= (ListView) findViewById(R.id.pinglun5_listView);
       listView.setAdapter(adapter);

        ping_user_icon4= (ImageView)findViewById(R.id.ping_user_icon4);
        ping_images4=(ImageView)findViewById(R.id.ping_images4);

        ping_user_name4=(TextView)findViewById(R.id.ping_user_name4);
        ping_content4=(TextView)findViewById(R.id.ping_content4);
        ping_up4=(TextView)findViewById(R.id.ping_up4);
        ping_comments_count4=(TextView)findViewById(R.id.ping_comments_count4);
        ping_share_count4=(TextView)findViewById(R.id.ping_share_count4);


        Intent intent = getIntent();
        zuiXItem zuiXItems= (zuiXItem)intent.getSerializableExtra("zuiXItems");

        long pid = zuiXItems.getPid();

        ping_user_name4.setText(zuiXItems.getUsername());
        ping_content4.setText(zuiXItems.getContent());
        ping_up4.setText("好笑:"+zuiXItems.getUp());
        ping_comments_count4.setText("评论:"+zuiXItems.getComments_count());
        ping_share_count4.setText("分享:"+zuiXItems.getShare_count());




        if(zuiXItems.getUsername()!=null){
            ping_user_name4.setText(zuiXItems.getUsername());
            Picasso.with(this).load(getIconURL(zuiXItems.getUserId(),zuiXItems.getUserIcon()))
                    .transform(new CircleTransfrom())
                    .into( ping_user_icon4);
        }else {
            ping_user_name4.setText("匿名用户");
            ping_user_icon4.setImageResource(R.mipmap.ic_launcher);
        }




        //获取屏幕宽度
        DisplayMetrics dm=new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth=dm.widthPixels;

        //窗口的高度
        int screenHeight=dm.heightPixels;


        if(zuiXItems.getImage()==null){
            ping_images4.setVisibility(View.GONE);

        }else{
            ping_images4.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(getImageURL(zuiXItems.getImage()))
                            //   .fit()//在imageView中不大好使
                    .resize(screenWidth,0)
                            // .placeholder(R.mipmap.ic_launcher)//占位图
                            //   .error(R.mipmap.ic_launcher)
                    .into(ping_images4);
        }

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url("http://m2.qiushibaike.com/article/"+pid+"/comments?page=0").get().build();

        call=client.newCall(request);
        //同步请求
        // Response
        call.enqueue(this);



    }

    public static  String getImageURL(String image){
        String url="http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern=Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher=pattern.matcher(image);

        matcher.find();
        //缺一个检测网络
        return  String.format(url,matcher.group(1),matcher.group(),"small",image);
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
                list.add(new PingLun5(items.getJSONObject(i)));
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
