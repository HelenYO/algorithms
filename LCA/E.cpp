#include <iostream>
#include <vector>
 
using namespace std;
 
vector<int> children[200002];
vector<bool> isVisit;
int weight[200002];
bool isDeleted[200002];
int parentAnswer[200002];
unsigned int n;
 
void dfs(int v) {
    weight[v] = 1;
    isVisit[v] = true;
    for (int u : children[v]) {
        if (!isDeleted[u] && !isVisit[u]) {
            dfs(u);
            weight[v] += weight[u];
        }
    }
}
 
int findCentroid(int v, int size) {
    isVisit[v] = true;
    for (int child : children[v]) {
        if (!isVisit[child]) {
            if (!isDeleted[child] && weight[child] > size / 2) {
                return findCentroid(child, size);
            }
        }
    }
   return v;
}
 
int doDecompose(int v) {
    if (weight[v] == 1) {
        isDeleted[v] = true;
        return v;
    }
    isVisit = vector<bool>(n + 1);
    dfs(v);
    if (weight[v] == 1) {
        isDeleted[v] = true;
        return v;
    }
    isVisit = vector<bool>(n + 1);
    int centr = findCentroid(v, weight[v]);
    isDeleted[centr] = true;
    for (int u : children[centr]) {
        if (!isDeleted[u]) {
            parentAnswer[doDecompose(u)] = centr;
        }
    }
    return centr;
}
 
int main() {
    ios_base::sync_with_stdio(false);
    cin >> n;
    isVisit.resize(n + 1);
    for (int i = 0; i < n + 1; i++) {
        children[i] = vector<int>();
    }
    for (int i = 1; i <= n - 1; i++) {
        int a, b;
        cin >> a >> b;
        children[a].push_back(b);
        children[b].push_back(a);
    }
    doDecompose(1);
    for (int i = 1; i < n + 1; i++) {
        cout << parentAnswer[i] << " ";
    }
}
/*
 * 3 1 2 2 3
 * 9 3 2 4 2 1 2 5 1 1 6 7 6 6 8 8 9
 * 7 1 2 2 3 3 4 4 5 5 6 6 7
 */
