package com.example.myapplication.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.GregorianCalendar;

/**
 * Created by DoofUndAlt on 21.02.14.
 */
public class StundenplanFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static StundenplanFragment newInstance(int sectionNumber) {
        StundenplanFragment fragment = new StundenplanFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stundenplan, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GregorianCalendar nextDayWithLesson = StundenplannDatabaseHandler.getNextDayWithLesson(getActivity());
        System.out.println("next day with lesson" + nextDayWithLesson);
        DayDrawer.DrawDay(nextDayWithLesson, this.getView(), this.getActivity());
    }
}
