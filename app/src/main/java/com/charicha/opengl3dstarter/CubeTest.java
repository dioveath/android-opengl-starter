package com.charicha.opengl3dstarter;

import android.opengl.GLU;

import com.charicha.Game;
import com.charicha.Input;
import com.charicha.Screen;
import com.charicha.game.Texture;
import com.charicha.impl.GLGame;
import com.charicha.impl.GLScreen;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/16/2018.
 */

public class CubeTest extends GLGame {

    @Override
    public Screen getStartScreen(){
        return new CubeScreen(this);
    }

    class CubeScreen extends GLScreen {

        GL10 gl;

        Texture zeddexTexture;
        MyVertices3 cubeVertices;

        float anglex = 0;
        float angley = 0;

        List<Input.TouchEvent> touchEvents;
        VirtualStick mStick;

        public CubeScreen(Game game) {
            super(game);

            gl = glGraphics.getGL();

            zeddexTexture = new Texture(glGame, "crate.png");

            cubeVertices = new MyVertices3(gl, 4 * 6, 6 * 6, true, false);
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

            mStick = new VirtualStick();
        }

        @Override
        public void update(float deltaTime) {
            touchEvents = glGame.getInput().getTouchEvents();
            for(int i = 0; i < touchEvents.size(); i++){
                Input.TouchEvent touchEvent = touchEvents.get(i);
                if(touchEvent.type == Input.TouchEvent.TOUCH_DOWN){
                    if(mStick.pointerId == -1){
                        mStick.cx = mStick.x = touchEvent.x;
                        mStick.cy = mStick.y = touchEvent.y;
                        mStick.pointerId = touchEvent.pointerId;
                    }
                }
                if(touchEvent.type == Input.TouchEvent.TOUCH_DRAGGED){
                    if(mStick.pointerId == touchEvent.pointerId){
                        mStick.x = touchEvent.x;
                        mStick.y = touchEvent.y;
                    }
                }
                if(touchEvent.type == Input.TouchEvent.TOUCH_UP){
                    if(mStick.pointerId == touchEvent.pointerId){
                        mStick.x = touchEvent.x;
                        mStick.y = touchEvent.y;
                        mStick.pointerId = -1;
                    }
                }
            }

            if(mStick.pointerId != -1){
                float dx = mStick.x - mStick.cx;
                float dy = mStick.y - mStick.cy;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);

                if(dist > 10){
                    if (Math.abs(dx) > Math.abs(dy)) { //Horizontal
                        if(dx > 0){  //right
                            angley += dist/360;
                        } else { //left
                            angley += -dist/360;
                        }
                    } else { //Vertical
                        if(dy > 0){ //Down
                            anglex += dist/360;
                        } else { //Up
                            anglex += -dist/360;
                        }
                    }
                }

            }

        }

        @Override
        public void render(float deltaTime) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl.glEnable(GL10.GL_DEPTH_TEST);

            gl.glColor4f(1f, 1f, 0.4f, 1);
//            gl.glEnable(GL10.GL_BLEND);
//            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

            zeddexTexture.bind();
            cubeVertices.bind();
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, 0, -3);
            gl.glRotatef(anglex, 1, 0, 0);
            gl.glRotatef(angley, 0, 1, 0);
            cubeVertices.draw(GL10.GL_TRIANGLES, 0, cubeVertices.numIndices());
            cubeVertices.unbind();
            zeddexTexture.unbind();


        }

        @Override
        public void resume() {
            gl.glClearColor(0.9f, 0.9f, 0.9f, 1f);
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
//            gl.glOrthof(-2f, 2f, -2f, -2f, 2f, 2f);
            GLU.gluPerspective(gl, 67, glGraphics.getWidth()/(float)glGraphics.getHeight(), 0.1f, 10);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            zeddexTexture.reload();
        }

        @Override
        public void pause() {
        }

        @Override
        public void dispose() {

        }
    }

    class VirtualStick {
        float cx;
        float cy;
        float x;
        float y;
        int pointerId = -1;
    }

}
