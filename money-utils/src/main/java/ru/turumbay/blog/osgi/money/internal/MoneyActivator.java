package ru.turumbay.blog.osgi.money.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import ru.turumbay.blog.osgi.money.CurrencyService;

/**
 * Extension of the default OSGi bundle activator
 */
public final class MoneyActivator implements BundleActivator {
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    @Override
    public void start(BundleContext context) throws Exception {
        context.registerService(CurrencyService.class.getName(),new CurrencyServiceImpl(), null);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        // Don't need to unregister service. Framework do it for us
    }
}

