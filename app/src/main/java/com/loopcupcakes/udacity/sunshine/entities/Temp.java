
package com.loopcupcakes.udacity.sunshine.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp implements Parcelable {

    @SerializedName("day")
    @Expose
    private Double day;
    @SerializedName("min")
    @Expose
    private Double min;
    @SerializedName("max")
    @Expose
    private Double max;
    @SerializedName("night")
    @Expose
    private Double night;
    @SerializedName("eve")
    @Expose
    private Double eve;
    @SerializedName("morn")
    @Expose
    private Double morn;

    /**
     *
     * @return
     *     The day
     */
    public Double getDay() {
        return day;
    }

    /**
     *
     * @param day
     *     The day
     */
    public void setDay(Double day) {
        this.day = day;
    }

    /**
     *
     * @return
     *     The min
     */
    public Double getMin() {
        return min;
    }

    /**
     *
     * @param min
     *     The min
     */
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     *
     * @return
     *     The max
     */
    public Double getMax() {
        return max;
    }

    /**
     *
     * @param max
     *     The max
     */
    public void setMax(Double max) {
        this.max = max;
    }

    /**
     *
     * @return
     *     The night
     */
    public Double getNight() {
        return night;
    }

    /**
     *
     * @param night
     *     The night
     */
    public void setNight(Double night) {
        this.night = night;
    }

    /**
     *
     * @return
     *     The eve
     */
    public Double getEve() {
        return eve;
    }

    /**
     *
     * @param eve
     *     The eve
     */
    public void setEve(Double eve) {
        this.eve = eve;
    }

    /**
     *
     * @return
     *     The morn
     */
    public Double getMorn() {
        return morn;
    }

    /**
     *
     * @param morn
     *     The morn
     */
    public void setMorn(Double morn) {
        this.morn = morn;
    }


    protected Temp(Parcel in) {
        day = in.readByte() == 0x00 ? null : in.readDouble();
        min = in.readByte() == 0x00 ? null : in.readDouble();
        max = in.readByte() == 0x00 ? null : in.readDouble();
        night = in.readByte() == 0x00 ? null : in.readDouble();
        eve = in.readByte() == 0x00 ? null : in.readDouble();
        morn = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (day == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(day);
        }
        if (min == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(min);
        }
        if (max == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(max);
        }
        if (night == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(night);
        }
        if (eve == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(eve);
        }
        if (morn == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(morn);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Temp> CREATOR = new Parcelable.Creator<Temp>() {
        @Override
        public Temp createFromParcel(Parcel in) {
            return new Temp(in);
        }

        @Override
        public Temp[] newArray(int size) {
            return new Temp[size];
        }
    };
}