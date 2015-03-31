package com.vxpai.interfaces;

import com.vxpai.entity.ShitListItem;
import com.vxpai.entity.UserData;

/**
 * Created by 俊成 on 2015/3/28.
 */
public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onSendShits(boolean isHomePage);

    public void onGoBackToMain();

    public void onLogin(String email,String password);
    public void onRegister(String email,String username,String pwd);

    public void onShowDetailShits(ShitListItem shit);

    public void onShowDetailUser(UserData user);

    public void onNewUser();

    public void onGoBackToLogin(boolean isUserDataWrong);

    public void onEditPersonalData();

    public void onSendVent(String vent, boolean isAnnoy);
}
