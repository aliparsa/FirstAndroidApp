package Intefaces;


import java.util.ArrayList;

import DataModel.Soal;

/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBackSoal {

    public void onSuccess(ArrayList<Soal> soals);

    public void onError(String errorMessage);


}
