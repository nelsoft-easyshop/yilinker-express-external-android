package com.yilinker.expresspublic.modules.bookDelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilinker.expresspublic.BaseApplication;
import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.deserializer.BookingDateDeserializer;
import com.yilinker.expresspublic.core.helpers.QRCodeHelper;
import com.yilinker.expresspublic.core.responses.EvBookDeliveryResponse;
import com.yilinker.expresspublic.core.utilities.DateUtils;
import com.yilinker.expresspublic.modules.BaseActivity;
import com.yilinker.expresspublic.modules.myShipment.MyShipmentActivity;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BookingSuccessFulActivity extends BaseActivity implements View.OnClickListener {
    private static final Logger logger = Logger.getLogger(BookingSuccessFulActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String bookingNumber = bundle.getString(BundleKey.WAYBILL_NUMBER);
//        String response = bundle.getString(BundleKey.BOOK_DELIVERY_RESPONSE);

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Date.class, new BookingDateDeserializer())
//                .create();
//        EvBookDeliveryResponse evBookDeliveryResponse = gson.fromJson(response, EvBookDeliveryResponse.class);

        // Setup UI
//        ((TextView) findViewById(R.id.tv_message)).setText(evBookDeliveryResponse.message);
//        ((TextView) findViewById(R.id.tv_bookingNumber)).setText(evBookDeliveryResponse.data.bookingNumber);
//        ((TextView) findViewById(R.id.tv_pickUpSchedule)).setText(DateUtils.displayDateAsReadable(evBookDeliveryResponse.data.schedule));
        ((TextView) findViewById(R.id.tv_message)).setText(R.string.booking_successful);
        ((TextView) findViewById(R.id.tv_bookingNumber)).setText(bookingNumber);

//        String qrImageUrl = "http://express.dautour.com/" + evBookDeliveryResponse.data.qrImage;

        // Load Image
        ImageView iv_qrImage = (ImageView) findViewById(R.id.iv_qrImage);
        iv_qrImage.setImageBitmap(QRCodeHelper.generateQRCode(this, bookingNumber, R.dimen.qr_code_print));
//        ImageLoader imageLoader = BaseApplication.getInstance().getImageLoader();
//        imageLoader.get(qrImageUrl, ImageLoader.getImageListener(iv_qrImage, -1, -1));
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base_ll;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.title_booking_successful;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_booking_successful;
    }

    @Override
    protected void initListeners() {
        // Set onclick listener for view outgoing list
        findViewById(R.id.btn_viewOngoingList).setOnClickListener(this);
    }

    @Override
    protected Intent resultIntent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_viewOngoingList:
                handleViewOngoingList();
                break;

            default:
                break;
        }
    }

    private void handleViewOngoingList() {
        Intent intent = new Intent(this, MyShipmentActivity.class);
        startActivity(intent);

        finish();
    }
}
