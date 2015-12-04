
package io.leftshift.sample.parser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    @Expose
    public double speed;
    @SerializedName("deg")
    @Expose
    public int degree;

}
