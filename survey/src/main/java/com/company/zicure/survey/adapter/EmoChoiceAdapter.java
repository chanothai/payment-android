package com.company.zicure.survey.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.zicure.survey.R;
import com.company.zicure.survey.interfaces.ItemClickListener;

/**
 * Created by 4GRYZ52 on 2/21/2017.
 */

public abstract class EmoChoiceAdapter extends RecyclerView.Adapter<EmoChoiceAdapter.EmoChoiceHolder> {
    private int valueChoice;
    public static int answerPosition = 0;

    public EmoChoiceAdapter(int valueChoice){
        this.valueChoice = valueChoice;
    }

    @Override
    public EmoChoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_choice_pattern_emotion, null);

        EmoChoiceHolder holder = new EmoChoiceHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        if (valueChoice >= 4){
            return valueChoice;
        }
        return valueChoice;
    }

    public class EmoChoiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView question, choiceBad, choiceNormal, choiceGreat;
        public ImageView imgEmoBad, imgEmoNormal, imgEmoGreat;
        public ItemClickListener itemClickListener;

        public EmoChoiceHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.topic_question);
            imgEmoBad = (ImageView) itemView.findViewById(R.id.image_emo_red);
            imgEmoNormal = (ImageView) itemView.findViewById(R.id.image_emo_yellow);
            imgEmoGreat = (ImageView) itemView.findViewById(R.id.image_emo_green);
            choiceBad = (TextView) itemView.findViewById(R.id.choice_bad);
            choiceNormal = (TextView) itemView.findViewById(R.id.choice_normal);
            choiceGreat = (TextView) itemView.findViewById(R.id.choice_great);

            imgEmoBad.setOnClickListener(this);
            imgEmoNormal.setOnClickListener(this);
            imgEmoGreat.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getLayoutPosition());
        }
    }
}
