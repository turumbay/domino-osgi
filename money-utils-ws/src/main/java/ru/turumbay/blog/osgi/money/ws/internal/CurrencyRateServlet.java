package ru.turumbay.blog.osgi.money.ws.internal;


import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import ru.turumbay.blog.osgi.money.Currency;
import ru.turumbay.blog.osgi.money.CurrencyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Component
public class CurrencyRateServlet extends HttpServlet {

    @Reference
    CurrencyService rateService;

    @Reference
    HttpService httpService;

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

    protected void activate(ComponentContext ctx) throws ServletException, NamespaceException {
       httpService.registerServlet("/rates/USD", this, null, null);
    }

    protected void deactivate(ComponentContext ctx) {
       httpService.unregister("/rates/USD");
    }
}
