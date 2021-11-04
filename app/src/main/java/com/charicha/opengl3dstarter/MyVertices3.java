package com.charicha.opengl3dstarter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/16/2018.
 */

public class MyVertices3 {

    GL10 gl;
    final int VERTEX_SIZE;

    boolean hasTexture;
    boolean hasColor;

    int[] tmpBuffer;
    IntBuffer intBuffer;
    ShortBuffer shortBuffer;

    public MyVertices3(GL10 gl, int maxVertices, int maxIndices, boolean hasTexture, boolean hasColor){
        this.gl = gl;
        VERTEX_SIZE = (3 + (hasTexture ? 2 : 0) + (hasColor ? 4 : 0)) * 4;

        this.hasTexture = hasTexture;
        this.hasColor = hasColor;

        tmpBuffer = new int[maxVertices * (VERTEX_SIZE/4)];

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(VERTEX_SIZE * maxVertices);
        byteBuffer.order(ByteOrder.nativeOrder());
        intBuffer = byteBuffer.asIntBuffer();

        if(maxIndices > 0){
            byteBuffer = ByteBuffer.allocateDirect(maxIndices * 2); //Short.SIZE/8
            byteBuffer.order(ByteOrder.nativeOrder());
            shortBuffer = byteBuffer.asShortBuffer();
        } else {
            shortBuffer = null;
        }
    }

    public void addVertices(float[] verticesInfo, int offset, int length){
        intBuffer.clear();
        for(int i = offset, j = 0; i < offset + length; i++, j++){
            tmpBuffer[j] = Float.floatToRawIntBits(verticesInfo[i]);
        }
        intBuffer.put(tmpBuffer, offset, length);
        intBuffer.flip();
    }

    public void addIndices(short[] indices, int offset, int length){
        shortBuffer.clear();
        shortBuffer.put(indices, offset, length);
        shortBuffer.flip();
    }

    public void bind(){
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        intBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, VERTEX_SIZE, intBuffer);

        if(hasTexture){
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            intBuffer.position(3);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, intBuffer);
        }

        if(hasColor){
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            intBuffer.position((hasTexture ? 5 : 3));
            gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, intBuffer);
        }
    }

    public void draw(int primitiveType, int offset, int numVertices){
        if(shortBuffer != null){
            shortBuffer.position(0);
            gl.glDrawElements(GL10.GL_TRIANGLES, numVertices, GL10.GL_UNSIGNED_SHORT, shortBuffer);
        } else {
            gl.glDrawArrays(GL10.GL_TRIANGLES, offset, numVertices);
        }
    }

    public void unbind(){
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        if(hasTexture)
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        if(hasColor)
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }

    public int numIndices(){
        return shortBuffer.limit();
    }

}
