#!/bin/bash
clear
heroku container:login
APP_NAME=$(heroku info | grep  "=== .*" |sed "s/=== //")
mvn clean package -Dquarkus.container-image.build=true -Dquarkus.container-image.group=registry.heroku.com/"$APP_NAME" -Dquarkus.container-image.name=web -Dquarkus.container-image.tag=latest -Pnative -Dquarkus.native.container-build=true
docker push registry.heroku.com/"$APP_NAME"/web

heroku pg:backups:capture --app "$APP_NAME"
heroku container:release web --app "$APP_NAME"

heroku logs --app "$APP_NAME" --tail