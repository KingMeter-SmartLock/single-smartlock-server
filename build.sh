#!/usr/bin/env bash

cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-common
mvn clean
mvn install


cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-utils
mvn clean
mvn install

cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-dto-smartlock
mvn clean
mvn install

cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-socket-framework
mvn clean
mvn install

cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-socket-smartlock
mvn clean
mvn install

cd /Users/crazyandy/Documents/codesource/kingmeter-framework/kingmeter-mvc
mvn clean
mvn install

cd /Users/crazyandy/Documents/codesource/KingMeterIOTGroup/single-smartlock-server
mvn clean
mvn package