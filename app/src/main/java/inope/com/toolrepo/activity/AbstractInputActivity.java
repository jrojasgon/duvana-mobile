package inope.com.toolrepo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import inope.com.toolrepo.R;
import inope.com.toolrepo.beans.AddressBean;
import inope.com.toolrepo.beans.SinkBean;
import inope.com.toolrepo.enums.SinkDiameterEnum;
import inope.com.toolrepo.enums.SinkPlumbOptionEnum;
import inope.com.toolrepo.enums.SinkStatusEnum;
import inope.com.toolrepo.enums.SinkTypeEnum;
import inope.com.toolrepo.fragment.MainFragment;
import inope.com.toolrepo.fragment.PhotoFragment;
import inope.com.toolrepo.fragment.PipelineFragment;
import inope.com.toolrepo.fragment.SinkFragment;
import inope.com.toolrepo.utils.AddressUtils;
import inope.com.toolrepo.utils.ImageUtils;

import static inope.com.toolrepo.activity.utils.ActivityUtils.hasText;
import static inope.com.toolrepo.activity.utils.ActivityUtils.isNumeric;
import static inope.com.toolrepo.activity.utils.ActivityUtils.isValidSpinner;
import static inope.com.toolrepo.activity.utils.ActivityUtils.setDefaultClient;

public abstract class AbstractInputActivity extends AbstractCreationActivity {

    private MainFragment mainFragment = new MainFragment();
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    protected Location lastLocation;
    protected AddressBean addressBean;

    protected abstract void addSendListenerAction();

    protected abstract void populateFromExtras(String extra);

    protected abstract void updateCustomValues(SinkBean sinkBean);

