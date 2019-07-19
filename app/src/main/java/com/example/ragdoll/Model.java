package com.example.ragdoll;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {
    public transient ArrayList<IView> observers;
    public transient Context context;

}
