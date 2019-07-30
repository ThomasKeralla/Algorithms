
# dicts for holding data
simple = [ {},{},{},{},{} ]
faster = [ {},{},{},{},{} ]
faster_quad = [ {},{},{},{},{} ]
simple_python = [ {},{},{},{},{} ]

simple_avg = {}
faster_avg = {}
faster_quad_avg = {}
simple_python_avg = {}

def read_file(file_name, my_dict):
    file_name = file_name
    file = open(file_name)
    for line in file:
        space_index = line.find(" ")
        n = line[:space_index]
        time = float(line[space_index+1:len(line)-1])
        my_dict[n] = time
    file.close()

def add_tables(algorithm_name, dict_list):
    index = 0
    for selected_dict in dict_list:
        index += 1
        read_file(algorithm_name+"/"+algorithm_name+"-"+str(index)+".table",selected_dict)

def select_avg_dict(algo_name):
    if algo_name == "Simple":
        return simple_avg
    elif algo_name == "Faster":
        return faster_avg
    elif algo_name == "FasterQuad":
        return faster_quad_avg
    elif algo_name == "Simple_python":
        return simple_python_avg

def find_values(dict_list, algo_name):
    my_dict = dict_list[0]

    avg_dict = select_avg_dict(algo_name)

    # find min values
    for n in my_dict.keys():
        my_min = my_dict[n]
        for selected_dict in dict_list:
            for key in selected_dict:
                if key == n:
                    if selected_dict[key] < my_min:
                        my_min = selected_dict[key]
        print("{} min: {}".format(n,my_min))

        my_max = my_dict[n]
        for selected_dict in dict_list:
            for key in selected_dict:
                if key == n:
                    if selected_dict[key] > my_max:
                        my_max = selected_dict[key]
        print("{} max: {}".format(n,my_max))

        my_sum = 0
        for selected_dict in dict_list:
            for key in selected_dict:
                if key == n:
                    my_sum += selected_dict[key]
        average = my_sum / len(dict_list)
        print("{} average: {}".format(n,average))

        avg_dict[n] = average

        print()

def write_avg_table_file(avg_dict, algo_name):
    sorted_key_list = []
    for key in avg_dict.keys():
        sorted_key_list.append(int(key))
    sorted_key_list.sort()

    avg_table_file = open("tex_tables/{}.table".format(algo_name),"w+")
    for key in sorted_key_list:
        avg_table_file.write("{} {}\n".format(key,avg_dict[str(key)]))
    avg_table_file.close()


add_tables("Simple",simple)
add_tables("Faster",faster)
add_tables("FasterQuad",faster_quad)
add_tables("Simple_python",simple_python)

print ("\n\tSIMPLE\n")
find_values(simple,"Simple")

print("\n\tFASTER\n")
find_values(faster,"Faster")

print ("\n\tFASTER_QUAD\n")
find_values(faster_quad,"FasterQuad")

print ("\n\tSIMPLE_PYTHON\n")
find_values(simple_python,"Simple_python")

write_avg_table_file(simple_avg,"Simple")
write_avg_table_file(faster_avg,"Faster")
write_avg_table_file(faster_quad_avg,"FasterQuad")
write_avg_table_file(simple_python_avg,"Simple_python")
