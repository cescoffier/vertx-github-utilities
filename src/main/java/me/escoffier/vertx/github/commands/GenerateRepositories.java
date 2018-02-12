package me.escoffier.vertx.github.commands;

import com.beust.jcommander.Parameter;
import com.google.common.io.Files;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.utils.GithubClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class GenerateRepositories extends AbstractCommand {


    @Parameter(names = {"--output", "-o"},
        description = "The json file listing the repositories")
    private File output;


    @Override
    public void run() throws IOException {
        if (output == null) {
            output = new File("repositories.json");
        }
        GithubClient client = new GithubClient(token);
        List<String> responses = client.request("vert-x3", null, "repos", null);

        List<Project> projects = new ArrayList<>();
        projects.add(new Project("vert.x", "eclipse"));

        for (String resp : responses) {
            JsonArray array = new JsonArray(resp);
            for (int i = 0 ; i < array.size(); i++) {
                JsonObject object = array.getJsonObject(i);
                Project project = new Project(object.getString("name"), object.getJsonObject("owner").getString
                    ("login"));
                projects.add(project);
            }
        }

        logger.info(projects.size() + " projects collected.");

        JsonArray out = new JsonArray();
        projects.forEach(project -> out.add(project.toJson()));

        Files.write(out.encodePrettily().getBytes(), output);

        logger.info("Repositories file generated: " + output.getAbsolutePath());
    }
}
