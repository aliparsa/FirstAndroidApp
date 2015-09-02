package Intefaces;


import java.util.ArrayList;

import DataModel.Group;
import DataModel.News;

/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackNews {

    void onSuccess(ArrayList<News> newses);

    void onError(String errorMessage);


}
