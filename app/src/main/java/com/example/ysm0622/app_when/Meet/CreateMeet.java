package com.example.ysm0622.app_when.meet;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.search.Item;

import net.daum.mf.map.api.MapView;

import java.util.HashMap;


public class CreateMeet extends AppCompatActivity implements View.OnFocusChangeListener, TextWatcher, View.OnClickListener {

    // TAG
    private static final String TAG = CreateMeet.class.getName();

    // Const
    private static final int mInputNum = 3;
    private static final int mToolBtnNum = 2;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private TextInputLayout mTextInputLayout[] = new TextInputLayout[mInputNum];
    private ImageView mImageView[] = new ImageView[mInputNum];
    private EditText mEditText[] = new EditText[mInputNum];
    private TextView mTextViewErrMsg[] = new TextView[mInputNum];
    private TextView mTextViewCounter[] = new TextView[mInputNum];
    private LinearLayout mMapContainer;
    private int mMinLength[] = new int[mInputNum];
    private int mMaxLength[] = new int[mInputNum];
    private String mErrMsg[] = new String[mInputNum];

    //MapView
    private boolean mShowMap;
    private ImageView mMapViewImageView;
    private MapView mMapView;
    private HashMap<Integer, Item> mTagItemMap = new HashMap<Integer, Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeet_main);

        // Receive intent
        mIntent = getIntent();

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_arrow_forward_white_24dp);
        String toolbarTitle = getResources().getString(R.string.create_meet);

        initToolbar(toolbarIcon, toolbarTitle);


        initialize();
    }

    private void initialize() {

        // Array allocation

        // Create instance

        // View allocation
        mTextInputLayout[0] = (TextInputLayout) findViewById(R.id.TextInputLayout0);
        mTextInputLayout[1] = (TextInputLayout) findViewById(R.id.TextInputLayout1);
        mTextInputLayout[2] = (TextInputLayout) findViewById(R.id.TextInputLayout2);

        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);
        mImageView[2] = (ImageView) findViewById(R.id.ImageView2);

        mEditText[0] = (EditText) findViewById(R.id.EditText0);
        mEditText[1] = (EditText) findViewById(R.id.EditText1);
        mEditText[2] = (EditText) findViewById(R.id.EditText2);

        mTextViewErrMsg[0] = (TextView) findViewById(R.id.TextView0);
        mTextViewErrMsg[1] = (TextView) findViewById(R.id.TextView2);
        mTextViewErrMsg[2] = (TextView) findViewById(R.id.TextView4);

        mTextViewCounter[0] = (TextView) findViewById(R.id.TextView1);
        mTextViewCounter[1] = (TextView) findViewById(R.id.TextView3);
        mTextViewCounter[2] = (TextView) findViewById(R.id.TextView5);

        // Add listener
        for (int i = 0; i < mInputNum; i++) {
            mEditText[i].setOnFocusChangeListener(this);
            mEditText[i].addTextChangedListener(this);
        }

        // Default setting
        mToolbarAction[1].setVisibility(View.INVISIBLE);

        for (int i = 0; i < mInputNum; i++) {
            mImageView[i].setColorFilter(getResources().getColor(R.color.grey7), PorterDuff.Mode.SRC_ATOP);
            mTextViewErrMsg[i].setVisibility(View.INVISIBLE);
            mTextViewCounter[i].setVisibility(View.INVISIBLE);
            mTextViewCounter[i].setTextColor(getResources().getColor(R.color.grey6));
            mTextViewErrMsg[i].setText("");
        }

        mMinLength[0] = 1;
        mMinLength[1] = 0;
        mMinLength[2] = 0;

        mMaxLength[0] = 20;
        mMaxLength[1] = 128;
        mMaxLength[2] = 128;

        mErrMsg[0] = getResources().getString(R.string.group_title_errmsg1);
        mErrMsg[1] = getResources().getString(R.string.desc_errmsg1);
        mErrMsg[2] = getResources().getString(R.string.location_errmsg1);

    }
//        //MapView
//        mShowMap = false;
//        mMapViewImageView = (ImageView) findViewById(R.id.map_imageview);
//        mMapView = (MapView) findViewById(R.id.map_view);
//        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
//        mMapView.setMapViewEventListener(this);
//        mMapView.setPOIItemEventListener(this);
//        mMapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
//        mMapView.setVisibility(View.INVISIBLE);

