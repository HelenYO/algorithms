#include <algorithm>
#include <vector>
#include <iostream>
#include <set>

using namespace std;

const int MAXN = 20000;
const int MAXM = 200000;
vector<pair<int, int>> g[MAXN];
bool used[MAXN]{};
//bool used_in_count[MAXN]{};
int timer, tin[MAXN], tout[MAXN];
int n, m;
vector<int> num_of_component;
bool used_in_count[MAXN]{};
int maxcount = 0;


void dfs(int v, int ed = -1) {
    used[v] = true;
    tin[v] = tout[v] = timer++;
    int count_for_root = 0;
    for (auto r:g[v]) {
        int to = r.first;
        if (ed != r.second) {
            if (used[to])
                tout[v] = min(tout[v], tin[to]);
            else {
                dfs(to, r.second);
                count_for_root++;
                tout[v] = min(tout[v], tout[to]);
            }
        }
    }
}

void find_points() {
    timer = 0;
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i);
        }
    }
}

void dfs_to_count(int v, int ed, int color) {
    used_in_count[v] = true;
    for (auto r:g[v]) {
        int to = r.first;
        //if (to != p) {
        if(r.second != ed) {
            if (!used_in_count[to]) {
                if (tout[to] >= tin[v]) {
                    maxcount++;
                    num_of_component[r.second] = maxcount;
                    dfs_to_count(to, r.second, maxcount);
                } else {
                    num_of_component[r.second] = color;
                    dfs_to_count(to, r.second, color);
                }
            } else if (tin[to] < tin[v]) {
                num_of_component[r.second] = color;
            }
        }
    }
}

void do_count() {
    for (int i = 0; i < n; ++i) {
        if (!used_in_count[i]) {
            //++maxcount;
            dfs_to_count(i, -1, maxcount);
        }
    }
}

int main() {
    cin >> n >> m;
    for (int i = 1; i <= m; ++i) {
        int to, from;
        cin >> from >> to;
        g[from - 1].emplace_back(to - 1, i);
        g[to - 1].emplace_back(from - 1, i);
    }
    find_points();
    num_of_component.resize(m + 1, 0);
    do_count();

    cout << maxcount << "\n";
    for (int i = 1; i <= m; ++i) {
        cout << num_of_component[i] << " ";
    }
}

/*
 *
 * леденец
 4 4
1 2 2 3 2 4 4 3
 */
