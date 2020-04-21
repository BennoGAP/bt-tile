package org.groebl.bluetoothsetting;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    public static final Method setAdapterScanMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    static {
        Method btScan = null;
        try {
            btScan = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
            //m.setAccessible(true);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        setAdapterScanMode = btScan;
    }
}
