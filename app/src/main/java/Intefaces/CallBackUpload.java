package Intefaces;

/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackUpload<T> {

    void onSuccess(T result, String tag);

    void onError(String errorMessage);


}
