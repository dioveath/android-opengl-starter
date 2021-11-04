package com.charicha.opengl3dstarter;

import android.opengl.GLU;

import com.charicha.Game;
import com.charicha.Screen;
import com.charicha.game.MyCamera2D;
import com.charicha.impl.GLGame;
import com.charicha.impl.GLScreen;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/16/2018.
 */

public class Vertices3Test extends GLGame {

    @Override
    public Screen getStartScreen() {
        return new Vertices3Screen(this);
    }

    class Vertices3Screen extends GLScreen{

        GL10 gl;

        MyVertices3 vertices;

        public Vertices3Screen(Game game) {
            super(game);
            gl = glGraphics.getGL();

            vertices = new MyVertices3(gl, 6, 0, false, true);
            vertices.addVertices(new float[]{
                    -0.5f, -0.5f, -3, 1, 0, 0, 1,
                    0.5f, -0.5f, -3, 1, 0, 0, 1,
                    0.0f,  0.5f, -3, 1, 0, 0, 1,

                    0.0f,  -0.5f, -5, 0, 1, 0, 1,
                    1.0f,  -0.5f, -5, 0, 1, 0, 1,
                    0.5f,  0.5f, -5, 0, 1, 0, 1
            }, 0, 7 * 6);

//            vertices.addVertices(new float[]{
//                    0f,   0f, -3f, 1, 0, 0, 1,
//                    1f,   0f, -3f, 0, 1, 0, 1,
//                    0.5f, 1f, -3f, 0, 0, 1, 1,
//
//                    0.5f,   0f,     -2f,       1,    0.9f, 0,    1,
//                    1f, 1f,     -3f,    0.9f,    0.5f, 0,    1,
//                    2f,   0f,     -5f,       1,    0.0f, 0.5f, 1
//            }, 0, 7 * 6);

        }

        @Override
        public void update(float deltaTime) {

        }

        float x = 0;

        @Override
        public void render(float deltaTime) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            gl.glEnable(GL10.GL_DEPTH_TEST);

            vertices.bind();
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
//            x += 0.2;
//            gl.glTranslatef(-0.5f, -0.5f, 0);
//            gl.glRotatef(x, 0, 1, 0);
            vertices.draw(GL10.GL_TRIANGLES, 0, 3);
            vertices.draw(GL10.GL_TRIANGLES, 3, 3);
            vertices.unbind();

            gl.glDisable(GL10.GL_DEPTH_TEST);

            //Other UI Rendering
        }

        @Override
        public void resume() {
            gl.glClearColor(0.4f, 0.2f, 0.6f, 1f);
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
//            gl.glOrthof(-2f, 2f, -2f, 2f, 0f, 10f);
            GLU.gluPerspective(gl, 67, glGraphics.getWidth()/(float)glGraphics.getHeight(), 0.1f, 10f);
        }

        @Override
        public void pause() {

        }

        @Override
        public void dispose() {

        }
    }
}
