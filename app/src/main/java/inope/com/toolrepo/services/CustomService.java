package inope.com.toolrepo.services;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import inope.com.toolrepo.beans.ClientBean;
import inope.com.toolrepo.beans.SinkBean;

public interface CustomService {

    /**
     * @param context
     * @return
     */
    boolean checkCameraHardware(Context context);

    /**
     * @param bitmap
     * @return
     */
    String encodeBase64(Bitmap bitmap);

    /**
     * @param sinkBean
     */
    boolean createAndSaveFile(SinkBean sinkBean, Context context);

    /**
     * delete all files with fileNames'name where flag is true
     * @param fileNames
     */
    void deleteFiles(HashMap<String, Boolean> fileNames, Context context);

    /**
     *
     * @param context
     * @param client
     * @param profile
     * @return
     */
    ArrayList<SinkBean> getAllSinksToSend(Context context, ClientBean client, String profile);

    /**
     * @return
     */
    ArrayList<SinkBean> getAllSinksSavedByDateAndReference(Context context, ClientBean client, String profile, Date startDate, Date endDate, String reference);

    /**
     *
     */
    void deleteTempImageFiles(Context context);
}
