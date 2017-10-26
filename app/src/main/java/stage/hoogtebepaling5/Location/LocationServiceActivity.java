package stage.hoogtebepaling5.Location;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.ProcessType;
import stage.hoogtebepaling5.R;

public class LocationServiceActivity extends AppCompatActivity implements LocationPresenter.ILocationView {

    private IntentFilter intentFilter;
    private LocationPresenter locationPresenter;
    private ProgressDialog progressDialog;
    private TextView locationText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_display_layout);

        locationText = (TextView) findViewById(R.id.locationText);
        locationPresenter = new LocationPresenter(this);

        displayProgress();
        startService(new Intent(this, LocationService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, getIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public String getText() {
        return locationText.getText().toString();
    }

    @Override
    public void setText(String text) {
        locationText.setText(text);
    }

    @Override
    public void updateProgress(String text) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(text);
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private IntentFilter getIntentFilter() {
        if (intentFilter == null) {
            intentFilter = new IntentFilter();
            intentFilter.addAction(LocationService.ACTION_LOCATION_CHANGED);
            intentFilter.addAction(LocationService.ACTION_LOCATION_FAILED);
            intentFilter.addAction(LocationService.ACTION_PROCESS_CHANGED);
        }
        return intentFilter;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LocationService.ACTION_LOCATION_CHANGED)) {
                locationPresenter.onLocationChanged((Location) intent.getParcelableExtra(LocationService.EXTRA_LOCATION));
            } else if (action.equals(LocationService.ACTION_LOCATION_FAILED)) {
                //noinspection WrongConstant
                locationPresenter.onLocationFailed(intent.getIntExtra(LocationService.EXTRA_FAIL_TYPE, FailType.UNKNOWN));
            } else if (action.equals(LocationService.ACTION_PROCESS_CHANGED)) {
                //noinspection WrongConstant
                locationPresenter.onProcessTypeChanged(intent.getIntExtra(LocationService.EXTRA_PROCESS_TYPE,
                      ProcessType.GETTING_LOCATION_FROM_CUSTOM_PROVIDER));
            }
        }
    };
}
