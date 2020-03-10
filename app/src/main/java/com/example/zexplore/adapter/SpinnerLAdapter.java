package com.example.zexplore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.zexplore.R;
import com.example.zexplore.model.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaba Michael on 2/04/2019.
 * Cyberspace Limited
 * michael.shaba@cyberspace.net.ng
 */
public class SpinnerLAdapter extends BaseAdapter {
    private Context context;
    private List<BaseResponse> responseList;

    public SpinnerLAdapter(Context context) {
        this.context = context;
        this.responseList = new ArrayList<>();
    }

    public void updateData(List<BaseResponse> responseList) {
        this.responseList.clear();
        this.responseList.addAll(responseList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return responseList.size();
    }

    @Override
    public BaseResponse getItem(int position) {
        return responseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_spinner, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BaseResponse baseResponse = responseList.get(position);
        if (baseResponse != null) {
            viewHolder.name.setText(baseResponse.getName().toUpperCase());
            viewHolder.name.setTag(baseResponse.getId());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
    }
}