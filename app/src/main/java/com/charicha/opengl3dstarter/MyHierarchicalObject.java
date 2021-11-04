package com.charicha.opengl3dstarter;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/16/2018.
 */

public class MyHierarchicalObject {

    public float x, y, z;
    public float scalex = 1, scaley = 1, scalez = 1;
    public float rotation, parentRotation;
    public boolean hasParent;
    public List<MyHierarchicalObject> allChildrens = new ArrayList<>();

    public MyVertices3 mesh;

    public MyHierarchicalObject(MyVertices3 mesh, boolean hasParent){
        this.mesh = mesh;
        this.hasParent = hasParent;
    }

    public void update(float deltaTime){
        rotation += 45 * deltaTime;
        parentRotation += 20 * deltaTime;

        for(int i = 0; i < allChildrens.size(); i++){
            allChildrens.get(i).update(deltaTime);
        }
    }

    public void render(GL10 gl){
        gl.glPushMatrix();
        if(hasParent)
            gl.glRotatef(parentRotation, 0, 1, 0);
        gl.glTranslatef(x, y, z);
        gl.glPushMatrix();
        gl.glRotatef(rotation, 0, 1, 0);
        gl.glScalef(scalex, scaley, scalez);
        mesh.bind();
        mesh.draw(GL10.GL_TRIANGLES, 0, mesh.numIndices());
        mesh.unbind();
        gl.glPopMatrix();

        for(int i = 0; i < allChildrens.size(); i++){
            allChildrens.get(i).render(gl);
        }
        gl.glPopMatrix();
    }

}
