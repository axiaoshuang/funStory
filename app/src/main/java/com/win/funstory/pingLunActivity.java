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
import com.win.funstory.adapters.pingLun1Adapter;
import com.win.funstory.domain.Item;
import com.win.funstory.domain.PingLun1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pingLunActivity extends AppCompatActivity implements Callback {

    private ImageView user_icon,ping_images;
    private TextView user_name,contents,up,comments_count,share_count;
    private ListView listView;
    private List<PingLun1> list;
    private pingLun1Adapter adapter;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_lun);



        listView=(ListView)findViewById(R.id.pinglun1_listView);

        adapter=new pingLun1Adapter(this);

        listView.setAdapter(adapter);


        user_icon= (ImageView) findViewById(R.id.ping_user_icon);
        user_name=(TextView)findViewById(R.id.ping_user_name);
        contents=(TextView)findViewById(R.id.ping_content);
        up=(TextView)findViewById(R.id.ping_up);
        comments_count=(TextView)findViewById(R.id.ping_comments_count);
        share_count=(TextView)findViewById(R.id.ping_share_count);

        ping_images=(ImageView)findViewById(R.id.ping_images);

        Intent intent=getIntent();

       Item  item= (Item) intent.getSerializableExtra("items");


        long pid = item.getPid();

        //    item.getUserIcon()

     //   user_icon.setImageResource();

        user_name.setText(item.getUsername());
        contents.setText(item.getContent());
        up.setText("好笑:" + item.getUp());
        comments_count.setText("评论:" + item.getComments_count());
        share_count.setText("分享:"+item.getShare_count());




        if(item.getUsername()!=null){
            user_name.setText(item.getUsername());
            Picasso.with(this).load(getIconURL(item.getUserId(), item.getUserIcon()))
                    .transform(new CircleTransfrom())
                    .into(user_icon);
        }else {
            user_name.setText("匿名用户");
            user_icon.setImageResource(R.mipmap.ic_launcher);
        }

       //获取屏幕宽度
        DisplayMetrics dm=new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth=dm.widthPixels;

        //窗口的高度
        int screenHeight=dm.heightPixels;

        if(item.getImage()==null){
            ping_images.setVisibility(View.GONE);

        }else{
            ping_images.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(getImageURL(item.getImage()))
                            //   .fit()//在imageView中不大好使
                    .resize(screenWidth, 0)
                    .into(ping_images);
        }

//===============================================================================
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

    public static  String getImageURL(String image){
        String url="http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern=Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher=pattern.matcher(image);

        matcher.find();
        //缺一个检测网络
        return  String.format(url,matcher.group(1),matcher.group(),"small",image);
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
                list.add(new PingLun1(items.getJSONObject(i)));
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
