public class Geo {
    private double lat;
    private double lng;

    public Geo(Builder builder) {
        this.lat = builder.lat;
        this.lng = builder.lng;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    static class Builder{
        private double lat;
        private double lng;

        public Builder lat(double lat){
            this.lat = lat;
            return this;
        }

        public Builder lng(double lng){
            this.lng = lng;
            return this;
        }

        public Geo build() {
            return new Geo(this);
        }

    }
}