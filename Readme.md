# Vert.x Github Utilities


A binary version of the tool is available in the `dist` directory.


## Install

Just clone this repository or download the `.jar` file from `dist`. You would also need the `repositories.json` file.

## Retrieving stargazers and generating CSVs

This command collects all stargazers from the listed repositories and does some statistics.


```
java -jar -cp dist/vertx-github-utilities-1.0-SNAPSHOT.jar \
 --command stargazers \
 --repositories repositories.json \
 --token your_github_token
```

Once executed you get two stats giving the total number of stars, and the total number of stargazer (unique 
stargazer)

```
08:43:48.112 [main] INFO  me.escoffier.vertx.github.commands.StargazerCommand - Number of stargazers: 6922
08:43:48.113 [main] INFO  me.escoffier.vertx.github.commands.StargazerCommand - Number of (unique) stargazers: 5875
```

In addition, it generates 2 CSV files:

* `starPerRepository.csv`: The number of star per repository (2 columns)
* `starEvolution.csv`: A 3 column tables: month, number of star won that month, total number of stars that month

## Retrieving contributors and generating CSVs

This command collects all the contributors for the listed repositories and does some statistics.

```
java  -jar dist/vertx-github-utilities-1.0-SNAPSHOT.jar \
 --command contributors \
 --token your_github_token \
 --repositories repositories.json
```

Once executes, you get a couple of statistics printed on the console:

* The number of contributors (not unique)
* The number of contributors (unique)
* The top 10 contributors
* The top 10 repositories (repo with the most contributors)

It also generates 2 CSV files:

* `scores.csv`: list the number of contribution per contributors (sorted)
* `contributions.csv`: list the number of contributors per repository (sorted)

## Retrieving issues and generating CSVs

```
java -jar target/vertx-github-utilities-1.0-SNAPSHOT.jar \
    --command issues -r repositories.json  --token YOUR_TOKEN


18:54:59.954 [main] INFO  me.escoffier.vertx.github.commands.IssueCommand - 56 projects loaded
18:55:11.463 [main] INFO  me.escoffier.vertx.github.commands.IssueCommand - Total number of issues: 478
```

It also generates a CSV file (issues.csv) containing the number of open issues per repository.


## The repositories file

The `repositories.json` file list the different repositories to process. Each repository is a Json object:

```
{
    "repo" : Name of the repository on github,
    "org": The Github organization, vert-x3 is ommitted,
    "dir" : Ignored in this project (this file is used by some other tools too),
    "url": Ignored in this project (this file is used by some other tools too),
    "branch": Ignored in this project (this file is used by some other tools too),        
}
```




