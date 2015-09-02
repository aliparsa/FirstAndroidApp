package Intefaces;


/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackAsync<T> {

    void onSuccessFinish(T result);
    void onError(String errorMessage);


}
