import random
import time

NUMBER_OF_SOLVING = 9
NUMBER_OF_QUEENS = 8


def main():
    print("Node\t\t\t\t\t   Random Restart Count\t  Switch Count\t Time")
    print("------------------------   --------------------\t  ------------\t --------")
    total_switch_count = 0  # total switch count of nodes to solve problem
    total_random_restart_count = 0  # total count of randomly restart when local minimum is 1
    total_start = time.time()
    for i in range(NUMBER_OF_SOLVING):
        start_time = time.time()
        restart_count = -1
        switch_count = 0
        node = [0]*NUMBER_OF_QUEENS  # location of queens
        h = 1  # number of pairs of queens that are attacking each other, either directly or indirectly, min local

        while h != 0:  # check local min
            restart_count += 1
            current_node, switch_count = hill_climbing(node, switch_count)
            h = get_h(current_node)
        completion_time = time.time()

        total_switch_count += switch_count
        total_random_restart_count += restart_count
        print(current_node, " ", restart_count, "\t\t\t         ", switch_count, "\t\t\t", ("{:.6f}".format(completion_time-start_time)))
    total_stop = time.time()
    print("---------------------------------------------------------------------4----")
    print("Averages:", "\t\t\t\t  ", "{:.2f}".format(total_random_restart_count/NUMBER_OF_SOLVING), "     \t\t\t", "{:.2f}".format(total_switch_count/NUMBER_OF_SOLVING),"\t\t\t", "{:.6f}".format((total_stop-total_start)/NUMBER_OF_SOLVING))


def hill_climbing(node, switch_count):
    main_node = generate_random_node(node)  # random recreation of node
    problem_solved = False
    while not problem_solved:
        problem_solved = is_global_min(main_node)
        if problem_solved:
            return main_node, switch_count
        successors = generate_successors(main_node)
        value_of_best_successor, index_of_best_successor = select_best_node(successors)
        current_value = get_h(main_node)
        if value_of_best_successor >= current_value:
            return main_node, switch_count

        main_node[index_of_best_successor // NUMBER_OF_QUEENS] = (index_of_best_successor % NUMBER_OF_QUEENS) + 1
        switch_count += 1


def generate_successors(node):  # create neighbours
    location_count = NUMBER_OF_QUEENS * NUMBER_OF_QUEENS
    successors = [0] * location_count
    for i in range(location_count):
        location_in_column = i % NUMBER_OF_QUEENS
        column = i // NUMBER_OF_QUEENS
        if location_in_column == 0:
            copy_node = node.copy()
            copy_location_in_column = node[column]

        copy_node[column] = location_in_column + 1
        if location_in_column + 1 == copy_location_in_column:  # ignore itself
            successors[i] = 100
        else:
            successors[i] = get_h(copy_node)
    return successors


def get_h(node):  # get h value to check local min
    h = 0
    for column_index in range(NUMBER_OF_QUEENS):
        other_column_index = column_index + 1
        for x in range(NUMBER_OF_QUEENS - 1 - column_index):  # check diagonal attacks also
            if (node[column_index] == node[other_column_index]) or (abs(column_index - other_column_index) == abs(node[column_index] - node[other_column_index])):
                h += 1
            other_column_index += 1
    return h


def select_best_node(successors):  # select best node from successors to move queen
    best_node = successors[0]
    best_index = 0
    best_choices = []
    for i in range(len(successors)):  # get best node
        if successors[i] < best_node:
            best_node = successors[i]

    for i in range(len(successors)):  # get all the possible best scenarios
        if successors[i] == best_node:
            best_choices.append(i)
    best_index = random.choice(best_choices)  # choose random index in best successors indexes
    best_node = successors[best_index]
    return best_node, best_index


def is_global_min(node):  # check if the node global min
    if get_h(node) == 0:
        return True
    return False


def generate_random_node(node):  # create random node case
    for i in range(NUMBER_OF_QUEENS):
        node[i] = random.randint(1, NUMBER_OF_QUEENS)  # array indexes represent random values of rows that represent columns
    return node


main()