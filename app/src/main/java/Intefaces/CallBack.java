package Intefaces;


/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBack<T> {

    void onSuccess(T result);

    void onError(String errorMessage);


}
