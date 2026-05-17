import numpy as np
from scipy.optimize import differential_evolution


def ackley(x):

    n = len(x)
    sum1 = np.sum(x**2)
    sum2 = np.sum(np.cos(2*np.pi*x))
    term1 = -20*np.exp(-0.2*sum1/n)
    term2 = -np.exp(sum2/n)
    return term1 + term2 + 20 + np.exp(1)


def sphere(x):

    return np.sum(x**2)


bounds = [(-32.768, 32.768)] * 20
result_ackley = differential_evolution(ackley, bounds,
                                       maxiter=100, tol=1e-7, disp=True)
result_sphere = differential_evolution(sphere, bounds,
                                       maxiter=100, tol=1e-7, disp=True)


print("Minimum value found: ", result_ackley.fun)
print("Minimum location found: ", result_ackley.x)

print("Minimum value found: ", result_sphere.fun)
print("Minimum location found: ", result_sphere.x)
