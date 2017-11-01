package com.bignerdranch.android.client;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import SharedFiles.EventResult;
import SharedFiles.Family;
import SharedFiles.FiltersAndSettings;
import SharedFiles.PersonResult;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private HashMap<Marker, EventResult> mMarkerEventResultHashMap = new HashMap<>();
    private HashMap<EventResult, Marker> mEventResultMarkerHashMap = new HashMap<>();
    private EventResult zoom_in = null;
    private View view;
    private Marker mSelectedMarker;
    private EventResult mCurrentEvent;


    private ArrayList<Polyline> mPolylines = new ArrayList<>();

    private static final String LIFE_STORY_LINES = "life story lines";
    private static final String FAMILY_TREE_LINES = "family tree lines";
    private static final String SPOUSE_LINES = "spouse lines";
    private static final String EVENT_KEY = "event_key";

    public MapFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        if(getArguments() != null) {
            String event_id = getArguments().getString(EVENT_KEY);
            zoom_in = Family.getInstance().getEventFromId(event_id);
        }
        view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //calls on map ready

        TextView person_info = (TextView) view.findViewById(R.id.map_text);
        person_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EventResult event = mMarkerEventResultHashMap.get(mSelectedMarker);
                Intent i = new Intent(getContext(), PersonActivity.class);
                i.putExtra(EVENT_KEY, event.getPersonID());
                startActivity(i);
            }
        });
        return view;

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        drawMarkers();

        if(zoom_in != null)
        {
            double latitude = Double.parseDouble(zoom_in.getLatitude());
            double longitude = Double.parseDouble(zoom_in.getLongitude());
            LatLng currentLocation = new LatLng(latitude, longitude);
            moveToCurrentLocation(currentLocation);


            ImageView img = null;
            img = (ImageView) view.findViewById(R.id.gender_icon);
            PersonResult person = Family.getInstance().getPersonfromId(zoom_in.getPersonID());

            if(person.getGender().equals("m")) {
                Drawable genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male).colorRes(R.color.male_icon).sizeDp(40);
                img.setImageDrawable(genderIcon);
            }
            else {
                Drawable genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female).colorRes(R.color.female_icon).sizeDp(40);
                img.setImageDrawable(genderIcon);
            }

            TextView textView = (TextView) view.findViewById(R.id.map_text);
            textView.setText(person.getFirstname() + " " + person.getLastname() + "\n" + zoom_in.getDescription().toLowerCase() + ": " + zoom_in.getCity() + ", " + zoom_in.getCountry() + " (" + zoom_in.getYear() + ")");
            createPolyLines(mEventResultMarkerHashMap.get(zoom_in));
        }
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mSelectedMarker = marker;
        ImageView img = null;
        img = (ImageView) view.findViewById(R.id.gender_icon);
        EventResult event = mMarkerEventResultHashMap.get(marker);

        for(int i = 0; i < mPolylines.size(); i++)
        {
            mPolylines.get(i).remove();
        }
        mPolylines.clear();
        createPolyLines(marker);
        PersonResult person = Family.getInstance().getPersonfromId(event.getPersonID());

        if(person.getGender().equals("m")) {
            Drawable genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male).colorRes(R.color.male_icon).sizeDp(40);
            img.setImageDrawable(genderIcon);
        }
        else {
            Drawable genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female).colorRes(R.color.female_icon).sizeDp(40);
            img.setImageDrawable(genderIcon);
        }

        TextView textView = (TextView) view.findViewById(R.id.map_text);
        textView.setText(person.getFirstname() + " " + person.getLastname() + "\n" +
        event.getDescription().toLowerCase() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
        return false;
//        textview.setText() overrides info  in text view.
    }
    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,4));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(4), 2000, null);
    }
