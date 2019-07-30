#!/bin/bash
set -e

filename=RunSort

if [[ $filename.java -ot $filename.class ]];
then echo -e "Already compiled $filename ...";
else
  echo -e "Compiling $filename: ..."
  javac $filename.java
fi

testFiles="tiny.txt tibny2.txt words3.txt"

for inputFile in $testFiles; do
  echo -e "\nExecuting: java $filename < $inputFile\n"
  java $filename < ../data/$inputFile
done

echo -e "\nDone testing $filename"
