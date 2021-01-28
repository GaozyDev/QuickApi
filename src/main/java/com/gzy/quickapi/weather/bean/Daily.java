package com.gzy.quickapi.weather.bean;

import java.util.List;

class Daily {
    String status;
    List<Comfort> comfort;
    List<Skycon_20h_32h> skycon_20h_32h;
    List<Temperature> temperature;
    List<Dswrf> dswrf;
    List<Cloudrate> cloudrate;
    List<Aqi> aqi;
    List<Skycon> skycon;
    List<Visibility> visibility;
    List<Humidity> humidity;
    List<Astro> astro;
    List<ColdRisk> coldRisk;
    List<Ultraviolet> ultraviolet;
    List<Pm25> pm25;
    List<Dressing> dressing;
    List<CarWashing> carWashing;
    List<Precipitation> precipitation;
    List<Wind> wind;
    List<Skycon_08h_20h> skycon_08h_20h;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Comfort> getComfort() {
        return comfort;
    }

    public void setComfort(List<Comfort> comfort) {
        this.comfort = comfort;
    }

    public List<Skycon_20h_32h> getSkycon_20h_32h() {
        return skycon_20h_32h;
    }

    public void setSkycon_20h_32h(List<Skycon_20h_32h> skycon_20h_32h) {
        this.skycon_20h_32h = skycon_20h_32h;
    }

    public List<Temperature> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Temperature> temperature) {
        this.temperature = temperature;
    }

    public List<Dswrf> getDswrf() {
        return dswrf;
    }

    public void setDswrf(List<Dswrf> dswrf) {
        this.dswrf = dswrf;
    }

    public List<Cloudrate> getCloudrate() {
        return cloudrate;
    }

    public void setCloudrate(List<Cloudrate> cloudrate) {
        this.cloudrate = cloudrate;
    }

    public List<Aqi> getAqi() {
        return aqi;
    }

    public void setAqi(List<Aqi> aqi) {
        this.aqi = aqi;
    }

    public List<Skycon> getSkycon() {
        return skycon;
    }

    public void setSkycon(List<Skycon> skycon) {
        this.skycon = skycon;
    }

    public List<Visibility> getVisibility() {
        return visibility;
    }

    public void setVisibility(List<Visibility> visibility) {
        this.visibility = visibility;
    }

    public List<Humidity> getHumidity() {
        return humidity;
    }

    public void setHumidity(List<Humidity> humidity) {
        this.humidity = humidity;
    }

    public List<Astro> getAstro() {
        return astro;
    }

    public void setAstro(List<Astro> astro) {
        this.astro = astro;
    }

    public List<ColdRisk> getColdRisk() {
        return coldRisk;
    }

    public void setColdRisk(List<ColdRisk> coldRisk) {
        this.coldRisk = coldRisk;
    }

    public List<Ultraviolet> getUltraviolet() {
        return ultraviolet;
    }

    public void setUltraviolet(List<Ultraviolet> ultraviolet) {
        this.ultraviolet = ultraviolet;
    }

    public List<Pm25> getPm25() {
        return pm25;
    }

    public void setPm25(List<Pm25> pm25) {
        this.pm25 = pm25;
    }

    public List<Dressing> getDressing() {
        return dressing;
    }

    public void setDressing(List<Dressing> dressing) {
        this.dressing = dressing;
    }

    public List<CarWashing> getCarWashing() {
        return carWashing;
    }

    public void setCarWashing(List<CarWashing> carWashing) {
        this.carWashing = carWashing;
    }

    public List<Precipitation> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(List<Precipitation> precipitation) {
        this.precipitation = precipitation;
    }

    public List<Wind> getWind() {
        return wind;
    }

    public void setWind(List<Wind> wind) {
        this.wind = wind;
    }

    public List<Skycon_08h_20h> getSkycon_08h_20h() {
        return skycon_08h_20h;
    }

    public void setSkycon_08h_20h(List<Skycon_08h_20h> skycon_08h_20h) {
        this.skycon_08h_20h = skycon_08h_20h;
    }

    static class Comfort {
        String index;
        String desc;
        String datetime;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

    static class Skycon_20h_32h {
        String date;
        String value;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class Temperature {
        String date;
        double max;
        double avg;
        double min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }
    }

    static class Dswrf {
        String date;
        double max;
        double avg;
        double min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }
    }

    static class Cloudrate {
        String date;
        double max;
        double avg;
        double min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }
    }

    static class Aqi {
        String date;
        int max;
        double avg;
        int min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    static class Skycon {
        String date;
        String value;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class Visibility {
        String date;
        double max;
        double avg;
        double min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }
    }

    static class Humidity {
        String date;
        double max;
        double avg;
        double min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }
    }

    static class Astro {
        String date;
        Sunset sunset;
        Sunrise sunrise;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Sunset getSunset() {
            return sunset;
        }

        public void setSunset(Sunset sunset) {
            this.sunset = sunset;
        }

        public Sunrise getSunrise() {
            return sunrise;
        }

        public void setSunrise(Sunrise sunrise) {
            this.sunrise = sunrise;
        }
    }

    static class Sunset {
        String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    static class Sunrise {
        String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    static class ColdRisk {
        String index;
        String desc;
        String datetime;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

    static class Ultraviolet {
        String index;
        String desc;
        String datetime;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

    static class Pres {
        String date;
        double max;
        double avg;
        double min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }
    }

    static class Pm25 {
        String date;
        int max;
        double avg;
        int min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    static class Dressing {
        String index;
        String desc;
        String datetime;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

    static class CarWashing {
        String index;
        String desc;
        String datetime;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

    static class Precipitation {
        String date;
        double max;
        double avg;
        double min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }
    }

    static class Wind {
        String date;
        WindValue max;
        WindValue avg;
        WindValue min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public WindValue getMax() {
            return max;
        }

        public void setMax(WindValue max) {
            this.max = max;
        }

        public WindValue getAvg() {
            return avg;
        }

        public void setAvg(WindValue avg) {
            this.avg = avg;
        }

        public WindValue getMin() {
            return min;
        }

        public void setMin(WindValue min) {
            this.min = min;
        }
    }

    static class WindValue {
        double direction;
        double speed;

        public double getDirection() {
            return direction;
        }

        public void setDirection(double direction) {
            this.direction = direction;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }

    static class Skycon_08h_20h {
        String date;
        String value;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}


