package test.zwx.com.dialogleaktest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import test.zwx.com.dialogleaktest.databinding.DialogFragmentBinding;

public class MyDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogFragmentBinding binding = DialogFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
