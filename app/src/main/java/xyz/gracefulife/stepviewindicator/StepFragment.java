package xyz.gracefulife.stepviewindicator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class StepFragment extends Fragment {

  public StepFragment() {
    // Required empty public constructor
  }

  public static StepFragment newInstance() {
    return new StepFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_step, container, false);
  }

}
