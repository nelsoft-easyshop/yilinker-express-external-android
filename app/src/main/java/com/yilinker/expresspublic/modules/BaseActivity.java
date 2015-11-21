package com.yilinker.expresspublic.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.services.gcm.RegistrationIntentService;
import com.yilinker.expresspublic.core.helpers.CommonPrefHelper;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.helpers.UserPrefHelper;
import com.yilinker.expresspublic.modules.profile.MyProfileActivity;
import com.yilinker.expresspublic.modules.home.HomeActivity;
import com.yilinker.expresspublic.modules.login.LoginActivity;

import java.util.logging.Logger;

/**
 * Created by Jeico on 8/8/2015.
 */
public abstract class BaseActivity extends AppCompatActivity
{
    private static final Logger logger = Logger.getLogger(BaseActivity.class.getSimpleName());

    /**
     * Result code
     */
    protected int resultCode = RESULT_CANCELED;

    /**
     * Reference of Toolbar
     */
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getBaseLayout());

        /**
         * Inflate the activity's content layout
         */
        ViewStub vs_container = (ViewStub) findViewById(R.id.vs_container);
        vs_container.setLayoutResource(getLayoutResource());
        vs_container.inflate();

        /**
         * Get reference of Toolbar and set this Toolbar to act as action bar
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /**
         * Toolbar back button
         */
        ImageView toolbar_back = (ImageView) toolbar.findViewById(R.id.toolbar_back);
        toolbar_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        /**
         * Set the title of Toolbar
         */
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getToolbarTitle());

        /**
         * Get reference to drawer layout
         */
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer);

        /**
         * Toolbar avatar
         */
        ImageView toolbar_avatar = (ImageView) toolbar.findViewById(R.id.toolbar_avatar);
        toolbar_avatar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(CommonPrefHelper.isUserLoggedIn(BaseActivity.this))
                {
                    // Open drawer when user is logged in
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                else
                {
                    // Redirect to login page if user is not logged in
                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                    startActivityForResult(intent, RequestCode.RCA_LOGIN);
                }
            }
        });

        if(CommonPrefHelper.isUserLoggedIn(this))
        {
            toolbar_avatar.setImageResource(R.drawable.ic_default_avatar_online);
        }
        else
        {
            toolbar_avatar.setImageResource(R.drawable.ic_default_avatar_offline);
        }

        /**
         * Get reference to Navigation View
         */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        Intent homeIntent = new Intent(BaseActivity.this, HomeActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                        return true;

                    case R.id.profile:
                        Intent intent = new Intent(BaseActivity.this, MyProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        return true;

                    default:
                        return true;
                }
            }
        });

        /**
         * Logout
         */
        TextView tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonPrefHelper.clearCommon(BaseActivity.this);
                OAuthPrefHelper.clearOAuth(BaseActivity.this);
                UserPrefHelper.clearUser(BaseActivity.this);

                Intent intent = new Intent(BaseActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        /**
         * Set drawer lock mode depending if user is logged in or not
         */
        setDrawerLockMode();

        /**
         * Initialise listeners
         */
        initListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == RequestCode.RCA_LOGIN)
            {
                // Successful login
                setDrawerLockMode();
                // Get Registration ID for GCM
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    @Override
    public void finish()
    {
        Intent intent = resultIntent();

        if(intent == null)
        {
            intent = new Intent();
        }

        setResult(resultCode, intent);

        super.finish();
    }

    /**
     * This method set the lock mode of layout depending if user is logged in or not
     */
    public void setDrawerLockMode()
    {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer);

        if(CommonPrefHelper.isUserLoggedIn(this))
        {
            // Enable gesture of drawer if user is logged in
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        else
        {
            // Disable gesture of drawer if user is not logged in
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    /**
     * This method gets the base resource ID of layout
     * @return base layout resource ID
     */
    protected abstract int getBaseLayout();

    /**
     * This method gets the resource id of title that will be used for the activity
     * @return title's resource id
     */
    protected abstract int getToolbarTitle();

    /**
     * This method gets the layout resource of the activity
     * @return layout resouce Id
     */
    protected abstract int getLayoutResource();

    /**
     * This method initialise the listeners of this activity
     */
    protected abstract void initListeners();

    /**
     * Returns data to calling intent
     * @return
     */
    protected abstract Intent resultIntent();
}
