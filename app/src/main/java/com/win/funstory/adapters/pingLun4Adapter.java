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
import com.win.funstory.domain.PingLun4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * author：WangShuang
 * Date: 2015/12/30 15:58
 * email：m15046658245_1@163.com
 */
public class pingLun4Adapter extends BaseAdapter {

    private Context context;
    private List<PingLun4> list;

    public pingLun4Adapter(Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.pinglun4,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }

        PingLun4 item=list.get(position);
        ViewHolder holder= (ViewHolder) convertView.getTag();

        if(item.getUsername()!=null){
            holder.pinglun4_user_name.setText(item.getUsername());
            Picasso.with(context).load(getIconURL(item.getUserId(),item.getUserIcon()))
                    .transform(new CircleTransfrom())
                    .into(holder.pinglun4_user_icon);
        }else {
            holder.pinglun4_user_name.setText("匿名用户");
            holder.pinglun4_user_icon.setImageResource(R.mipmap.ic_launcher);
        }
        holder.pinglun4_content.setText(item.getContent());

        return convertView;
    }


    private  static  String getIconURL(long id,String icon){
        String url="http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";

        return  String.format(url,id/10000,id,icon);
    }


    public void addAll(Collection<? extends PingLun4> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }

    private  static  class ViewHolder{
        private ImageView pinglun4_user_icon;
        private TextView pinglun4_user_name,pinglun4_content;
        public  ViewHolder(View itemView){
            pinglun4_user_icon=(ImageView)itemView.findViewById(R.id.pinglun4_user_icon);
            pinglun4_user_name=(TextView)itemView.findViewById(R.id.pinglun4_user_name);
            pinglun4_content=(TextView)itemView.findViewById(R.id.pinglun4_content);
        }
    }

}
