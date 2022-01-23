package com.pradeep.androidtask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.List;

public class AdapterData extends SliderViewAdapter<AdapterData.ViewHolder> {
    Context context;
    List<Data> list;

    public AdapterData(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txtv_text.setText(list.get(position).text);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        TextView txtv_text;

        public ViewHolder(View itemView) {
            super(itemView);
            txtv_text = itemView.findViewById(R.id.txtv_text);
            this.itemView = itemView;
        }
    }
}
