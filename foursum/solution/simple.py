from __future__ import print_function
import sys

N = int(sys.stdin.readline())
vals = list(map(int, sys.stdin.readlines()))

for i in range(0, N):
    for j in range (i+1, N):
        for k in range(j+1, N):
            for l in range(k+1, N):
                if vals[i]+vals[j]+vals[k]+vals[l] == 0:
                    print(i,j,k,l,file=sys.stderr)
                    print(True)
                    sys.exit()

print(False)
