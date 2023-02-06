#!/bin/bash
mvn exec:java -D "exec.mainClass=edu.paintOnline.server.Main" &
mvn exec:java -D "exec.mainClass=edu.paintOnline.game.App" &
mvn exec:java -D "exec.mainClass=edu.paintOnline.game.App"
pkill java