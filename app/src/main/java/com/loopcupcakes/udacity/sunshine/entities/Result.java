
package com.loopcupcakes.udacity.sunshine.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result implements Parcelable {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private List<Forecast> forecast = new ArrayList<Forecast>();

    /**
     *
     * @return
     *     The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     *     The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     *
     * @return
     *     The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     *     The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * @return
     *     The message
     */
    public Double getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The message
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     *
     * @return
     *     The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     *
     * @param cnt
     *     The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     *
     * @return
     *     The forecast
     */
    public java.util.List<Forecast> getForecast() {
        return forecast;
    }

    /**
     *
     * @param forecast
     *     The forecast
     */
    public void setForecast(java.util.List<Forecast> forecast) {
        this.forecast = forecast;
    }


    protected Result(Parcel in) {
        city = (City) in.readValue(City.class.getClassLoader());
        cod = in.readString();
        message = in.readByte() == 0x00 ? null : in.readDouble();
        cnt = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            forecast = new ArrayList<Forecast>();
            in.readList(forecast, Forecast.class.getClassLoader());
        } else {
            forecast = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(city);
        dest.writeString(cod);
        if (message == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(message);
        }
        if (cnt == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(cnt);
        }
        if (forecast == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(forecast);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}