    @Override
    protected void customInitialize() {
        setContentView(R.layout.activity_creation);
        //addCameraButtonListener((Button) findViewById(R.id.cameraFinalButton));
        //addSendButtonListener();
        //addSaveSendLaterButtonListener((Button) findViewById(R.id.saveAndSendLaterButton));
        Context context = getBaseContext();
        //initTypeSpinner(getTypeSpinner(), context);
        //initStateSpinner(getStatusSpinner(), context);
        //initDiameterSpinner(getDiameterSpinner(), context);
        //initPlumbSpinner(getPlumbSpinner(), context);
        //
        //
        //initLocationRequest();
        ///addSpinnerTypeListener();
        //addSpinnerPlumbOptionListener();
        String extra = getIntent().getStringExtra("sinkBean");
        if (extra != null) {
            populateFromExtras(extra);
        }

        createNavigation();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addSpinnerTypeListener() {
        getTypeSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String typeSelected = getTypeSpinner().getSelectedItem().toString();
                getLengthEditText().setEnabled(!SinkTypeEnum.COVENTIONAL.getLabel().equals(typeSelected));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addSpinnerPlumbOptionListener() {
        getPlumbSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String optionSelected = getPlumbSpinner().getSelectedItem().toString();
                getPipelineLengthEditText().setEnabled(!SinkPlumbOptionEnum.NO.getLabel().equals(optionSelected));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected boolean createSinkBean(SinkBean sinkBean) {

        sinkBean.setAddress(getAddressBean());
        updateCustomValues(sinkBean);

        Bitmap imageViewAfterDrawingCache = getImageView().getDrawingCache();

        if (imageViewAfterDrawingCache != null) {
            Object tag = getImageView().getTag();
            if (tag != null) { // photo has been changed if modeEdition, otherwise use taken or chosen photo
                sinkBean.setImageAfterPath(String.valueOf(tag));
                sinkBean.setImageAfter(customService.encodeBase64(imageViewAfterDrawingCache));
            } else if (tag == null && isModeEdition() && StringUtils.isEmpty(sinkBean.getImageAfterPath())) {
                Log.e("Creation", "There is no photo");
            }
        }
        getImageView().setDrawingCacheEnabled(true);
        setDefaultClient(sinkBean, this, getString(R.string.client_name_preference));

        // if image before exists : save encoded base 64. This happens in mode edition
        if (StringUtils.isNotEmpty(sinkBean.getImageBefore())) {
            Bitmap bipMapFromFile = ImageUtils.getBipMapFromFile(sinkBean.getImageBefore());
            if (bipMapFromFile != null) {
                sinkBean.setImageBefore(customService.encodeBase64(bipMapFromFile));
            }
        }

        boolean referenceExists = referenceExists(sinkBean);
        boolean validSinkStatusEnum = isValidSinkStatusEnum(sinkBean);
        boolean validSinkTypeEnum = isValidSinkTypeEnum(sinkBean);
        boolean validSinkDiameterEnum = isValidSinkDiameterEnum(sinkBean);
        boolean validSinkPlumbEnum = isValidSinkPlumbEnum(sinkBean);
        boolean photoExists = isPhotoTaken(sinkBean);

        return referenceExists && validSinkStatusEnum && validSinkTypeEnum && validSinkDiameterEnum && validSinkPlumbEnum && photoExists;
    }

    private boolean isPhotoTaken(SinkBean sinkBean) {
        //Button button = (Button) findViewById(R.id.cameraFinalButton);
        //return photoExists(sinkBean.getImageAfter(), button);
        return false;
    }

    private boolean isValidSinkStatusEnum(SinkBean sinkBean) {
        Spinner spinner = getStatusSpinner();
        TextView textView = (TextView) findViewById(R.id.stateTitle);
        if (isValidSpinner(spinner, textView)) {
            String selectedItem = (String) spinner.getSelectedItem();
            sinkBean.setSinkStatusId(SinkStatusEnum.getSinkStatutEnumByName(selectedItem).getId());
            return true;
        }
        return false;
    }

    private boolean isValidSinkTypeEnum(SinkBean sinkBean) {

        Spinner spinner = getTypeSpinner();
        if (isValidSpinner(getTypeSpinner(), (TextView) findViewById(R.id.typeTitle))) {
            String selectedItem = (String) spinner.getSelectedItem();
            SinkTypeEnum sinkTypeEnum = SinkTypeEnum.getSinkTypeEnum(selectedItem);
            if (Arrays.asList(SinkTypeEnum.LATERAL, SinkTypeEnum.TRANSVERSAL).contains(sinkTypeEnum)) {
                EditText lengthText = getLengthEditText();
                boolean lengthExists = isNumeric(lengthText, (EditText) findViewById(R.id.text_input_length));
                if (lengthExists) {
                    sinkBean.setLength(Long.valueOf(lengthText.getText().toString().trim()));
                } else {
                    return false;
                }
            }
            sinkBean.setSinkTypeId(sinkTypeEnum.getId());
            return true;
        }
        return false;
    }

    private boolean isValidSinkDiameterEnum(SinkBean sinkBean) {
        Spinner spinner = getDiameterSpinner();
        if (isValidSpinner(spinner, (TextView) findViewById(R.id.diameterTitle))) {
            String selectedItem = (String) spinner.getSelectedItem();
            sinkBean.setPipeLineDiameterId(SinkDiameterEnum.getSinkDiameterEnum(selectedItem).getId());
            return true;
        }


        return false;
    }

    private boolean isValidSinkPlumbEnum(SinkBean sinkBean) {
        Spinner spinner = getPlumbSpinner();
        if (isValidSpinner(spinner, (TextView) findViewById(R.id.plumbOptionTitle))) {
            String selectedItem = (String) spinner.getSelectedItem();
            SinkPlumbOptionEnum sinkPlumbOptionEnum = SinkPlumbOptionEnum.getSinkPlumbEnum(selectedItem);
            if (SinkPlumbOptionEnum.YES.equals(sinkPlumbOptionEnum)) {
                EditText pipeLineLengthText = getPipelineLengthEditText();
                boolean lengthExists = isNumeric(pipeLineLengthText, (EditText) findViewById(R.id.editTextLength));
                if (lengthExists) {
                    sinkBean.setPipeLineLength(Long.valueOf(pipeLineLengthText.getText().toString().trim()));
                } else {
                    return false;
                }
            }
            sinkBean.setPlumbOptionId(sinkPlumbOptionEnum.getId());
            return true;
        }
        return false;
    }

    private boolean referenceExists(SinkBean sinkBean) {

        boolean referenceExist = hasText(getReferenceEditText(), (EditText) findViewById(R.id.text_input_reference));

        if (referenceExist) {
            sinkBean.setReference(getReferenceEditText().getText().toString());
            sinkBean.setObservations(getObservationsEditText().getText().toString());
        }
        return referenceExist;
    }

    @Override
    protected void onStart() {
        //mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected ImageView getImageView() {
        //return (ImageView) findViewById(R.id.imageViewAfter);
        return null;
    }

    protected EditText getAddressEditText() {
        return (EditText) findViewById(R.id.editTextAddress);
    }

    protected EditText getNeighborhoodEditText() {
        return (EditText) findViewById(R.id.editTextNeighborhood);
    }

    protected EditText getLengthEditText() {
        return (EditText) findViewById(R.id.editTextLength);
    }

    protected EditText getPipelineLengthEditText() {
        return (EditText) findViewById(R.id.editTextLength);
    }

    protected EditText getObservationsEditText() {
        return (EditText) findViewById(R.id.editTextObservations);
    }

    protected Spinner getTypeSpinner() {
        return (Spinner) findViewById(R.id.typeSpinner);
    }

    protected Spinner getStatusSpinner() {
        return (Spinner) findViewById(R.id.stateSpinner);
    }

    protected Spinner getPlumbSpinner() {
        return (Spinner) findViewById(R.id.plumbSpinner);
    }

    protected Spinner getDiameterSpinner() {
        return (Spinner) findViewById(R.id.diameterSpinner);
    }

    protected void getAddressFromLocation() {
        addressBean = addressBean != null ? addressBean : AddressUtils.initAddressFromLocation((EditText) findViewById(R.id.editTextAddress), (EditText) findViewById(R.id.editTextNeighborhood), getBaseContext(), lastLocation);
    }

    private void addSendButtonListener() {
        /*sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSendListenerAction();
            }
        });*/
    }

    @NonNull
    private AddressBean getAddressBean() {
        if (addressBean == null) {
            addressBean = new AddressBean();
        }
        AddressUtils.validateAddressFields(getBaseContext(),
                (EditText) findViewById(R.id.editTextAddress),
                (EditText) findViewById(R.id.editTextNeighborhood),
                (EditText) findViewById(R.id.text_input_address),
                (EditText) findViewById(R.id.text_input_layout_neighborhood), addressBean);

        return addressBean;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mainFragment, "Principal");
        adapter.addFragment(new SinkFragment(), "Estado");
        adapter.addFragment(new PipelineFragment(), "Tuberia");
        adapter.addFragment(new PhotoFragment(), "Foto");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
