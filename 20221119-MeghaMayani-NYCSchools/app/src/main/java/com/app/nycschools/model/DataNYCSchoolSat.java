package com.app.nycschools.model;

public class DataNYCSchoolSat {
    String num_of_sat_test_takers;

    public String getNum_of_sat_test_takers() {
        return num_of_sat_test_takers;
    }

    public String getSat_critical_reading_avg_score() {
        return sat_critical_reading_avg_score;
    }

    public String getSat_math_avg_score() {
        return sat_math_avg_score;
    }

    public String getSat_writing_avg_score() {
        return sat_writing_avg_score;
    }

    public String getSchool_name() {
        return school_name;
    }

    String sat_critical_reading_avg_score;
    String sat_math_avg_score;
    String sat_writing_avg_score;
    String school_name;
}
