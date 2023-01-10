package org.example.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public Courier basicCourier() {
        return new Courier("kappa", "qwerty123", "pride ");

    }


    public Courier getRandomCourier() {
        return new Courier(RandomStringUtils.randomAlphanumeric(7), RandomStringUtils.randomAlphanumeric(7), RandomStringUtils.randomAlphanumeric(7));

    }
}