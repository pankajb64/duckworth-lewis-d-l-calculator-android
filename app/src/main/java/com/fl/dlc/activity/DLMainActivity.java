package com.fl.dlc.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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

        LinearLayout layout = (LinearLayout) view.getParent();
        TextView textView = (TextView) layout.findViewById(R.id.final_result_status);
        String text = DLModel.t1StartOvers + " Overs";
        textView.setText(text);

    }
}
