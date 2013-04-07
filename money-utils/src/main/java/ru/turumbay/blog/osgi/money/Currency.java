package ru.turumbay.blog.osgi.money;

public enum Currency{ USD(840),  RUB(643), EUR(978);

    /** код валюты ISO_4217  **/
    public final int code;

    Currency(int code) {
        this.code = code;
    }
}
