package com.fl.dlc.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fl.dlc.R;
import com.fl.dlc.util.DLConstants;
import com.fl.dlc.util.DLModel;
import com.fl.dlc.util.Suspension;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListSuspensionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListSuspensionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListSuspensionsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int team;
    private List<Suspension> suspensions;

    public ListSuspensionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param team Team number.
     * @return A new instance of fragment ListSuspensionsFragment.
     */

    public static ListSuspensionsFragment newInstance(int team) {
        ListSuspensionsFragment fragment = new ListSuspensionsFragment();
        Bundle args = new Bundle();
        args.putInt(DLConstants.TEAM_ID, team);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            team = getArguments().getInt(DLConstants.TEAM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_suspensions, container, false);

        if (team == DLConstants.TEAM_1) {
            suspensions = DLModel.getT1Suspensions();
        } else {
            suspensions = DLModel.getT2Suspensions();
        }

        TextView textView = (TextView) view.findViewById(R.id.suspensions_list_empty_text);
        ListView listView = (ListView) view.findViewById(R.id.suspensions_list);

        if (suspensions != null) {
            textView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            ArrayAdapter<Suspension> adapter = new ArrayAdapter<Suspension>(getActivity(), android.R.layout.simple_list_item_1, suspensions);
            listView.setAdapter(adapter);
        } else {
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
