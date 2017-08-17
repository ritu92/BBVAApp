package com.example.malik.bbvaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.malik.bbvaapp.R;
import com.example.malik.bbvaapp.model.BankLocation;

import java.util.ArrayList;

/**
 * Created by malik on 8/9/2017.
 */

public class BanksListAdapter extends BaseAdapter {
    ArrayList<BankLocation> mbbvaLocations;
    Context mContext;
    LayoutInflater layoutInflater;


    public BanksListAdapter(ArrayList<BankLocation> bankLocations, Context context){

        mbbvaLocations = bankLocations;
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mbbvaLocations.size();
    }

    @Override
    public BankLocation getItem(int position) {
        return mbbvaLocations.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View view = convertView;
        if(convertView==null) {

            view = layoutInflater.inflate(R.layout.list_item, viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_address = (TextView) view.findViewById(R.id.address);
            view.setTag(viewHolder);

        }else{


        viewHolder = (ViewHolder) view.getTag();

    }
    BankLocation bankLocation = mbbvaLocations.get(position);
        if(bankLocation != null) {

            viewHolder.tv_address.setText(bankLocation.getFormattedAddress());
        }
        return view;
    }
    static class ViewHolder{
        private TextView tv_address;
    }
}
