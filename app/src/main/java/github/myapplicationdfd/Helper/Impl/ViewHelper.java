package github.myapplicationdfd.Helper.Impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import github.myapplicationdfd.Helper.IViewHelper;

/**
 * Author root
 * Date: 2016/8/26.
 */
public class ViewHelper implements IViewHelper{
    private  View view;
    private ViewGroup.LayoutParams params;
    private ViewGroup parentView;
    private  int  viewIndex;
    private View currentView;

    public ViewHelper(View view){
        this.view=view;
    }

    public  void init(){
        params = view.getLayoutParams();
        if(view.getParent()!=null){
            parentView = (ViewGroup)view.getParent();
        }else{
            parentView= (ViewGroup) view.getRootView().findViewById(android.R.id.content);
        }
        int count = parentView.getChildCount();
        for (int i=0;i<count;i++){
            View childAt = parentView.getChildAt(i);
            if(childAt==view){
                viewIndex=i;
                break;
            }
        }
        currentView = view;
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public void showLayout(View view) {
        if(parentView==null){
            init();
        }
        this.currentView=view;
        if(parentView.getChildAt(viewIndex)!=view){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
            parentView.removeViewAt(viewIndex);
            parentView.addView(view,viewIndex,params);
        }

    }

    @Override
    public View getCurrentView() {
        return currentView;
    }

    @Override
    public View inflate(int id) {
        return LayoutInflater.from(getContext()).inflate(id,null);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void restoreView() {
        showLayout(view);
    }
}
