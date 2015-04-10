package com.fl.dlc.activity;

import android.content.Intent;
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
import com.fl.dlc.util.DLDBConstants;
import com.fl.dlc.util.DLDBHelper;
import com.fl.dlc.util.DLModel;
import com.fl.dlc.util.DLPagerAdapter;
import com.fl.dlc.util.DLUtil;
import com.fl.dlc.util.Suspension;

import java.util.ArrayList;
import java.util.List;


public class DLMainActivity extends ActionBarActivity
        implements TypeAndFormatFragment.OnFragmentInteractionListener,
        FinalResultFragment.OnFragmentInteractionListener,
        Team1DetailsFragment.OnFragmentInteractionListener,
        Team2DetailsFragment.OnFragmentInteractionListener {

    ViewPager viewPager;
    DLPagerAdapter adapter;
    DLDBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlmain);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new DLPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        dbhelper = new DLDBHelper(this);
        DLDBConstants.dbhelper = dbhelper;
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
        toast.show();
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

        Spinner format_spinner = (Spinner) findViewById(R.id.format_spinner);
        int format = format_spinner.getSelectedItemPosition();
        DLModel.setFormat(format);

        Spinner type_spinner = (Spinner) findViewById(R.id.type_spinner);
        int type = type_spinner.getSelectedItemPosition();

        int g = DLUtil.getG(format, type);
        DLModel.setG(g);

        EditText team1_overs = (EditText) findViewById(R.id.team1_overs_text);
        Double t1overs = DLUtil.getValidOvers(team1_overs.getText().toString(), DLConstants.TEAM_1);

        if (t1overs == null) {
            DLUtil.showAlertDialog(this, "Invalid Overs for Team 1", "Please enter a valid amount of overs for Team 1");
            return;
        }

        DLModel.setT1StartOvers(t1overs);

        EditText team1_score = (EditText) findViewById(R.id.team1_final_score_text);
        Integer t1score = DLUtil.getValidScore(team1_score.getText().toString());

        if (t1score == null) {
            DLUtil.showAlertDialog(this, "Invalid Score for Team 1", "Please enter a valid final score for Team 1");
            return;
        }

        DLModel.setT1FinalScore(t1score);

        EditText team2_overs = (EditText) findViewById(R.id.team2_overs_text);
        Double t2overs = DLUtil.getValidOvers(team2_overs.getText().toString(), DLConstants.TEAM_2);

        if (t2overs == null) {
            DLUtil.showAlertDialog(this, "Invalid Overs for Team 2", "Please enter a valid amount of overs for Team 2");
            return;
        }

        if (t2overs > t1overs) {
            DLUtil.showAlertDialog(this, "Invalid Overs for Team 2", "Number of overs at the start for Team 2 cannot be greater than those for Team 1");
            return;
        }

        DLModel.setT2StartOvers(t2overs);

        EditText team2_score = (EditText) findViewById(R.id.team2_final_score_text);
        String t2_score = team2_score.getText().toString().trim();
        Integer t2score = DLUtil.getValidScore(t2_score);

        if (t2score == null && t2_score != null && !t2_score.equals("")) {
            DLUtil.showAlertDialog(this, "Invalid Score for Team 2", "Please enter a valid score for Team 2");
            return;
        }

        DLModel.setT2FinalScore(t2score);

        List<Suspension> t1_suspensions = DLModel.getT1Suspensions();
        System.out.println("t1_suspensions == null ? " + t1_suspensions);
        if (t1_suspensions == null || t1_suspensions.size() == 0) {
            DLModel.setT1Suspensions(new ArrayList<Suspension>());
        } else {
            t1_suspensions = DLUtil.getValidAndNonOverlappingSuspensions(t1_suspensions, DLConstants.TEAM_1);

            if (t1_suspensions == null) {
                DLUtil.showAlertDialog(this, "Invalid Suspensions for Team 1", "Please check your suspension list for Team 1");
                return;
            }
            DLModel.setT1Suspensions(t1_suspensions);
        }

        List<Suspension> t2_suspensions = DLModel.getT2Suspensions();

        if (t2_suspensions == null || t2_suspensions.size() == 0) {
            DLModel.setT2Suspensions(new ArrayList<Suspension>());
        } else {
            t2_suspensions = DLUtil.getValidAndNonOverlappingSuspensions(t2_suspensions, DLConstants.TEAM_2);

            if (t2_suspensions == null) {
                DLUtil.showAlertDialog(this, "Invalid Suspensions for Team 2", "Please check your suspension list for Team 2");
                return;
            }

            DLModel.setT2Suspensions(t2_suspensions);
        }

        String result = DLUtil.calculateResult();
        System.out.println(result);

        if (result != null) {
            TextView result_status = (TextView) findViewById(R.id.final_result_status);
            result_status.setText(result);
        }
    }

    public void addOrEditTeam1Suspensions(View view) {

        addOrEditSuspensions(view, DLConstants.TEAM_1);
    }


    public void addOrEditTeam2Suspensions(View view) {

        addOrEditSuspensions(view, DLConstants.TEAM_2);
    }

    private void addOrEditSuspensions(View view, int team) {

        Intent intent = new Intent(this, SuspensionsActivity.class);
        intent.putExtra(DLConstants.TEAM_ID, team);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbhelper.close();
    }
}
