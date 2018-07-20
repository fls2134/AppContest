package com.example.root.appcontest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapCircleData;
import com.nhn.android.maps.overlay.NMapCircleStyle;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.maps.overlay.NMapPathLineStyle;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import static android.content.ContentValues.TAG;
import static com.example.root.appcontest.BuildConfig.DEBUG;

public class MapsActivity extends NMapActivity {

    private NMapView mMapView;
    private final String CLIENT_ID = "A2hMBsKeTvfrDPF5q_dM";
    final int MY_PERMISSON_REQUEST_FINE_LOCATION = 3;
    NMapController nMapController;
    NMapResourceProvider nMapResourceProvider;
    NMapOverlayManager nMapOverlayManager;
    NMapCompassManager compassManager;
    NMapMyLocationOverlay myLocationOverlay;
    NMapCircleData circleData;
    NMapLocationManager locationManager;
    MapContainerView mMapContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                ;//
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSON_REQUEST_FINE_LOCATION);


        }



        mMapView = new NMapView(this);
        mMapView.setClientId(CLIENT_ID);
        setContentView(mMapView);
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.setAutoRotateEnabled(true, true);
        mMapView.requestFocus();

        compassManager = new NMapCompassManager(this);

        locationManager = new NMapLocationManager(this);
        locationManager.enableMyLocation(true);
        locationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        nMapController = mMapView.getMapController();
        nMapResourceProvider = new ResProvider(this);

        //nMapResourceProvider = new NMapResourceProvider
        nMapOverlayManager = new NMapOverlayManager(this,mMapView,nMapResourceProvider);
        myLocationOverlay = nMapOverlayManager.createMyLocationOverlay(locationManager, compassManager);

        testPOIdataOverlay();

    }
    private void testPOIdataOverlay() {

        // Markers for POI item
        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, nMapResourceProvider);
        poiData.beginPOIdata(2);
        NMapPOIitem item = poiData.addPOIitem(126.95976602854101, 37.49470439862809, "Pizza 777-111", markerId, 0);
        item.setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW);
        poiData.addPOIitem(127.061, 37.51, "Pizza 123-456", markerId, 0);



        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = nMapOverlayManager.createPOIdataOverlay(poiData, null);
        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

        // select an item
        poiDataOverlay.selectPOIitem(0, true);

        // show all POI data
        poiDataOverlay.showAllPOIdata(0);
    }

    private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

        @Override
        public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            if (DEBUG) {
                Log.i("asd", "onCalloutClick: title=" + item.getTitle());
            }

            // [[TEMP]] handle a click event of the callout
            Toast.makeText(MapsActivity.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            if (DEBUG) {
                if (item != null) {
                    Log.i("asd", "onFocusChanged: " + item.toString());
                } else {
                    Log.i("asd", "onFocusChanged: ");
                }
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSON_REQUEST_FINE_LOCATION:
            {
                if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    ;
                }
                else
                {
                    ;
                }
                return;
            }
        }
    };

    private void startMyLocation() {

        if (myLocationOverlay != null) {
            if (!nMapOverlayManager.hasOverlay(myLocationOverlay)) {
                nMapOverlayManager.addOverlay(myLocationOverlay);
            }

            if (locationManager.isMyLocationEnabled()) {

                if (!mMapView.isAutoRotateEnabled()) {
                    myLocationOverlay.setCompassHeadingVisible(true);

                    compassManager.enableCompass();

                    mMapView.setAutoRotateEnabled(true, false);

                    mMapContainerView.requestLayout();
                } else {
                    stopMyLocation();
                }

                mMapView.postInvalidate();
            } else {
                boolean isMyLocationEnabled = locationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Toast.makeText(MapsActivity.this, "Please enable a My Location source in system settings",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                }
            }
        }
    }

    private void stopMyLocation() {
        if (myLocationOverlay != null) {
            locationManager.disableMyLocation();

            if (mMapView.isAutoRotateEnabled()) {
                myLocationOverlay.setCompassHeadingVisible(false);

                compassManager.disableCompass();

                mMapView.setAutoRotateEnabled(false, false);

                mMapView.requestLayout();
            }
        }
    }

    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {
        @Override
        public boolean onLocationChanged(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {
            Log.d(TAG, "onCreate: " + nGeoPoint.longitude);
            Log.d(TAG, "onCreate: " + nGeoPoint.latitude);
            if(nMapController != null)
            {
                nMapController.animateTo(nGeoPoint);
                //nMapController.setZoomLevel(13);
                NMapPathDataOverlay pathDataOverlay = nMapOverlayManager.createPathDataOverlay();
                if(circleData == null)
                    circleData = new NMapCircleData(1);
                else
                {
                    nMapOverlayManager.removeOverlay(pathDataOverlay);
                    circleData = new NMapCircleData(1);
                }
                circleData.initCircleData();

                circleData.addCirclePoint(nGeoPoint.getLongitude(),nGeoPoint.getLatitude(),100.0F);//50.0F가 사이즈
                circleData.endCircleData();
                pathDataOverlay.addCircleData(circleData);
                NMapCircleStyle circleStyle = new NMapCircleStyle(mMapView.getContext());

                circleStyle.setLineType(NMapPathLineStyle.TYPE_DASH);
                circleStyle.setFillColor(0x0000FF,0x20);
                circleData.setCircleStyle(circleStyle);
                pathDataOverlay.showAllPathData(0);
            }
            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager nMapLocationManager) {

        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {

        }
    };
    private class MapContainerView extends ViewGroup {

        public MapContainerView(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            final int width = getWidth();
            final int height = getHeight();
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);
                final int childWidth = view.getMeasuredWidth();
                final int childHeight = view.getMeasuredHeight();
                final int childLeft = (width - childWidth) / 2;
                final int childTop = (height - childHeight) / 2;
                view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }

            if (changed) {
                nMapOverlayManager.onSizeChanged(width, height);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            int sizeSpecWidth = widthMeasureSpec;
            int sizeSpecHeight = heightMeasureSpec;

            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);

                if (view instanceof NMapView) {
                    if (mMapView.isAutoRotateEnabled()) {
                        int diag = (((int)(Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
                        sizeSpecWidth = View.MeasureSpec.makeMeasureSpec(diag, View.MeasureSpec.EXACTLY);
                        sizeSpecHeight = sizeSpecWidth;
                    }
                }

                view.measure(sizeSpecWidth, sizeSpecHeight);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
