package com.exercise.UserDBClient.model;

import java.time.OffsetDateTime;

public class TrackInfo {

    private int ct;
    private OffsetDateTime dateTime;

    public TrackInfo(int ct, OffsetDateTime dateTime) {
        this.ct = ct;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TrackInfo{");
        sb.append("ct=").append(ct);
        sb.append(", dateTime=").append(dateTime);
        sb.append('}');
        return sb.toString();
    }
}
