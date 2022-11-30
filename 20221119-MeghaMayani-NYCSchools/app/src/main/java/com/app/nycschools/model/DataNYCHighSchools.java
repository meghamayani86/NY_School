package com.app.nycschools.model;

public class DataNYCHighSchools {

    // constructor to initialize variable
    public DataNYCHighSchools(String dbn, String school_name) {
        this.dbn = dbn;
        this.school_name = school_name;
    }

    String dbn;

    // getter and setter methods
    // for school name variable
    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    // getter methods
    // for dbn string for pass next fragment variable
    public String getDbn() {
        return dbn;
    }

    String school_name;
}
