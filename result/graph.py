from graphviz import Digraph

g = Digraph('G', filename='cluster.gv')
colors = ['green','red','blue','orange']
g.edge('../input/digit-recognizer/train1.csv','data')
g.edge('data','rmNull')
g.edge('rmNull','rmOutliers')
g.edge('rmOutliers','PreProssDATA')
g.edge('PreProssDATA','split')
g.edge('split','PreProssTrain')
g.edge('split','PreProssTest')
g.node('t1', style='filled', fillcolor='green')
g.edge('PreProssTrain','t1')
g.edge('PreProssTest','t1')
g.edge('t1','t1_pca55')
g.edge('t1_pca55','t1_pca55')
g.edge('t1_pca55','t2')
g.node('t2', style='filled', fillcolor='green')
g.edge('t2','t2_pca55')
g.edge('t2_pca55','t2_pca62')
g.edge('t2_pca62','t2_std')
g.edge('t2_std','t4')
g.node('t4', style='filled', fillcolor='green')
g.edge('t4','t4_pca55')
g.edge('t4_pca55','t4_std')
g.edge('t4_std','t4_std')
g.edge('t1_pca55','t3')
g.node('t3', style='filled', fillcolor='green')
g.edge('t3','t3_pca55')
g.edge('t3_pca55','t3_std')
g.edge('t3_std','t3_pca62')
g.node('rndForest1', style='filled', fillcolor='cyan')
g.edge('t3_pca62', 'rndForest1')
g.node('gaussian1', style='filled', fillcolor='cyan')
g.edge('t2_std', 'gaussian1')
g.node('gaussian2', style='filled', fillcolor='cyan')
g.edge('t3_pca62', 'gaussian2')
g.node('knn1', style='filled', fillcolor='cyan')
g.edge('t2_std', 'knn1')
g.view()