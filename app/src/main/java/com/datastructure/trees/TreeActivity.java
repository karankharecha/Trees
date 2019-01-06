package com.datastructure.trees;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Edge;
import de.blox.graphview.Graph;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class TreeActivity extends AppCompatActivity {

    private Boolean exit = false;
    private Graph graph;
    private BinaryTree binaryTree;
    private TextView tvValue;
    private TextView sequence;
    private WorkaroundGraphView graphView;
    private Button btnSubmitValue;
    private ArrayList<Button> numericKeypad;
    private ImageView ivBackSpace, ivFeed, ivSave, ivDelete, addTree;
    private View viewNumericKeypad;
    private RelativeLayout rlNumericKeypad;
    private LinearLayout llAddTree;
    private String sequenceString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        getSupportActionBar().setTitle("Trees");

        initialize();
        initializeClickListener();

        if (graph.getNodeCount() > 0) {
            graphView.setVisibility(View.INVISIBLE);
            llAddTree.setVisibility(View.VISIBLE);
        }

        final BaseGraphAdapter<ViewHolder> adapter = new BaseGraphAdapter<ViewHolder>(this, R.layout.node, graph) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.mTextView.setText(data.toString());
            }
        };
        graphView.setAdapter(adapter);

        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));

        List<Edge> a = graph.getEdges();
        for (Edge e : a) {
            Log.d("insertData", "insertData: " + e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tree_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent dashboardIntent = new Intent(TreeActivity.this, DashboardActivity.class);
        startActivity(dashboardIntent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (viewNumericKeypad.getVisibility() == View.VISIBLE &&
                rlNumericKeypad.getVisibility() == View.VISIBLE) {
            viewNumericKeypad.setVisibility(View.GONE);
            rlNumericKeypad.setVisibility(View.GONE);
        } else {
            if (exit) finish();
            else {
                exit = true;
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 2000);
            }
        }
    }

    private void initialize() {
        llAddTree = findViewById(R.id.ll_add_tree);
        addTree = findViewById(R.id.add_tree);
        tvValue = findViewById(R.id.tv_value);
        sequence = findViewById(R.id.sequence);
        graph = new Graph();
        binaryTree = new BinaryTree();
        graphView = findViewById(R.id.graph);
        btnSubmitValue = findViewById(R.id.btn_submitvalue);
        numericKeypad = new ArrayList<Button>() {{
            add((Button) findViewById(R.id.btn_1));
            add((Button) findViewById(R.id.btn_2));
            add((Button) findViewById(R.id.btn_3));
            add((Button) findViewById(R.id.btn_4));
            add((Button) findViewById(R.id.btn_5));
            add((Button) findViewById(R.id.btn_6));
            add((Button) findViewById(R.id.btn_7));
            add((Button) findViewById(R.id.btn_8));
            add((Button) findViewById(R.id.btn_9));
            add((Button) findViewById(R.id.btn_0));
        }};
        ivBackSpace = findViewById(R.id.iv_backspace);
        ivFeed = findViewById(R.id.iv_feed);
        viewNumericKeypad = findViewById(R.id.view_numeric_keypad);
        rlNumericKeypad = findViewById(R.id.rl_numeric_keypad);
        ivSave = findViewById(R.id.iv_save);
        ivDelete = findViewById(R.id.iv_delete);
    }

    private void initializeClickListener() {
        addTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewNumericKeypad.getVisibility() == View.GONE &&
                        rlNumericKeypad.getVisibility() == View.GONE) {
                    viewNumericKeypad.setVisibility(View.VISIBLE);
                    rlNumericKeypad.setVisibility(View.VISIBLE);
                }
            }
        });
        tvValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewNumericKeypad.getVisibility() == View.GONE &&
                        rlNumericKeypad.getVisibility() == View.GONE) {
                    viewNumericKeypad.setVisibility(View.VISIBLE);
                    rlNumericKeypad.setVisibility(View.VISIBLE);
                } else {
                    viewNumericKeypad.setVisibility(View.GONE);
                    rlNumericKeypad.setVisibility(View.GONE);
                }
            }
        });
        for (final Button btn : numericKeypad) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvValue.getText().length() < 6) {
                        String text = tvValue.getText() + "" + btn.getText();
                        tvValue.setText(text);
                    }
                }
            });
        }
        sequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = sequence.getText().toString();
                if (s.isEmpty()) return;
                if (!s.contains(",")) sequence.setText("");
                else {
                    sequence.setText(s.substring(0, s.lastIndexOf(",")));
                }
            }
        });
        ivFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (addTree.getVisibility() == View.VISIBLE) {
                    llAddTree.setVisibility(View.GONE);
                    graphView.setVisibility(View.VISIBLE);
                }*/
                sequence.setVisibility(View.VISIBLE);
                if (!tvValue.getText().toString().isEmpty()) {
                    if (sequence.getText().toString().isEmpty()) {
                        sequenceString = tvValue.getText().toString();
                        sequence.setText(sequenceString);
                    } else {
                        sequenceString = sequence.getText().toString() + ", " + tvValue.getText().toString();
                        sequence.setText(sequenceString);
                    }
                    tvValue.setText("");
                    sequence.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSubmitValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTree.getVisibility() == View.VISIBLE) {
                    llAddTree.setVisibility(View.GONE);
                    graphView.setVisibility(View.VISIBLE);
                }
                viewNumericKeypad.setVisibility(View.GONE);
                rlNumericKeypad.setVisibility(View.GONE);
                insertGraphNode(sequence.getText().toString().split(","));
            }
        });
        ivBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvValue.getText().length() > 0) {
                    StringBuilder text = new StringBuilder(tvValue.getText().toString());
                    text.deleteCharAt(text.length() - 1);
                    tvValue.setText(text.toString());
                }
            }
        });
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Save the tree
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graph.getNodeCount() > 0) {
                    binaryTree.alpha = null;
                    graph.removeNode(graph.getNodes().get(0));
                    graphView.setVisibility(View.INVISIBLE);
                    llAddTree.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void insertGraphNode(String... values) {
        try {
            for (String value: values){
                binaryTree.insert(Integer.parseInt(value.trim()));
            }
            tvValue.setText(null);
            sequence.setText(null);
            sequence.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class BinaryTree {

        private Node alpha;
        private Node beta;
        private int treeSize = 0;
        private boolean isUpperLevel = true;

        public void insert(int data) {
            alpha = insertData(alpha, data);
            Toast.makeText(TreeActivity.this, "Inserted !", Toast.LENGTH_SHORT).show();

        }

        public void preOrder() {
            traversePreOrder(alpha);
        }

        public void postOrder() {
            traversePostOrder(alpha);
        }

        public void inOrder() {
            traverseInOrder(alpha);
        }

        public HashMap<Integer, ArrayList<Node>> breadthFirstTraversal() {
            return getLevels(alpha);
        }

        public int getSize() {
            return treeSize;
        }

        private Node insertData(Node kNode, int data) {
            if (kNode == null) {
                Node tNode = new Node(data, null, null);
                if (alpha != null && beta != null) {
                    graph.addEdge(new de.blox.graphview.Node(beta.value + ""),
                            new de.blox.graphview.Node(tNode.value + ""));
                    List<Edge> edges = graph.getEdges();
                    ArrayList<Integer> idx = new ArrayList<>();
                    for (int i = 0; i < edges.size(); i++) {
                        if (edges.get(i).getSource().getData().equals(beta.value + "")) {
                            idx.add(i);
                            idx.add(Integer.parseInt(edges.get(i).getDestination().getData().toString()));
                        }
                    }
                    if (idx.size() == 4 && idx.get(1) >= idx.get(3)) {
                        Collections.swap(edges, idx.get(0), idx.get(2));
                    }
                    graphView.zoomTo(100f, true);
                } else {
                    graph.addNode(new de.blox.graphview.Node(tNode.value + ""));
                }
                treeSize++;
                return tNode;
            }
            if (kNode.value >= data) {
                beta = kNode;
                kNode.leftLink = insertData(kNode.leftLink, data);
            } else {
                beta = kNode;
                kNode.rightLink = insertData(kNode.rightLink, data);
            }
            return kNode;
        }

        private void traversePreOrder(Node kNode) {
            if (kNode != null) {
                System.out.print(kNode.value + " ");
                traversePreOrder(kNode.leftLink);
                traversePreOrder(kNode.rightLink);
            }
        }

        private void traversePostOrder(Node kNode) {
            if (kNode != null) {
                traversePostOrder(kNode.leftLink);
                traversePostOrder(kNode.rightLink);
                System.out.print(kNode.value + " ");
            }
        }

        private void traverseInOrder(Node kNode) {
            if (kNode != null) {
                traverseInOrder(kNode.leftLink);
                System.out.print(kNode.value + " ");
                traverseInOrder(kNode.rightLink);
            }
        }

        private HashMap<Integer, ArrayList<Node>> getLevels(Node kNode) {
            isUpperLevel = true;
            HashMap<Integer, ArrayList<Node>> levelsMap = new HashMap<>();
            int level = 0;
            if (kNode != null) {
                ArrayList<Node> parentList = new ArrayList<Node>() {{
                    add(alpha);
                }};
                ArrayList<Node> childList = new ArrayList<>();
                while (isUpperLevel) {
                    ArrayList<Node> elements = getChildNodes(parentList, childList);
                    if (!elements.isEmpty()) {
                        levelsMap.put(level++, elements);
                        parentList = new ArrayList<>(childList);
                        childList.clear();
                    }
                }
            }
            return levelsMap;
        }

        private ArrayList<Node> getChildNodes(ArrayList<Node> parentList, ArrayList<Node> childList) {
            ArrayList<Node> nodes = new ArrayList<>();
            for (Node parentNode : parentList) {
                nodes.add(parentNode);
                if (parentNode.leftLink != null) childList.add(parentNode.leftLink);
                if (parentNode.rightLink != null) childList.add(parentNode.rightLink);
            }
            if (nodes.isEmpty()) isUpperLevel = false;
            return nodes;
        }

        public class Node {
            int value;
            Node leftLink;
            Node rightLink;

            Node(int value, Node leftLink, Node rightLink) {
                this.value = value;
                this.leftLink = leftLink;
                this.rightLink = rightLink;
            }
        }
    }

}