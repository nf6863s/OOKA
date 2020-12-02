package IO;

import Runtime.Laufzeitumgebung;
import java.util.Scanner;

public class CLI {
    private Scanner sc;
    private Laufzeitumgebung lzu;

    public CLI() {
        this.sc = new Scanner(System.in);
    }

    public void startCLI() {
        System.out.println("Starting Runtime Environment...");
        this.lzu = new Laufzeitumgebung();
        boolean running = true;
        System.out.println("Runtime Environment started. Ready for deployment! Type !help for Command-List.");

        while(running) {
            // Get input from command line and remove all leading, trailing and multiple whitespaces
            String input = sc.nextLine();
            input = input.trim().replaceAll(" +", " ");

            // separate the input into command (i.e. !add) and param (i.e. C:/Test/Jar/TestClass.jar)
            String[] separated = separate(input);
            String cmd = separated[0];
            // if no param was given, this value is null
            String param = separated[1];

            // init id with dummy value
            int id = -999;
            try {
                // if param was set and is convertible to integer, convert it into id.
                // Otherwise keep the dummy value for id
                param = param.trim();
                id = Integer.parseInt(param);
            } catch (NumberFormatException | NullPointerException ignored) {}

            // check if a param, an id or the 'all' param was set
            // if one is set, wrongParam is false, if non are set, wrongParam is true
            boolean wrongParam = param == null || (id == -999 && !param.equalsIgnoreCase("all"));

            switch (cmd) {
                case "!help":
                    System.out.println(getCommandList());
                    break;
                case "!add":
                    if (param == null) {
                        System.out.println("Missing parameter <Component-Path>!");
                    } else {
                        System.out.println(lzu.addComponent(param.toString()));
                    }
                    break;
                case "!status":
                    System.out.println(lzu.getStatus());
                    break;
                case "!run":
                    if (wrongParam) {
                        System.out.println("Missing or non-numerical parameter <Component-ID>!");
                    } else {
                        if (id == -999) {
                            System.out.println(lzu.runAllComponents());
                        } else {
                            System.out.println(lzu.runComponent(id));
                        }
                    }
                    break;
                case "!stop":
                    if (wrongParam) {
                        System.out.println("Missing or non-numerical parameter <Component-ID>!");
                    } else {
                        if (id == -999) {
                            System.out.println(lzu.stopAllComponents());
                        } else {
                            System.out.println(lzu.stopComponent(id));
                        }
                    }
                    break;
                case "!remove":
                    if (wrongParam) {
                        System.out.println("Missing or non-numerical parameter <Component-ID>!");
                    } else {
                        if (id == -999) {
                            System.out.println(lzu.removeAllComponents());
                        } else {
                            System.out.println(lzu.removeComponent(id));
                        }
                    }
                    break;
                case "!shutdown":
                    System.out.println(lzu.stopAllComponents());
                    System.out.println("Shutting down...");
                    running = false;
                    break;
                default:
                    System.out.println("Command not recognized. Type !help for a List of possible commands.");
                    break;
            }
        }
    }

    public String getCommandList() {
        StringBuilder sb = new StringBuilder();

        sb.append("!add <Component-Path>: Searches for a Component at the given path. If the Path contains a .jar-File " +
                "with a annotated Start- and Stop-Method, it will be added to the Runtime Environment.\n");
        sb.append("!status: Returns the current Status of all Components in the Runtime Environment.\n");
        sb.append("!run <Component-ID | all>: Calls the annotated Start-Method for the given Component or all Components.\n");
        sb.append("!stop <Component-ID | all>: Calls the annotated Stop-Method for the given Component or all Components.\n");
        sb.append("!remove <Component-ID | all>: Removes the given Component or all Components from the Runtime Environment.\n");
        sb.append("!shutdown: Shuts down all Components and the Runtime Environment. \n\n");

        return sb.toString();
    }

    /**
     * Separate the input into the Command and the parameter. The Command extends untli the first whitespace.
     * After which the parameter starts.
     *
     * @param input
     * @return A String-Array of length 2. If a param was found the Command is in pos 0 and the param at pos 1.
     * If no param was found pos 1 is null.
     */
    private String[] separate(String input) {
        String[] separated = new String[2];
        for(int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                separated[0] = input.substring(0, i);
                separated[1] = input.substring(i+1);

                return separated;
            }
        }

        separated[0] = input;
        separated[1] = null;

        return separated;
    }

    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.startCLI();
    }
}
