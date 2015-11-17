#!/bin/bash

lein uberjar && docker build -t fardog/mkwords .
