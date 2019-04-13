//
// Created by Елена on 2019-03-26.
//

#include<iostream>
#include <vector>
#include <cmath>

using namespace std;

int n, m;
vector<vector<pair<double, double>>> g;
vector<int> mt;
vector<bool> used;

bool try_kuhn(int v, double t) {
    if (used[v]) return false;
    used[v] = true;
    for (size_t i = 0; i < g[v].size(); ++i) {
        int to = g[v][i].first;
        //
        if (g[v][i].second > t) {
            continue;
        }
        //
        if (mt[to] == -1 || try_kuhn(mt[to], t)) {
            mt[to] = v;
            mt[v] = to;//
            return true;
        }
    }
    return false;
}

bool doPairs(double t) {
    mt.assign(n + n, -1);

    for (int v = 0; v < n; ++v) {
        used.assign(n, false);
        try_kuhn(v, t);
    }
    int sum = 0;
    for (int j = 0; j < n; ++j) {
        if (mt[j] != -1) {
            sum++;
        }
    }
    return (sum == n);
}

struct vertex {
    double x, y, v;

    vertex(double x, double y, double v) : x(x), y(y), v(v) {}
};

struct pl {
    double x, y;

    pl(double x, double y) : x(x), y(y) {}
};

vector<vertex> points;
vector<pl> places;

double getTime(vertex v, pl p) {
    double t = sqrt((v.x - p.x) * (v.x - p.x) + (v.y - p.y) * (v.y - p.y)) / v.v;
    return t;
}

double doWork() {
    double r = 10000;
    double l = 0;
    while (r - l > 0.00001) {
        double m = (l + r) / 2;
        if (doPairs(m)) {
            r = m;
        } else {
            l = m;
        }
    }
    return l;
}

int main() {
    cin >> n;
    g.resize(n + n);
    for (int i = 0; i < n; ++i) {
        double x, y, v;
        cin >> x >> y >> v;
        points.emplace_back(x, y, v);
    }

    for (int j = 0; j < n; ++j) {
        double x, y;
        cin >> x >> y;
        places.emplace_back(x, y);
    }

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            g[i].push_back({j + n, getTime(points[i], places[j])});
        }
    }

    double ans = doWork();

    cout << ans;
};