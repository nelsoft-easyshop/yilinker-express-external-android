package com.yilinker.expresspublic.core.interfaces;

import com.yilinker.expresspublic.core.models.Branch;

/**
 * Created by Jeico.
 */
public interface BranchListener
{
    void onBranchNameSelected(Branch branch);
    void onBranchContactNumberSelected(Branch branch);
    void onBranchDirectionSelected(Branch branch);
}
