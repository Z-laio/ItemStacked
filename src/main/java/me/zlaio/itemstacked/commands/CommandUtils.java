package me.zlaio.itemstacked.commands;

public class CommandUtils {

    public static String getStringFromArgs(int startArg, String[] args) {
        StringBuilder builder = new StringBuilder();

        for (int i = startArg; i < args.length; i++) {
            builder.append(args[i]);

            if (i != args.length - 1)
                builder.append(" ");

        }

        return builder.toString();
    }

    public static String getContentBetweenQuotes(String argument) {
        return argument.substring(1, argument.length() - 1);
    }

    public static boolean argumentHasValidQuotations(String argument) {
        boolean isWrappedInQuotes =
                argument.substring(0, 1).equals("\"")
                        && argument.substring(argument.length() - 1).equals("\"");

        return isWrappedInQuotes;
    }
}