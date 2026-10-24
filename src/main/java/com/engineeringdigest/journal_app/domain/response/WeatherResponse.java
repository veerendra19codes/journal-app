package com.engineeringdigest.journal_app.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    public class Current{
        public int temperature;
        public int feelslike;
    }
}

