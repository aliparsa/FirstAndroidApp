package Intefaces;


/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackDownload {

    public void onSuccessFinish(String result);
    public void onError(String errorMessage);
    public void onProgressUpdate(int progress);


}
