package frameworkDevelopment.resources;

import serialization.pojo.AddPlace;
import serialization.pojo.Locations;

import java.util.List;

public class TestDataBuild {

    public AddPlace addPlacePayload(String name, String address, String language) {
        AddPlace addPlace = new AddPlace();
        Locations locations = new Locations();
        locations.setLat(-38.383494);
        locations.setLng(33.427362);
        addPlace.setAccuracy(50);
        addPlace.setAddress(address);
        addPlace.setLanguage(language);
        addPlace.setName(name);
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setWebsite("http://google.com");
        addPlace.setTypes(List.of(new String[]{"shoe park", "shop"}));
        addPlace.setLocation(locations);
        return addPlace;
    }

    public String deletePlacePayload(String placeId) {
        return "{\n" +
                "    \"place_id\":\"" + placeId + "\"\n" +
                "}";
    }
}
