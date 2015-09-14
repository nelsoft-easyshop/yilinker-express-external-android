package com.yilinker.expresspublic.core;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.yilinker.expresspublic.core.requests.EvBookDeliveryReq;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BookDeliveryRequest extends AsyncTask<Void, Void, Void>
{
    private static final Logger logger = Logger.getLogger(BookDeliveryRequest.class.getSimpleName());

    private ProgressDialog progressDialog;

    private Context context;

    private String endpoint;

    private String accessToken;

    private EvBookDeliveryReq evBookDeliveryReq;

    public interface BookingDeliveryListener
    {

    }

    public BookDeliveryRequest(Context context, String endpoint, String accessToken, EvBookDeliveryReq evBookDeliveryReq)
    {
        this.context = context;
        this.endpoint = endpoint;
        this.accessToken = accessToken;
        this.evBookDeliveryReq = evBookDeliveryReq;

        logger.severe("endpoint: " + endpoint);
        logger.severe("accessToken: " + accessToken);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Booking delivery...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        try
        {
            MultipartEntity form = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            // Add image to the form
            List<String> photoFilepathList = evBookDeliveryReq.getImages();
            for (int i = 0; i < photoFilepathList.size(); i++)
            {
                form.addPart("images["+i+"]", new FileBody(new File(photoFilepathList.get(i))));
                logger.severe("add image: " + photoFilepathList.get(i));
            }

            // Sender Details
            form.addPart("sender_consumer_id", new StringBody(Long.toString(evBookDeliveryReq.getSenderConsumerId())));
            form.addPart("sender_address_id", new StringBody(Long.toString(evBookDeliveryReq.getSenderAddressId())));
            form.addPart("sender_consumer_address_id", new StringBody(Long.toString(evBookDeliveryReq.getSenderConsumerAddressId())));
            // Recipient Details
            form.addPart("recipient_consumer_id", new StringBody(Long.toString(evBookDeliveryReq.getRecipientConsumerId())));
            form.addPart("recipient_address_id", new StringBody(Long.toString(evBookDeliveryReq.getRecipientAddressId())));
            form.addPart("recipient_consumer_address_id", new StringBody(Long.toString(evBookDeliveryReq.getRecipientConsumerAddressId())));
            // Package Details
            form.addPart("package_name", new StringBody(evBookDeliveryReq.getPackageName()));
            form.addPart("sku", new StringBody(evBookDeliveryReq.getSku()));
            form.addPart("quantity", new StringBody(Integer.toString(evBookDeliveryReq.getQuantity())));
            form.addPart("fragile", new StringBody(Boolean.toString(evBookDeliveryReq.getFragile())));
            form.addPart("paid_by", new StringBody(evBookDeliveryReq.getPaidBy()));
            // Dimension and Weight
            form.addPart("length", new StringBody(evBookDeliveryReq.getLength()));
            form.addPart("height", new StringBody(evBookDeliveryReq.getHeight()));
            form.addPart("width", new StringBody(evBookDeliveryReq.getWidth()));
            form.addPart("weight", new StringBody(evBookDeliveryReq.getWeight()));
//            form.addPart("branch_id", new StringBody(Long.toString(evBookDeliveryReq.getbra)));
            // Pickup Schedule
            form.addPart("pickup_date", new StringBody(evBookDeliveryReq.getPickUpDate().toString()));
            form.addPart("package_pickup_schedule_id", new StringBody(Long.toString(evBookDeliveryReq.getPackagePickupScheduleId())));


            HttpPost httpPost = new HttpPost(endpoint);
//            httpPost.addHeader("Content-Type", "multipart/form-data");
            httpPost.addHeader("Authorization", "Bearer " + accessToken);
            httpPost.setEntity(form);

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            String response = EntityUtils.toString(httpResponse.getEntity());
            logger.severe("Response: " + response);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }
}
