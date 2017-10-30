import com.google.gson.JsonObject;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.util.Scanner;

public class Main {

    public static final String PARAM_CONTENT_LENGTH = "Content_length";
    public static final String PARAM_URL = "Url";
    public static final String PARAM_STATUS_CODE = "Status_code";
    public static final String PARAM_DATE = "Date";
    public static final String PREFIX_HTTP = "http://";
    public static final String PREFIX_HTTPS = "https://";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printStartline();
        String input = scanner.nextLine();

        while (!input.equals("quit")) {
            if (isURLLegal(input)) {
                dispatchHttpRequest(input);
            }
            input = scanner.nextLine();
        }

    }

    private static boolean isURLLegal(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        String lowercase = url.toLowerCase();
        if (lowercase.startsWith(PREFIX_HTTP)
                || lowercase.toLowerCase().startsWith(PREFIX_HTTPS)) {
            return true;
        } else {
            System.out.println("Invalid input.\nThe website must start with \"http://\" or \"https://\"");
            printStartline();
        }

        return false;
    }

    private static void dispatchHttpRequest(String url) {
        HttpRequestClient client = new HttpRequestClient(url);
        Observable<Response<ResponseBody>> observable = client.getRequestService().getResponse(url);
        observable
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> buildResponse(url, response),
                        throwable -> System.out.println("Error " + throwable.toString()),
                        Main::printStartline);
    }

    private static void buildResponse(String url, Response<ResponseBody> response) {
        Headers headers = response.headers();
        String date = headers.get(PARAM_DATE);
        int statusCode = response.code();
        long contentLength = response.body() == null ? -1 : response.body().contentLength();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(PARAM_URL, url);
        jsonObject.addProperty(PARAM_STATUS_CODE, statusCode);
        if (contentLength >= 0) {
            jsonObject.addProperty(PARAM_CONTENT_LENGTH, contentLength);
        }
        jsonObject.addProperty(PARAM_DATE, date);
        System.out.println(jsonObject);
    }

    private static void printStartline() {
        System.out.println("Enter a website URL, then press \"Enter\" key to execute:\n(Enter quit to exit)");
    }
}
