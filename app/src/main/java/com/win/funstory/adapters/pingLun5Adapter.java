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
import com.win.funstory.domain.PingLun5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * author：WangShuang
 * Date: 2015/12/30 15:58
 * email：m15046658245_1@163.com
 */
public class pingLun5Adapter extends BaseAdapter {

    private Context context;
    private List<PingLun5> list;

    public pingLun5Adapter(Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.pinglun5,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }

        PingLun5 item=list.get(position);
        ViewHolder holder= (ViewHolder) convertView.getTag();

        if(item.getUsername()!=null){
            holder.pinglun5_user_name.setText(item.getUsername());
            Picasso.with(context).load(getIconURL(item.getUserId(),item.getUserIcon()))
                    .transform(new CircleTransfrom())
                    .into(holder.pinglun5_user_icon);
        }else {
            holder.pinglun5_user_name.setText("匿名用户");
            holder.pinglun5_user_icon.setImageResource(R.mipmap.ic_launcher);
        }
        holder.pinglun5_content.setText(item.getContent());

        return convertView;
    }


    private  static  String getIconURL(long id,String icon){
        String url="http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";

        return  String.format(url,id/10000,id,icon);
    }


    public void addAll(Collection<? extends PingLun5> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }

    private  static  class ViewHolder{
        private ImageView pinglun5_user_icon;
        private TextView pinglun5_user_name,pinglun5_content;
        public  ViewHolder(View itemView){
            pinglun5_user_icon=(ImageView)itemView.findViewById(R.id.pinglun5_user_icon);
            pinglun5_user_name=(TextView)itemView.findViewById(R.id.pinglun5_user_name);
            pinglun5_content=(TextView)itemView.findViewById(R.id.pinglun5_content);
        }
    }

}
