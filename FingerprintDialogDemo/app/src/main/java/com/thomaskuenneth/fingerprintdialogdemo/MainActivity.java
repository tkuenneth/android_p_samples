package com.thomaskuenneth.fingerprintdialogdemo;

import android.app.Activity;
import android.hardware.fingerprint.FingerprintDialog;
import android.hardware.fingerprint.FingerprintDialog.AuthenticationResult;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FingerprintDialog.Builder b = new FingerprintDialog.Builder();
        b.setDescription(getString(R.string.descr));
        b.setTitle(getString(R.string.title));
        b.setSubtitle(getString(R.string.subtitle));
        b.setNegativeButton(getString(R.string.button),
                getMainExecutor(), (dialogInterface, i) ->
                        Log.d(TAG, "button clicked")
        );
        FingerprintDialog d = b.build(this);
        CancellationSignal cs = new CancellationSignal();
        d.authenticate(cs, getMainExecutor(),
                new FingerprintDialog.AuthenticationCallback() {

                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        toast(R.string.error);
                    }

                    @Override
                    public void onAuthenticationSucceeded(AuthenticationResult result) {
                        toast(R.string.ok);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        toast(R.string.failed);
                    }
                });
    }

    private void toast(int resid) {
        Toast.makeText(this, resid, Toast.LENGTH_LONG).show();
    }
}
