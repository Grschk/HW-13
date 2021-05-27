public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo cords;

    public Address(Builder builder) {
        this.street = builder.street;
        this.suite = builder.suite;
        this.city = builder.city;
        this.zipcode = builder.zipcode;
        this.cords = builder.geo;
    }
    @Override
    public String toString() {
        return "{" +
                "street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", geo=" + cords +
                '}';
    }

    static class Builder{
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public Builder street(String street) {
            this.street = street;
            return this;
        }
        public Builder suite(String suite) {
            this.suite = suite;
            return this;
        }
        public Builder city(String city) {
            this.city = city;
            return this;
        }
        public Builder zipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }
        public Builder geo(Geo geo) {
            this.geo = geo;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}