package com.win.funstory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.win.funstory.R;
import com.win.funstory.domain.Item;
import com.win.funstory.domain.chunTu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author：WangShuang
 * Date: 2015/12/29 16:30
 * email：m15046658245_1@163.com
 */
public class chunTuAdapter extends BaseAdapter {

    private Context context;
    private List<chunTu> list;


    public chunTuAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.chuntu,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }

        chunTu item=list.get(position);
        ViewHolder holder= (ViewHolder) convertView.getTag();

        if(item.getUsername()!=null){
            holder.name.setText(item.getUsername());
           Picasso.with(context).load(getIconURL(item.getUserId(),item.getUserIcon()))
                    .transform(new CircleTransfrom())
                    .into(holder.icon);
        }else {
            holder.name.setText("匿名用户");
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        holder.content.setText(item.getContent());
        holder.up.setText("好笑:"+item.getUp());
        holder.comments_count.setText("评论:"+item.getComments_count()+"");
        holder.share_count.setText("分享:"+item.getShare_count()+"");
        if(item.getImage()==null){
            holder.images.setVisibility(View.GONE);

        }else{
            holder.images.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(getImageURL(item.getImage()))
                            //   .fit()//在imageView中不大好使
                    .resize(parent.getWidth(),0)
                            // .placeholder(R.mipmap.ic_launcher)//占位图
                            //   .error(R.mipmap.ic_launcher)
                    .into(holder.images);
        }
        return convertView;
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



    public void addAll(Collection<? extends chunTu> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }

    private  static  class ViewHolder{
        private ImageView icon,images;
        private TextView name,content,up,comments_count,share_count;
        public  ViewHolder(View itemView){
            icon=(ImageView)itemView.findViewById(R.id.user_icon);
            images=(ImageView)itemView.findViewById(R.id.images);
            name=(TextView)itemView.findViewById(R.id.user_name);
            content=(TextView)itemView.findViewById(R.id.content);
            up=(TextView)itemView.findViewById(R.id.up);
            comments_count=(TextView)itemView.findViewById(R.id.comments_count);
            share_count=(TextView)itemView.findViewById(R.id.share_count);
        }
    }
}
