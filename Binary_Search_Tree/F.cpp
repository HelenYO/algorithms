#include <iostream>
 
using namespace std;
 
struct Node {
    Node *left;
    Node *right;
    int x;
    int y;
    int count;
 
    Node(Node *left, Node *right, int x, int y, int count) : left(left), right(right), x(x), y(y), count(count) {}
};
 
Node *glava = nullptr;
 
bool exists(Node *v, int x);
 
 
Node *prev(int x);
 
 
Node *next(int x);
 
void update(Node *t);
 
int cnt(Node *t);
 
 
Node *Merge(Node *L, Node *R) {
    if (L == nullptr) return R;
    if (R == nullptr) return L;
 
    if (L->y > R->y) {
        Node *newR = Merge(L->right, R);
        update(newR);
        Node *temp = new Node(L->left, newR, L->x, L->y, 1);
        update(temp);
        return temp;
    } else {
        Node *newL = Merge(L, R->left);
        Node *temp = new Node(newL, R->right, R->x, R->y, 1);
        update(temp);
        return temp;
    }
}
 
pair<Node *, Node *> split(Node *v, int x) { // <,  >=
    if (v == nullptr) {
        return make_pair(nullptr, nullptr);//
    } else if (x > v->x) {
        pair<Node *, Node *> p = split(v->right, x);
        v->right = p.first;
        update(v);
        update(p.second);
        return make_pair(v, p.second);
    } else {
        pair<Node *, Node *> p = split(v->left, x);
        v->left = p.second;
        update(p.first);
        update(v);
        return make_pair(p.first, v);
    }
}
 
void insert(int x) {
    if (exists(glava, x)) {
        return;
    }
    pair<Node *, Node *> p = split(glava, x);
    Node *m = new Node(nullptr, nullptr, x, rand(), 1);
    glava = Merge(Merge(p.first, m), p.second);
    update(glava);
}
 
Node *remove(int x) {
    pair<Node *, Node *> p = split(glava, x);
    pair<Node *, Node *> p2 = split(p.second, x + 1);
    glava = Merge(p.first, p2.second);
    update(glava);
    return glava;
}
 
int cnt(Node *t) {
    if (t == nullptr) {
        return 0;
    } else {
        return t->count;
    }
}
 
void update(Node *t) {
    if (t != nullptr) {
        t->count = 1 + cnt(t->left) + cnt(t->right);
    }
}
 
int find(Node *v, int k) {
 
    if (cnt(v->right) + 1 == k) {
        return v->x;
    } else if (cnt(v->right) >= k){
        return find(v->right, k);
    } else {
        return find(v->left, k - cnt(v->right) - 1);
    }
 
}
 
int main() {
    string komand;
    int n;
    cin >> n;
    while (n > 0) {
        cin >> komand;
        if (komand[0] == '+' || komand[0] == '1') {
            int x;
            cin >> x;
            if (glava != nullptr) {
                insert(x);
            } else {
                glava = new Node(nullptr, nullptr, x, rand(), 1);
            }
        } else if (komand[0] == '-') {
            int x;
            cin >> x;
            remove(x);
        } else {
            int k;
            cin >> k;
            cout << find(glava, k) << endl;
        }
        n--;
    }
}
 
bool exists(Node *v, int x) {
    if (v == nullptr) {
        return false;
    }
    if (x > v->x) {
        if (v->right != nullptr) {
            return exists(v->right, x);
        } else {
            return false;
        }
    } else if (x < v->x) {
        if (v->left != nullptr) {
            return exists(v->left, x);
        } else {
            return false;
        }
    } else {
        return true;
    }
}
 
Node *next(int x) {
    Node *now = glava;
    Node *answer = nullptr;
    while (now != nullptr) {
        if (now->x > x) {
            answer = now;
            now = now->left;
        } else {
            now = now->right;
        }
    }
    return answer;
}
 
Node *prev(int x) {
    Node *now = glava;
    Node *answer = nullptr;
    while (now != nullptr) {
        if (now->x >= x) {
            now = now->left;
        } else {
            answer = now;
            now = now->right;
        }
    }
    return answer;
}
