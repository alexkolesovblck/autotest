package ru.bccomon;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestTools {
    public static final String RUS_UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String RUS_LOWER = RUS_UPPER.toLowerCase();
    private final static Logger LOGGER = Logger.getLogger(TestTools.class);

    public static String getCurrentDataTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static java.sql.Date getCurrentData() {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        return new java.sql.Date(timeStamp.getTime());
    }

    public static int compareDataTime(Timestamp actualData, LocalDateTime expectedData) {
        Objects.requireNonNull(actualData, "actualData is null");
        Objects.requireNonNull(expectedData, "expectedData is null");
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
        Date acDate = null;
        Date exDate = null;
        try {
            acDate = sdf.parse(actualData.toString());
            exDate = sdf.parse(Timestamp.valueOf(expectedData).toString());
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
        return acDate.compareTo(exDate);
    }

    public static String generateAlphabeticUpperString(int length) {
        return RandomStringUtils.randomAlphabetic(length).toUpperCase();
    }

    public static String generateAlphabeticLowerString(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }

    public static String generateAlphaNumericString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateNumber(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += String.valueOf(RandomUtils.nextInt(0, 10));
        }
        return result;
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static String generateUUIDasString() {
        return UUID.randomUUID().toString();
    }


    public static String generateINN() {
        return String.valueOf(RandomUtils.nextLong(11111, 99999) + RandomUtils.nextLong(11111, 99999));
    }

    public static URI getUriFromUrl(String urlStr) {
        Objects.requireNonNull(urlStr, "urlStr is null");
        URI uri = null;
        try {
            URL url = new URL(urlStr);
            uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                    url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    public static String getValueFromUrl(String urlStr, String nameParam) {
        Objects.requireNonNull(urlStr, "urlStr is null");
        URI uri = getUriFromUrl(urlStr);
        String value = uri.getQuery().replace(nameParam + "=", "");
        LOGGER.info(String.format("urlStr = %s,\n %s = %s", urlStr, nameParam, value));
        return value;
    }

    public static void sleepTime(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String generateRusLowerString(int length) {
        return nextString(length, RUS_LOWER);
    }

    public static String generateRusUpperString(int length) {
        return nextString(length, RUS_UPPER);
    }

    private static String nextString(int length, String string) {
        char[] symbols = string.toCharArray();
        char[] buf = new char[length];
        Random random = new Random();
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static <E extends Enum<E>> Set<E> strs2enums(Class<E> enumType, String[] values) {
        EnumSet<E> res = EnumSet.noneOf(enumType);
        if (values != null) {
            Arrays.stream(values).forEach(value -> res.add(Enum.valueOf(enumType, value)));
        }
        return res;
    }

    public static String[] enums2strs(Set<? extends Enum> enums) {
        return (enums == null || enums.isEmpty())
                ? new String[]{}
                : enums.stream().map(Enum::name).collect(Collectors.toSet()).toArray(new String[enums.size()]);
    }
}
