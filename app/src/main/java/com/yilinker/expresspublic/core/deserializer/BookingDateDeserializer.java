package com.yilinker.expresspublic.core.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jeico.
 */
public class BookingDateDeserializer implements JsonDeserializer<Date>
{

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try
        {
            return df.parse(json.getAsString());
        }
        catch (final java.text.ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
