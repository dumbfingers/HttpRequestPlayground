import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequestClient {

    private HttpRequestService requestService;

    public HttpRequestClient(String url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.requestService = retrofit.create(HttpRequestService.class);
    }

    public HttpRequestService getRequestService() {
        return requestService;
    }
}
