package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Map;

public class Response {

    boolean success;
    long timestamp;
    String base;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    Map <String, Double> rates;
    Error error;

    public boolean getSuccess() {
        return success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBase() {
        return base;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public Error getError() {
        return error;
    }
}
