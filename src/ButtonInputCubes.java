import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;


public class ButtonInputCubes {
    static TheCube d = new TheCube();


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Button Input Cube");
            frame.setSize(1000, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel controlPanel = new JPanel();
            JButton firstViewButton = new JButton("First View");
            JButton secondViewButton = new JButton("Second View");
            JButton thirdViewButton = new JButton("Third View");
            JButton spinButton = new JButton("Spin");

            controlPanel.add(firstViewButton);
            controlPanel.add(secondViewButton);
            controlPanel.add(thirdViewButton);
            controlPanel.add(spinButton);

            JPanel movePanel = new JPanel();
            JButton U = new JButton("U");
            JButton UPrime = new JButton("U'");
            JButton D = new JButton("D");
            JButton DPrime = new JButton("D'");
            JButton R = new JButton("R");
            JButton RPrime = new JButton("R'");
            JButton L = new JButton("L");
            JButton LPrime = new JButton("L'");
            JButton F = new JButton("F");
            JButton FPrime = new JButton("F'");
            JButton B = new JButton("B");
            JButton BPrime = new JButton("B'");

            movePanel.add(U);
            movePanel.add(D);
            movePanel.add(R);
            movePanel.add(L);
            movePanel.add(F);
            movePanel.add(B);
            movePanel.add(UPrime);
            movePanel.add(DPrime);
            movePanel.add(RPrime);
            movePanel.add(LPrime);
            movePanel.add(FPrime);
            movePanel.add(BPrime);

            GLProfile profile = GLProfile.getDefault();
            GLCapabilities capabilities = new GLCapabilities(profile);
            GLCanvas canvas = new GLCanvas(capabilities);
            ButtonInputCubesRenderer renderer = new ButtonInputCubesRenderer();
            canvas.addGLEventListener(renderer);

            frame.getContentPane().add(canvas, BorderLayout.CENTER);
            frame.getContentPane().add(movePanel, BorderLayout.NORTH);
            frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

            Animator animator = new Animator(canvas);
            animator.start();

            U.addActionListener(e -> renderer.U());
            D.addActionListener(e -> renderer.D());
            R.addActionListener(e -> renderer.R());
            L.addActionListener(e -> renderer.L());
            F.addActionListener(e -> renderer.F());
            B.addActionListener(e -> renderer.B());
            UPrime.addActionListener(e -> renderer.UPrime());
            DPrime.addActionListener(e -> renderer.DPrime());
            RPrime.addActionListener(e -> renderer.RPrime());
            LPrime.addActionListener(e -> renderer.LPrime());
            FPrime.addActionListener(e -> renderer.FPrime());
            BPrime.addActionListener(e -> renderer.BPrime());

            firstViewButton.addActionListener(e -> renderer.firstView());
            secondViewButton.addActionListener(e -> renderer.secondView());
            thirdViewButton.addActionListener(e -> renderer.thirdView());
            spinButton.addActionListener(e -> renderer.spin());

            frame.setVisible(true);
        });
    }

    static class ButtonInputCubesRenderer implements GLEventListener {

        private float rotation = 0.09f;
        boolean animated = true;

        public void U(){
            d.applyMove("U");
        }
        
        public void D(){
            d.applyMove("D");
        }
        public void R(){
            d.applyMove("R");
        }
        public void L(){
            d.applyMove("L");
        }
        public void F(){
            d.applyMove("F");
        }
        public void B(){
            d.applyMove("B");
        }
        public void UPrime(){
            d.applyMove("U'");
        }
        public void DPrime(){
            d.applyMove("D'");
        }
        public void RPrime(){
            d.applyMove("R'");
        }
        public void LPrime(){
            d.applyMove("L'");
        }
        public void FPrime(){
            d.applyMove("F'");
        }
        public void BPrime(){
            d.applyMove("B'");
        }

        public void firstView() {
            animated = false;
            rotation = -190.0f;
        }

        public void secondView() {
            animated = false;
            rotation = -75.0f;
        }

        public void thirdView() {
            animated = false;
            rotation = 55.0f;
        }

        public void spin() {
            animated = true;
            rotation = 0.09f;
        }

        @Override
        public void init(GLAutoDrawable drawable) {
            GL2 gl = drawable.getGL().getGL2();
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            gl.glEnable(GL2.GL_DEPTH_TEST);
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {}

        @Override
        public void display(GLAutoDrawable drawable) {
            GL2 gl = drawable.getGL().getGL2();
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

            gl.glLoadIdentity();
            gl.glTranslatef(0.0f, 0.0f, -10.0f);

            long time = animated ? System.currentTimeMillis() % 4000 : 1;
            
            float angle = rotation * ((int) time);
            gl.glRotatef(angle, 1.0f, 1.0f, 1.0f);

            drawCubes(gl, d.cube);
        }

        private void drawCubes(GL2 gl, char[][][] cube) {

            for(int i = -1; i<=1; i++){
                for(int j = -1; j<=1; j++){
                    gl.glPushMatrix();
                    gl.glTranslatef(1.05f * -0.425f, i * 0.425f, j * 0.425f);
                    Color c1 = new Color(cube[1][i+1][j+1]);
                    drawRightFace(gl, c1.r, c1.g, c1.b); //Blue
                    gl.glPopMatrix();
                                        
                    gl.glPushMatrix();
                    gl.glTranslatef(1.05f * 0.425f, i * 0.425f, j * 0.425f);
                    Color c2 = new Color(cube[3][i+1][j+1]);
                    drawLeftFace(gl, c2.r, c2.g, c2.b); // Green
                    gl.glPopMatrix();
                }
            }

            for(int i = -1; i<=1; i++){
                for(int j = -1; j<=1; j++){
                    gl.glPushMatrix();
                    gl.glTranslatef( i * 0.425f, j * 0.425f, 1.05f * -0.425f);
                    Color c1 = new Color(cube[5][i+1][j+1]);
                    drawBackFace(gl, c1.r, c1.g, c1.b); //Orange
                    gl.glPopMatrix();
                   
                    gl.glPushMatrix();
                    gl.glTranslatef(i * 0.425f, j * 0.425f, 1.05f * 0.425f);
                    Color c2 = new Color(cube[2][i+1][j+1]);
                    drawFrontFace(gl, c2.r, c2.g, c2.b); //Red
                    gl.glPopMatrix();
                }
            }

            for(int i = -1; i<=1; i++){
                for(int j = -1; j<=1; j++){
                    gl.glPushMatrix();
                    gl.glTranslatef( i * 0.425f, 1.05f * -0.425f, j * 0.425f);
                    Color c1 = new Color(cube[4][i+1][j+1]);
                    drawBottomFace(gl, c1.r, c1.g, c1.b); //Yellow
                    gl.glPopMatrix();
                   
                    gl.glPushMatrix();
                    gl.glTranslatef(i * 0.425f, 1.05f * 0.425f, j * 0.425f);
                    Color c2 = new Color(cube[0][i+1][j+1]);
                    drawTopFace(gl, c2.r, c2.g, c2.b); // White
                    gl.glPopMatrix();
                }
            }
        }

        private void drawFrontFace(GL2 gl, float r, float g, float b) {
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(r, g, b);
            gl.glVertex3f(-0.2f , -0.2f , 0.2f ); // Reduced size
            gl.glVertex3f(0.2f , -0.2f , 0.2f );
            gl.glVertex3f(0.2f , 0.2f , 0.2f );
            gl.glVertex3f(-0.2f , 0.2f , 0.2f );
            gl.glEnd();
        }
        private void drawBackFace(GL2 gl, float r, float g, float b) {
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(r, g, b);
            gl.glVertex3f(0.2f , -0.2f , -0.2f );
            gl.glVertex3f(-0.2f , -0.2f , -0.2f );
            gl.glVertex3f(-0.2f , 0.2f , -0.2f );
            gl.glVertex3f(0.2f , 0.2f , -0.2f );
            gl.glEnd();
        }
        private void drawTopFace(GL2 gl, float r, float g, float b) {
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(r, g, b);
            gl.glVertex3f(-0.2f , 0.2f , 0.2f );
            gl.glVertex3f(0.2f , 0.2f , 0.2f );
            gl.glVertex3f(0.2f , 0.2f , -0.2f );
            gl.glVertex3f(-0.2f , 0.2f , -0.2f );
            gl.glEnd();
        }
        private void drawBottomFace(GL2 gl, float r, float g, float b) {
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(r, g, b);
            gl.glVertex3f(-0.2f , -0.2f , -0.2f );
            gl.glVertex3f(0.2f , -0.2f , -0.2f );
            gl.glVertex3f(0.2f , -0.2f , 0.2f );
            gl.glVertex3f(-0.2f , -0.2f , 0.2f );
            gl.glEnd();
        }
        private void drawRightFace(GL2 gl, float r, float g, float b) {
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(r, g, b);
            gl.glVertex3f(-0.2f , -0.2f , -0.2f );
            gl.glVertex3f(-0.2f , -0.2f , 0.2f );
            gl.glVertex3f(-0.2f , 0.2f , 0.2f );
            gl.glVertex3f(-0.2f , 0.2f , -0.2f );
            gl.glEnd();
        }
        private void drawLeftFace(GL2 gl, float r, float g, float b) {
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(r, g, b);
            gl.glVertex3f(0.2f , -0.2f , 0.2f );
            gl.glVertex3f(0.2f , -0.2f , -0.2f );
            gl.glVertex3f(0.2f , 0.2f , -0.2f );
            gl.glVertex3f(0.2f , 0.2f , 0.2f );
            gl.glEnd();
        }

        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            GL2 gl = drawable.getGL().getGL2();
            if (height <= 0) {
                height = 1;
            }
            float h = (float) width / (float) height;
            gl.glViewport(0, 0, width, height);
            gl.glMatrixMode(GL2.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glFrustum(-h, h, -1.0, 1.0, 5.0, 20.0);
            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glLoadIdentity();
        }
    }
}