package com.seyijs.springbootmongodb.collection;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Address{

    private String address1;
    private String address2;
    private String city;
}