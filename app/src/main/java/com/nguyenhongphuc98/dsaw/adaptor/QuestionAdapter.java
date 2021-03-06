package com.nguyenhongphuc98.dsaw.adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.anychart.charts.Resource;
import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.data.model.Question;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter {
    final int CODE_OPEN_DOCUMENT = 22;

    Context context;
    List<Question> lsQuestion;
    AnswerAdaptor mAdapter;

    public QuestionAdapter(@NonNull Context context, List<Question> ls){
        super(context, R.layout.custom_survey_multichoice_answer, ls);
        this.context = context;
        lsQuestion = ls;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewRow = convertView;
        View subRow;
        QuestionAdapter.ViewHolder holder = new QuestionAdapter.ViewHolder();

        if (lsQuestion.get(position).getType().equals("MT")){
            if (viewRow == null) {
                viewRow = layoutInflater.inflate(R.layout.custom_survey_multichoice_question,parent,false);

                holder.number = viewRow.findViewById(R.id.number);
                holder.question = viewRow.findViewById(R.id.question);
                holder.listviewOfAnswer = viewRow.findViewById(R.id.listview_of_answer);
                holder.listOfAnswer = viewRow.findViewById(R.id.list_of_answer);

                viewRow.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }

            int numOfQuestion = position + 1;
            ViewHolder viewHolder = (ViewHolder) viewRow.getTag();
            Question e = lsQuestion.get(position);
            viewHolder.number.setText("Câu " + numOfQuestion + ":");
            viewHolder.question.setText(e.getTitle());
            //mAdapter = new AnswerAdaptor(getContext(), lsQuestion.get(position).getAnswers());
            //viewHolder.listviewOfAnswer.setAdapter(mAdapter);
            viewHolder.listOfAnswer.removeAllViews();

            for(int i = 0; i < lsQuestion.get(position).getAnswers().size(); i++) {
                subRow = layoutInflater.inflate(R.layout.custom_survey_multichoice_answer,null);
                final CheckedTextView answer = subRow.findViewById(R.id.answer);
                answer.setText(lsQuestion.get(position).getAnswers().get(i));

                subRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (answer.isChecked()) answer.setChecked(false);
                        else answer.setChecked(true);
                    }
                });

                /*CheckedTextView answer = new CheckedTextView(context);
                answer.setText(lsQuestion.get(position).getAnswers().get(i));
                answer.setCheckMarkDrawable(checkMarkDrawableResId);*/

                viewHolder.listOfAnswer.addView(subRow);
            }
            Log.e("QuestionAdapter","Câu " + numOfQuestion + " loai " + lsQuestion.get(position).getType());
        }
        else if (lsQuestion.get(position).getType().equals("image")){
            if (viewRow == null) {
                viewRow = layoutInflater.inflate(R.layout.custom_survey_file_question,parent,false);
                holder.number = viewRow.findViewById(R.id.tqfNumber);
                holder.question = viewRow.findViewById(R.id.tqfQuestion);
                holder.attachFile = viewRow.findViewById(R.id.attach_file_button);
                holder.imageCover = viewRow.findViewById(R.id.image_cover);

                holder.attachFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intentOpenFile = new Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT);
                        //ActivityCompat.startActivityForResult(intentOpenFile, Intent.createChooser(intentOpenFile,"Choose image"),CODE_OPEN_DOCUMENT);
                        //context.startActivity(Intent.createChooser(intentOpenFile,"Choose image"));

                        Log.e("QuestionAdapter","Choose image event");
                    }
                });

                viewRow.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }

            int numOfQuestion = position + 1;
            ViewHolder viewHolder = (ViewHolder) viewRow.getTag();
            Question e = lsQuestion.get(position);
            viewHolder.number.setText("Câu " + numOfQuestion + ":");
            viewHolder.question.setText(e.getTitle());
            Log.e("QuestionAdapter","Câu " + numOfQuestion + " loai " + lsQuestion.get(position).getType());
        }
        else{
            if (viewRow == null) {
                viewRow = layoutInflater.inflate(R.layout.custom_survey_text_question,parent,false);
                holder.number = viewRow.findViewById(R.id.tqNumber);
                holder.question = viewRow.findViewById(R.id.tqQuestion);
                holder.answer = viewRow.findViewById(R.id.edtAnswer);

                viewRow.setTag(holder);
            }
            int numOfQuestion = position + 1;
            ViewHolder viewHolder = (ViewHolder) viewRow.getTag();
            Question e = lsQuestion.get(position);
            viewHolder.number.setText("Câu " + numOfQuestion + ":");
            viewHolder.question.setText(e.getTitle());
            Log.e("QuestionAdapter","Câu " + numOfQuestion + " loai " + lsQuestion.get(position).getType());
            Log.e("QuestionAdapter","Câu " + numOfQuestion + " co cau tra loi la " + lsQuestion.get(position).getAnswers());
        }

        return viewRow;
    }

    public static class ViewHolder{
        TextView number;
        TextView question;
        ListView listviewOfAnswer;
        EditText answer;
        LinearLayout listOfAnswer;
        LinearLayout attachFile;
        ImageView imageCover;
    }
}
