package me.escoffier.vertx.github.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public abstract class AbstractCommand {

  @Parameter(names = {"--help", "-h"}, help = true, hidden = true)
  private boolean help;

  @Parameter(names = "--token", description = "The Github OAuth Token (https://github.com/settings/tokens)")
  protected String token;

  @Parameter
  private List<String> parameters = new ArrayList<>();

  private JCommander commander;

  protected final Logger logger = LogManager.getLogger(this.getClass().getName());

  public void setCommander(JCommander commander) {
    this.commander = commander;
  }

  public void execute(String[] args) {
    JCommander commander = null;
    try {
      commander = new JCommander(this);
      commander.setAcceptUnknownOptions(true);
      commander.parse(args);
    } catch (Exception e) {
      fatal("Unable to parse command line parameters", e);
    }

    setCommander(commander);

    if (help) {
      commander.usage();
    } else {
      run();
    }
  }

  public abstract void run();

  public static void fatal(Logger logger, String message, Exception cause) {
    logger.error(message, cause);
    System.exit(-1);
  }

  public void fatal(String message, Exception cause) {
    fatal(logger, message, cause);
  }

}
