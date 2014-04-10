package com.andrewq.planets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.os.SystemClock;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VenusRenderer implements Renderer {
    private final String quadVS =
            "precision mediump float;\n" +
                    "attribute vec4 vPosition;\n" +
                    "uniform vec4 uRatio;\n" +
                    "varying vec4 Position;\n" +

                    "void main() {\n" +
                    "	gl_Position = vPosition * uRatio;\n" +
                    "	Position = vPosition;\n" +
                    "}\n";

    private final String quadFS =
            "precision mediump float;\n" +
                    "uniform sampler2D uTexture0;\n" +
                    "uniform sampler2D uTexture1;\n" +
                    "uniform sampler2D uTexture2;\n" +
                    "uniform vec3 uRotate;\n" +
                    "varying vec4 Position;\n" +

                    "void main() {\n" +
                    "	float sx = Position.x;\n" +
                    "	float sy = -Position.y;\n" +
                    "	float z2 = 1.0 - sx * sx - sy * sy;\n" +

                    "	if (z2 > 0.0) {\n" +
                    "		float sz = sqrt(z2);\n" +
                    "		float tx = (1.0 + sx) * 0.5;\n" +
                    "		float y = (sz * uRotate.y - sy * uRotate.z);\n" +
                    "		float z = (sy * uRotate.y + sz * uRotate.z);\n" +
                    "		vec2 vCoord;\n" +

                    "		if (abs(z) > abs(y)) {\n" +
                    "			vec4 vTex = texture2D(uTexture1, vec2(tx, (1.0 - y) * 0.5));\n" +
                    "			vec4 vOff = floor(vTex * 255.0 + 0.5);\n" +
                    "			vCoord = vec2(\n" +
                    "				(vOff.y * 256.0 + vOff.x) / 4095.0,\n" +
                    "				(vOff.w * 256.0 + vOff.z) / 4095.0);\n" +
                    "			if (z < 0.0) { vCoord.x = 1.0 - vCoord.x; }\n" +
                    "		}\n" +
                    "		else {\n" +
                    "			vec4 vTex = texture2D(uTexture2, vec2(tx, (1.0 + z) * 0.5));\n" +
                    "			vec4 vOff = floor(vTex * 255.0 + 0.5);\n" +
                    "			vCoord = vec2(\n" +
                    "				(vOff.y * 256.0 + vOff.x) / 4095.0,\n" +
                    "				(vOff.w * 256.0 + vOff.z) / 4095.0);\n" +
                    "			if (y < 0.0) { vCoord.y = 1.0 - vCoord.y; }\n" +
                    "		}\n" +

                    "		vCoord.x += uRotate.x;\n" +

                    "		vec3 vCol = texture2D(uTexture0, vCoord).rgb;\n" +
                    "   	gl_FragColor = vec4(vCol * sz, 1.0);\n" +
                    "	} else {\n" +
                    "   	gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n" +
                    "	}\n" +
                    "}\n";
    private final int[] genbuf = new int[1];
    private final Context mContext;
    public float fps = 0;
    public float rotateAngle = 0;
    public float tiltAngle = 0;
    public int screenWidth = 0;
    public int screenHeight = 0;
    public float scaleFactor = 1;
    public double rotateSpeed = -0.125f;
    public double tiltSpeed = 0;
    float ratioX, ratioY;
    private int quadProgram;
    private int qvPosition;
    private int quRatio;
    private int quTexture0;
    private int quTexture1;
    private int quTexture2;
    private int quRotate;
    private int quadVB;
    private int planetTex;
    private int offsetTex1;
    private int offsetTex2;
    private long start_frame;
    private long frames_drawn;
    private long prevTime = -1;

    public VenusRenderer(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        long curTime = SystemClock.uptimeMillis();

        if (curTime > start_frame + 1000) {
            fps = frames_drawn * 1000.0f / (curTime - start_frame);
            start_frame = curTime;
            frames_drawn = 0;
        }

        if (prevTime < 0) prevTime = curTime;
        double delta = (curTime - prevTime) / 1000.0f;
        prevTime = curTime;

        rotateAngle += delta * rotateSpeed;
        rotateAngle -= Math.floor(rotateAngle);

        tiltAngle += delta * tiltSpeed;
        while (tiltAngle > 2) tiltAngle -= 2;
        while (tiltAngle < 0) tiltAngle += 2;

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, planetTex);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, offsetTex1);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, offsetTex2);

        GLES20.glUseProgram(quadProgram);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, quadVB);
        GLES20.glEnableVertexAttribArray(qvPosition);
        GLES20.glVertexAttribPointer(qvPosition, 2, GLES20.GL_FLOAT, false, 8, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glUniform1i(quTexture0, 0);
        GLES20.glUniform1i(quTexture1, 1);
        GLES20.glUniform1i(quTexture2, 2);

        double ta = tiltAngle * Math.PI;
        GLES20.glUniform3f(quRotate, rotateAngle, (float) Math.sin(ta), (float) Math.cos(ta));

        float minScale = 0.5f, maxScale = 2.0f / (ratioX < ratioY ? ratioX : ratioY);
        if (scaleFactor < minScale) scaleFactor = minScale;
        if (scaleFactor > maxScale) scaleFactor = maxScale;

        GLES20.glUniform4f(quRatio, ratioX * scaleFactor, ratioY * scaleFactor, 1, 1);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(qvPosition);
        GLES20.glDisable(GLES20.GL_BLEND);

        frames_drawn++;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        screenWidth = width;
        screenHeight = height;

        if (width < height) {
            ratioX = 1;
            ratioY = width / (float) height;
        } else {
            ratioX = height / (float) width;
            ratioY = 1;
        }

        initPlanet();

        start_frame = SystemClock.uptimeMillis();
        frames_drawn = 0;
        fps = 0;
    }

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        Log.e("Shader", GLES20.glGetShaderInfoLog(shader));
        return shader;
    }

    private int Compile(String vs, String fs) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vs);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fs);

        int prog = GLES20.glCreateProgram();
        GLES20.glAttachShader(prog, vertexShader);
        GLES20.glAttachShader(prog, fragmentShader);
        GLES20.glLinkProgram(prog);

        return prog;
    }

    int loadTexture(final Context context, final int resourceId) {
        GLES20.glGenTextures(1, genbuf, 0);
        int tex = genbuf[0];

        if (tex != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
        }

        return tex;
    }

    int arrayTexture(int texSize, int[] pixels) {
        GLES20.glGenTextures(1, genbuf, 0);
        int tex = genbuf[0];
        if (tex != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, texSize, texSize, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, IntBuffer.wrap(pixels));
        }
        return tex;
    }

    private void initPlanet() {
        int texSize = 1024;
        double r = texSize * 0.5;
        int[] pixels = new int[texSize * texSize];

        for (int row = 0, idx = 0; row < texSize; row++) {
            double y = (r - row) / r;
            double sin_theta = Math.sqrt(1 - y * y);
            double theta = Math.acos(y);
            long v = Math.round(4095 * theta / Math.PI);

            for (int col = 0; col < texSize; col++) {
                double x = (r - col) / r;
                long u = 0;

                if (x >= -sin_theta && x <= sin_theta) {
                    double z = Math.sqrt(1 - y * y - x * x);
                    double phi = Math.atan2(z, x);
                    u = Math.round(4095 * phi / (2 * Math.PI));
                }

                pixels[idx++] = (int) ((v << 16) + u);
            }
        }

        offsetTex1 = arrayTexture(texSize, pixels);

        for (int row = 0, idx = 0; row < texSize; row++) {
            double z = (row - r) / r;
            double x_limit = Math.sqrt(1 - z * z);

            for (int col = 0; col < texSize; col++) {
                double x = (r - col) / r;
                long u = 0, v = 0;

                if (x >= -x_limit && x <= x_limit) {
                    double y = Math.sqrt(1 - z * z - x * x);
                    double phi = Math.atan2(z, x);
                    double theta = Math.acos(y);

                    if (phi < 0) phi += (2 * Math.PI);
                    u = Math.round(4095 * phi / (2 * Math.PI));
                    v = Math.round(4095 * theta / Math.PI);
                }

                pixels[idx++] = (int) ((v << 16) + u);
            }
        }

        offsetTex2 = arrayTexture(texSize, pixels);

        planetTex = loadTexture(mContext, R.drawable.venus_map);
    }

    private int createBuffer(float[] buffer) {
        FloatBuffer floatBuf = ByteBuffer.allocateDirect(buffer.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatBuf.put(buffer);
        floatBuf.position(0);

        GLES20.glGenBuffers(1, genbuf, 0);
        int glBuf = genbuf[0];
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glBuf);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, buffer.length * 4, floatBuf, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        return glBuf;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.25f, 0.25f, 0.25f, 1);

        quadProgram = Compile(quadVS, quadFS);
        qvPosition = GLES20.glGetAttribLocation(quadProgram, "vPosition");
        quRatio = GLES20.glGetUniformLocation(quadProgram, "uRatio");
        quTexture0 = GLES20.glGetUniformLocation(quadProgram, "uTexture0");
        quTexture1 = GLES20.glGetUniformLocation(quadProgram, "uTexture1");
        quTexture2 = GLES20.glGetUniformLocation(quadProgram, "uTexture2");
        quRotate = GLES20.glGetUniformLocation(quadProgram, "uRotate");

        final float quad[] = {
                -1, 1,
                -1, -1,
                1, 1,
                1, -1,
        };

        quadVB = createBuffer(quad);
    }
}
