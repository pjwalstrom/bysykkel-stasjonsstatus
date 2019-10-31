package com.pj;

import java.util.Objects;

public class Station {
    private String id;
    private String name;
    private int locksAvailable;
    private int bikesAvailable;

    public Station(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setLocksAvailable(int locksAvailable) {
        this.locksAvailable = locksAvailable;
    }

    public void setBikesAvailable(int bikesAvailable) {
        this.bikesAvailable = bikesAvailable;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Station{");
        sb.append("name='").append(name).append('\'');
        sb.append(", locksAvailable=").append(locksAvailable);
        sb.append(", bikesAvailable=").append(bikesAvailable);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return locksAvailable == station.locksAvailable &&
                bikesAvailable == station.bikesAvailable &&
                id.equals(station.id) &&
                Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, locksAvailable, bikesAvailable);
    }
}
