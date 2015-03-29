package com.fl.dlc.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.fl.dlc.R;
import com.fl.dlc.fragment.FinalResultFragment;
import com.fl.dlc.fragment.Team1DetailsFragment;
import com.fl.dlc.fragment.Team2DetailsFragment;
import com.fl.dlc.fragment.TypeAndFormatFragment;
import com.fl.dlc.util.DLConstants;
import com.fl.dlc.util.DLModel;
import com.fl.dlc.util.DLPagerAdapter;
import com.fl.dlc.util.DLUtil;


public class DLMainActivity extends ActionBarActivity
        implements TypeAndFormatFragment.OnFragmentInteractionListener,
        FinalResultFragment.OnFragmentInteractionListener,
        Team1DetailsFragment.OnFragmentInteractionListener,
        Team2DetailsFragment.OnFragmentInteractionListener {

    ViewPager viewPager;
    DLPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlmain);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new DLPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_dlmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri) {
        Toast toast = Toast.makeText(this, "Wheeee!", Toast.LENGTH_SHORT);
        //toast.show();
    }

    public void moveToTeam1Details(View view) {

        viewPager.setCurrentItem(DLConstants.TEAM1_DETAILS_FRAGMENT, true);
    }

    public void moveToTeam2Details(View view) {

        viewPager.setCurrentItem(DLConstants.TEAM2_DETAILS_FRAGMENT, true);
    }

    public void moveToFinalResult(View view) {

        viewPager.setCurrentItem(DLConstants.FINAL_RESULT_FRAGMENT, true);
    }

    public void calculateFinalResult(View view) {
        View w = (View) view.getParent();
        EditText team1_overs = (EditText) w.findViewById(R.id.team1_overs_text);
        Double t1_overs = DLUtil.getValidOvers(team1_overs.getText());

        if (t1_overs == null) {
            //alert enter overs properly
            return;
        }

        DLModel.setT1StartOvers(t1_overs);

        EditText team1_score = (EditText) findViewById(R.id.team1_final_score_text);
        Integer t1_score = DLUtil.getValidScore(team1_score.getText());

        if (t1_score == null) {
            //alert enter score properly
            return;
        }

        DLModel.setT1FinalScore(t1_score);

        EditText team2_overs = (EditText) findViewById(R.id.team2_overs_text);
        Double t2_overs = DLUtil.getValidOvers(team2_overs.getText());

        if (t2_overs == null) {
            //alert enter overs properly
            return;
        }

        DLModel.setT2StartOvers(t2_overs);

        EditText team2_score = (EditText) findViewById(R.id.team2_final_score_text);
        Integer t2_score = DLUtil.getValidScore(team2_score.getText());

        if (t2_score == null) {
            //alert enter score properly
            return;
        }

        DLModel.setT2FinalScore(t2_score);

        Spinner format_spinner = (Spinner) findViewById(R.id.format_spinner);
        int format = format_spinner.getSelectedItemPosition();

        Spinner type_spinner = (Spinner) findViewById(R.id.type_spinner);
        int type = type_spinner.getSelectedItemPosition();

        int g = DLUtil.getG(format, type);
        DLModel.setG(g);

        String result = DLUtil.calculateResult();

        TextView result_status = (TextView) findViewById(R.id.final_result_status);
        result_status.setText(result);
    }
}
