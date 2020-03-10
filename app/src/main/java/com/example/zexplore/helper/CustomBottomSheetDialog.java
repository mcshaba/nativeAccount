package com.example.zexplore.helper;

import android.app.Dialog;
import android.os.Bundle;

import com.example.zexplore.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;
/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */

public class CustomBottomSheetDialog extends BottomSheetDialogFragment {

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(Objects.requireNonNull(getContext()),R.style.BottomSheetDialogTheme);

    }
}
