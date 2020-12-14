package com.nguyenhongphuc98.dsaw.ui.medical_report;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.adaptor.QuestionAdapter;
import com.nguyenhongphuc98.dsaw.adaptor.TextQuestionAdapter;
import com.nguyenhongphuc98.dsaw.data.DataCenter;
import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.Question;

import java.util.ArrayList;
import java.util.List;

public class CreateSurvey extends Fragment {

    private CreateSurveyViewModel mViewModel;
    private List<Question> lsQuestion = new ArrayList<>();

    Spinner typeOfSurvey;
    Button chooseTypeQuestion;
    Button saveSurvey;
    ListView lvQuestion;
    EditText nameOfSurvey;
    ChooseTypeOfQuestionDialog typeOfQuestionDialog;
    CreateQuestionDialog createQuestionDialog;

    public static CreateSurvey newInstance() {
        return new CreateSurvey();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_create_survey, container, false);

        InitComponent(view);
        InitEvent();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CreateSurveyViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.setContext(this.getContext());
        /*mViewModel.GetAdapter().observe(this, new Observer<QuestionAdapter>() {
            @Override
            public void onChanged(QuestionAdapter questionAdapter) {
                lvQuestion.setAdapter(questionAdapter);
            }
        });*/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            String editQuestionString = data.getStringExtra("EDIT_QUESTION");
            ArrayList<String> editAnswerString = data.getStringArrayListExtra("EDIT_ANSWER");
            String questionType = data.getStringExtra("TYPE_QUESTION");
            Log.d("Create survey", "Question get: " + editQuestionString);
            Log.d("Create survey", "Answer get: " + editAnswerString);
            Log.d("Create survey", "Question type: " + questionType);

            Question newQues;
            if (questionType.equalsIgnoreCase("MT")) {
                newQues = new Question("", editAnswerString, "", editQuestionString, "MT");
                newQues.setAnswers(editAnswerString);
            }
            else {
                newQues = new Question("", editAnswerString, "", editQuestionString, questionType);
            }
            lsQuestion.add(newQues);
            QuestionAdapter newQuesAdapter = new QuestionAdapter(getContext(), lsQuestion);
            lvQuestion.setAdapter(newQuesAdapter);
        }
    }

    public void InitComponent(View view){
        chooseTypeQuestion =view.findViewById(R.id.btn_choose_type_question);
        typeOfSurvey = view.findViewById(R.id.type_of_survey);
        lvQuestion = view.findViewById(R.id.listview_of_new_question);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.type_of_survey, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typeOfSurvey.setAdapter(adapter);
        saveSurvey = view.findViewById(R.id.save_survey);
        nameOfSurvey = view.findViewById(R.id.name_of_survey);
    }

    public void InitEvent(){
        chooseTypeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int spinner_pos = typeOfSurvey.getSelectedItemPosition();
                if (spinner_pos > 1) {
                    typeOfQuestionDialog = new ChooseTypeOfQuestionDialog();
                    typeOfQuestionDialog.show(getFragmentManager(), "Type of question");
                    typeOfQuestionDialog.setTargetFragment(CreateSurvey.this, 0);
                    Bundle args = new Bundle();
                    args.putInt("number", lvQuestion.getCount());
                    typeOfQuestionDialog.setArguments(args);
                }
                else {
                    Log.e("Create survey", "Spinner position: " + spinner_pos);
                    createQuestionDialog = new CreateQuestionDialog();
                    createQuestionDialog.show(getFragmentManager(), "Type of question");
                    createQuestionDialog.setTargetFragment(CreateSurvey.this, 0);
                    Bundle args = new Bundle();
                    args.putString("type", "MT");
                    args.putInt("number", lvQuestion.getCount());
                    createQuestionDialog.setArguments(args);
                }
            }
        });

        saveSurvey.setOnClickListener(v -> {
            if (nameOfSurvey.getText().toString().isEmpty()) {

                Toast.makeText(getContext(), "Không dược bỏ trống tên khảo sát", Toast.LENGTH_LONG).show();
            }
            else {
                lvQuestion.removeAllViewsInLayout();
                int spinner_pos = typeOfSurvey.getSelectedItemPosition();

                if (spinner_pos == 0) DataManager.Instance().AddNewSurvey("personal_medical", lsQuestion, nameOfSurvey.getText().toString());
                else if (spinner_pos == 1) DataManager.Instance().AddNewSurvey("relatives_medical", lsQuestion, nameOfSurvey.getText().toString());
                else DataManager.Instance().AddNewSurvey("report", lsQuestion, nameOfSurvey.getText().toString());

                nameOfSurvey.setText("");
                Toast.makeText(getContext(), "Tạo khảo sát thành công", Toast.LENGTH_LONG).show();
            }
        });
    }
}
