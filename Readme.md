# Vert.x Github Utilities


A binary version of the tool is available in the `dist` directory.


## Install

Just clone this repository or download the `.jar` file from `dist`. You would also need the `repositories.json` file.

## Common characteristics

All the following command generate 2 CSV files:

* `x.csv` representing the data per month and per repository
* `x-consolidated.csv` representing the cumulated data (all the repositories together)

## Retrieving stargazers and generating CSVs

This command collects all stargazers from the listed repositories and does some statistics.


```
java -jar -cp dist/vertx-github-utilities-1.0-SNAPSHOT.jar \
 --command stargazers \
 --repositories repositories.json \
 --token your_github_token
```

## Retrieving contributors and generating CSVs

This command collects all the contributors for the listed repositories and does some statistics.

```
java  -jar dist/vertx-github-utilities-1.0-SNAPSHOT.jar \
 --command contributors \
 --token your_github_token \
 --repositories repositories.json
```

## Retrieving issues and generating CSVs

```
java -jar target/vertx-github-utilities-1.0-SNAPSHOT.jar \
    --command issues -r repositories.json  --token YOUR_TOKEN
```


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




