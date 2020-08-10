package test.zwx.com.dialogleaktest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import test.zwx.com.dialogleaktest.databinding.ListFragmentBinding;

public class ListFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ListFragment";
    private ListFragmentBinding bind;

    private HandlerThread mHandlerThread;
    private MyHandler mMyHandler;
    private Handler mH = new Handler(Looper.getMainLooper());

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandlerThread = new HandlerThread("test");
        mHandlerThread.start();
        mMyHandler = new MyHandler(mHandlerThread.getLooper());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ListFragmentBinding.bind(view);
        bind.bt1.setOnClickListener(this);
        bind.bt2.setOnClickListener(this);
        bind.bt3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1:
                Message msg = Message.obtain();
                msg.what = 1;
                mMyHandler.sendMessage(msg);
                break;
            case R.id.bt_2:
                Message msg2 = Message.obtain();
                msg2.what = 2;
                mMyHandler.sendMessage(msg2);
                break;
            case R.id.bt_3:
                bind.bt3.postDelayed(() -> Log.d(TAG, "DO STH."), 10_000);
                break;
        }
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                // Dialog有一定几率会拿到HandlerThread泄漏的Message对象
                // 只要拿到Dialog就把listener赋给了Message的obj变量，造成Activity内存泄漏
                // 除非HandlerThread的本地变量被赋新值，从而释放对前一个Message的引用
                // 否则Dialog销毁也解决不了这个泄漏问题
                //
                // 所以要么用包装类在Dialog销毁时切断与Message的关联；要么在HandlerThread空闲时发一个空消息
                mH.post(ListFragment.this::showAlertDialog);
            } else if (msg.what == 2) {
                mH.post(ListFragment.this::showDialogFragment);
            }
        }
    }

    private void showDialogFragment() {
        DialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getChildFragmentManager(), "dialog_fragment");
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("test", clickListener)
                .show();
    }

    final DialogInterface.OnClickListener clickListener = (dialog, which) -> {

    };
}
