#include <algorithm>
#include <vector>
#include <iostream>

using namespace std;

const int MAXN = 20000;
const int MAXM = 200000;
vector<pair<int, int>> g[MAXN];
bool used[MAXN]{};
int timer, tin[MAXN], tout[MAXN];
int n, m;
vector<int> answer;

void do_work(int from, int i) {
    int temp = g[from][i].second;

    //if(temp != -1) {
        answer.push_back(temp);
    //}
}

void dfs(int v, int p = -1) {
    used[v] = true;
    tin[v] = tout[v] = timer++;
    for (int i = 0; i < g[v].size(); ++i) {
        int to = g[v][i].first;
        if(to != p) {
            if (used[to])
                tout[v] = min(tout[v], tin[to]);
            else {
                dfs(to, v);
                tout[v] = min(tout[v], tout[to]);
                if (tout[to] > tin[v]) {
                    do_work(v, i);
                }
            }
        }
    }
}

void find_bridges() {
    timer = 0;
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i);
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
    find_bridges();
    sort(answer.begin(), answer.end());
    cout << answer.size() << "\n";
    for (int i = 0; i < answer.size(); ++i) {
        cout << (answer[i]) << " ";
    }
}

/*
9 11
1 2
2 3
3 1
3 4
4 5
5 6
4 6
6 7
7 8
8 9
9 7
*/

//6 6
//1 2
//2 3
//3 1
//4 5
//5 6
//6 4

//8 7
//1 2
//2 3
//3 1
//4 5
//5 6
//6 4
//7 8
