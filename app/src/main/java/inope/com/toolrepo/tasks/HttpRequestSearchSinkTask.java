package inope.com.toolrepo.tasks;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;



import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import inope.com.toolrepo.R;
import inope.com.toolrepo.beans.SinkBean;
import inope.com.toolrepo.constants.SinkConstants;
import inope.com.toolrepo.utils.PropertiesUtils;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class HttpRequestSearchSinkTask extends AsyncTask<Object, Object, SinkBean[]> {

    private static final String REQUEST_NAME = "/search/{startDate}/{endDate}/{clientName}/{reference}";

    private String startDate;
    private String endDate;
    private String reference;
    private Context context;

    public HttpRequestSearchSinkTask(String startDate, String endDate, String reference, Context context) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.context = context;
        this.reference = reference;
    }

    @Override
    protected SinkBean[] doInBackground(Object... params) {
        try {
            SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(context);
            String clientName = defaultSharedPreferences.getString(context.getString(R.string.client_name_preference), context.getString(R.string.client_name_preference));
            final String url = SinkConstants.HTTP_PREFIX + PropertiesUtils.getProperty(SinkConstants.ADDRESS_HOST, context) + ":" + PropertiesUtils.getProperty(SinkConstants.PORT_HOST, context) + REQUEST_NAME;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Map<String, String> mapVariables = new HashMap<>();
            mapVariables.put("startDate", startDate.replaceAll("/", "-"));
            mapVariables.put("endDate", endDate.replaceAll("/", "-"));
            mapVariables.put("clientName", clientName);
            mapVariables.put("reference", reference);
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(SinkConstants.CONNECT_TIMEOUT);
            return restTemplate.getForObject(url, SinkBean[].class, mapVariables);
        } catch (Exception e) {
            Log.e("HttpRequestTask ", e.getMessage(), e);
            //FirebaseCrash.report(e);
            return null;
        }
    }

}
