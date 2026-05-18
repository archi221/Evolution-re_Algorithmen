import numpy as np
from scipy.optimize import differential_evolution


iterations = 0


def ackley(x):

    n = len(x)
    sum1 = np.sum(x**2)
    sum2 = np.sum(np.cos(2*np.pi*x))
    term1 = -20*np.exp(-0.2*sum1/n)
    term2 = -np.exp(sum2/n)
    return term1 + term2 + 20 + np.exp(1)


def sphere(x):

    return np.sum(x**2)


def add_to_file(x_vector, congestion):
    global iterations
    iterations += 1
    fitness = function(x_vector)
    file.write(f"{iterations}, {fitness}, {congestion}\n")


strategies = [
    'best1bin',
    'best1exp',
    'rand1bin',
    'rand1exp',
    'rand2bin',
    'rand2exp',
    'randtobest1bin',
    'randtobest1exp',
    'currenttobest1bin',
    'currenttobest1exp',
    'best2exp',
    'best2bin',
]

bounds = [(-32.768, 32.768)] * 2
results_ackley = []
results_sphere = []
for strategie in strategies:
    function = ackley
    file = open(f"DE_ackley_{strategie}_results", "w")
    file.write("iterations, fitness, congestion\n")
    results_ackley.append(
        differential_evolution(ackley, bounds,
                               maxiter=100, tol=1e-7, disp=True,
                               callback=add_to_file,
                               updating="deferred",
                               strategy=strategie))

    file.close()

    function = sphere
    file = open(f"DE_sphere_{strategie}_results", "w")
    file.write("iterations, fitness, congestion\n")

    results_sphere.append(
        differential_evolution(sphere, bounds,
                               maxiter=100, tol=1e-7, disp=True,
                               callback=add_to_file,
                               strategy=strategie))
    file.close()

for result_ackley, result_sphere, strategie in zip(results_ackley, results_sphere, strategies):
    print(f"Results using the {strategie}:")
    print("Minimum value found: ", result_ackley.fun)
    print("Minimum location found: ", result_ackley.x)

    print("Minimum value found: ", result_sphere.fun)
    print("Minimum location found: ", result_sphere.x)
    print("\n")
