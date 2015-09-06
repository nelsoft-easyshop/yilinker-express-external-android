package com.yilinker.expresspublic.modules.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.helpers.UserPrefHelper;
import com.yilinker.expresspublic.core.models.User;
import com.yilinker.expresspublic.modules.BaseActivity;

import org.w3c.dom.Text;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class MyAccountActivity extends BaseActivity implements View.OnClickListener {
    private static final Logger logger = Logger.getLogger(MyAccountActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = UserPrefHelper.getUser(this);

        ((TextView) findViewById(R.id.tv_contactNumber)).setText(user.getContactNumber());
        ((TextView) findViewById(R.id.tv_fullname)).setText(user.getFullname());
        ((TextView) findViewById(R.id.tv_password)).setText("Password");
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_my_account;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_account;
    }

    @Override
    protected void initListeners() {
        findViewById(R.id.ll_updateContactNumber).setOnClickListener(this);
        findViewById(R.id.ll_updateProfile).setOnClickListener(this);
        findViewById(R.id.ll_updatePassword).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_updateContactNumber:
                startUpdateContactNumberActivity();
                break;

            case R.id.ll_updateProfile:
                startUpdateProfileActivity();
                break;

            case R.id.ll_updatePassword:
                startUpdatePasswordActivity();
                break;

            default:
                break;
        }
    }

    private void startUpdatePasswordActivity() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    private void startUpdateProfileActivity() {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        startActivity(intent);
    }

    private void startUpdateContactNumberActivity() {

    }
}
