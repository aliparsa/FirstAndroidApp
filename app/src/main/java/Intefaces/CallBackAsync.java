package Intefaces;


/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackAsync<T> {

    public void onBeforStart();
    public void onSuccessFinish(T result);
    public void onError(String errorMessage);


}
