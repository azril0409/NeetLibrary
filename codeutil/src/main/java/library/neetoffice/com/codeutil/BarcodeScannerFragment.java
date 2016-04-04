package library.neetoffice.com.codeutil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

/**
 * Created by Deo on 2016/3/23.
 */
public class BarcodeScannerFragment extends Fragment {
    ResultCallback resultCallback;
    BarcodeScannerView barcodeScannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        barcodeScannerView = new BarcodeScannerView(getContext());
        barcodeScannerView.setResultCallback(resultCallback);
        return barcodeScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeScannerView.openCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeScannerView.stopCamera();
    }

    public void setResultCallback(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
        if (barcodeScannerView != null) {
            barcodeScannerView.setResultCallback(resultCallback);
        }
    }
}
