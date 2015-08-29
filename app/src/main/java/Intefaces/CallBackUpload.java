package Intefaces;

/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackUpload<T> {

    public void onSuccess(T result, String tag);

    public void onError(String errorMessage);


}
