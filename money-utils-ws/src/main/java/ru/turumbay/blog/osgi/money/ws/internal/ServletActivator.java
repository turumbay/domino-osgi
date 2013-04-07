package ru.turumbay.blog.osgi.money.ws.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import ru.turumbay.blog.osgi.money.CurrencyService;


public class ServletActivator implements BundleActivator{
    private CurrencyService currencyService;
    private HttpService httpService;

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        ServiceReference currencyServiceRef = bundleContext.getServiceReference(CurrencyService.class.getName());
        currencyService = (CurrencyService) bundleContext.getService(currencyServiceRef);

        ServiceReference httpServiceRef = bundleContext.getServiceReference(HttpService.class.getName());
        httpService = (HttpService) bundleContext.getService(httpServiceRef);

        CurrencyRateServlet servlet = new CurrencyRateServlet();
        servlet.rateService = currencyService;
        httpService.registerServlet("/rates/USD", servlet, null, null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        httpService.unregister("/rates/USD");
    }
}
