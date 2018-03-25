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
import inope.com.toolrepo.enums.SinkStatusEnum;
import inope.com.toolrepo.enums.SinkTypeEnum;


public class SinkFragment extends Fragment {

    public SinkFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sink, container, false);
        initTypeSpinner((Spinner) view.findViewById(R.id.typeSpinner), getActivity());
        initStateSpinner((Spinner) view.findViewById(R.id.stateSpinner), getActivity());
        return view;
    }

    private static void initTypeSpinner(Spinner spinner, Context context) {
        List<String> types = Arrays.asList(StringUtils.EMPTY, SinkTypeEnum.COVENTIONAL.getLabel(), SinkTypeEnum.LATERAL.getLabel(), SinkTypeEnum.TRANSVERSAL.getLabel());
        spinner.setAdapter(new SpinnerArrayAdapter(context, types));
    }

    private static void initStateSpinner(Spinner spinner, Context context) {
        List<String> status = Arrays.asList(StringUtils.EMPTY, SinkStatusEnum.BAD.getLabel(), SinkStatusEnum.GOOD.getLabel(), SinkStatusEnum.MODERATE.getLabel());
        spinner.setAdapter(new SpinnerArrayAdapter(context, status));
    }
}
