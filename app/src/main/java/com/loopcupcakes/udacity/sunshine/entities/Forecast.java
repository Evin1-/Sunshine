
package com.loopcupcakes.udacity.sunshine.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast implements Parcelable {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Integer deg;
    @SerializedName("clouds")
    @Expose
    private Integer clouds;
    @SerializedName("snow")
    @Expose
    private Double snow;
    @SerializedName("rain")
    @Expose
    private Double rain;

    /**
     *
     * @return
     *     The dt
     */
    public Integer getDt() {
        return dt;
    }

    /**
     *
     * @param dt
     *     The dt
     */
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    /**
     *
     * @return
     *     The temp
     */
    public Temp getTemp() {
        return temp;
    }

    /**
     *
     * @param temp
     *     The temp
     */
    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    /**
     *
     * @return
     *     The pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     *
     * @param pressure
     *     The pressure
     */
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    /**
     *
     * @return
     *     The humidity
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     *
     * @param humidity
     *     The humidity
     */
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    /**
     *
     * @return
     *     The weather
     */
    public java.util.List<Weather> getWeather() {
        return weather;
    }

    /**
     *
     * @param weather
     *     The weather
     */
    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    /**
     *
     * @return
     *     The speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     *     The speed
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     *
     * @return
     *     The deg
     */
    public Integer getDeg() {
        return deg;
    }

    /**
     *
     * @param deg
     *     The deg
     */
    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    /**
     *
     * @return
     *     The clouds
     */
    public Integer getClouds() {
        return clouds;
    }

    /**
     *
     * @param clouds
     *     The clouds
     */
    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    /**
     *
     * @return
     *     The snow
     */
    public Double getSnow() {
        return snow;
    }

    /**
     *
     * @param snow
     *     The snow
     */
    public void setSnow(Double snow) {
        this.snow = snow;
    }

    /**
     *
     * @return
     *     The rain
     */
    public Double getRain() {
        return rain;
    }

    /**
     *
     * @param rain
     *     The rain
     */
    public void setRain(Double rain) {
        this.rain = rain;
    }


    protected Forecast(Parcel in) {
        dt = in.readByte() == 0x00 ? null : in.readInt();
        temp = (Temp) in.readValue(Temp.class.getClassLoader());
        pressure = in.readByte() == 0x00 ? null : in.readDouble();
        humidity = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            weather = new ArrayList<Weather>();
            in.readList(weather, Weather.class.getClassLoader());
        } else {
            weather = null;
        }
        speed = in.readByte() == 0x00 ? null : in.readDouble();
        deg = in.readByte() == 0x00 ? null : in.readInt();
        clouds = in.readByte() == 0x00 ? null : in.readInt();
        snow = in.readByte() == 0x00 ? null : in.readDouble();
        rain = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dt == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(dt);
        }
        dest.writeValue(temp);
        if (pressure == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(pressure);
        }
        if (humidity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(humidity);
        }
        if (weather == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(weather);
        }
        if (speed == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(speed);
        }
        if (deg == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(deg);
        }
        if (clouds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(clouds);
        }
        if (snow == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(snow);
        }
        if (rain == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(rain);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };
}