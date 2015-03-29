package com.fl.dlc.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fl.dlc.R;
import com.fl.dlc.util.DLModel;
import com.fl.dlc.util.DLUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Team2DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Team2DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Team2DetailsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText overs_text;
    private EditText score_text;

    public Team2DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Team2DetailsFragment.
     */

    public static Team2DetailsFragment newInstance() {
        Team2DetailsFragment fragment = new Team2DetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team2_details, container, false);
        overs_text = (EditText) view.findViewById(R.id.team2_overs_text);
        score_text = (EditText) view.findViewById(R.id.team2_final_score_text);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {

        Double overs = DLUtil.getValidOvers(overs_text.getText());
        Integer score = DLUtil.getValidScore(score_text.getText());
        DLModel.setT2StartOvers(overs);
        DLModel.setT2FinalScore(score);

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
