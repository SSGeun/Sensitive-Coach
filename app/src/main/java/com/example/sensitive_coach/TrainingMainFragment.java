package com.example.sensitive_coach;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TrainingMainFragment extends Fragment {

    private TextView count_t;

    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private Handler mBluetoothHandler;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private InputStream IS;
    private ConnectedBluetoothThread mThreadConnectedBluetooth;
    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");    // 시리얼 통신용

    private int wholeSet;
    private int nowSet;
    private int wholeTimes; // 한 세트에 실행할 갯 수
    private int wholeStep; // 운동의 전체 단계 수
    private int nowTimes; // 현재 진행한 갯 수
    private int nowStep; // 현재 단계

    private String tmp;


    ViewGroup viewGroup;

    private int count = 0; // 서버에서 값 수신되면 ++
    private String warning1 = "허 리 를  곧 게  펴 주 세 요"; // 랜덤하게 출력할 안내문
    private String warning2 = "허 벅 지 의  자 세 를  수 평 으 로  유 지 해 주 세 요";
    private String warning3 = "팔 을 더  들 어 주 세 요";

    private ImageView posture1;
    private ImageView _posture1;
    private ImageView posture2;
    private ImageView _posture2;
    private TextView guide;
    private TextView initialCount;

    private int initial;

    public static TrainingMainFragment newInstance() {
        return new TrainingMainFragment();
    }

    @Nullable
    @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.training_main,container,false);

        posture1 = (ImageView) viewGroup.findViewById(R.id.posture1);
        _posture1 = (ImageView) viewGroup.findViewById(R.id._posture1);
        posture2 = (ImageView) viewGroup.findViewById(R.id.posture2);
        _posture2 = (ImageView) viewGroup.findViewById(R.id._posture2);
        guide = (TextView) viewGroup.findViewById(R.id.guide);
        count_t = (TextView) viewGroup.findViewById(R.id.count);
        guide = (TextView) viewGroup.findViewById(R.id.guide);
        initialCount = (TextView) viewGroup.findViewById(R.id.initialCount);

        guide.setText("초기 자세를 잡아주세요.");

        wholeSet = Integer.parseInt(getArguments().getString("sets"));
        wholeTimes = Integer.parseInt(getArguments().getString("times"));

        initial = 3;

        nowSet = wholeSet;
        nowTimes = wholeTimes;

        wholeStep = 2;
        nowStep = 1;

        tmp = nowTimes + " / " + nowSet;
        count_t.setText(tmp);

//        try {
//            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 연결된 BlueTooth 장치의 Socket / InputStream 연결
        //mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
        //mThreadConnectedBluetooth.start();


        // 블루투스 연결 후 수신된 데이터를 TextView에 표시
        mBluetoothHandler = new Handler() {

            public void handleMessage(android.os.Message msg) {

                if(msg.what == 1) {

                    if (initial > 0) {

                        initial -= 1;
                        initialCount.setText(String.valueOf(initial));
                    }
                    else if (initial == 0) {

                        initialCount.setVisibility(View.GONE);
                        guide.setVisibility(View.GONE);
                        count_t.setVisibility(View.VISIBLE);

                        nowStep = nowStep + 1;
                        _posture1.setVisibility(View.VISIBLE);
                        posture1.setVisibility(View.GONE);
                        posture2.setVisibility(View.VISIBLE);
                        _posture2.setVisibility(View.GONE);
                        //Log.e("123",Integer.toString(nowStep));
                        if (nowStep > wholeStep) { // 모든 단계 완료시 세트 증가
                            nowStep = 1;
                            nowTimes -= 1;

                            posture1.setVisibility(View.VISIBLE);
                            _posture1.setVisibility(View.GONE);
                            _posture2.setVisibility(View.VISIBLE);
                            posture2.setVisibility(View.GONE);
                        }
                        if (nowTimes <= 0) {
                            nowSet--;
                            nowTimes = wholeTimes;
                        }

                        tmp = nowTimes + " / " + nowSet;
                        count_t.setText(tmp);
                    }
                }
            }
        };
        connectSelectedDevice("SensitiveCoach");
        //mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();

        return viewGroup;
    }

    // 페어링 장치 목록에서 선택된 장치 연결해주는 메소드
    void connectSelectedDevice(String selectedDeviceName) {

        mPairedDevices = mBluetoothAdapter.getBondedDevices();

        for(BluetoothDevice tempDevice : mPairedDevices) {
            Log.e("123123", tempDevice.getName());
            if (selectedDeviceName.equals(tempDevice.getName())) {

                Log.e("123123", tempDevice.getName());
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

            Toast.makeText(getActivity().getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }



    // 데이터는 언제 수신 받을지 모르므로 데이터 수신을 위한 Thread를 따로 생성하여 처리해야 함
    private class ConnectedBluetoothThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        // Thread 초기화 과정
        public ConnectedBluetoothThread(BluetoothSocket socket) {// 생성자 수정해서 wholeSet, wholeStep 값 초기화 시켜줘야함

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {

                // getInputStream(), getOutputStream(): 소켓을 통한 전송을 처리하는 InputStream, OutputStream을 가져옴 (데이터 송수신의 길을 만들어주는 작업)
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {

                Toast.makeText(getActivity().getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
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



                        //((MainActivity)getActivity()).refresh_trainingMainFragment();


                        mBluetoothHandler.obtainMessage(1, nowTimes, -1, buffer).sendToTarget();

                        /*
                            운동 동작에 대한 피드백 메세지 변경
                            Stemp에 따른 이미지 갱신
                            Set에 따른 텍스트 갱신
                        */
                    }
                } catch (IOException e) {

                    break;
                }
            }
        }

        // 블루투스 소켓을 닫는 메소드 (애플리케이션을 닫으면 자동 종료)
        public void cancel() {

            try {

                mmSocket.close();
            } catch (IOException e) {

                Toast.makeText(getActivity().getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
