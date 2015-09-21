package irdevelopers.asemanweb2;

import android.app.Application;

        import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
        import org.acra.sender.HttpSender;

import irdevelopers.asemanweb2.R;

/**
 * Created by nuuneoi on 2/19/2015.
 */

@ReportsCrashes(
        formUri = "http://www.irdevelopers.ir/aseman/errgetter.php",
        reportType = HttpSender.Type.JSON,
        httpMethod = HttpSender.Method.POST,

        //formUriBasicAuthLogin = "osessucheystrefureciesta",
        //formUriBasicAuthPassword = "bb25ccb06a471b4e90a84445ba1339e410555ea7",
        //formKey = "", // This is required for backward compatibility but not used
        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PACKAGE_NAME,
                ReportField.REPORT_ID,
                ReportField.BUILD,
                ReportField.STACK_TRACE
        },
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.toast_crash
)


public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }

}