//    private Color getRandomColor(){
//
//    }
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void onResume() {
        super.onResume();


        FiltersAndSettings settings = FiltersAndSettings.getInstance();
        String map_type = settings.getMapType();
        if(mMap != null)
        {
            mMarkerEventResultHashMap.clear();
            mEventResultMarkerHashMap.clear();
            mMap.clear();
            drawMarkers();
            switch (map_type) {
                case "Normal":
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case "Hybrid":
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                case "Sattelite":
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case "Terrain":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    break;
                default:
            }
        }
        for(Polyline line: mPolylines)
        {
            line.remove();
        }
        mPolylines.clear();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getActivity() instanceof MainActivity)
            inflater.inflate(R.menu.search_filter_set, menu);
        else
            inflater.inflate(R.menu.up_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.topItem:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.searchMenuSettings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.searchMenuFilter:
                intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
                break;
            case R.id.searchMenuItem:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            default:
        }
        return true;
    }

    public void createPolyLines(Marker marker)
    {
        mCurrentEvent = mMarkerEventResultHashMap.get(marker);
        FiltersAndSettings settings = FiltersAndSettings.getInstance();
        Family family = Family.getInstance();

        PersonResult person = family.getPersonfromId(mCurrentEvent.getPersonID());

        double latitude = Double.parseDouble(mCurrentEvent.getLatitude());
        double longitude = Double.parseDouble(mCurrentEvent.getLongitude());

        if(settings.isLifeStoryBool())
        {
            ArrayList<EventResult> all_events = family.getEventsFromPersonID(mCurrentEvent.getPersonID());
            for(int i = 0; i < all_events.size() - 1; i++)
            {
                double lat1 = Double.parseDouble(all_events.get(i).getLatitude());
                double long1 = Double.parseDouble(all_events.get(i).getLongitude());
                double lat2 = Double.parseDouble(all_events.get(i+1).getLatitude());
                double long2 = Double.parseDouble(all_events.get(i+1).getLongitude());

                Polyline polyline = mMap.addPolyline(new PolylineOptions().add(new LatLng(lat1, long1), new LatLng(lat2, long2)).width(5).color(settings.getIntColorFromMap(settings.getLifeStoryLinesColor())));
                mPolylines.add(polyline);
            }

//            Polyline polyline = mMap.addPolyline(new PolylineOptions().add(new LatLng(), new LatLng()).width().color(settings.getLifeStoryLinesColor()));

        }
        if(FiltersAndSettings.getInstance().isFamilyTreeBool())
        {
            createFamilyTreeLines(person, 6);
        }
        if(FiltersAndSettings.getInstance().isSpouseBool())
        {
            EventResult spouse_birth = family.getEarliestEvent(person.getSpouse());
            double spouse_lat = Double.parseDouble(spouse_birth.getLatitude());
            double spouse_long = Double.parseDouble(spouse_birth.getLongitude());
            Polyline polyline = mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude), new LatLng(spouse_lat, spouse_long)).width(5).color(settings.getIntColorFromMap(settings.getSpouseLineColor())));
            mPolylines.add(polyline);
        }
    }
    public void createFamilyTreeLines(PersonResult person, int width)
    {
        Family family = Family.getInstance();
        FiltersAndSettings settings = FiltersAndSettings.getInstance();

        PersonResult mother = Family.getInstance().getPersonfromId(person.getFather());
        PersonResult father = Family.getInstance().getPersonfromId(person.getMother());

        if(mother != null && father != null)
        {
            EventResult person_birth = family.getEarliestEvent(person.getPersonID());
            EventResult mother_birth = family.getEarliestEvent(mother.getPersonID());
            EventResult father_birth = family.getEarliestEvent(father.getPersonID());

            double mother_lat = Double.parseDouble(mother_birth.getLatitude());
            double mother_long = Double.parseDouble(mother_birth.getLongitude());
            double father_lat = Double.parseDouble(father_birth.getLatitude());
            double father_long = Double.parseDouble(father_birth.getLongitude());
            double person_lat;
            double person_long;
            if(width != 6) {
                person_lat = Double.parseDouble(person_birth.getLatitude());
                person_long = Double.parseDouble(person_birth.getLongitude());
            }
            else{
                person_lat = Double.parseDouble(mCurrentEvent.getLatitude());
                person_long = Double.parseDouble(mCurrentEvent.getLongitude());
            }

//            Polyline mother_child = mMap.addPolyline(new PolylineOptions().add(new LatLng(person_lat, person_long), new LatLng(mother_lat, mother_long))).width(5).color(settings.getIntColorFromMap(settings.getFamilyTreeLineColor()));
            Polyline mother_child = mMap.addPolyline(new PolylineOptions().add(new LatLng(person_lat, person_long), new LatLng(mother_lat, mother_long)).width(width).color(settings.getIntColorFromMap(settings.getFamilyTreeLineColor())));
            Polyline father_child = mMap.addPolyline(new PolylineOptions().add(new LatLng(person_lat, person_long), new LatLng(father_lat, father_long)).width(width).color(settings.getIntColorFromMap(settings.getFamilyTreeLineColor())));
            mPolylines.add(mother_child);
            mPolylines.add(father_child);
            createFamilyTreeLines(Family.getInstance().getPersonfromId(person.getMother()), width - 1);
            createFamilyTreeLines(Family.getInstance().getPersonfromId(person.getFather()), width - 1);
        }
    }

    public void drawMarkers()
    {
        FiltersAndSettings settings = FiltersAndSettings.getInstance();
        for(EventResult event: Family.getInstance().getAllEvents())
        {
            if(FiltersAndSettings.getInstance().getFilters().isEmpty() || FiltersAndSettings.getInstance().getBoolFromEventType(event.getDescription().toLowerCase())) {
                if(!settings.isFemaleBool())
                {
                    PersonResult person = Family.getInstance().getPersonfromId(event.getPersonID());
                    if(person.getGender().equals("f"))
                        continue;
                }
                if(!settings.isMaleBool())
                {
                    PersonResult person = Family.getInstance().getPersonfromId(event.getPersonID());
                    if(person.getGender().equals("m"))
                        continue;
                }
                if(!settings.isFatherSideBool())
                {
                    PersonResult user = Family.getInstance().getPersonfromId(Family.getInstance().getRegisterRequest().getPersonID());
                    if(Family.getInstance().isDescendant(user.getFather(), event.getPersonID())){
                        continue;
                    }
                    
                }
                if(!settings.isMotherSideBool())
                {
                    PersonResult user = Family.getInstance().getPersonfromId(Family.getInstance().getRegisterRequest().getPersonID());
                    if(Family.getInstance().isDescendant(user.getMother(), event.getPersonID())){
                        continue;
                    }
                }

                double latitude = Double.parseDouble(event.getLatitude());
                double longitude = Double.parseDouble(event.getLongitude());
//            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(getMarkerIcon(Family.getInstance().getColorFromEvent("#ff2299"))));
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.defaultMarker(Family.getInstance().getColorFromEvent(event.getDescription().toLowerCase()))));
                mEventResultMarkerHashMap.put(event, marker);
                mMarkerEventResultHashMap.put(marker, event);
            }
        }
    }
}



