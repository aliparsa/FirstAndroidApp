package Intefaces;


import java.util.ArrayList;

import DataModel.Group;

/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackGroup {

    void onSuccess(ArrayList<Group> groups);

    void onError(String errorMessage);


}
