package io.avec.map.map;

import com.vaadin.addon.leaflet4vaadin.LeafletMap;
import com.vaadin.addon.leaflet4vaadin.controls.LayersControl;
import com.vaadin.addon.leaflet4vaadin.controls.LayersControlOptions;
import com.vaadin.addon.leaflet4vaadin.layer.groups.LayerGroup;
import com.vaadin.addon.leaflet4vaadin.layer.map.options.DefaultMapOptions;
import com.vaadin.addon.leaflet4vaadin.layer.map.options.MapOptions;
import com.vaadin.addon.leaflet4vaadin.layer.ui.marker.Marker;
import com.vaadin.addon.leaflet4vaadin.types.Icon;
import com.vaadin.addon.leaflet4vaadin.types.LatLng;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import io.avec.map.util.MapIcon;
import io.avec.map.data.group.LocationGroupRepository;
import io.avec.map.data.group.LocationGroup;
import io.avec.map.data.group.LocationGroupType;
import io.avec.map.data.place.Location;
import io.avec.map.main.MainView;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RouteAlias(value = "", layout = MainView.class) // default
@Route(value = "map2", layout = MainView.class)
@PageTitle("Map2")
@CssImport("./styles/views/map/map-view.css")
public class MapView2 extends Div {

    private final LatLng oslo = new LatLng(59.914800, 10.749178);
    private LeafletMap map;
    private final LocationGroupRepository locationGroupRepository;

    @SneakyThrows
    public MapView2(LocationGroupRepository locationGroupRepository) {
        this.locationGroupRepository = locationGroupRepository;
        setId("map-view");
        setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setMargin(false);

        map = getLeafletMap();

        layout.add(map);

        add(layout);

        addLayers();


    }

    private void addLayers() {
        LayersControl layersControl = createLayersControl();
        layersControl.addTo(map);
        LayerGroup hotelGroup = createLayersGroups(MapIcon.HOTEL, LocationGroupType.HOTEL);
        LayerGroup resturantGroup = createLayersGroups(MapIcon.RESTURANT, LocationGroupType.RESTAURANT);
        LayerGroup museumGroup = createLayersGroups(MapIcon.MUSEUM, LocationGroupType.MUSEUM);
        layersControl.addOverlay(hotelGroup, "Hotel");
        layersControl.addOverlay(resturantGroup, "Resturant");
        layersControl.addOverlay(museumGroup, "Museum");
    }

    private LayerGroup createLayersGroups(MapIcon mapIcon, LocationGroupType locationGroupType) {
        Icon hotelIcon = new Icon(mapIcon.getPath());

        // TODO need to have a parent in the future
        final List<LocationGroup> locationGroups = locationGroupRepository.findLocationGroupByLocationGroupType(locationGroupType);
        LayerGroup layerGroup = new LayerGroup();
        final LocationGroup locationGroup = locationGroups.get(0);// there is only one for now
        final List<Location> locations = locationGroup.getLocations();
        for (Location location: locations) {
           Marker marker = new Marker(LatLng.latlng(location.getLat(), location.getLon()));
           marker.setIcon(hotelIcon);
            final String date = location.getLocalDate().format(DateTimeFormatter.ISO_DATE);
            marker.setTitle("Title: " + date);
            marker.setTooltipContent("Tooltip: " + date);
            marker.bindPopup("Popup: " + date);
           marker.addTo(layerGroup);
        }

        return layerGroup;

    }


    private LayersControl createLayersControl() {
        // Initialize the layers control

        LayersControlOptions layersControlOptions = new LayersControlOptions();
        layersControlOptions.setCollapsed(false);
        LayersControl layersControl = new LayersControl(layersControlOptions);
        layersControl.addTo(map);
        return layersControl;
    }

    private LeafletMap getLeafletMap() {
        MapOptions options = new DefaultMapOptions();
        options.setCenter(oslo);
        options.setMinZoom(2);
        options.setMaxZoom(18);
        options.setZoom(12);

        map = new LeafletMap(options );
        // OSM: Uses Spherical Mercator projection aka EPSG:3857
        map.setBaseUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
        return map;
    }



}
