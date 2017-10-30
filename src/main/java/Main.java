import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Util.printEntryline();
        String input = scanner.nextLine();

        while (!input.equals("quit")) {
            if (Util.isURLLegal(input)) {
                RequestHandler.dispatchHttpRequest(Util.addTrailingSlash(input));
            } else {
                System.out.println("Invalid input.\nThe website must start with \"http://\" or \"https://\"");
                Util.printError(input, "invalid url");
                Util.printEntryline();
            }
            input = scanner.nextLine();
        }

    }
}
