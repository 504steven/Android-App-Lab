package com.wgc.labthree;


import android.Manifest;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;


import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment{
    private View view;
    private SupportMapFragment supportMapFragment;
    private GoogleMap gMap;
    private PlacesClient placesClient;
    private final String FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private boolean locationPermissionGranted = false;
    private final int PERMISSIONS_REQUEST_CODE = 1111;
    List<String> list;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_map, container, false);
        view.findViewById(R.id.list_cafe).setVisibility(View.GONE);
        view.findViewById(R.id.list_cafe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListCafeActivity.class);
                intent.putExtra("nearbyCafes", list.toArray(new String[list.size()]));
                v.setVisibility(View.GONE);
                startActivity(intent);
            }
        });

        isAllServiceAvailable();
//            return view;
//        }

        getLocationPermission();

        initMap();

        // set up google places api
        setupGooglePlacesAPI();


        return view;
    }

    private boolean isAllServiceAvailable() {
        String msg = isGoolgeServiceAvailable();
        msg += isNetworkAvailable();
        System.out.println("XXXXXXXXXXXXXX check all service  XXXXXXXXXXXX::" + msg);
        if(msg.equals("")) {
            return true;
        }else {
            snackbarMessage(msg);
            return false;
        }
    }

    private String isGoolgeServiceAvailable() {

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
//        System.out.println("XXXXXXXXXXXXXX set up places api next  yyyyyyyyyyyy::" + available);
        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            return "";
        } else {
//            if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //an error occured but we can resolve it
            return "google play service is not available";
        }
    }

    private String isNetworkAvailable() {
        ConnectivityManager connectivityManager = ((ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE ));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return "";
        }
        return "NO Network Connection";
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), FINE_LOCATION_PERMISSION)
                == PackageManager.PERMISSION_GRANTED ) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{FINE_LOCATION_PERMISSION}, PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                    return;
                }
            }
        }
        snackbarMessage("GPS Location Service is NOT permitted");
    }

    private void snackbarMessage(String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        System.out.println("XXXXXXXXXXXXXX set up places api next  yyyyyyyyyyyy::" + snackbar);
        snackbar.show();
    }

    private void initMap() {
        supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        if(supportMapFragment == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            supportMapFragment = new SupportMapFragment();
            fragmentTransaction.replace(R.id.map, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
                try {
                    System.out.println("XXXXXXXXXXXXXX   get location");
                    if(locationPermissionGranted) {
                        Task loc = fusedLocationProviderClient.getLastLocation();
                        loc.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()) {
                                    Location curLoc = (Location)task.getResult();
                                    gMap.moveCamera( CameraUpdateFactory.newLatLngZoom( new LatLng(curLoc.getLatitude(), curLoc.getLongitude()), 15f));
                                }else {
                                    snackbarMessage("get GPS Location failed");
                                }
                            }
                        });
                    }
                }catch (Exception e) {

                }
                gMap.setMyLocationEnabled(true);
                gMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
        });
    }

    private void setupGooglePlacesAPI() {
//        System.out.println("XXXXXXXXXXXXXX set up places api");
        if(!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), "AIzaSyC1IrYbAWHptUguOZO7ZTdKkuF3CLiTun4");
            placesClient = Places.createClient(getActivity());
        }
        AutocompleteSupportFragment autocompleteSupportFragment =  (AutocompleteSupportFragment)getChildFragmentManager().findFragmentById(R.id.g_places_search);
//        System.out.println("XXXXXXXXXXXXXX" + autocompleteSupportFragment);
//        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME,  Place.Field.ADDRESS_COMPONENTS));
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG ));
        autocompleteSupportFragment.setHint("enter a cafe name");
        autocompleteSupportFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // mark selected place
//                System.out.println("XXXXXXXXXXXXXX" + place.getLatLng());
                MarkerOptions markerOptions = new MarkerOptions().position(place.getLatLng()).title(place.getName());
                gMap.addMarker(markerOptions);
                gMap.moveCamera( CameraUpdateFactory.newLatLngZoom( place.getLatLng(), 15f));
                nearbyCafeSearch(place.getLatLng());
                view.findViewById(R.id.list_cafe).setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
    }

    private void nearbyCafeSearch(LatLng latLng){
        LatLngBounds latLngBounds = LatLngBounds.builder().include(latLng).build();
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        RectangularBounds bounds = RectangularBounds.newInstance( latLngBounds);
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                                                        .setLocationBias(bounds)
                                                        .setTypeFilter(TypeFilter.ESTABLISHMENT)
                                                        .setSessionToken(token)
                                                        .setQuery("cafe")
                                                        .build();

        placesClient.findAutocompletePredictions(request)
                            .addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
                                @Override
                                public void onSuccess(FindAutocompletePredictionsResponse response) {
                                    list = new LinkedList();
                                    for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                                        prediction.getPlaceId();
                                        list.add(prediction.getPrimaryText(null).toString());
                                    }
//                                    System.out.println("XXXXXXXXXXXXXX nearby place" + sb.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                    }
                                });
    }

//    private void setupAutocompleteTextview() {
////        ArrayAdapter<Place.Field> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME,  Place.Field.ADDRESS_COMPONENTS) );
//        String[] COUNTRIES = new String[] {"Belgium", "France", "Italy", "Germany", "Spain"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.places_autocomplete_edit_text);
//        autoCompleteTextView.setAdapter(adapter);
//    }

}
