package com.gkalyan0510.root.nikiandroid;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static FloatingActionButton fab;
    static WebView wv;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static contactlist conlist;
    static ContactsMap cp;
    private ViewPager mViewPager;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(2);

        fab= (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position==1){
                    fab.setVisibility(View.INVISIBLE);

                    return ;

                }
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(1);
                    }
                });
                if(position==2){

                    fab.setImageResource(R.mipmap.left);
                    fab.setVisibility(View.VISIBLE);
                    return ;
                }
                fab.setImageResource(R.mipmap.right);
                fab.setVisibility(View.VISIBLE);
                return;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
             wv = new WebView(context);
            wv.getSettings().setJavaScriptEnabled(true); // enable javascript
            wv.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
                }
            });

            wv.loadUrl("http://www.github.com/kalyan0510");
            return wv;
        }
    }
    public static class ContactsMap extends SupportMapFragment implements OnMapReadyCallback {
        GoogleMap mMap;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }
       /*
        public static ContactsMap newInstance(int sectionNumber) {
            ContactsMap fragment = new ContactsMap();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        private void drawMarker(double lat,double longi){
            mMap.clear();
            LatLng currentPosition = new LatLng(lat,longi);

// zoom to the current location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));

// add a marker to the map indicating our current position
            mMap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .snippet("Lat:"));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           final View rootView = inflater.inflate(R.layout.fragment_map, container, false);
            setUpMapIfNeeded();
            return rootView;
        }
        private void setUpMapIfNeeded()
        {
            if (mMap == null)
            {
               *//* if(getActivity().getSupportFragmentManager()!=null)
               mMap = ((SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map)).getMap();*//*
              *//*  boolean shouldCreateChild = getArguments().getBoolean("shouldYouCreateAChildFragment");
                Fragment fragTwo=null;
                if (true) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    fm.beginTransaction();
                    fragTwo  = new Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putBoolean("shouldYouCreateAChildFragment", false);
                    fragTwo.setArguments(arguments);
                    ft.add(R.id.frag_container, fragTwo);
                    ft.commit();

                }*//*
                        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();


                if (mMap != null)
                {
                    setUpMap();
                }
            }
        }

        private void setUpMap()
        {
            mMap.addMarker(new MarkerOptions().position(new LatLng(22.7253, 75.8655)).title("Indore"));
            // here is marker Adding code
        }*/

        @Override
        public void onMapReady(GoogleMap googleMap) {
            Log.d("MyMap", "onMapReady");
            //Toast.makeText(context, "xxxxxxxxxxxxxx", Toast.LENGTH_SHORT).show();
            mMap = googleMap;
           /* googleMap.addMarker(new MarkerOptions().position(new LatLng(22.7253, 75.8655)).title("Indore KALYAN"));*/

            googleMap.setPadding(20, 0, 0, 20);
            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
              //      Toast.makeText(context, "xxxxxxxxxxx", Toast.LENGTH_SHORT).show();
                }
            });
            //setUpMap();
        }
        

        private void drawMarker(double lat,double longi){
            mMap.clear();
            LatLng currentPosition = new LatLng(lat,longi);

// zoom to the current location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));

// add a marker to the map indicating our current position
            mMap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .snippet("Lat:"));
        }
    }
    //helpful res:/    http://stackoverflow.com/questions/32578227/google-map-not-showing-on-tab-activity
    public static class RecentChat extends Fragment {


        private static final String ARG_SECTION_NUMBER = "section_number";
        RecentUserAdapter adapter;

        public RecentChat() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RecentChat newInstance(int sectionNumber) {

            RecentChat fragment = new RecentChat();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        ListView recentlistview;
        ArrayList<contacts> recent_user_list;
        //TextView tvw;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.recent_chats, container, false);
            recentlistview = (ListView) rootView.findViewById(R.id.recent_list_view);
            new GetNikicontacts().execute();
            //Toast.makeText(context, "Working or nt--"+strlist[0], Toast.LENGTH_SHORT).show();

            recent_user_list = new ArrayList<contacts>();
            adapter = new RecentUserAdapter(context, recent_user_list);
            //tvw = (TextView)rootView.findViewById(R.id.textv);
            recentlistview.setAdapter(adapter);
            recentlistview.setEnabled(true);
            adapter.notifyDataSetChanged();
            //new AddusertoRecentListview().execute();
            rootView.findViewById(R.id.synccontact).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(contacts c:conlist.contacts){
                        AddContact(c);
                    }
                    Toast.makeText(context, "Successfully synced", Toast.LENGTH_SHORT).show();
                }
            });
            adapter.notifyDataSetChanged();
            return rootView;
        }
        void AddContact(contacts c){
            String DisplayName = c.name+" niki.ai test";
            String MobileNumber = c.phone;
            String HomeNumber = null;
            String WorkNumber = c.officePhone;
            String emailID = c.email;
            String company = null;
            String jobTitle = "developer";

            ArrayList <ContentProviderOperation> ops = new ArrayList < ContentProviderOperation > ();

            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            //------------------------------------------------------ Names
            if (DisplayName != null) {
                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(
                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                DisplayName).build());
            }

            //------------------------------------------------------ Mobile Number
            if (MobileNumber != null) {
                ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }

            //------------------------------------------------------ Home Numbers
            if (HomeNumber != null) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                        .build());
            }

            //------------------------------------------------------ Work Numbers
            if (WorkNumber != null) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                        .build());
            }

            //------------------------------------------------------ Email
            if (emailID != null) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .build());
            }

            //------------------------------------------------------ Organization
            if(company!=null)
            if (!company.equals("") && !jobTitle.equals("")) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                        .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                        .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                        .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                        .build());
            }

            // Asking the Contact provider to create a new contact
            try {
                context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        class GetNikicontacts extends AsyncTask<String, String, Void> {

            private ProgressDialog progressDialog = new ProgressDialog(context);
            InputStream inputStream = null;
            String result = "";
            String url_select = "http://private-b08d8d-nikitest.apiary-mock.com/contacts";
            protected void onPreExecute() {
                progressDialog.setMessage("Downloading your data...");
                progressDialog.show();
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface arg0) {
                        GetNikicontacts.this.cancel(true);
                        Toast.makeText(context, "loading contacts cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            protected Void doInBackground(String... params) {

                JSONParser jParser = new JSONParser();
                result = jParser.getstringFromUrl(url_select);

                return null;
            } //
            protected void onPostExecute(Void v) {

                result = result.substring(1, result.length() - 1);
                conlist = new Gson().fromJson(result, contactlist.class);
                for(contacts c:conlist.contacts){
                        //AddContact(c);
                    recent_user_list.add(c);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
               // Toast.makeText(context, "ccccccccc", Toast.LENGTH_SHORT).show();
                cp.getMapAsync(new OnMapReadyCallback() {
                    GoogleMap mMap;

                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        //Toast.makeText(context, "xxxxxxxxxxxx", Toast.LENGTH_SHORT).show();
                        mMap = googleMap;
                        mMap.clear();
                        int i=0;
                        for (contacts c : conlist.contacts) {
                            drawMarker(c.latitude, c.longitude, c.name, false,i++);
                        }
                        contacts c = conlist.contacts[0];
                        drawMarker(c.latitude, c.longitude, c.name, false,0);





                        // Setting a custom info window adapter for the google map
                        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                            // Use default InfoWindow frame
                            @Override
                            public View getInfoWindow(Marker arg0) {
                                return null;
                            }

                            // Defines the contents of the InfoWindow
                            @Override
                            public View getInfoContents(Marker arg0) {

                                // Getting view from the layout file info_window_layout
                                View v = getLayoutInflater(new Bundle()).inflate(R.layout.info_window, null);

                                // Getting the position from the marker
                                LatLng latLng = arg0.getPosition();
                                final contacts c = conlist.contacts[Integer.parseInt(arg0.getTitle())];
                                TextView nam = (TextView) v.findViewById(R.id.getnamecc);
                                TextView phone = (TextView) v.findViewById(R.id.getphonecc);
                                TextView email = (TextView) v.findViewById(R.id.getemailcc);
                                //TextView off = (TextView) v.findViewById(R.id.getofficenocc);

                                nam.setText(c.name);
                                phone.setText(c.phone);
                                email.setText(c.email);

                                v.findViewById(R.id.recent_item_imagecc).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel: " + c.phone));
                                        Toast.makeText(context, "tttt", Toast.LENGTH_SHORT).show();
                                        startActivity(callIntent);
                                    }
                                });
                               // off.setText(c.officePhone);


                                // Returning the view containing InfoWindow contents
                                return v;

                            }
                        });

                    }

                    private void drawMarker(double lat, double longi, String nam, boolean x,int id) {
                        //mMap.clear();
                        LatLng currentPosition = new LatLng(lat, longi);
                        if (x)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));
                         mMap.addMarker(new MarkerOptions()
                                .position(currentPosition)
                                .snippet(nam)).setTitle(id+"");
                    }


                });
            }
                // protected void onPostExecute(Void v)
        }


    }
        /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==1){


                return RecentChat.newInstance(position+1);

            }

            if(position==2){
                cp =new ContactsMap();

                return cp;
            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "about Developer";
                case 1:
                    return "All Contacts";
                case 2:
                    return "Contacts Map";
            }
            return null;
        }
    }
}
