package com.yilinker.expresspublic.core.managers;

import android.location.Location;

import com.yilinker.expresspublic.core.models.Branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BranchManager
{
    private static final Logger logger = Logger.getLogger(BranchManager.class.getSimpleName());

    /**
     * Singleton instance of Branch Manager
     */
    private static BranchManager sBranchManager;

    private Map<Long, Branch> branchMap;

    private BranchManager()
    {
        branchMap = new HashMap<>();
    }

    public static synchronized BranchManager getInstance()
    {
        if(sBranchManager == null)
        {
            sBranchManager = new BranchManager();
        }

        return sBranchManager;
    }

    public void clearBranchMap()
    {
        branchMap.clear();
    }

    public void setBranchMap(List<Branch> branchList)
    {
        if(branchList != null)
        {
            branchMap.clear();

            for (Branch branch : branchList)
            {
                branchMap.put(branch.getId(), branch);
            }
        }
    }

    public Map<Long, Branch> getBranchMap()
    {
        return branchMap;
    }


    public Branch getBranchMap(Long id)
    {
        return branchMap.get(id);
    }

    public void addBranch(Branch branch)
    {
        branchMap.put(branch.getId(), branch);
    }

    public Branch getNearestBranch(Location currentLocation)
    {
        Branch nearestBranch = null;

        Float distance = null;

        for(Long key : branchMap.keySet())
        {
            Branch branch = branchMap.get(key);

            float[] results = new float[1];

            // Modified due to change of datatype within longitude and latitude
            /*Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                    branch.getLatitude(), branch.getLongitude(), results);*/

            Location.distanceBetween(Double.parseDouble(String.valueOf(currentLocation.getLatitude())),
                    Double.parseDouble(String.valueOf(currentLocation.getLongitude())),
                    Double.parseDouble(String.valueOf(branch.getLatitude())),
                            Double.parseDouble(String.valueOf(branch.getLongitude())), results);

            if(distance == null)
            {
                distance = results[0];
                nearestBranch = branch;
            }
            else
            {
                if(results[0] < distance)
                {
                    distance = results[0];
                    nearestBranch = branch;
                }
            }
        }

        return nearestBranch;
    }
}
