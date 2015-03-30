package com.vxpai.interfaces;

import com.vxpai.entity.ShitListItem;

/**
 * Created by 俊成 on 2015/3/28.
 */
public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onSendShits();

    public void onGoBackToMain();

    public void onLogin(String email,String password);

    public void onShowDetailShits(ShitListItem shit);

    public void onNewUser();

    public void onGoBackToLogin();

    public void onEditPersonalData();
}
