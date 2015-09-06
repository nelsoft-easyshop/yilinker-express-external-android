package com.yilinker.expresspublic.modules.bookDelivery;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.ResponseHandler;
import com.yilinker.expresspublic.core.api.DeliveryApi;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.interfaces.PickUpScheduleListener;
import com.yilinker.expresspublic.core.models.PickUpSchedule;
import com.yilinker.expresspublic.core.responses.EvPickUpScheduleListResp;
import com.yilinker.expresspublic.core.utilities.DateUtils;
import com.yilinker.expresspublic.modules.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class PickUpScheduleActivity extends BaseActivity implements View.OnClickListener, PickUpScheduleListener, ResponseHandler {
    private static final Logger logger = Logger.getLogger(PickUpScheduleActivity.class.getSimpleName());

    private List<PickUpSchedule> pickUpScheduleList;

    private PickUpScheduleAdapter pickUpScheduleAdapter;

    /**
     * Year checked item
     */
    private int yearCheckedItem;

    /**
     * Month checked item
     */
    private int monthCheckedItem;

    /**
     * Day checked item
     */
    private int dayCheckedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Calendar calendar = Calendar.getInstance();
        yearCheckedItem = calendar.get(Calendar.YEAR);
        monthCheckedItem = calendar.get(Calendar.MONTH);
        dayCheckedItem = calendar.get(Calendar.DAY_OF_MONTH);

        pickUpScheduleList = new ArrayList<>();

        pickUpScheduleAdapter = new PickUpScheduleAdapter(this, pickUpScheduleList, this);

        RecyclerView rv_scheduleList = (RecyclerView) findViewById(R.id.rv_scheduleList);
        rv_scheduleList.setHasFixedSize(true);
        rv_scheduleList.setAdapter(pickUpScheduleAdapter);
        rv_scheduleList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_pickup_schedule;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pickup_schedule;
    }

    @Override
    protected void initListeners() {
        findViewById(R.id.btn_selectDate).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_selectDate:
                showDateDialog();
                break;

            default:
                break;
        }
    }

    private void showDateDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth)
                    {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth, 0, 0, 0);

                        String dateStr = DateUtils.displayDateAsReadable(c.getTime());

                        ((TextView) findViewById(R.id.tv_date)).setText(dateStr);

                        yearCheckedItem = year;
                        monthCheckedItem = monthOfYear;
                        dayCheckedItem = dayOfMonth;

                        volleyGetPickUpScheduleList();
                    }
                }, yearCheckedItem, monthCheckedItem, dayCheckedItem);

        datePickerDialog.show();
    }

    @Override
    public void onPickUpScheduleSelected(PickUpSchedule pickUpSchedule) {
        /**
         * TODO make request
         */
    }

    @Override
    public void onResponse(int requestCode, Object object) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_PICKUP_SCHEDULE_LIST:
                processScheduleListResp((EvPickUpScheduleListResp) object);
                break;

            default:
                break;
        }

        showListHideLoadingBar();
    }

    private void processScheduleListResp(EvPickUpScheduleListResp object) {

        if(object != null)
        {
            pickUpScheduleList.clear();

            for(PickUpSchedule pickUpSchedule : object.data)
            {
                pickUpScheduleList.add(pickUpSchedule);
            }

            pickUpScheduleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorResponse(int requestCode, String message) {
        switch (requestCode)
        {
            case RequestCode.RCR_GET_PICKUP_SCHEDULE_LIST:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }

        showListHideLoadingBar();
    }

    private void volleyGetPickUpScheduleList() {

        Request request = DeliveryApi.getPickUpScheduleList(RequestCode.RCR_GET_PICKUP_SCHEDULE_LIST, this);
        BaseApplication.getInstance().getRequestQueue().add(request);

        showLoadingBarHideList();
    }

    private void showLoadingBarHideList()
    {
        findViewById(R.id.pb_loading).setVisibility(View.VISIBLE);
        findViewById(R.id.rv_scheduleList).setVisibility(View.GONE);
    }

    private void showListHideLoadingBar()
    {
        findViewById(R.id.pb_loading).setVisibility(View.GONE);
        findViewById(R.id.rv_scheduleList).setVisibility(View.VISIBLE);
    }
}
