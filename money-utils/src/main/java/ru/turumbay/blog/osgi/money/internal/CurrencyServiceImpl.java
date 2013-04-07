package ru.turumbay.blog.osgi.money.internal;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import ru.turumbay.blog.osgi.money.Currency;
import ru.turumbay.blog.osgi.money.CurrencyService;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Service(value = CurrencyService.class)
public class CurrencyServiceImpl implements CurrencyService{

    @Override
    public float getRate(Currency currency, Date date) {
        if (Currency.RUB == currency) return 1f;
        return getCbRates(date).get(currency.name());
    }

    @Override
    public float getRate(Currency currency) {
        return getRate(currency, new Date());
    }


    /**
     * Возвращает список курсов валют на указанную дату
     */
    private Map<String, Float> getCbRates(Date date){
        final Map<String, Float> rates = new HashMap<String, Float>();
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(getCbrHandler(rates));
            String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" +
                    new SimpleDateFormat("dd/MM/yyyy").format(date);
            xmlReader.parse(url);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return rates;
    }

    /**
     * SAX Parser for cbr.ru
     */
    private DefaultHandler getCbrHandler(final Map<String, Float> result){
        return new DefaultHandler() {
            String key = null;
            Float value = null;
            boolean keyStarted = false;
            boolean valueStarted = false;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equals("CharCode")) {
                    keyStarted = true;
                } else if (qName.equals("Value")) {
                    valueStarted = true;
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (keyStarted) {
                    key = new String(ch, start, length);
                    keyStarted = false;
                } else if (valueStarted) {
                    value = parseFloat(new String(ch, start, length));
                    valueStarted = false;
                }
                if (key != null & value != null) {
                    result.put(key, value);
                    key = null;
                    value = null;
                }
            }

            private Float parseFloat(String st) {
                try {
                    return df.parse(st).floatValue();
                } catch (ParseException e) {
                    throw new IllegalArgumentException(st);
                }
            }

            private DecimalFormat df = new DecimalFormat();

            {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator(',');
                symbols.setGroupingSeparator(' ');
                df.setDecimalFormatSymbols(symbols);
            }
        };
    }
}
