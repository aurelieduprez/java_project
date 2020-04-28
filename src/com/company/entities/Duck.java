package com.company.entities;

import com.company.pond.PondManager;
import com.company.tools.GraphicUtils;
import com.company.tools.MathUtils;
import com.company.tools.Vector2D;

import java.awt.*;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class Duck implements IPondEntity {
    private double x;
    private double y;
    private int width;
    private int height;
    private double rotation;
    private boolean justChangedRot;
    private long lastChangedRot;


    private int level;
    private BufferedImage image;

    public Duck(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = this.height = 50;
        this.level = 1;
        this.rotation = 45;
        this.image = PondManager.getSingleton().getDuckImg1();
    }

    @Override
    public void update() {
        this.forward();

        //
    }

    public void forward() {
        Double translated = MathUtils.translate2D(this.x, this.y, this.rotation, getMoveSpeed(this.level));
        this.x = translated.x;
        this.y = translated.y;

    }

    public void levelUp() {
        if (level >= 10) return;

        level += 1;

        this.width = this.height += 5;

        if (level == 10) { // Becomes chief
            this.image = PondManager.getSingleton().getDuckImg2();
        }
        System.out.println(this.level);
    }

    int getMoveSpeed(int level) {
        if (level < 10){
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public void render(Graphics2D g) {
        PondManager pm = PondManager.getSingleton();
        BufferedImage duckImg = null;
        Color color;
        if (level <= 3) { // baby duck
            color = new Color(255,255,0);
            duckImg = pm.getDuckImg1();
        }
        else if (level < 10) { // pretty duck
            color = new Color(255,255,0);
            duckImg = pm.getDuckImg1();
        }
        else { // king of the ducks
            color = new Color(220,220,220);
            duckImg = pm.getDuckImg2();
        }

        g.drawImage(GraphicUtils.rotateImageByDegrees(duckImg, rotation), (int)this.x, (int)this.y, width, height, null);
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(this.x, this.y);
    }

    @Override
    public Vector2D getSize() {
        return new Vector2D(this.width, this.height);
    }

    public void setRotation(double rot) {
        setRotation(rot, false);
    }

    public void setRotation(double rot, boolean spamProtect) {
        long nowTimeMilli = System.currentTimeMillis();
        if (spamProtect && nowTimeMilli - this.lastChangedRot < 100) return;

        if (rot < 0) rot += 360;
        this.rotation = rot;
        lastChangedRot = nowTimeMilli;
    }

    public double getRotation() {
        return this.rotation;

    }
}

