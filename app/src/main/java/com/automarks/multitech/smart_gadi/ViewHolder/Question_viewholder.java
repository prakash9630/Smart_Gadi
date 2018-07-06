package com.automarks.multitech.smart_gadi.ViewHolder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.automarks.multitech.smart_gadi.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class Question_viewholder extends GroupViewHolder {
    private TextView listtitle;
    Typeface type;

    public Question_viewholder(View itemView) {
        super(itemView);
        listtitle=(TextView)itemView.findViewById(R.id.list_title);
        type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/CenturyGothicRegular.ttf");
        listtitle.setTypeface(type);
    }
    public void setQuestion(String question)
    {

        listtitle.setText(question);
    }
}
