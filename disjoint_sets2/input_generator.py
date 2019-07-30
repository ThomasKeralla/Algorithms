from random import randint
import time
import sys

start_time = time.process_time()

input_range = int(sys.argv[1])
number_of_operations = int(sys.argv[2])

input_file = open('input_file.txt', 'w+')

first_line_str = "{} {}\n".format(input_range,number_of_operations)

input_file.write(first_line_str)

def write_random_line():
    opeation_rand_int = randint(0,10)
    if opeation_rand_int < 4:
        operation = 'u'
    else:
        operation = 'q'
    p = randint(0,input_range-1)
    q = randint(0,input_range-1)
    line_to_write = "{} {} {}\n".format(operation,p,q)
    return line_to_write

def write_line(i):

    if (i % 3 == 0):
        operation = 'u'
    else:
        operation = 'q'

    p = randint(0,input_range-1)
    q = randint(0,input_range-1)


    line_to_write = "{} {} {}\n".format(operation,p,q)
    return line_to_write


for i in range(number_of_operations):
    input_file.write(write_line(i))

input_file.close()

end_time = time.process_time()

total_time = end_time - start_time

print('Finished generating input file, time elapsed in seconds: ',total_time)
