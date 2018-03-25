package inope.com.toolrepo.tasks;


import android.content.Context;
import android.content.SharedPreferences;



import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import inope.com.toolrepo.R;
import inope.com.toolrepo.beans.SinkBean;
import inope.com.toolrepo.beans.UserBean;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class HttpRequestSendFileTask extends AbstractHttpRequestTask<HashMap<String, Boolean>> {

    private static final String REQUEST_NAME = "/saveFromFile/";

    private Set<SinkBean> sinks;

    public HttpRequestSendFileTask(Set<SinkBean> sinks, Context context, UserBean userBean) {
        super(context, userBean);
        this.sinks = sinks;
    }

    @Override
    protected HashMap<String, Boolean> post(RestTemplate restTemplate, String url, Map<String, String> mapVariables) {

        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        String profile = sharedPref.getString(context.getString(R.string.profile_name_preference), context.getString(R.string.profile_name_preference));
        url += "/{profile}";
        mapVariables.put("profile", profile);
        for(SinkBean sinkBean : sinks) {
            encodePhotoFile(sinkBean);
        }
        return restTemplate.postForObject(url, sinks, HashMap.class, mapVariables);
    }

    @Override
    protected String getNameRequest() {
        return REQUEST_NAME;
    }

}
