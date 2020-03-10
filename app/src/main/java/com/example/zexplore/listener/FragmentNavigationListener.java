package com.example.zexplore.listener;


import com.example.zexplore.model.Bvn;
import com.example.zexplore.model.Form;

/**
 * Created by mishael.harry on 3/24/2018.
 */

public interface FragmentNavigationListener {

    void onSubmitFormClicked(Form form);

    void onSaveFormClicked(Form form);

    void onDeleteFormClicked(Form form);

    void onCloseFormClicked();

//    void verifyBvn(Bvn bvn);
}
