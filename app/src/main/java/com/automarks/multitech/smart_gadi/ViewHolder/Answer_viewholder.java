package com.automarks.multitech.smart_gadi.ViewHolder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.automarks.multitech.smart_gadi.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class Answer_viewholder extends ChildViewHolder {
    private TextView lists;
    Typeface type;

    public Answer_viewholder(View itemView) {
        super(itemView);
        type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/CenturyGothicRegular.ttf");

        lists=(TextView)itemView.findViewById(R.id.single_list_id);
        lists.setTypeface(type);

    }
    public void setAnswer(String answer)
    {
        lists.setText(answer);
    }

}
