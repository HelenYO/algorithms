//
// Created by Елена on 09.10.18.
//


#include <algorithm>
#include <vector>
#include <iostream>

using namespace std;

int n, m;
vector<int> graph[100000];
vector<int> color;
vector<int> ans;

bool dfs (int v) {
    //used[v] = true;
    color[v] = 1;
    for (int i=0; i<graph[v].size(); ++i) {
        int to = graph[v][i];
        if(color[to] == 0) {
            if (dfs (to))  return true;
        } else if(color[to] == 1) {
            return true;
        }
    }
    color[v] = 2;
    ans.push_back(v);
    return false;
}

bool top_sort() {
    ans.clear();
    for (int i=0; i<n ; ++i)
        if (color[i] != 2) {
            if(dfs(i)) {
                return false;
            }
        }
    std::reverse(ans.begin(), ans.end());
    return true;
}

int main (){
       cin >> n >> m;
        for (int i = 0; i < m; i++) {
            int from, to;
            cin >> from >> to;
            graph[from - 1].push_back(to - 1);
        }
        color.resize(n, 0);

        if(!top_sort()) {
            cout << -1 ;
        } else {
            for (int i = 0; i < ans.size(); i++) {
                cout << (ans[i] + 1) << " ";
            }
        }

}
//6 6
//1 2
//3 2
//4 2
//2 5
//6 5
//4 6
