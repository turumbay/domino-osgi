package ru.turumbay.blog.osgi.money.ws.internal;


import ru.turumbay.blog.osgi.money.Currency;
import ru.turumbay.blog.osgi.money.CurrencyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class CurrencyRateServlet extends HttpServlet {

    CurrencyService rateService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer writer = resp.getWriter();
        try {
            float rate = rateService.getRate(Currency.USD);
            writer.write("USD: " + rate);
            writer.flush();
        } finally {
            writer.close();
        }
    }

}
