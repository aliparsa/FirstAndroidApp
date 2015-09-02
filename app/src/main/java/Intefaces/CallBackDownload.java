package Intefaces;


/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackDownload {

    void onSuccessFinish(String result);
    void onError(String errorMessage);
    void onProgressUpdate(int progress);


}
