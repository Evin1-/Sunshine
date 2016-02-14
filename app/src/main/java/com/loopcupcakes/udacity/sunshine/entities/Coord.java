
package com.loopcupcakes.udacity.sunshine.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord implements Parcelable {

    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;

    /**
     *
     * @return
     *     The lon
     */
    public Double getLon() {
        return lon;
    }

    /**
     *
     * @param lon
     *     The lon
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    /**
     *
     * @return
     *     The lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     *     The lat
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }


    protected Coord(Parcel in) {
        lon = in.readByte() == 0x00 ? null : in.readDouble();
        lat = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (lon == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(lon);
        }
        if (lat == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(lat);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel in) {
            return new Coord(in);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[size];
        }
    };
}