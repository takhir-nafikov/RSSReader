package com.takhir.rssreader.requests;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ServiceGenerator {

    private String url;
    private Retrofit retrofit;
    private RSSApi rssApi;

    private ServiceGenerator(){}

    public RSSApi getRssApi() {
        return rssApi;
    }

    public static Builder newBuilder() {
        return new ServiceGenerator().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder setUrl(String url) {
            ServiceGenerator.this.url = url;

            return this;
        }

        public Builder buildRetrofit() {
            ServiceGenerator.this.retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            return this;
        }

        public Builder setRSSApi() {
            ServiceGenerator.this.rssApi = retrofit.create(RSSApi.class);

            return this;
        }

        public ServiceGenerator build() {
            return ServiceGenerator.this;
        }
    }
}
