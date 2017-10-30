import com.google.gson.JsonObject;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printEntryline();
        String input = scanner.nextLine();

        while (!input.equals("quit")) {
            if (isURLLegal(input)) {
                dispatchHttpRequest(addTrailingSlash(input));
            }
            input = scanner.nextLine();
        }

    }

    private static boolean isURLLegal(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        String urlLowercase = url.toLowerCase();
        if (urlLowercase.startsWith(Params.PREFIX_HTTP)
                || urlLowercase.toLowerCase().startsWith(Params.PREFIX_HTTPS)) {
            return true;
        } else {
            System.out.println("Invalid input.\nThe website must start with \"http://\" or \"https://\"");
            printError(url, "invalid url");
            printEntryline();
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
                        throwable -> printError(url, throwable.toString()),
                        Main::printEntryline);
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

    private static void printEntryline() {
        System.out.println("Enter a website URL, then press \"Enter\" key to execute:\n(Enter quit to exit)");
    }

    private static void printError(String url, @NonNull String error) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Params.PARAM_URL, url);
        jsonObject.addProperty(Params.PARAM_ERROR, error);
        System.out.println(jsonObject);
    }

    private static String addTrailingSlash(@NonNull String url) {
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        return url;
    }
}
