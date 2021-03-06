package com.nguyenhongphuc98.dsaw.ui.surveys;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.adaptor.SurveyResultAdaptor;
import com.nguyenhongphuc98.dsaw.data.DataCenter;
import com.nguyenhongphuc98.dsaw.data.model.AnswerViewModel;

import java.util.ArrayList;
import java.util.List;

public class SurveyResultFragment extends Fragment {

    private SurveyResultViewModel mViewModel;

    private ListView lv;

    private SurveyResultAdaptor adaptor;

    private String surveyID;

    private List<AnswerViewModel> lsAnswers = new ArrayList<>();

    public static SurveyResultFragment newInstance() {
        return new SurveyResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_result, container, false);
        setupView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SurveyResultViewModel.class);

        adaptor = new SurveyResultAdaptor(getContext(),lsAnswers);
        lv.setAdapter(adaptor);

        mViewModel.getlistAnswers().observe(this, new Observer<List<AnswerViewModel>>() {
            @Override
            public void onChanged(List<AnswerViewModel> answerViewModels) {
                lsAnswers.clear();
                for (AnswerViewModel a : answerViewModels) {
                    lsAnswers.add(a);
                }

                if (lsAnswers.size() == 0)
                    lsAnswers.add(new AnswerViewModel("Tạm thời chưa có câu hỏi", new ArrayList<String>()));

                adaptor.notifyDataSetChanged();
            }
        });

        mViewModel.fetchData(DataCenter.surveyID);
    }

    private void setupView(View view) {
        lv = view.findViewById(R.id.survey_result_lv);
    }

    public void setSurveyId(String sid) {
        this.surveyID = sid;
    }
}
