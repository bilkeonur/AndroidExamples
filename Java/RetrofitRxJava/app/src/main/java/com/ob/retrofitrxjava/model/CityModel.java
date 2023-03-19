package com.ob.retrofitrxjava.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CityModel {

    @SerializedName("code")
    int code;

    @SerializedName("data")
    Cities data;

    public int getCode() {
        return code;
    }

    public Cities getData() {
        return data;
    }

    public class Cities {
        @SerializedName("cities")
        List<City> cities;

        public List<City> getCities() {
            return cities;
        }
    }

    public static class City {

        @SerializedName("id")
        int id;

        @SerializedName("name")
        String name;

        @SerializedName("lat")
        Double lat;

        @SerializedName("lon")
        Double lon;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Double getLat() {
            return lat;
        }

        public Double getLon() {
            return lon;
        }
    }
}
