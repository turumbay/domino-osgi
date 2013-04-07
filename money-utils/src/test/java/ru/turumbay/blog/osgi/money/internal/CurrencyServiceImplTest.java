package ru.turumbay.blog.osgi.money.internal;


import org.junit.Before;
import org.junit.Test;
import ru.turumbay.blog.osgi.money.CurrencyService;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static ru.turumbay.blog.osgi.money.Currency.RUB;
import static ru.turumbay.blog.osgi.money.Currency.USD;

public class CurrencyServiceImplTest {

    private final CurrencyService service = new CurrencyServiceImpl();

    private Date foolsDay; // черный вторник 1994-04-11


    @Before
    public void setup(){
      Calendar calendar = Calendar.getInstance();
      calendar.set(2013,Calendar.APRIL,1);
      foolsDay = calendar.getTime();
    }

    @Test
    public void testGetRate(){
       float usdRate = service.getRate(USD, foolsDay);
       assertEquals(31.0834f , usdRate);
    }

    @Test
    public void testRubRate(){
        CurrencyService service = new CurrencyServiceImpl();
        float rubRate = service.getRate(RUB, foolsDay);
        assertEquals(1f , rubRate);
    }

}
