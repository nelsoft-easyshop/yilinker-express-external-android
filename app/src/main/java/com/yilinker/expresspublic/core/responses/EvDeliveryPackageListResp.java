package com.yilinker.expresspublic.core.responses;

import com.yilinker.expresspublic.core.models.DeliveryPackage;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.List;

/**
 * Created by Jeico.
 */
public class EvDeliveryPackageListResp extends EvBaseResp
{
    public List<DeliveryPackage> data;
}
