package com.automarks.multitech.smart_gadi.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.automarks.multitech.smart_gadi.Pojo.Faq_answer;
import com.automarks.multitech.smart_gadi.R;
import com.automarks.multitech.smart_gadi.ViewHolder.Answer_viewholder;
import com.automarks.multitech.smart_gadi.ViewHolder.Question_viewholder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Question_Adapter extends ExpandableRecyclerViewAdapter<Question_viewholder,Answer_viewholder> {
    public Question_Adapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public Question_viewholder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_layout,parent,false);
        return new Question_viewholder(view);    }

    @Override
    public Answer_viewholder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_layout,parent,false);
        return new Answer_viewholder(view);
    }

    @Override
    public void onBindChildViewHolder(Answer_viewholder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Faq_answer list= (Faq_answer) group.getItems().get(childIndex);
        holder.setAnswer(list.getBody());
    }

    @Override
    public void onBindGroupViewHolder(Question_viewholder holder, int flatPosition, ExpandableGroup group) {
        holder.setQuestion(group.getTitle());

    }


}