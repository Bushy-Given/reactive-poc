package com.rank.reactive.poc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import java.util.List;

public class Provider {
    public int id;
    public String name;

    public Provider() {
    }

    public Provider(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonIgnore
    public Provider getProvider(int id) {
        return providerList.get(id);
    }

    @JsonIgnore
    public List<Provider> getAllProviders() {
        return providerList;
    }

    @JsonIgnore
    public Provider addProvider(Provider provider) {
        providerList.add(provider);
        return provider;
    }

    static List<Provider> providerList = Lists.newArrayList(
            new Provider(0, "Playzido"),
            new Provider(1, "Pragmatic"),
            new Provider(2, "Netent"),
            new Provider(3, "Redtiger"),
            new Provider(4, "PlayTech Ims"),
            new Provider(5, "Novomatic")
    );

}
