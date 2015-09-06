package com.yilinker.expresspublic.modules.bookDelivery;

import java.util.Observable;

/**
 * Created by Jeico.
 */
public class BookingSyncModel extends Observable
{
    private boolean isSenderReady;
    private boolean isRecipientReady;
    private boolean isPackageDetailsReady;
    private boolean isPackageSizeReady;
    private boolean isPickUpScheduleReady;

    public BookingSyncModel() {
        this.isSenderReady = false;
        this.isRecipientReady = false;
        this.isPackageDetailsReady = false;
        this.isPackageSizeReady = false;
        this.isPickUpScheduleReady = false;
    }

    public boolean isSenderReady() {
        return isSenderReady;
    }

    public void setIsSenderReady(boolean isSenderReady) {
        this.isSenderReady = isSenderReady;
        triggerObservers();
    }

    public boolean isRecipientReady() {
        return isRecipientReady;
    }

    public void setIsRecipientReady(boolean isRecipientReady) {
        this.isRecipientReady = isRecipientReady;
        triggerObservers();
    }

    public boolean isPackageDetailsReady() {
        return isPackageDetailsReady;
    }

    public void setIsPackageDetailsReady(boolean isPackageDetailsReady) {
        this.isPackageDetailsReady = isPackageDetailsReady;
        triggerObservers();
    }

    public boolean isPackageSizeReady() {
        return isPackageSizeReady;
    }

    public void setIsPackageSizeReady(boolean isPackageSizeReady) {
        this.isPackageSizeReady = isPackageSizeReady;
        triggerObservers();
    }

    public boolean isPickUpScheduleReady() {
        return isPickUpScheduleReady;
    }

    public void setIsPickUpScheduleReady(boolean isPickUpScheduleReady) {
        this.isPickUpScheduleReady = isPickUpScheduleReady;
        triggerObservers();
    }

    public void triggerObservers()
    {
        if(isSenderReady && isRecipientReady && isPackageDetailsReady && isPackageSizeReady && isPickUpScheduleReady)
        {
            setChanged();
            notifyObservers();
        }
    }
}
