package com.charicha.opengl3dstarter;

import android.opengl.GLU;

import com.charicha.Game;
import com.charicha.Screen;
import com.charicha.game.Texture;
import com.charicha.impl.GLGame;
import com.charicha.impl.GLScreen;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/16/2018.
 */

public class HierarchicalTest extends GLGame {

    @Override
    public Screen getStartScreen() {
        return new HierarchicalScreen(this);
    }

    class HierarchicalScreen extends GLScreen {

        GL10 gl;

        MyHierarchicalObject sun;
        Texture crateTexture;

        public HierarchicalScreen(Game game) {
            super(game);
            gl = glGraphics.getGL();

            MyVertices3 mesh = createCube();

            sun = new MyHierarchicalObject(mesh, false);
            sun.z = -10;

            MyHierarchicalObject planet = new MyHierarchicalObject(mesh, true);
            planet.x = 3;
            planet.y = 2;
            planet.scalex = planet.scaley = planet.scalez = 0.2f;
            sun.allChildrens.add(planet);

            MyHierarchicalObject moon = new MyHierarchicalObject(mesh, true);
            moon.x = 1;
            planet.y = 1;
            moon.scalex = moon.scaley = moon.scalez = 0.1f;
            planet.allChildrens.add(moon);

            crateTexture = new Texture(glGame, "crate.png");
        }

        @Override
        public void update(float deltaTime) {
            sun.update(deltaTime);
        }

        @Override
        public void render(float deltaTime) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            GLU.gluPerspective(gl, 67, glGraphics.getWidth()/(float)glGraphics.getHeight(), 0.1f, 30f);

            crateTexture.bind();
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, -2, 0);
            sun.render(gl);
            crateTexture.unbind();


        }

        @Override
        public void resume() {
            crateTexture.reload();
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }

        @Override
        public void pause() {

        }

        @Override
        public void dispose() {

        }

        public MyVertices3 createCube(){
            MyVertices3 cubeVertices = new MyVertices3(gl, 4 * 6, 6 * 6, true, false);
            float[] cubeVerticesInfo = {
                    //forward
                    -0.5f, -0.5f,  0.5f, 0, 1,
                    0.5f, -0.5f,  0.5f, 1, 1,
                    0.5f,  0.5f,  0.5f, 1, 0,
                    -0.5f,  0.5f,  0.5f, 0, 0,
                    //backward
                    -0.5f, -0.5f, -0.5f, 0, 1,
                    0.5f, -0.5f, -0.5f, 1, 1,
                    0.5f,  0.5f, -0.5f, 1, 0,
                    -0.5f,  0.5f, -0.5f, 0, 0,
                    //left
                    -0.5f, -0.5f, -0.5f, 0, 1,
                    -0.5f, -0.5f,  0.5f, 1, 1,
                    -0.5f,  0.5f,  0.5f, 1, 0,
                    -0.5f,  0.5f, -0.5f, 0, 0,
                    //right
                    0.5f, -0.5f, -0.5f, 0, 1,
                    0.5f, -0.5f,  0.5f, 1, 1,
                    0.5f,  0.5f,  0.5f, 1, 0,
                    0.5f,  0.5f, -0.5f, 0, 0,
                    //top
                    -0.5f,  0.5f, -0.5f, 0, 1,
                    -0.5f,  0.5f,  0.5f, 1, 1,
                    0.5f,  0.5f,  0.5f, 1, 0,
                    0.5f,  0.5f, -0.5f, 0, 0,
                    //bottom
                    -0.5f, -0.5f, -0.5f, 0, 1,
                    -0.5f, -0.5f,  0.5f, 1, 1,
                    0.5f, -0.5f,  0.5f, 1, 0,
                    0.5f, -0.5f, -0.5f, 0, 0
            };
            cubeVertices.addVertices(cubeVerticesInfo, 0, cubeVerticesInfo.length);

            short[] cubeIndices = new short[] {
                    0,   1,  2, 2,   3,  0,
                    4,   5,  6, 6,   7,  4,
                    8,   9, 10, 10, 11,  8,
                    12, 13, 14, 14, 15, 12,
                    16, 17, 18, 18, 19, 16,
                    20, 21, 22, 22, 23, 20

            };

            cubeVertices.addIndices(cubeIndices, 0, cubeIndices.length);
            return cubeVertices;
        }
    }
}
