//
// Created by Елена on 22.10.18.
//

#include <algorithm>
#include <vector>
#include <iostream>
#include <set>

using namespace std;

const int MAXN = 10005;
vector<int> g[MAXN];
vector<int> gT[MAXN];
int n,m;
vector<int> mass;
vector<bool> used;
vector<int> component_num;
set<pair<int, int>> set_of_diff_edjes;

void dfs_for_mass(int v) {
    used[v] = true;
    for(int i = 0; i < g[v].size(); ++i) {
        int temp = g[v][i];
        if(!used[temp]) {
            dfs_for_mass(temp);
        }
    }
    mass.push_back(v);
}

void dfs_for_components(int v, int col) {
    used[v] = true;
    component_num[v] = col;
    for(int i = 0;i < gT[v].size(); ++i) {
        int temp = gT[v][i];
        if(!used[temp]) {
            dfs_for_components(temp, col);
        }
    }
}

int main() {
    cin >> n >> m;
    for(int i = 0; i < m; ++i) {
        int from, to;
        cin >> from >> to;
        g[from - 1].push_back(to - 1);
        gT[to - 1].push_back(from - 1);
    }
    used.assign(n + 1, false);
    for(int i = 0; i < n; ++i) {
        if(!used[i]) {
            dfs_for_mass(i);
        }
    }
    used.assign(n + 1, false);
    component_num.assign(n + 1, -1);
    int color_of_cur_comp = 0;
    reverse(mass.begin(), mass.end());
    for (int u : mass) {
        if(!used[u]) {
            dfs_for_components(u, color_of_cur_comp++);
        }
    }

    for(int i = 0; i < n; ++i) {
        for(int j = 0; j < g[i].size(); ++j) {
            int temp = g[i][j];
            if(component_num[i] != component_num[temp]) {
                set_of_diff_edjes.emplace(component_num[i], component_num[temp]);
            }
        }
    }
    cout << set_of_diff_edjes.size();
}

/*
 восьмерка с палкой в центре:
 6 7
 1 2 2 3 3 1 3 4 4 5 5 6 6 4
 */
