package com.yilinker.expresspublic.core.responses;

import com.yilinker.expresspublic.core.models.PickUpSchedule;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.List;

/**
 * Created by Jeico.
 */
public class EvPickUpScheduleListResp extends EvBaseResp
{
    public List<PickUpSchedule> data;
}
