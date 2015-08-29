package Intefaces;


import java.util.ArrayList;

import DataModel.Group;

/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackGroup {

    public void onSuccess(ArrayList<Group> groups);

    public void onError(String errorMessage);


}
