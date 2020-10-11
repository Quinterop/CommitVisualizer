package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// Les informations à saisir :
// cette classe designe un plugin dans notre logiciel et son role est de calculer le nombre de commits 
// fait par chaque personne et cela en executant la methode run qui appelle la methode CountCommitsPerAuthorPlugin
// qui prend la liste des commits du projet. Cette derniére calcule le nombre de commits fait par chaque personne 
// et le stocke dans une variable result qui contient un hashmap<Autheur,NombreCommits>.

public class CountCommitsPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);
            result.commitsPerAuthor.put(commit.author, nb + 1);
        }
        return result;
    }

    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    @Override
    public Result getResult() {
        if (result == null)
            run();
        return result;
    }

    /// Les informations sur la classe :
    // cette classe implemeente l'interface result et redifinit les deux methodes
    /// getResultAsString/AsHtmlDiv
    static class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> commitsPerAuthor = new HashMap<>();

        Map<String, Integer> getCommitsPerAuthor() {
            return commitsPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per author: <ul>");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
