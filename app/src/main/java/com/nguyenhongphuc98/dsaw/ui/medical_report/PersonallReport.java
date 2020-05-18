package com.nguyenhongphuc98.dsaw.ui.medical_report;

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
import com.nguyenhongphuc98.dsaw.adaptor.PersonalReportAdaptor;

public class PersonallReport extends Fragment {

    private PersonalReportViewModel mViewModel;

    private ListView lvQuestion;

    public static PersonallReport newInstance() {
        return new PersonallReport();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_medical_report_fragment, container, false);
        lvQuestion = view.findViewById(R.id.list_of_question);
        mViewModel = ViewModelProviders.of(this).get(PersonalReportViewModel.class);
        mViewModel.setContext(getContext());
        mViewModel.getAdaptor().observe(this, new Observer<PersonalReportAdaptor>() {
            @Override
            public void onChanged(PersonalReportAdaptor personalReportAdaptor) {
                lvQuestion.setAdapter(personalReportAdaptor);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel

    }
}
