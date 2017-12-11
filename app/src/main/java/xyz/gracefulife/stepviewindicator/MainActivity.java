package xyz.gracefulife.stepviewindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import xyz.gracefulife.stepindicator.StepsView;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();
  ViewPager pagerSteps;
  StepsView indicatorSteps;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    pagerSteps = findViewById(R.id.pager_steps);
    indicatorSteps = findViewById(R.id.indicator_steps);

    StepPagerAdapter stepPagerAdapter = new StepPagerAdapter(getSupportFragmentManager());
    pagerSteps.setAdapter(stepPagerAdapter);

    pagerSteps.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override public void onPageSelected(int position) {
        Log.i(TAG, "onPageSelected: " + position);
        indicatorSteps.setCompletedPosition(position + 1);
        indicatorSteps.drawView();
      }

      @Override public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_SETTLING) {
          indicatorSteps.setCompletedPosition(pagerSteps.getCurrentItem() + 1);
        }
      }
    });

    indicatorSteps.setLabels(new String[]{"test1", "test2", "test3", "test4"})
        .setProgressColorIndicator(ContextCompat.getColor(this, R.color.colorPrimary))
        .setBarColorIndicator(ContextCompat.getColor(this, R.color.colorAccent))
        .setCompletedPosition(1)
        .drawView();
  }


  static class StepPagerAdapter extends FragmentStatePagerAdapter {
    final Fragment[] fragments = {
        StepFragment.newInstance(), StepFragment.newInstance(),
        StepFragment.newInstance(), StepFragment.newInstance()
    };

    StepPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      return fragments[position];
    }

    @Override public int getCount() {
      return fragments.length;
    }
  }
}
