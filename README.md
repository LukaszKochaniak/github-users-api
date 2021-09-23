# Github-users-api

### CI

[![CI Pipeline](https://github.com/LukaszKochaniak/github-users-api/actions/workflows/github_deploy_ci.yml/badge.svg?branch=develop)](https://github.com/LukaszKochaniak/github-users-api/actions/workflows/github_deploy_ci.yml)

### How to run?

Just by using command:
```sh
$ ./gradlew bootJar && docker-compose up
```

There also can be used image from dockerhub, which is present in docker-compose - it should be uncommented. (https://hub.docker.com/r/lukkochaniak/github-users-api/tags)
### What is it?

It is simple api, which connects to github api and it fetches user by login