//        mImageView[2].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String query = mEditText[2].getText().toString();
//                if (query == null || query.length() == 0) {
//                    showToast("검색어를 입력하세요.");
//                    return;
//                }
//                hideSoftKeyboard(); // 키보드 숨김
//                MapPoint.GeoCoordinate geoCoordinate = mMapView.getMapCenterPoint().getMapPointGeoCoord();
//                double latitude = geoCoordinate.latitude; // 위도
//                double longitude = geoCoordinate.longitude; // 경도
//                int radius = 10000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
//                int page = 1; // 페이지 번호 (1 ~ 3). 한페이지에 15개
//                String apikey = MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY;
//
//                Searcher searcher = new Searcher(); // net.daum.android.map.openapi.search.Searcher
//                searcher.searchKeyword(getApplicationContext(), query, latitude, longitude, radius, page, apikey, new OnFinishSearchListener() {
//                    @Override
//                    public void onSuccess(List<Item> itemList) {
//                        mMapView.removeAllPOIItems(); // 기존 검색 결과 삭제
//                        showResult(itemList); // 검색 결과 보여줌
//                    }
//
//                    @Override
//                    public void onFail() {
//                        showToast("API_KEY의 제한 트래픽이 초과되었습니다.");
//                    }
//                });
//            }
//        });
//    }
//
//    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
//
//        private final View mCalloutBalloon;
//
//        public CustomCalloutBalloonAdapter() {
//            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
//        }
//
//        @Override
//        public View getCalloutBalloon(MapPOIItem poiItem) {
//            if (poiItem == null) return null;
//            Item item = mTagItemMap.get(poiItem.getTag());
//            if (item == null) return null;
//            ImageView imageViewBadge = (ImageView) mCalloutBalloon.findViewById(R.id.badge);
//            TextView textViewTitle = (TextView) mCalloutBalloon.findViewById(R.id.title);
//            textViewTitle.setText(item.title);
//            TextView textViewDesc = (TextView) mCalloutBalloon.findViewById(R.id.desc);
//            textViewDesc.setText(item.address);
//            imageViewBadge.setImageDrawable(createDrawableFromUrl(item.imageUrl));
//            return mCalloutBalloon;
//        }
//
//        @Override
//        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
//            return null;
//        }
//
//    }
//
//    private Drawable createDrawableFromUrl(String url) {
//        try {
//            InputStream is = (InputStream) this.fetch(url);
//            Drawable d = Drawable.createFromStream(is, "src");
//            return d;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private Object fetch(String address) throws MalformedURLException, IOException {
//        URL url = new URL(address);
//        Object content = url.getContent();
//        return content;
//    }
//
//    private void hideSoftKeyboard() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(mEditText[2].getWindowToken(), 0);
//    }
//
//    private void showToast(final String text) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(CreateMeet.this, text, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void showResult(List<Item> itemList) {
//        MapPointBounds mapPointBounds = new MapPointBounds();
//
//        for (int i = 0; i < itemList.size(); i++) {
//            Item item = itemList.get(i);
//
//            MapPOIItem poiItem = new MapPOIItem();
//            poiItem.setItemName(item.title);
//            poiItem.setTag(i);
//            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude);
//            poiItem.setMapPoint(mapPoint);
//            mapPointBounds.add(mapPoint);
//            poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
//            poiItem.setCustomImageResourceId(R.drawable.map_pin_blue);
//            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
//            poiItem.setCustomSelectedImageResourceId(R.drawable.map_pin_red);
//            poiItem.setCustomImageAutoscale(false);
//            poiItem.setCustomImageAnchor(0.5f, 1.0f);
//
//            mMapView.addPOIItem(poiItem);
//            mTagItemMap.put(poiItem.getTag(), item);
//        }
//
//        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));
//
//        MapPOIItem[] poiItems = mMapView.getPOIItems();
//        if (poiItems.length > 0) {
//            mMapView.selectPOIItem(poiItems[0], false);
//        }
//    }

    private void initToolbar(Drawable Icon[], String Title) {
        mToolbarAction = new ImageView[2];
        mToolbarAction[0] = (ImageView) findViewById(R.id.Toolbar_Action0);
        mToolbarAction[1] = (ImageView) findViewById(R.id.Toolbar_Action1);
        mToolbarTitle = (TextView) findViewById(R.id.Toolbar_Title);

        for (int i = 0; i < mToolBtnNum; i++) {
            mToolbarAction[i].setOnClickListener(this);
            mToolbarAction[i].setImageDrawable(Icon[i]);
            mToolbarAction[i].setBackground(getResources().getDrawable(R.drawable.selector_btn));
        }
        mToolbarTitle.setText(Title);
    }

    @Override
    public void onClick(View v) {
        if (mToolbarAction[0].getId() == v.getId()) {
            super.onBackPressed();
        }
        if (mToolbarAction[1].getId() == v.getId()) {
            mIntent.setClass(CreateMeet.this, SelectDay.class);
            mIntent.putExtra(Gl.MEET_TITLE, mEditText[0].getText().toString());
            mIntent.putExtra(Gl.MEET_DESC, mEditText[1].getText().toString());
            mIntent.putExtra(Gl.MEET_LOCATION, mEditText[2].getText().toString());
//            mMapView.setVisibility(View.GONE);

            //지도 위도, 경도 얻고 삭제
            //mMapContainer.removeView(mMapView);
            //mMapView.releaseUnusedMapTileImageResources();
            //mMapView.destroyDrawingCache();
            startActivityForResult(mIntent, Gl.CREATEMEET_SELECTDAY);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        for (int i = 0; i < mInputNum; i++) {
            if (v.getId() == mEditText[i].getId()) {
                mImageView[i].clearColorFilter();
                if (hasFocus) {
                    mImageView[i].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    mTextViewCounter[i].setVisibility(View.VISIBLE);
                    mTextViewCounter[i].setText(mEditText[i].getText().toString().length() + " / " + mMinLength[i] + "–" + mMaxLength[i]);
                } else {
                    mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    mTextViewCounter[i].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int cnt = 0;
        for (int i = 0; i < mInputNum; i++) {
            if (mEditText[i].getText().toString().length() >= mMinLength[i]) {
                cnt++;
            }
        }
        if (cnt == mInputNum) {
            mToolbarAction[1].setVisibility(View.VISIBLE);
        } else {
            mToolbarAction[1].setVisibility(View.INVISIBLE);
        }
        validationCheck();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void validationCheck() { // 인풋 확인
        for (int i = 0; i < mInputNum; i++) {
            if (mEditText[i].hasFocus()) {
                mTextViewCounter[i].setText(mEditText[i].getText().toString().length() + " / " + mMinLength[i] + "–" + mMaxLength[i]);
            }
            if (mEditText[i].getText().toString().length() < mMinLength[i] && mEditText[i].hasFocus()) {
                mTextViewErrMsg[i].setText(mErrMsg[i]);
                mTextViewErrMsg[i].setVisibility(View.VISIBLE);
                mTextViewCounter[i].setTextColor(getResources().getColor(R.color.red_dark));
                mEditText[i].getBackground().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
                //mImageView[i].setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
                //mTextInputLayout[i].setHintTextAppearance(R.style.TextAppearance_Design_Error_);
            } else if (mEditText[i].getText().toString().length() >= mMinLength[i] && mEditText[i].hasFocus()) {
                mTextViewErrMsg[i].setText("");
                mTextViewErrMsg[i].setVisibility(View.INVISIBLE);
                mTextViewCounter[i].setTextColor(getResources().getColor(R.color.colorAccent));
                mEditText[i].getBackground().setColorFilter(getResources().getColor(R.color.textPrimary), PorterDuff.Mode.SRC_ATOP);
                mEditText[i].getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                //mImageView[i].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                //mTextInputLayout[i].setHintTextAppearance(R.style.TextAppearance_AppCompat_Display1_);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Gl.CREATEMEET_SELECTDAY) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

//    //지도 유동적으로 보여주기
//    public void showMap(View v) {
//        if (mShowMap) {
//            mMapViewImageView.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
//            mMapView.setVisibility(View.INVISIBLE);
//            mShowMap = false;
//        } else {
//            mMapViewImageView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
//            mMapView.setVisibility(View.VISIBLE);
//            mShowMap = true;
//        }
//    }
//
//    @Override
//    public void onMapViewInitialized(MapView mapView) {
//        mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.450647, 127.12887), 2, true);
//
//        Searcher searcher = new Searcher();
//        double latitude = 37.450647;
//        double longitude = 127.12887;
//        int radius = 10000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
//        int page = 1;
//        String apikey = MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY;
//
//        searcher.searchKeyword(getApplicationContext(), "제주도", latitude, longitude, radius, page, apikey, new OnFinishSearchListener() {
//            @Override
//            public void onSuccess(final List<Item> itemList) {
//                showResult(itemList);
//            }
//
//            @Override
//            public void onFail() {
//                showToast("API_KEY의 제한 트래픽이 초과되었습니다.");
//            }
//        });
//    }
//
//    @Override
//    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
//
//    }
//
//    @Override
//    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
//
//    }
//
//    @Override
//    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
//
//    }
//
//    @Override
//    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
//
//    }
//
//    @Override
//    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
//
//    }
//
//    @Override
//    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
//
//    }
//
//    @Override
//    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
//
//    }
//
//    @Override
//    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
//
//    }
//
//    @Override
//    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
//
//    }
//
//    @Override
//    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
//
//    }
//
//    @Override
//    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
//        Item item = mTagItemMap.get(mapPOIItem.getTag());
//        StringBuilder sb = new StringBuilder();
//        sb.append("title=").append(item.title).append("\n");
//        sb.append("imageUrl=").append(item.imageUrl).append("\n");
//        sb.append("address=").append(item.address).append("\n");
//        sb.append("newAddress=").append(item.newAddress).append("\n");
//        sb.append("zipcode=").append(item.zipcode).append("\n");
//        sb.append("phone=").append(item.phone).append("\n");
//        sb.append("category=").append(item.category).append("\n");
//        sb.append("longitude=").append(item.longitude).append("\n");
//        sb.append("latitude=").append(item.latitude).append("\n");
//        sb.append("distance=").append(item.distance).append("\n");
//        sb.append("direction=").append(item.direction).append("\n");
//        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
//
//    }
}
