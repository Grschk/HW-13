public class Company {
    private String name;
    private String catchPhrase;
    private String bs;

    public Company(Builder builder) {
        this.name = builder.name;
        this.catchPhrase = builder.catchPhrase;
        this.bs = builder.bs;
    }

    static class Builder{
        private String name;
        private String catchPhrase;
        private String bs;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder catchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
            return this;
        }

        public Builder bs(String bs) {
            this.bs = bs;
            return this;
        }

        public Company build() {
            return new Company(this);
        }
    }
    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", catchPhrase='" + catchPhrase + '\'' +
                ", bs='" + bs + '\'' +
                '}';
    }
}