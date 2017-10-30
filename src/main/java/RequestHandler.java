import com.google.gson.JsonObject;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;

class RequestHandler {
    static void dispatchHttpRequest(String url) {
        HttpRequestClient client = new HttpRequestClient(url);
        Observable<Response<ResponseBody>> observable = client.getRequestService().getResponse(url);
        observable
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> buildResponse(url, response),
                        throwable -> Util.printError(url, throwable.toString()),
                        Util::printEntryline);
    }

    private static void buildResponse(String url, Response<ResponseBody> response) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Params.PARAM_URL, url);
        jsonObject.addProperty(Params.PARAM_STATUS_CODE, response.code());
        if (response.body() != null
                && response.body().contentLength() >= 0) {
            jsonObject.addProperty(Params.PARAM_CONTENT_LENGTH, response.body().contentLength());
        }
        Headers headers = response.headers();
        jsonObject.addProperty(Params.PARAM_DATE, headers.get(Params.PARAM_DATE));
        System.out.println(jsonObject);
    }
}
