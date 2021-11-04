package com.charicha.opengl3dstarter;

import com.charicha.Game;
import com.charicha.Input;
import com.charicha.Screen;
import com.charicha.game.MyVertices;
import com.charicha.game.Texture;
import com.charicha.impl.GLGame;
import com.charicha.impl.GLScreen;
import com.charicha.math.Rectangle;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/20/2018.
 */

public class GoingCrazy extends GLGame {

    @Override
    public Screen getStartScreen(){
        return new GoingCrazyScreen(this);
    }

    class GoingCrazyScreen extends GLScreen {

        GL10 gl;

        Texture buttonTexture;
        Rectangle[] allBounds;
        String[] allButtonText;
        MyVertices[] allButtonMeshes;

        MyVertices3 testCubeMesh;

        List<Input.TouchEvent> touchEvents;

        public GoingCrazyScreen(Game game) {
            super(game);
            gl = glGraphics.getGL();

            buttonTexture = new Texture(glGame, "button.png");
            allBounds = new Rectangle[10];
            for(int i = 0; i < 10; i++){

            }
            allButtonText = new String[10];
            allButtonMeshes = new MyVertices[10];

            testCubeMesh = createCube();


        }



        @Override
        public void update(float deltaTime) {

        }

        @Override
        public void render(float deltaTime) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        }

        @Override
        public void resume() {
            gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
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

        public MyVertices createTexturedQuad(){
            MyVertices quad = new MyVertices(glGraphics, 4, 6, true, false);
            quad.addVertices(new float[]{
                    -0.5f, -0.5f, 0, 1,
                     0.5f, -0.5f, 1, 1,
                     0.5f,  0.5f, 1, 0,
                    -0.5f,  0.5f, 0, 0
            }, 0, 16);
            quad.addIndices(new short[]{
                    0, 1, 2, 2, 3, 0
            }, 0, 6);
            return quad;
        }
    }

}
