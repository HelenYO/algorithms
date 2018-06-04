#include <iostream>
#include <vector>
#include <cmath>
 
using namespace std;
vector<int> children[1000900];
int dep[1000900];
int difColors[1000900];
int colorofNode[1000900];
int lastColorWasIn[1000900];
vector<int> parentsBin[1000900];
unsigned int lg;
 
int lca(int v, int u) {
    if (dep[v] > dep[u]) {
        int tmp = u;
        u = v;
        v = tmp;
    }
    for (int i = lg; i >= 0; i--) {
        if (dep[parentsBin[u][i]] >= dep[v]) {
            u = parentsBin[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = lg; i >= 0; i--) {
        if (parentsBin[v][i] != parentsBin[u][i]) {
            v = parentsBin[v][i];
            u = parentsBin[u][i];
 
        }
 
    }
    return parentsBin[v][0];
}
 
void dfs(int v, int height) {
    dep[v] = height;
    for (int i = 0; i < children[v].size(); i++) {
        int child = children[v][i];
        dfs(child, height + 1);
    }
}
 
void dfsColor(int v) {
    difColors[v] = 1;
    for (int i = 0; i < children[v].size(); i++) {
        dfsColor(children[v][i]);
        difColors[v] += difColors[children[v][i]];
    }
    if (lastColorWasIn[colorofNode[v]] != -1) {
        difColors[lca(v, lastColorWasIn[colorofNode[v]])]--;
    }
    lastColorWasIn[colorofNode[v]] = v;
}
 
 
 
int main() {
    unsigned int n;
    cin >> n;
//    dep.resize(n + 1);
//    difColors.resize(n + 1);
//    colorofNode.resize(n + 1);
//    lastColorWasIn.resize(n + 1);
 
    lg = (unsigned int) ceil(log2(n + 1));
    for (int i = 0; i < n + 1; i++) {
        parentsBin[i] = vector<int>();
        parentsBin[i].resize(lg + 1);
        children[i] = vector<int>();
        lastColorWasIn[i] = -1;
    }
    int root;
    for (int i = 1; i <= n; i++) {
        int tmp;
        cin >> tmp;
        if (tmp == 0) {
            root = i;
        }
        children[tmp].push_back(i);
        parentsBin[i][0] = tmp;
        cin >> colorofNode[i];
    }
 
 
    dfs(root, 1);
    for (int j = 1; j < lg + 1; j++) {
        for (int i = 1; i < n + 1; i++) {
            parentsBin[i][j] = parentsBin[parentsBin[i][j - 1]][j - 1];
        }
    }
 
    dfsColor(root);
    int m;
    for (int i = 1; i < n + 1; i++) {
        cout << difColors[i] << " ";
    }
}
/*
 * 5 2 1 3 2 0 3  3 3 2 1
 */
