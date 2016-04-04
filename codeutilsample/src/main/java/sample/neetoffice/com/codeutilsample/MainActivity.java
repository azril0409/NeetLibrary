package sample.neetoffice.com.codeutilsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import library.neetoffice.com.codeutil.ResultCallback;
import library.neetoffice.com.codeutil.BarcodeScannerView;

public class MainActivity extends AppCompatActivity implements ResultCallback {
    private BarcodeScannerView cameraPreview;
    private ArrayList<Integer> mSelectedIndices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraPreview = (BarcodeScannerView) findViewById(R.id.cameraPreview);
        cameraPreview.setResultCallback(this);
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if (mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for (int i = 0; i < BarcodeScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }
        for (int index : mSelectedIndices) {
            formats.add(BarcodeScannerView.ALL_FORMATS.get(index));
        }
        if (cameraPreview != null) {
            cameraPreview.setFormats(formats);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraPreview.openCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraPreview.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d(BarcodeScannerView.class.getSimpleName(),"handleResult : "+rawResult.getText());
        Toast.makeText(this,rawResult.getText(),Toast.LENGTH_SHORT).show();
    }
}
