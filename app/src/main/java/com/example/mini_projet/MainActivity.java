package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private String jsonData;

    //Liste des villes
    List<String> villes = new ArrayList<>();

    //Liste des points sur la carte
    private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        //récupération de la référence de la map
        map = (MapView) findViewById(R.id.MAP);
        map.setTileSource(TileSourceFactory.MAPNIK);

        requestPermissionsIfNecessary(new String[] {
                // if you need to show the current location, uncomment the line below
                // Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        //Récupération de la liste des villes de l'api
        String requete2 = "https://api.jcdecaux.com/vls/v3/contracts?apiKey=7886a12c53604b2668a08582a04795afcc9375b0";
        GetData gt = new GetData();
        try {
            jsonData = gt.execute(requete2).get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        try {
            JSONArray json = new JSONArray(jsonData);

            for (int i=0; i < json.length(); i++)
            {
                try {
                    JSONObject obj = json.getJSONObject(i);
                    String ville = obj.getString("name");
                    villes.add(ville);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        //Récupération des stations de chaques villes
        for (String ville: villes) {
            try {
                String url = "https://api.jcdecaux.com/vls/v1/stations?contract="+ ville +"&apiKey=7886a12c53604b2668a08582a04795afcc9375b0";

                String jsonLocation = null;
                GetData gl = new GetData();
                try {
                    jsonLocation = gl.execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                JSONArray json = new JSONArray(jsonLocation);
                for (int i = 0; i < json.length(); ++i){
                    try {
                        JSONObject object = json.getJSONObject(i);
                        String adresse = object.getString("address");
                        String nbBike = Integer.toString(object.getInt("bike_stands"));
                        JSONObject position = object.getJSONObject("position");
                        Double latitude = position.getDouble("lat");
                        Double longitude = position.getDouble("lng");
                        items.add(new OverlayItem(adresse, nbBike, new GeoPoint(latitude,longitude))); // Lat/Lon decimal degrees
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Points sur la map
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, ctx);


        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        //zoom sur la map initiale
        IMapController mapController = map.getController();
        mapController.setZoom(15);
        GeoPoint startPoint = new GeoPoint(48.8583, 2.2944);
        mapController.setCenter(startPoint);
    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        //map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        //map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}
