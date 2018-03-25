package inope.com.toolrepo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import inope.com.toolrepo.R;
import inope.com.toolrepo.adapters.SpinnerArrayAdapter;
import inope.com.toolrepo.enums.SinkDiameterEnum;
import inope.com.toolrepo.enums.SinkPlumbOptionEnum;


public class PipelineFragment extends Fragment {

    public PipelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_pipeline, container, false);
        initDiameterSpinner((Spinner) view.findViewById(R.id.diameterSpinner), getActivity());
        initPlumbSpinner((Spinner) view.findViewById(R.id.plumbSpinner), getActivity());
        return view;
    }

    public static void initDiameterSpinner(Spinner spinner, Context context) {
        List<String> diameters = Arrays.asList(StringUtils.EMPTY, SinkDiameterEnum.EIGHT.getLabel(), SinkDiameterEnum.TEN.getLabel(), SinkDiameterEnum.TWELVE.getLabel());
        spinner.setAdapter(new SpinnerArrayAdapter(context, diameters));
    }

    public static void initPlumbSpinner(Spinner spinner, Context context) {
        List<String> options = Arrays.asList(StringUtils.EMPTY, SinkPlumbOptionEnum.YES.getLabel(), SinkPlumbOptionEnum.NO.getLabel());
        spinner.setAdapter(new SpinnerArrayAdapter(context, options));
    }
}
