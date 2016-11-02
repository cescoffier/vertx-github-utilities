package me.escoffier.vertx.github.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Main {

  private static final Logger LOGGER = LogManager.getLogger("Main");
  private static final Map<String, AbstractCommand> COMMANDS;

  static {
    COMMANDS = ImmutableMap.of(
        "stargazers", new StargazerCommand(),
        "contributors", new ContributorCommand(),
        "release-notes", new ReleaseNoteCommand(),
        "issues", new IssueCommand()
    );
  }

  @Parameter(names = {"--command"}, required = true)
  String command;

  @Parameter(names = {"--help", "-h"}, help = true, hidden = true)
  private boolean help;

  public static void main(String[] args) {
    Main main = new Main();
    try {
      JCommander commander = new JCommander(main);
      commander.setAcceptUnknownOptions(true);
      commander.parse(args);

      if (main.help  && main.command == null) {
        System.out.println("Available commands:");
        COMMANDS.forEach((name, cmd) -> System.out.println("\t" + name));
      } else {
        AbstractCommand cmd = COMMANDS.get(main.command);
        if (cmd == null) {
          AbstractCommand.fatal(LOGGER,
              "Unknown command '" + main.command + "', available commands: " + COMMANDS.keySet(), null);

        } else {
          cmd.execute(args);
        }
      }
    } catch (Exception e) {
      AbstractCommand.fatal(LOGGER, "Unable to parse command line parameters", e);
    }
  }

}
