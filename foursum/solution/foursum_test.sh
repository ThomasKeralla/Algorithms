#!/bin/bash

# the input
input_numbers="30 50 100 200 " # 400 800 1600 3200"

# the java files to be run
algorithms="Simple Faster FasterQuad"

# compile the algorithms
for algorithm in $algorithms; do
  javac $algorithm.java
done

# compile Weed.java
javac Weed.java

# only make the dir for inputs if it does not exists
if [ ! -d input ]; then
  mkdir input
fi
# make the input files only if they do not already exist
for input in $input_numbers; do
  if [ ! -f input/Weed1_$input.in ]; then
    echo "created input/Weed1_$input.in"
    java Weed $input 1 > input/Weed_$input.in
  fi
done

# java part ===============================

# run the tests for each implementation
for algorithm in $algorithms; do

  # run the tests
  for input in $input_numbers; do
    for i in `seq 1 5`; do
      # remove the old test
      echo "running test for: $algorithm input size: $input pass: $i"
      /usr/bin/time -f "$input %e" bash -c "java $algorithm < input/Weed_$input.in > /dev/null 2>&1" >> output/$algorithm/$algorithm-$i.table 2>&1
    done
  done
done

# python part =============================
for input in $input_numbers; do
  for i in `seq 1 5`; do
    # remove old test
    echo "running test for: simple.py input size: $input pass: $i"
    /usr/bin/time -f "$input %e" bash -c "python2 simple.py < input/Weed_$input.in > /dev/null 2>&1" >> output/Simple_python/Simple_python-$i.table 2>&1
  done
done
