package travelsky.fulinpolice.mvplibrary.data.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import solin.mvplibrary.util.VerifyTool;

/**
 * Created by Solin on 2017/3/1.
 */

public class GsonResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return VerifyTool.notNullBean(adapter.read(jsonReader));
        } finally {
            value.close();
        }
    }
}
