package com.gzy.quickapi.weather.bean;

class Realtime {
    String status;
    double o3;
    double co;
    double temperature;
    double pm10;
    String skycon;
    double cloudrate;
    Precipitation precipitation;
    double dswrf;
    double visibility;
    double humidity;
    double so2;
    Ultraviolet ultraviolet;
    double pres;
    int aqi;
    int pm25;
    double no2;
    double apparent_temperature;
    Comfort comfort;
    Wind wind;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public String getSkycon() {
        return skycon;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

    public double getCloudrate() {
        return cloudrate;
    }

    public void setCloudrate(double cloudrate) {
        this.cloudrate = cloudrate;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Precipitation precipitation) {
        this.precipitation = precipitation;
    }

    public double getDswrf() {
        return dswrf;
    }

    public void setDswrf(double dswrf) {
        this.dswrf = dswrf;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSo2() {
        return so2;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public Ultraviolet getUltraviolet() {
        return ultraviolet;
    }

    public void setUltraviolet(Ultraviolet ultraviolet) {
        this.ultraviolet = ultraviolet;
    }

    public double getPres() {
        return pres;
    }

    public void setPres(double pres) {
        this.pres = pres;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public double getNo2() {
        return no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getApparent_temperature() {
        return apparent_temperature;
    }

    public void setApparent_temperature(double apparent_temperature) {
        this.apparent_temperature = apparent_temperature;
    }

    public Comfort getComfort() {
        return comfort;
    }

    public void setComfort(Comfort comfort) {
        this.comfort = comfort;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    static class Precipitation {
        Nearest nearest;
        Local local;

        public Nearest getNearest() {
            return nearest;
        }

        public void setNearest(Nearest nearest) {
            this.nearest = nearest;
        }

        public Local getLocal() {
            return local;
        }

        public void setLocal(Local local) {
            this.local = local;
        }
    }

    static class Nearest {
        String status;
        double distance;
        double intensity;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public double getIntensity() {
            return intensity;
        }

        public void setIntensity(double intensity) {
            this.intensity = intensity;
        }
    }

    static class Local {
        String status;
        double intensity;
        String datasource;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getIntensity() {
            return intensity;
        }

        public void setIntensity(double intensity) {
            this.intensity = intensity;
        }

        public String getDatasource() {
            return datasource;
        }

        public void setDatasource(String datasource) {
            this.datasource = datasource;
        }
    }

    static class Ultraviolet {
        double index;
        String desc;

        public double getIndex() {
            return index;
        }

        public void setIndex(double index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    static class Comfort {
        int index;
        String desc;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    static class Wind {
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
}

