package com.example.sensitive_coach;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnection extends AppCompatActivity {

    TextView mTvBluetoothStatus;
    TextView mTvReceiveData;
    TextView mTvSendData;
    Button mBtnBluetoothOn;
    Button mBtnBluetoothOff;
    Button mBtnConnect;
    Button mBtnSendData;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");    // 시리얼 통신용

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_test);

        mTvBluetoothStatus = (TextView)findViewById(R.id.tvBluetoothStatus);
        mTvReceiveData = (TextView)findViewById(R.id.tvReceiveData);
        mTvSendData =  (EditText) findViewById(R.id.tvSendData);
        mBtnBluetoothOn = (Button)findViewById(R.id.btnBluetoothOn);
        mBtnBluetoothOff = (Button)findViewById(R.id.btnBluetoothOff);
        mBtnConnect = (Button)findViewById(R.id.btnConnect);
        mBtnSendData = (Button)findViewById(R.id.btnSendData);

        // getDefaultAdapter(): 장치가 블루투스 기능을 지원하는지 알아오는 메소드
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Button ClickListener
        mBtnBluetoothOn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothOn();
            }
        });

        mBtnBluetoothOff.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothOff();
            }
        });

        mBtnConnect.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPairedDevices();
            }
        });

        mBtnSendData.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mThreadConnectedBluetooth != null) {
                    mThreadConnectedBluetooth.write(mTvSendData.getText().toString());
                    mTvSendData.setText("");
                }
            }
        });

        // 블루투스 연결 후 수신된 데이터를 TextView에 표시
        mBluetoothHandler = new Handler() {

            public void handleMessage(android.os.Message msg) {

                if(msg.what == BT_MESSAGE_READ) {

                    String readMessage = null;
                    try {

                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }

                    mTvReceiveData.setText(readMessage);
                }
            }
        };
    }

    // 블루투스 활성화 메소드
    void bluetoothOn() {

        if(mBluetoothAdapter == null) {

            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
        }

        else {

            if (mBluetoothAdapter.isEnabled()) {

                Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
                mTvBluetoothStatus.setText("활성화");
            }

            else {

                Toast.makeText(getApplicationContext(), "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
            }
        }
    }

    // 블루투스 비활성화 메소드
    void bluetoothOff() {

        if (mBluetoothAdapter.isEnabled()) {

            // disable(): 블루투스 비활성화
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();
            mTvBluetoothStatus.setText("비활성화");
        }

        else {

            Toast.makeText(getApplicationContext(), "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case BT_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면

                    Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();
                    mTvBluetoothStatus.setText("활성화");
                }

                else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면

                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                    mTvBluetoothStatus.setText("비활성화");
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // 블루투스 연결 메소드
    void listPairedDevices() {

        if (mBluetoothAdapter.isEnabled()) {

            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            // 페어링 된 장치 목록 알림
            if (mPairedDevices.size() > 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();

                for (BluetoothDevice device : mPairedDevices) {

                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }

                // 페어링 된 장치 목록에 ClickListener Event 추가
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        connectSelectedDevice(items[item].toString());
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }

            else {

                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        }

        else {

            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 페어링 장치 목록에서 선택된 장치 연결해주는 메소드
    void connectSelectedDevice(String selectedDeviceName) {

        for(BluetoothDevice tempDevice : mPairedDevices) {

            if (selectedDeviceName.equals(tempDevice.getName())) {

                mBluetoothDevice = tempDevice;
                break;
            }
        }

        try {

            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {

            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }

    // 데이터는 언제 수신 받을지 모르므로 데이터 수신을 위한 Thread를 따로 생성하여 처리해야 함
    private class ConnectedBluetoothThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        // Thread 초기화 과정
        public ConnectedBluetoothThread(BluetoothSocket socket) {

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {

                // getInputStream(), getOutputStream(): 소켓을 통한 전송을 처리하는 InputStream, OutputStream을 가져옴 (데이터 송수신의 길을 만들어주는 작업)
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {

                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        // 수신받을 데이터가 언제 들어올지 모르니 항상 확인 (while문 처리)
        public void run() {

            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {

                try {

                    bytes = mmInStream.available();

                    if (bytes != 0) {

                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {

                    break;
                }
            }
        }

        // 데이터 전송 메소드
        public void write(String str) {

            byte[] bytes = str.getBytes();

            try {

                mmOutStream.write(bytes);
            } catch (IOException e) {

                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }

        // 블루투스 소켓을 닫는 메소드 (애플리케이션을 닫으면 자동 종료)
        public void cancel() {

            try {

                mmSocket.close();
            } catch (IOException e) {

                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
