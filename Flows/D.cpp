//
// Created by Елена on 2019-03-26.
//

#include<iostream>
#include <vector>

using namespace std;

int n, m;
vector<vector<int>> g;
vector<int> mt;
vector<bool> used;

bool try_kuhn (int v) {
    if (used[v])  return false;
    used[v] = true;
    for (size_t i=0; i<g[v].size(); ++i) {
        int to = g[v][i];
        if (mt[to] == -1 || try_kuhn (mt[to])) {
            mt[to] = v;
            mt[v] = to;//
            return true;
        }
    }
    return false;
}

int main() {
    cin >> n >> m;
    g.resize(n + m);
    for (int i = 0; i < n; ++i) {
        int to;
        cin >> to;
        while(to != 0) {
           g[i].push_back(to + n - 1);
           //g[to + n - 1].push_back(i);
           cin >> to;
        }
    }
    mt.assign(n + m, -1);

    for (int v=0; v<n; ++v) {
        used.assign (n, false);
        try_kuhn(v);
    }
    int sum = 0;
    for (int j = 0; j < n; ++j) {
        if(mt[j] != -1) {
            sum ++;
        }
    }
    cout << sum << endl;
    for (int k = 0; k < n; ++k) {
        if(mt[k] != -1) {
            cout << k + 1 << " " << mt[k] - n + 1 << endl;
        }
    }
};