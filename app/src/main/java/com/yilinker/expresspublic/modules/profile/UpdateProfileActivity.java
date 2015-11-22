package com.yilinker.expresspublic.modules.profile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.UserApi;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.OAuthPrefHelper;
import com.yilinker.expresspublic.core.helpers.UserPrefHelper;
import com.yilinker.expresspublic.core.models.User;
import com.yilinker.expresspublic.core.responses.EvMeResp;
import com.yilinker.expresspublic.core.utilities.DateUtils;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class UpdateProfileActivity
        extends BaseActivity
        implements View.OnClickListener, Observer, ResponseHandler, View.OnFocusChangeListener
{
    private static final Logger logger = Logger.getLogger(UpdateProfileActivity.class.getSimpleName());

    private CharSequence[] genderList = {"Male", "Female"};

    private ProgressDialog progressDialog;

    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        User user = UserPrefHelper.getUser(this);

        userModel = new UserModel(
                user.getFirstname(),
                user.getLastname(),
                user.getBirthdate(),
                user.getGender(),
                user.getEmail()
        );
        userModel.addObserver(this);
        userModel.triggerObservers();
    }

    @Override
    protected int getBaseLayout()
    {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle()
    {
        return R.string.title_update_profile;
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_update_profile;
    }

    @Override
    protected void initListeners()
    {
        // Set onclick listener for submit and update
        findViewById(R.id.btn_submitAndUpdate).setOnClickListener(this);
        // Set focus change listener for bithdate
        findViewById(R.id.et_birthdate).setOnFocusChangeListener(this);
        // set focus change listener for gender
        findViewById(R.id.et_gender).setOnFocusChangeListener(this);
    }

    @Override
    protected Intent resultIntent()
    {
        return null;
    }

    @Override
    public void update(Observable observable, Object data)
    {
        if (data instanceof UserModel)
        {
            updateUi((UserModel) data);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_submitAndUpdate:
                volleyUpdateProfile();
                break;

            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        switch (v.getId())
        {
            case R.id.et_birthdate:
                if(hasFocus)
                {
                    displayBirthdayDialog(R.id.et_birthdate, userModel.getBirthdate());
                }
                break;

            case R.id.et_gender:
                if(hasFocus)
                {
//                    displayGenderDialog(R.id.et_gender, userModel.getGender().equals("1") ||
//                            userModel.getGender().equals(genderList[0]) ?
//                            genderList[0].toString() : genderList[1].toString());
                    displayGenderDialog(R.id.et_gender, userModel.getGender().equals("2") ||
                            userModel.getGender().equals(genderList[0]) ?
                            genderList[0].toString() : genderList[1].toString());
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onResponse(int requestCode, Object object)
    {
        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_UPDATE_PROFILE:
                EvMeResp evMeResp = (EvMeResp) object;
//                processUpdateProfileResp(evMeResp);

                if (evMeResp.isSuccessful) {

                    processUpdateProfileResp(evMeResp);
                    Toast.makeText(this, getString(R.string.profile_message_user_details_successful), Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(this, evMeResp.message, Toast.LENGTH_LONG).show();

                finish();
                break;

            default:
                break;
        }
    }

    private void processUpdateProfileResp(EvMeResp evMeResp)
    {
        User user = evMeResp.data;
        UserPrefHelper.setUser(this, user);
    }

    @Override
    public void onErrorResponse(int requestCode, String message)
    {
        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        switch (requestCode)
        {
            case RequestCode.RCR_UPDATE_PROFILE:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    /**
     * This method displays date dialog for birthday
     */
    private void displayBirthdayDialog(final int resId, final Date currentBirthdate)
    {
        // Set current birthdate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentBirthdate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth)
                    {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth, 0, 0, 0);

                        setNewUserDetails(c, userModel.getGender(), true);

                        findViewById(resId).clearFocus();
                    }
                }, year, month, day);

        // Set calendar limit
        Calendar calendarLimit = Calendar.getInstance();
        int yearLimit = calendarLimit.get(Calendar.YEAR);
        int monthLimit = calendarLimit.get(Calendar.MONTH);
        int dayLimit = calendarLimit.get(Calendar.DAY_OF_MONTH);

        calendar.set(yearLimit, monthLimit, dayLimit);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                findViewById(resId).clearFocus();
            }
        });
        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                findViewById(resId).clearFocus();
            }
        });

        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    /**
     * TODO
     * @param resId
     */
    private void displayGenderDialog(final int resId, final String currentGender)
    {
        final EditText editText = (EditText) findViewById(resId);

        int currentPosition = -1;

        for (int i = 0; i < genderList.length; i++)
        {
            if (currentGender.equalsIgnoreCase(genderList[i].toString()))
            {
                currentPosition = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_gender));
        builder.setSingleChoiceItems(genderList, currentPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedGender = genderList[which].toString();

                if (selectedGender.equalsIgnoreCase(currentGender)) {
                    // Do nothing
                } else {
                    setNewUserDetails(Calendar.getInstance(), selectedGender, false);
                    editText.clearFocus();
                }

                dialog.dismiss();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                editText.clearFocus();
            }
        });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                editText.clearFocus();
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateUi(UserModel userModel)
    {
        String firstname = userModel.getFirstname();
        String lastname = userModel.getLastname();
        String birthdate = DateUtils.displayDateAsReadable(userModel.getBirthdate());
        String gender = userModel.getGender();
        String email = userModel.getEmail();

        ((EditText) findViewById(R.id.et_firstname)).setText(firstname);
        ((EditText) findViewById(R.id.et_lastname)).setText(lastname);
        ((EditText) findViewById(R.id.et_birthdate)).setText(birthdate);
//        ((EditText) findViewById(R.id.et_gender)).setText(gender.equals("2") ||
//                gender.equals(genderList[1]) ?
//                genderList[1] : genderList[0]);
        ((EditText) findViewById(R.id.et_gender)).setText(gender.equals("2") ||
                gender.equals(genderList[0]) ?
                genderList[0] : genderList[1]);
        ((EditText) findViewById(R.id.et_email)).setText(email);
    }

    private void volleyUpdateProfile()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_changing_user_details));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String firstname = ((EditText) findViewById(R.id.et_firstname)).getText().toString().trim();
        String lastname = ((EditText) findViewById(R.id.et_lastname)).getText().toString().trim();
        String birthdate = ((EditText) findViewById(R.id.et_birthdate)).getText().toString().trim();
        String gender = ((EditText) findViewById(R.id.et_gender)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString().trim();

        Request request = UserApi.updateProfile(
                OAuthPrefHelper.getAccessToken(this),
                firstname,
                lastname,
                birthdate,
                gender,
                email,
                RequestCode.RCR_UPDATE_PROFILE,
                this);

        BaseApplication.getInstance().getRequestQueue().add(request);
    }

    private void setNewUserDetails(Calendar c, String selectedGender, boolean isCalendar) {
        String firstname = ((EditText) findViewById(R.id.et_firstname)).getText().toString().trim();
        String lastname = ((EditText) findViewById(R.id.et_lastname)).getText().toString().trim();
        String gender = ((EditText) findViewById(R.id.et_gender)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString().trim();

        userModel.setFirstname(firstname);
        userModel.setLastname(lastname);
        if (isCalendar)
            userModel.setBirthdate(c.getTime());
        else
            gender = selectedGender;
//        userModel.setGender(gender.equals("2") ||
//                gender.equals(genderList[1]) ?
//                genderList[1].toString() : genderList[0].toString());
        userModel.setGender(gender.equals("2") ||
                gender.equals(genderList[0]) ?
                genderList[0].toString() : genderList[1].toString());
        userModel.setEmail(email);
    }

    /**
     * Model
     */
    class UserModel
            extends Observable
    {
        private String firstname;
        private String lastname;
        private Date birthdate;
        private String gender;
        private String email;

        public UserModel(String firstname, String lastname, Date birthdate, String gender, String email)
        {
            this.firstname = firstname;
            this.lastname = lastname;
            this.birthdate = birthdate;
            this.gender = gender;
            this.email = email;
        }

        public String getFirstname()
        {
            return firstname;
        }

        public void setFirstname(String firstname)
        {
            this.firstname = firstname;
            triggerObservers();
        }

        public String getLastname()
        {
            return lastname;
        }

        public void setLastname(String lastname)
        {
            this.lastname = lastname;
            triggerObservers();
        }

        public Date getBirthdate()
        {
            return birthdate;
        }

        public void setBirthdate(Date birthdate)
        {
            this.birthdate = birthdate;
            triggerObservers();
        }

        public String getGender()
        {
            return gender;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
            triggerObservers();
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
            triggerObservers();
        }

        public void triggerObservers()
        {
            setChanged();
            notifyObservers(this);
        }
    }
}
