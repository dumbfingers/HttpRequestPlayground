import com.google.gson.JsonObject;
import io.reactivex.annotations.NonNull;

public class Util {

    public static String addTrailingSlash(@NonNull String url) {
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        return url;
    }

    public static boolean isURLLegal(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        String urlLowercase = url.toLowerCase();
        if (urlLowercase.startsWith(Params.PREFIX_HTTP)
                || urlLowercase.toLowerCase().startsWith(Params.PREFIX_HTTPS)) {
            return true;
        }

        return false;
    }

    public static void printEntryline() {
        System.out.println("Enter a website URL, then press \"Enter\" key to execute:\n(Enter quit to exit)");
    }

    public static void printError(String url, @NonNull String error) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Params.PARAM_URL, url);
        jsonObject.addProperty(Params.PARAM_ERROR, error);
        System.out.println(jsonObject);
    }
}
