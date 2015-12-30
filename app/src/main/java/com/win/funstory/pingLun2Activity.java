package com.win.funstory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.win.funstory.adapters.pingLun1Adapter;
import com.win.funstory.adapters.pingLun2Adapter;
import com.win.funstory.domain.PingLun1;
import com.win.funstory.domain.PingLun2;
import com.win.funstory.domain.Video_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pingLun2Activity extends AppCompatActivity implements Callback {

    private ImageView user_icon1,ping_images1;
    private TextView user_name1,contents1,up1,comments_count1,share_count1;
    private ListView listView;

    private List<PingLun2> list;
    private pingLun2Adapter adapter;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_lun2);



        adapter=new pingLun2Adapter(this);
        listView= (ListView) findViewById(R.id.pinglun2_listView);

        listView.setAdapter(adapter);

        user_icon1=(ImageView)findViewById(R.id.ping_user_icon1);
        user_name1=(TextView)findViewById(R.id.ping_user_name1);
        contents1=(TextView)findViewById(R.id.ping_content1);
        up1=(TextView)findViewById(R.id.ping_up1);
        comments_count1=(TextView)findViewById(R.id.ping_comments_count1);
        share_count1=(TextView)findViewById(R.id.ping_share_count1);
        ping_images1=(ImageView)findViewById(R.id.ping_images1);


        Intent  intent  =getIntent();


        Video_item video_item= (Video_item) intent.getSerializableExtra("Video_item");
        long pid = video_item.getPid();


        user_name1.setText(video_item.getUsername());
        contents1.setText(video_item.getContent());
        up1.setText("好笑:"+video_item.getUp());
        comments_count1.setText("评论:"+video_item.getComments_count());
        share_count1.setText("分享:"+video_item.getShare_count());


        if(video_item.getUsername()!=null){
            user_name1.setText(video_item.getUsername());
            Picasso.with(this).load(getIconURL(video_item.getUserId(), video_item.getUserIcon()))
                    .transform(new CircleTransfrom())
                    .into(user_icon1);
        }else {
            user_name1.setText("匿名用户");
            user_icon1.setImageResource(R.mipmap.ic_launcher);
        }


        //获取屏幕宽度
        DisplayMetrics dm=new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth=dm.widthPixels;

        //窗口的高度
        int screenHeight=dm.heightPixels;

        if(video_item.getUrlimage()==null){
            ping_images1.setVisibility(View.GONE);

        }else{
            ping_images1.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(video_item.getUrlimage())
                            //   .fit()//在imageView中不大好使
                    .resize(screenWidth, 0)
                            // .placeholder(R.mipmap.ic_launcher)//占位图
                            //   .error(R.mipmap.ic_launcher)
                    .into(ping_images1);
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
                list.add(new PingLun2(items.getJSONObject(i)));
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
