#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	openssl aes-256-cbc -K $encrypted_e1d4b2f3c747_key -iv $encrypted_e1d4b2f3c747_iv -in etc/codesigning.asc.enc -out etc/codesigning.asc -d
	gpg --fast-import etc/codesigning.asc
fi
