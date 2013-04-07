package ru.turumbay.blog.osgi.money;

public interface CurrencyService {
    float getRate(Currency currency, java.util.Date date) ;
    float getRate(Currency currency);
}
