#include <iostream>
#include <vector>
 
using namespace std;
 
struct Node {
    Node *left;
    Node *right;
    int y;
    int count;
    int number;
 
    Node(Node *left, Node *right, int y, int count, int number) : left(left), right(right), y(y), count(count),
                                                                  number(number) {}
};
 
Node *glava = nullptr;
 
void update(Node *t);
 
int cnt(Node *t);
 
 
Node *Merge(Node *L, Node *R) {
    if (L == nullptr) return R;
    if (R == nullptr) return L;
 
    if (L->y > R->y) {
        Node *newR = Merge(L->right, R);
        update(newR);
        Node *temp = new Node(L->left, newR, L->y, 1, L->number);
        update(temp);
        return temp;
    } else {
        Node *newL = Merge(L, R->left);
        Node *temp = new Node(newL, R->right, R->y, 1, R->number);
        update(temp);
        return temp;
    }
}
 
pair<Node *, Node *> split(Node *v, int x) { // <,  >=
    if (v == nullptr) {
        return make_pair(nullptr, nullptr);//
    }
    int curIndex = cnt(v->left);
    if (x > curIndex) {
        pair<Node *, Node *> p = split(v->right, x - curIndex - 1);
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
    pair<Node *, Node *> p = split(glava, x);
    Node *m = new Node(nullptr, nullptr, rand(), 1, x);
    glava = Merge(Merge(p.first, m), p.second);
    update(glava);
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
 
void shift(int l, int r) {
    pair<Node *, Node *> p = split(glava, l - 1);
    pair<Node *, Node *> p2 = split(p.second, r - l + 1);
    glava = Merge(Merge(p2.first, p.first), p2.second);
    update(glava);
}
 
void inorderTraversal(Node *v) {
    //cout <<  " v\n";
    if (v != nullptr) {
        vector<int> temp;
        if (v->left != nullptr) {
            inorderTraversal(v->left);
        }
        //print x.key
        cout << v->number << " ";
        //temp.insert(inorderTraversal(v->right));
        if (v->right != nullptr) {
            inorderTraversal(v->right);
        }
        //temp.insert(temp.end(), temp2.begin(), temp2.end());
    }
}
 
int main() {
    int m;
    int n;
    cin >> n >> m;
    glava = new Node(nullptr, nullptr, rand(), 1, 1);
    for (int i = 1; i < n; i++) {
        insert(i + 1);
    }
    int m2 = m;
    while (m > 0) {
        int l;
        int r;
        cin >> l >> r;
        shift(l, r);
        m--;
    }
 
    //cout << m;
    inorderTraversal(glava);
 
 
}
 
/*bool exists(Node *v, int x) {
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
}*/
