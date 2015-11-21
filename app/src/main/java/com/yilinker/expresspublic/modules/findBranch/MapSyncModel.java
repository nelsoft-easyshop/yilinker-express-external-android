package com.yilinker.expresspublic.modules.findBranch;

import java.util.Observable;

/**
 * Created by Jeico.
 */
public class MapSyncModel extends Observable
{
    private boolean isMapReady;
    private boolean isLocationReady;

    public MapSyncModel()
    {
        this.isMapReady = false;
        this.isLocationReady = false;
    }

    public boolean isMapReady() {
        return isMapReady;
    }

    public void setIsMapReady(boolean isMapReady) {
        this.isMapReady = isMapReady;
        triggerObservers();
    }

    public boolean isLocationReady() {
        return isLocationReady;
    }

    public void setIsLocationReady(boolean isLocationReady) {
        this.isLocationReady = isLocationReady;
        triggerObservers();
    }

    public void triggerObservers()
    {
        if(isMapReady && isLocationReady)
        {
            setChanged();
            notifyObservers();
        }
    }
}
