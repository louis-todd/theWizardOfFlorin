#!/bin/bash

find -name "*.java" > sources.txt

javac -d classes -cp classes:lib/jsfml.jar @sources.txt
