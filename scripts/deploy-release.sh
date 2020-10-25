#!/usr/bin/env bash

if [[ "$TRAVIS_PULL_REQUEST" == "false" ]]; then

  echo -e "Running release script...\n"
  echo -e "Publishing javadocs and artifacts...\n"
  cd $HOME

  rsync -r --quiet -e "ssh -p 2222 -o StrictHostKeyChecking=no" \
  $HOME/build/internetpolice-eu/PropHuntSigns/target/mvn-repo/ \
  travis@travis.internetpolice.eu:WWW/repo/

  echo -e "Publishing javadocs...\n"

  rsync -r --delete --quiet -e "ssh -p 2222 -o StrictHostKeyChecking=no" \
  $HOME/build/internetpolice-eu/PropHuntSigns/target/site/apidocs/ \
  travis@travis.internetpolice.eu:WWW/javadocs/PropHuntSigns/master/

  echo -e "Publishing final plugin release...\n"

  rsync -r --quiet -e "ssh -p 2222 -o StrictHostKeyChecking=no" \
  $HOME/build/internetpolice-eu/PropHuntSigns/target/PropHuntSigns-*.jar \
  travis@travis.internetpolice.eu:WWW/downloads/PropHuntSigns/

fi
