package inope.com.toolrepo.tasks;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;



import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import inope.com.toolrepo.beans.SinkBean;
import inope.com.toolrepo.constants.SinkConstants;
import inope.com.toolrepo.utils.PropertiesUtils;

public class HttpRequestDeleteSinkTask extends AsyncTask<Void, Void, Boolean> {

    private static final String REQUEST_NAME = "/delete";

    private SinkBean sinkBean;
    private Context context;

    public HttpRequestDeleteSinkTask(SinkBean sinkBean, Context context) {
        this.sinkBean = sinkBean;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            final String url = SinkConstants.HTTP_PREFIX + PropertiesUtils.getProperty(SinkConstants.ADDRESS_HOST, context) + ":" + PropertiesUtils.getProperty(SinkConstants.PORT_HOST, context) + REQUEST_NAME;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(SinkConstants.CONNECT_TIMEOUT);
            return restTemplate.postForObject(url, sinkBean, Boolean.class);
        } catch (Exception e) {
            Log.e("HttpRequestTask ", e.getMessage(), e);
            //FirebaseCrash.report(e);
            return null;
        }
    }
}
