package com.example.printallviewinterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Gracker";
    TextView resultTex;
    LinkedList<View> breadthFirstLinkedList = new LinkedList();
    LinkedList<View> deepFirstLinkedList = new LinkedList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTex = findViewById(R.id.textView);
    }


    public void printAllViews(View view) {
        // 使用递归调用
        int deep = 0;
        resultTex.setText("Result: \n");
        View rootView = this.getWindow().getDecorView();

        long now = System.currentTimeMillis();
        Trace.beginSection("recursionPrint");
        recursionPrint(rootView , deep);
        long spend = System.currentTimeMillis() - now;
        Trace.endSection();
        resultTex.append("使用递归实现共耗时: " + spend + " ms \n");
        resultTex.append("--------------------------------------- \n");

        // 使用广度优先实现
        long now1 = System.currentTimeMillis();
        Trace.beginSection("recursionPrint");
        breadthFirst(rootView , deep);
        long spend1 = System.currentTimeMillis() - now1;
        Trace.endSection();
        resultTex.append("使用广度优先实现实现共耗时: " + spend1 + " ms  \n");
        resultTex.append("--------------------------------------- \n");

        // 使用深度优先实现
        long now2 = System.currentTimeMillis();
        Trace.beginSection("recursionPrint");
        deepFirst(rootView, deep);
        long spend2 = System.currentTimeMillis() - now2;
        Trace.endSection();
        resultTex.append("使用深度优先实现共耗时: " + spend2 + " ms  \n");
        resultTex.append("--------------------------------------- \n");
    }

    // 使用深度优先实现
    private void deepFirst(View rootView, int deep) {
        deepFirstLinkedList.push(rootView);
        while (!deepFirstLinkedList.isEmpty()) {
            View currentView = deepFirstLinkedList.pop();
            printView(currentView, deep);

            if (currentView instanceof ViewGroup) {
                int childCount = ((ViewGroup) currentView).getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childView = ((ViewGroup) currentView).getChildAt(i);
                    deepFirstLinkedList.push(childView);
                }
                deep++;
            }
        }
    }

    // 使用广度优先实现
    private void breadthFirst(View rootView, int deep) {
        breadthFirstLinkedList.push(rootView);

        while (!breadthFirstLinkedList.isEmpty()) {
            View currentView = breadthFirstLinkedList.poll();
            printView(currentView, deep);

            if (currentView instanceof ViewGroup) {
                int childCount = ((ViewGroup) currentView).getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childView = ((ViewGroup) currentView).getChildAt(i);
                    breadthFirstLinkedList.addLast(childView);
                }
                deep++;
            }
        }
    }

    private void recursionPrint(View rootView, int deep) {
        printView(rootView, deep);
        if (rootView instanceof ViewGroup) {
            deep++;
            int childCount = ((ViewGroup) rootView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = ((ViewGroup) rootView).getChildAt(i);
                recursionPrint(childView, deep);
            }
        }
    }

    private void printView(View rootView, int deep) {
        Log.v(TAG, "ViewName is " + rootView.getClass().getSimpleName());
        if (rootView instanceof TextView) {
            if (rootView.getId() == R.id.textView) {
                resultTex.append(getStringByDeep(deep) + "Result" + "  \n");
            } else {
                resultTex.append(getStringByDeep(deep) + ((TextView) rootView).getText() + "  \n");
            }
        } else {
            resultTex.append(getStringByDeep(deep) + rootView.getClass().getSimpleName() + "  \n");
        }
    }

    private String getStringByDeep(int deep) {
        StringBuilder result = new StringBuilder("-");
        for (int i = 0; i < deep; i++) {
            result.append(" - ");
        }
        return result.toString();
    }
}
